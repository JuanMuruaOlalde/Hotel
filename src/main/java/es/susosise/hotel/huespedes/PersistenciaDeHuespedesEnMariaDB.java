package es.susosise.hotel.huespedes;

public class PersistenciaDeHuespedesEnMariaDB {
    
    public static String sqlParaBorrarLaTablaDeEstanciasHuespedes() {
        return "DROP TABLE IF EXISTS estancias_huespedes ;";
    }
    public static String sqlParaCrearLaTablaDeEstanciasHuespedes() {
        return"CREATE TABLE estancias_huespedes ( " + System.lineSeparator()
             + "    idEstancia CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    idHuesped CHAR(36) NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    


}
