package es.susosise.hotel.habitaciones;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;
import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


class HabitacionesTest {

	java.nio.file.Path carpeta;
	PersistenciaDeHabitaciones persistencia;
	
	@BeforeEach
	void prepararPersistencia() throws IOException, SQLException {
	    
	    //persistencia = new PersistenciaDeHabitacionesEnBaseDeDatosSQL(OpcionesYConstantes.getServidorDeDatosParaPruebas());	    
	  
	    persistencia = new PersistenciaDeHabitacionesEnArchivoJSON(OpcionesYConstantes.getCarpetaDeDatosParaPruebas());
	    
	    //persistencia = new PersistenciaDeHabitacionesMocParaAgilizarLosTests();
	}
	

   @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElNumeroDeHabitacion() throws IOException {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crearUnaNueva("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElIdentificadorInterno() throws IOException {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crearUnaNueva("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get(habitacionCreada.getIdInterno());
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void alCrearUnaNuevaHabitacionSeAñadeALasExistentes() throws IOException {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada01 = creador.crearUnaNueva("201");
    	Habitacion habitacionCreada02 = creador.crearUnaNueva("202");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada01 = buscador.get("201");
    	Habitacion habitacionRecuperada02 = buscador.get("202");
    	
    	assertEquals(habitacionCreada01, habitacionRecuperada01);
    	assertEquals(habitacionCreada02, habitacionRecuperada02);
    }

    @Test
    void noSeCreanHabitacionesDuplicadas() throws IOException {
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("101");
    	
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
		     new Executable() {
	            @Override
	            public void execute() throws Throwable {
	            	creador.crearUnaNueva("101");
	            }
	    	 }
    													 );
   	}
    
    
    @Test
    void recuperarTodasLasHabitaciones() throws IOException {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("302");
    	creador.crearUnaNueva("303");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	java.util.List<Habitacion> habitaciones = buscador.getTodas();
    	assertEquals(3, habitaciones.size());
    }


    @Test
    void recuperarConFiltroDeNumeroDeHabitacion() throws IOException {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	java.util.List<Habitacion> habitaciones = buscador.getAquellasQueComiencenPor("3");
    	assertEquals(2, habitaciones.size());
    }
    
    @Test
    void inactivarUnaHabitacion() throws IOException {
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacion = buscador.get("101");
    	assertEquals(true, habitacion.estaActiva());
    	java.util.UUID id = habitacion.getIdInterno();

    	EliminadorDeHabitaciones eliminador = new EliminadorDeHabitaciones(persistencia);
    	eliminador.inactivar(id);
    	Habitacion habitacionDespues = buscador.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());
    }
    
    @Test
    void reactivarUnaHabitacion() throws IOException {
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacion = buscador.get("101");
    	assertEquals(true, habitacion.estaActiva());
    	java.util.UUID id = habitacion.getIdInterno();

    	EliminadorDeHabitaciones eliminador = new EliminadorDeHabitaciones(persistencia);
    	eliminador.inactivar(id);
    	Habitacion habitacionDespues = buscador.get("101");
    	assertEquals(false, habitacionDespues.estaActiva());

    	eliminador.activar(id);
    	Habitacion habitacionDespuesDespues = buscador.get("101");
    	assertEquals(true, habitacionDespuesDespues.estaActiva());
    }
    
    @Test
    void elTipoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
        Habitacion habitacion = creador.crearUnaNueva("101");
        assertEquals(TipoDeHabitacion._SIN_ASIGNAR_AUN_, habitacion.getTipo());
    }
    
    @Test
    void cambiarElTipoAUnaHabitacion() throws IOException {
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacion = buscador.get("101");
    	java.util.UUID id = habitacion.getIdInterno();
    	
    	ModificadorDeHabitaciones modificador = new ModificadorDeHabitaciones(persistencia);
    	modificador.cambiarTipoDeHabitacion(id, TipoDeHabitacion.DOBLE);
    	Habitacion habitacionDespues = buscador.get("101");
    	assertEquals(TipoDeHabitacion.DOBLE, habitacionDespues.getTipo());
    }
    
    @Test
    void elTipoDeBañoDeUnaHabitacionRecienCreadaEsSinAsignar() throws IllegalArgumentException, IOException {
        CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
        Habitacion habitacion = creador.crearUnaNueva("101");
        assertEquals(TipoDeBaño._SIN_ASIGNAR_AUN_, habitacion.getTipoDeBaño());
    }
    
    @Test
    void cambiarElTipoDeBañoAUnaHabitacion() throws IOException {
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacion = buscador.get("101");
    	java.util.UUID id = habitacion.getIdInterno();
    	
        ModificadorDeHabitaciones modificador = new ModificadorDeHabitaciones(persistencia);
        modificador.cambiarTipoDeBaño(id, TipoDeBaño.DUCHA);
    	Habitacion habitacionDespues = buscador.get("101");
    	assertEquals(TipoDeBaño.DUCHA, habitacionDespues.getTipoDeBaño());
    }

   
}
