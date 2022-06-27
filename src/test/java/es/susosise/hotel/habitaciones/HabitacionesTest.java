package es.susosise.hotel.habitaciones;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;
import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;
import es.susosise.hotel.estancias.GestorDeEstancias;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Disabled;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


class HabitacionesTest {
	
	java.sql.Connection baseDeDatos;
	GestorDeHabitaciones gestorDeHabitaciones;
	
	
	@BeforeEach
	void prepararEntorno() throws IOException, SQLException {
	    //PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesMocParaAgilizarLosTests();
	    //PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesEnArchivoJSON(OpcionesYConstantes.getCarpetaDeDatosParaPruebas());
	    baseDeDatos = OpcionesYConstantes.getServidorDeDatosParaPruebas();
	    PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos);
	    ((PersistenciaDeHabitacionesEnBaseDeDatosSQL) persistencia).crearLaTabla();
	    
        gestorDeHabitaciones = new GestorDeHabitaciones(persistencia);
	}
	@AfterEach
	void limpiarEntorno() {
	    //borrarRecursivo(OpcionesYConstantes.getCarpetaDeDatosParaPruebas().toFile());
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
    	
    	Habitacion habitacionCreada = gestorDeHabitaciones.crearUnaNueva("101");
    	
    	Habitacion habitacionRecuperada = gestorDeHabitaciones.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElIdentificadorInterno() throws IOException {
    	
    	Habitacion habitacionCreada = gestorDeHabitaciones.crearUnaNueva("101");
    	
    	Habitacion habitacionRecuperada = gestorDeHabitaciones.get(habitacionCreada.getIdInterno());
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void alCrearUnaNuevaHabitacionSeAñadeALasExistentes() throws IOException {
    	
    	Habitacion habitacionCreada01 = gestorDeHabitaciones.crearUnaNueva("201");
    	Habitacion habitacionCreada02 = gestorDeHabitaciones.crearUnaNueva("202");
    	
    	Habitacion habitacionRecuperada01 = gestorDeHabitaciones.get("201");
    	Habitacion habitacionRecuperada02 = gestorDeHabitaciones.get("202");
    	
    	assertEquals(habitacionCreada01, habitacionRecuperada01);
    	assertEquals(habitacionCreada02, habitacionRecuperada02);
    }

    @Test
    void noSeCreanHabitacionesDuplicadas() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("101");
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
		     new Executable() {
	            @Override
	            public void execute() throws Throwable {
	                gestorDeHabitaciones.crearUnaNueva("101");
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
                    gestorDeHabitaciones.crearUnaNueva("");
                }
             }
                                                         );
    }
    
    
    @Test
    void recuperarTodasLasHabitaciones() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("302");
        gestorDeHabitaciones.crearUnaNueva("303");
    	
    	java.util.List<Habitacion> habitaciones = gestorDeHabitaciones.getTodas();
    	assertEquals(3, habitaciones.size());
    }


    @Test
    void recuperarConFiltroDeNumeroDeHabitacion() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("101");
        gestorDeHabitaciones.crearUnaNueva("302");
    	
    	java.util.List<Habitacion> habitaciones = gestorDeHabitaciones.getAquellasCuyoNumeroComiencePor("3");
    	assertEquals(2, habitaciones.size());
    }
    
    @Test
    void inactivarUnaHabitacion() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("101");
        gestorDeHabitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = gestorDeHabitaciones.get("101");
    	assertEquals(true, habitacion.estaActiva());

    	gestorDeHabitaciones.desactivarHabitacion(habitacion.getIdInterno());
    	Habitacion habitacionDespues = gestorDeHabitaciones.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());
    }
    
    @Test
    void reactivarUnaHabitacion() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("101");
        gestorDeHabitaciones.crearUnaNueva("302");

     	Habitacion habitacion = gestorDeHabitaciones.get("101");
    	assertEquals(true, habitacion.estaActiva());

    	gestorDeHabitaciones.desactivarHabitacion(habitacion.getIdInterno());
    	Habitacion habitacionDespues = gestorDeHabitaciones.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());

    	gestorDeHabitaciones.reactivarHabitacion(habitacionDespues.getIdInterno());
    	Habitacion habitacionDespuesDespues = gestorDeHabitaciones.get("101");
    	assertEquals(true, habitacionDespuesDespues.estaActiva());
    }
    
    @Test
    void guardarCambiosAUnaHabitacion() throws IllegalArgumentException, IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("101");
        gestorDeHabitaciones.crearUnaNueva("302");
        
        Habitacion habitacion = gestorDeHabitaciones.get("101");
        habitacion.setEstaActiva(false);
        habitacion.setTipoDeHabitacion(TipoDeHabitacion.DOBLE);
        habitacion.setTipoDeBaño(TipoDeBaño.DUCHA);
        gestorDeHabitaciones.guardarCambios(habitacion);
        
        Habitacion habitacionDespues = gestorDeHabitaciones.get("101");
        assertEquals(false, habitacionDespues.getEstaActiva());
        assertEquals(TipoDeHabitacion.DOBLE, habitacionDespues.getTipoDeHabitacion());
        assertEquals(TipoDeBaño.DUCHA, habitacionDespues.getTipoDeBaño());
    }
    
    @Test
    void elTipoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        Habitacion habitacion = gestorDeHabitaciones.crearUnaNueva("101");
        assertEquals(TipoDeHabitacion._SIN_ASIGNAR_AUN_, habitacion.getTipoDeHabitacion());
    }
    
    @Test
    void cambiarElTipoAUnaHabitacion() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
    	gestorDeHabitaciones.crearUnaNueva("101");
    	gestorDeHabitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = gestorDeHabitaciones.get("101");
    	habitacion.setTipoDeHabitacion(TipoDeHabitacion.DOBLE);
    	gestorDeHabitaciones.guardarCambios(habitacion);
    	
    	Habitacion habitacionDespues = gestorDeHabitaciones.get("101");
    	assertEquals(TipoDeHabitacion.DOBLE, habitacionDespues.getTipoDeHabitacion());
    }
    
    @Test
    void elTipoDeBañoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        Habitacion habitacion = gestorDeHabitaciones.crearUnaNueva("101");
        assertEquals(TipoDeBaño._SIN_ASIGNAR_AUN_, habitacion.getTipoDeBaño());
    }
    
    @Test
    void cambiarElTipoDeBañoAUnaHabitacion() throws IOException {
        gestorDeHabitaciones.crearUnaNueva("301");
        gestorDeHabitaciones.crearUnaNueva("101");
        gestorDeHabitaciones.crearUnaNueva("302");
    	
    	Habitacion habitacion = gestorDeHabitaciones.get("101");
    	habitacion.setTipoDeBaño(TipoDeBaño.DUCHA);
    	gestorDeHabitaciones.guardarCambios(habitacion);
        
    	Habitacion habitacionDespues = gestorDeHabitaciones.get("101");
    	assertEquals(TipoDeBaño.DUCHA, habitacionDespues.getTipoDeBaño());
    }

   
}
