package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class PersistenciaDeHabitacionesEnBaseDeDatosSQL implements PersistenciaDeHabitaciones {

    private java.sql.Connection baseDeDatos;
    
    public PersistenciaDeHabitacionesEnBaseDeDatosSQL(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
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
    protected void crearLaTabla() throws SQLException {
        java.sql.Statement comando = baseDeDatos.createStatement();
        comando.execute(getSQLparaCrearLaTabla());
        comando.close();
    }
    
    
    @Override
    public void añadirUnaNueva(Habitacion unaHabitacion) throws IOException {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("INSERT INTO habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  SET idInterno = ");
        sentenciaSQL.append("'" + unaHabitacion.getIdInterno().toString() + "'");
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      activa = ");
        sentenciaSQL.append(unaHabitacion.estaActiva());
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      numeroDeHabitacion = ");
        sentenciaSQL.append("'" + unaHabitacion.getNumeroDeHabitacion() + "'");
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      tipo = ");
        sentenciaSQL.append("'" + unaHabitacion.getTipo().toString() + "'");
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      tipoDeBaño = ");
        sentenciaSQL.append("'" + unaHabitacion.getTipoDeBaño().toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        try {
            java.sql.Statement comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL.toString());
            comando.close();
        } catch(SQLException ex) {
            throw new IOException("Error al añadir la habitacion " + unaHabitacion.getNumeroDeHabitacion() 
                                   + " a la base de datos.");
        }
    }

    @Override
    public Habitacion get(UUID id) {
        Habitacion habitacion = null;
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT * FROM habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  WHERE idInterno = ");
        sentenciaSQL.append("'" + id.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        try {
            java.sql.Statement comando = baseDeDatos.createStatement();
            java.sql.ResultSet respuesta = comando.executeQuery(sentenciaSQL.toString());
            if (respuesta.next()) {
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
            }
            respuesta.close();
            comando.close();
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la habitacion con código " + id.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + ex.getStackTrace().toString());
        }
        return habitacion;
    }

    @Override
    public Habitacion get(String numeroDeHabitacion) {
        Habitacion habitacion = null;
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT * FROM habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  WHERE numeroDeHabitacion = ");
        sentenciaSQL.append("'" + numeroDeHabitacion + "'");
        sentenciaSQL.append(System.lineSeparator());
        try {
            java.sql.Statement comando = baseDeDatos.createStatement();
            java.sql.ResultSet respuesta = comando.executeQuery(sentenciaSQL.toString());
            if (respuesta.next()) {
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
            }
            respuesta.close();
            comando.close();
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la habitacion " + numeroDeHabitacion
                             + System.lineSeparator() + System.lineSeparator()
                             + ex.getStackTrace().toString());
        }
        return habitacion;
    }

    @Override
    public List<Habitacion> getTodas() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void inactivar(UUID id) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void activar(UUID id) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void cambiarTipo(UUID id, TipoDeHabitacion tipo) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void cambiarTipoDeBaño(UUID id, TipoDeBaño tipo) throws IOException {
        // TODO Auto-generated method stub

    }

}
