package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import java.sql.SQLException;

import es.susosise.hotel.habitaciones.PersistenciaDeHabitacionesEnMariaDB;
import es.susosise.hotel.huespedes.PersistenciaDeHuespedesEnMariaDB;
import es.susosise.hotel.estancias.PersistenciaDeEstanciasEnMariaDB;


public class CreadorDeLasTablasEnLaBD {
    
    private java.sql.Connection baseDeDatos;

    public CreadorDeLasTablasEnLaBD(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    
    public void crear() throws SQLException {
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            
            comando.execute(PersistenciaDeHabitacionesEnMariaDB.sqlParaBorrarLaTablaDeHabitaciones());
            comando.execute(PersistenciaDeHabitacionesEnMariaDB.sqlParaCrearLaTablaDeHabitaciones());
            
            comando.execute(PersistenciaDeEstanciasEnMariaDB.sqlParaBorrarLaTablaDeEstancias());
            comando.execute(PersistenciaDeHuespedesEnMariaDB.sqlParaBorrarLaTablaDeEstanciasHuespedes());
            comando.execute(PersistenciaDeEstanciasEnMariaDB.sqlParaBorrarLaTablaDeEstanciasHabitaciones());
            comando.execute(PersistenciaDeEstanciasEnMariaDB.sqlParaCrearLaTablaDeEstancias());
            comando.execute(PersistenciaDeEstanciasEnMariaDB.sqlParaCrearLaTablaDeEstanciasHabitaciones());
            comando.execute(PersistenciaDeHuespedesEnMariaDB.sqlParaCrearLaTablaDeEstanciasHuespedes());
            
            // TODO  aqui iremos poniendo el resto de tablas... a medida que vayamos completando cada entidad del dominio...
            
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }

}
