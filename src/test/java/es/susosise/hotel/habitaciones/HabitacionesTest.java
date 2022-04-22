package es.susosise.hotel.habitaciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class HabitacionesTest {

  
    @Test
    void seCreaBienUnaNuevaHabitacion() {
    	PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesEnArchivoJSON();
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crear("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
   
}
