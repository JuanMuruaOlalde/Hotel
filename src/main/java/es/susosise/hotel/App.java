package es.susosise.hotel;

public class App 
{
    public es.susosise.hotel.habitaciones.PersistenciaDeHabitaciones persistenciaDeHabitaciones;
    
    public static void main( String[] args )
    {
    
 
    }
    
    private void inicializarPersistencia() {
     	persistenciaDeHabitaciones = new es.susosise.hotel.habitaciones.PersistenciaDeHabitacionesEnArchivoJSON();
   	
    }
    
}
