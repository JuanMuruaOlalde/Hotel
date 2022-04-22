package es.susosise.hotel.habitaciones;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



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
    	persistencia = new PersistenciaDeHabitacionesEnArchivoJSON(carpeta);
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
    void seCreaBienUnaNuevaHabitacion() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crear("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
    
    @Test
    void alCrearUnaNuevaHabitacionSeAñadeALasExistentes() {
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada01 = creador.crear("201");
    	Habitacion habitacionCreada02 = creador.crear("202");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada01 = buscador.get("201");
    	Habitacion habitacionRecuperada02 = buscador.get("202");
    	
    	assertEquals(habitacionCreada01, habitacionRecuperada01);
    	assertEquals(habitacionCreada02, habitacionRecuperada02);
    }
    
   
}
