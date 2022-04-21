package es.susosise.hotel.habitaciones;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.framework.Test;


public class HabitacionesTest extends TestCase {

    public HabitacionesTest( String testName )
    {
        super( testName );
    }
	
    public static Test suite()
    {
        return new TestSuite( HabitacionesTest.class );
    }
	
    
   
    public void testCrearUnaNuevaHabitacion() {
    	PersistenciaDeHabitaciones persistencia = new PersistenciaDeHabitacionesMocParaTest();
    	
    	CreadorDeHabitaciones creador = new CreadorDeHabitaciones(persistencia);
    	Habitacion habitacionCreada = creador.crearUnaNuevaHabitacion("101");
    	
    	BuscadorDeHabitaciones buscador = new BuscadorDeHabitaciones(persistencia);
    	Habitacion habitacionRecuperada = buscador.get("101");
    	
    	assertEquals(habitacionCreada, habitacionRecuperada);
    }
   
}
