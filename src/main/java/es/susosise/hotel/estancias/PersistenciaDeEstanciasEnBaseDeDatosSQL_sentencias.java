package es.susosise.hotel.estancias;

public final class PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias {

    private PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias() {
        
    }
    
    //nota: Al añadir funciones aquí, acordarse de hacer las llamadas correspondientes a ellas en la función
    //      es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento.CreadorDeLasTablasEnBD()
    
    public static String paraBorrarLaTablaDeEstancias() {
        return "DROP TABLE IF EXISTS estancias ;";
    }
    public static String paraCrearLaTablaDeEstancias() {
        return "CREATE TABLE estancias ( " + System.lineSeparator()
             + "    idInterno CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    fechaEntrada DATE NOT NULL, " + System.lineSeparator()
             + "    fechaSalida DATE NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    
    public static String paraBorrarLaTablaDeEstanciasHabitaciones() {
        return "DROP TABLE IF EXISTS estancias_habitaciones ;";
    }
    public static String paraCrearLaTablaDeEstanciasHabitaciones() {
        return "CREATE TABLE estancias_habitaciones ( " + System.lineSeparator()
             + "    idEstancia CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    idHabitacion CHAR(36) NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    
    public static String paraBorrarLaTablaDeEstanciasHuespedes() {
        return "DROP TABLE IF EXISTS estancias_huespedes ;";
    }
    public static String paraCrearLaTablaDeEstanciasHuespedes() {
        return"CREATE TABLE estancias_huespedes ( " + System.lineSeparator()
             + "    idEstancia CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    idHuesped CHAR(36) NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    

}
