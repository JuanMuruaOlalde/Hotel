package herramientas_de_instalacion_y_mantenimiento;

import java.sql.SQLException;

import es.susosise.hotel.habitaciones.PersistenciaDeHabitacionesEnBaseDeDatosSQL;


public class CreadorDeLasTablasEnBD {
    
    private java.sql.Connection baseDeDatos;

    public CreadorDeLasTablasEnBD(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    
    public void crear() throws SQLException {
        java.sql.Statement comando = baseDeDatos.createStatement();
        
        comando.execute(PersistenciaDeHabitacionesEnBaseDeDatosSQL.getSQLparaCrearLaTabla());
        // TODO  aqui iremos poniendo el resto de tablas... a medida que vayamos completando cada entidad del dominio...
        
        comando.close();
    }

}
