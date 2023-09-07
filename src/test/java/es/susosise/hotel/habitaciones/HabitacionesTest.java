package es.susosise.hotel.habitaciones;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;
import es.susosise.hotel.ManejoDePreferenciasYConfiguracion;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import java.io.IOException;
import java.sql.SQLException;


class HabitacionesTest {
	
	java.sql.Connection baseDeDatos;
	ManejoDeHabitaciones habitaciones;
	
	
	@BeforeEach
	void prepararEntorno() throws IOException, SQLException {
	    //PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesMocParaAgilizarLosTests();
	    //PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesEnArchivoJSON(PreferenciasGeneralesDeLaAplicacion.getCarpetaDeDatosParaPruebas());
	    baseDeDatos = ManejoDePreferenciasYConfiguracion.getServidorDeDatosParaPruebas();
	    PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesEnMariaDB(baseDeDatos);
	    ((PersistenciaDeHabitacionesEnMariaDB) persistencia).crearLaTabla();
	    
        habitaciones = new ManejoDeHabitaciones(persistencia);
	}
	@AfterEach
	void limpiarEntorno() {
	    //borrarRecursivo(PreferenciasGeneralesDeLaAplicacion.getCarpetaDeDatosParaPruebas().toFile());
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
	}
//    static void borrarRecursivo (File carpeta) {
//        if (carpeta.exists()) {
//            if (carpeta.isDirectory()) {
//                for (File contenido : carpeta.listFiles()) {
//                    borrarRecursivo(contenido);
//                }
//            }
//            carpeta.delete();
//        }
//    }
	

    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElNumeroDeHabitacion() throws IOException {
    	
    	Habitacion habitacionCreada = habitaciones.crearUnaNueva("101");
    	
    	Habitacion habitacionRecuperada = habitaciones.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElIdentificadorInterno() throws IOException {
    	
    	Habitacion habitacionCreada = habitaciones.crearUnaNueva("101");
    	
    	Habitacion habitacionRecuperada = habitaciones.get(habitacionCreada.getIdInterno());
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void alCrearUnaNuevaHabitacionSeAñadeALasExistentes() throws IOException {
    	
    	Habitacion habitacionCreada01 = habitaciones.crearUnaNueva("201");
    	Habitacion habitacionCreada02 = habitaciones.crearUnaNueva("202");
    	
    	Habitacion habitacionRecuperada01 = habitaciones.get("201");
    	Habitacion habitacionRecuperada02 = habitaciones.get("202");
    	
    	assertEquals(habitacionCreada01, habitacionRecuperada01);
    	assertEquals(habitacionCreada02, habitacionRecuperada02);
    }

    @Test
    void noSeCreanHabitacionesDuplicadas() throws IOException {
        habitaciones.crearUnaNueva("101");
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
		     new Executable() {
	            @Override
	            public void execute() throws Throwable {
	                habitaciones.crearUnaNueva("101");
	            }
	    	 }
    													 );
   	}
    
    @Test
    void noSeCreanHabitacionesSinNumero() throws IOException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    habitaciones.crearUnaNueva("");
                }
             }
                                                         );
    }
    
    
    @Test
    void recuperarTodasLasHabitaciones() throws IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("302");
        habitaciones.crearUnaNueva("303");
    	
    	java.util.List<Habitacion> listaDeHabitaciones = habitaciones.getTodas();
    	assertEquals(3, listaDeHabitaciones.size());
    }


    @Test
    void recuperarConFiltroDeNumeroDeHabitacion() throws IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("101");
        habitaciones.crearUnaNueva("302");
    	
    	java.util.List<Habitacion> listaDeHabitaciones = habitaciones.getAquellasCuyoNumeroComiencePor("3");
    	assertEquals(2, listaDeHabitaciones.size());
    }
    
    @Test
    void inactivarUnaHabitacion() throws IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("101");
        habitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = habitaciones.get("101");
    	assertEquals(true, habitacion.estaActiva());

    	habitaciones.desactivarHabitacion(habitacion.getIdInterno());
    	Habitacion habitacionDespues = habitaciones.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());
    }
    
    @Test
    void reactivarUnaHabitacion() throws IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("101");
        habitaciones.crearUnaNueva("302");

     	Habitacion habitacion = habitaciones.get("101");
    	assertEquals(true, habitacion.estaActiva());

    	habitaciones.desactivarHabitacion(habitacion.getIdInterno());
    	Habitacion habitacionDespues = habitaciones.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());

    	habitaciones.reactivarHabitacion(habitacionDespues.getIdInterno());
    	Habitacion habitacionDespuesDespues = habitaciones.get("101");
    	assertEquals(true, habitacionDespuesDespues.estaActiva());
    }
    
    @Test
    void guardarCambiosAUnaHabitacion() throws IllegalArgumentException, IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("101");
        habitaciones.crearUnaNueva("302");
        
        Habitacion habitacion = habitaciones.get("101");
        habitacion.setEstaActiva(false);
        habitacion.setTipoDeHabitacion(TipoDeHabitacion.DOBLE);
        habitacion.setTipoDeBaño(TipoDeBaño.DUCHA);
        habitaciones.guardarCambios(habitacion);
        
        Habitacion habitacionDespues = habitaciones.get("101");
        assertEquals(false, habitacionDespues.getEstaActiva());
        assertEquals(TipoDeHabitacion.DOBLE, habitacionDespues.getTipoDeHabitacion());
        assertEquals(TipoDeBaño.DUCHA, habitacionDespues.getTipoDeBaño());
    }
    
    @Test
    void elTipoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        Habitacion habitacion = habitaciones.crearUnaNueva("101");
        assertEquals(TipoDeHabitacion._SIN_ASIGNAR_AUN_, habitacion.getTipoDeHabitacion());
    }
    
    @Test
    void cambiarElTipoAUnaHabitacion() throws IOException {
        habitaciones.crearUnaNueva("301");
    	habitaciones.crearUnaNueva("101");
    	habitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = habitaciones.get("101");
    	habitacion.setTipoDeHabitacion(TipoDeHabitacion.DOBLE);
    	habitaciones.guardarCambios(habitacion);
    	
    	Habitacion habitacionDespues = habitaciones.get("101");
    	assertEquals(TipoDeHabitacion.DOBLE, habitacionDespues.getTipoDeHabitacion());
    }
    
    @Test
    void elTipoDeBañoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        Habitacion habitacion = habitaciones.crearUnaNueva("101");
        assertEquals(TipoDeBaño._SIN_ASIGNAR_AUN_, habitacion.getTipoDeBaño());
    }
    
    @Test
    void cambiarElTipoDeBañoAUnaHabitacion() throws IOException {
        habitaciones.crearUnaNueva("301");
        habitaciones.crearUnaNueva("101");
        habitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = habitaciones.get("101");
    	habitacion.setTipoDeBaño(TipoDeBaño.DUCHA);
    	habitaciones.guardarCambios(habitacion);
        
    	Habitacion habitacionDespues = habitaciones.get("101");
    	assertEquals(TipoDeBaño.DUCHA, habitacionDespues.getTipoDeBaño());
    }

   
}
