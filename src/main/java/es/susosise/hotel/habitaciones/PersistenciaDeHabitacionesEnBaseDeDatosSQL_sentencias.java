package es.susosise.hotel.habitaciones;

public final class PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias {
    
    private PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias() {
        
    }
    
    public static String getSQLparaCrearLaTabla() {
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
