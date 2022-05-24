package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import java.sql.SQLException;

import es.susosise.hotel.habitaciones.PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias;
import es.susosise.hotel.estancias.PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias;


public class CreadorDeLasTablasEnLaBD {
    
    private java.sql.Connection baseDeDatos;

    public CreadorDeLasTablasEnLaBD(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    
    public void crear() throws SQLException {
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            
            comando.execute(PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias.paraBorrarLaTablaDeHabitaciones());
            comando.execute(PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias.paraCrearLaTablaDeHabitaciones());
            
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraBorrarLaTablaDeEstancias());
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraBorrarLaTablaDeEstanciasHuespedes());
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraBorrarLaTablaDeEstanciasHabitaciones());
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraCrearLaTablaDeEstancias());
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraCrearLaTablaDeEstanciasHabitaciones());
            comando.execute(PersistenciaDeEstanciasEnBaseDeDatosSQL_sentencias.paraCrearLaTablaDeEstanciasHuespedes());
            
            // TODO  aqui iremos poniendo el resto de tablas... a medida que vayamos completando cada entidad del dominio...
            
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }

}
