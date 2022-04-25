package es.susosise.hotel.habitaciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;



class HabitacionesTest {

	java.nio.file.Path carpeta;
	PersistenciaDeHabitaciones persistencia;
	
	@BeforeEach
	void prepararPersistencia() {
    	carpeta = java.nio.file.Paths.get(System.getProperty("user.home"), "Hotel_pruebas");
		if (carpeta.toFile().exists()) {
			borradoRecursivo(carpeta.toFile());
		}
		carpeta.toFile().mkdir();
    	try {
			persistencia = new PersistenciaDeHabitacionesEnArchivoJSON(carpeta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterEach
	void borrarPersistencia() {
		borradoRecursivo(carpeta.toFile());
	}
	
	private void borradoRecursivo(java.io.File archivo) {
		for (java.io.File subarchivo : archivo.listFiles()) {
			if (subarchivo.isDirectory()) {
				borradoRecursivo(subarchivo);
			}
			else {
				subarchivo.delete();
			}
		}
		archivo.delete();
	}
  
	
    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElNumeroDeHabitacion() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crearUnaNueva("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void seCreaUnaNuevaHabitacionYSeRecuperaUsandoElIdentificadorInterno() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crearUnaNueva("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get(habitacionCreada.getIdInterno());
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void alCrearUnaNuevaHabitacionSeAñadeALasExistentes() {
    	
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
    void recuperarTodasLasHabitaciones() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("302");
    	creador.crearUnaNueva("303");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	java.util.List<Habitacion> habitaciones = buscador.getTodas();
    	assertEquals(3, habitaciones.size());
    }


    @Test
    void recuperarConFiltroDeNumeroDeHabitacion() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	creador.crearUnaNueva("301");
    	creador.crearUnaNueva("101");
    	creador.crearUnaNueva("302");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	java.util.List<Habitacion> habitaciones = buscador.getAquellasQueComiencenPor("3");
    	assertEquals(2, habitaciones.size());
    }
    
    
}
