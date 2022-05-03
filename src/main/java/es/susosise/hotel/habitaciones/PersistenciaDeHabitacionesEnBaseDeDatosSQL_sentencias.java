package es.susosise.hotel.habitaciones;

public final class PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias {
    
    private PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias() {
        
    }
 
    //nota: Al añadir funciones aquí, acordarse de hacer las llamadas correspondientes a ellas en la función
    //      es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento.CreadorDeLasTablasEnBD()
    
    public static String paraCrearLaTablaDeHabitaciones() {
        return "CREATE TABLE habitaciones ( " + System.lineSeparator()
             + "    idInterno CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    activa BOOLEAN NULL, " + System.lineSeparator()
             + "    numeroDeHabitacion VARCHAR(10) NOT NULL, " + System.lineSeparator()
             + "    tipo VARCHAR(25), " + System.lineSeparator()
             + "    tipoDeBaño VARCHAR(25) " + System.lineSeparator()
             + ")"
             ;
    }

}
