package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;


final class PersistenciaDeHabitacionesEnBaseDeDatosSQL implements PersistenciaDeHabitaciones {

    private java.sql.Connection baseDeDatos;
    
    public PersistenciaDeHabitacionesEnBaseDeDatosSQL(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    protected void crearLaTabla() throws SQLException {
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(PersistenciaDeHabitacionesEnBaseDeDatosSQL_sentencias.paraCrearLaTablaDeHabitaciones());
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
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
        
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL.toString());
        } catch(SQLException ex) {
            throw new IOException("Error al añadir la habitacion " + unaHabitacion.getNumeroDeHabitacion() 
                                   + " a la base de datos.");
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
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
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            if (respuesta.next()) {
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la habitacion con código " + id.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
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
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            if (respuesta.next()) {
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la habitacion " + numeroDeHabitacion
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        return habitacion;
    }

    @Override
    public List<Habitacion> getTodas() {
        java.util.ArrayList<Habitacion> habitaciones = new java.util.ArrayList<>();
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT * FROM habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            while (respuesta.next()) {
                Habitacion habitacion;
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
                habitaciones.add(habitacion);
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar habitaciones de la base de datos "
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        
        return habitaciones;
    }

    @Override
    public List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
        java.util.ArrayList<Habitacion> habitaciones = new java.util.ArrayList<>();
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT * FROM habitaciones");
        sentenciaSQL.append("  WHERE numeroDeHabitacion LIKE ");
        sentenciaSQL.append("'" + criterio + "%'");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            while (respuesta.next()) {
                Habitacion habitacion;
                habitacion = new Habitacion(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                            respuesta.getBoolean("activa"),
                                            respuesta.getString("numeroDeHabitacion"),
                                            TipoDeHabitacion.valueOf(respuesta.getString("tipo")),
                                            TipoDeBaño.valueOf(respuesta.getString("tipoDeBaño"))
                                            );
                habitaciones.add(habitacion);
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar habitaciones de la base de datos "
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        
        return habitaciones;
    }

    private void modificarActiva(UUID id, boolean nuevoEstado) {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("UPDATE habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("SET activa = ");
        sentenciaSQL.append(nuevoEstado);
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  WHERE idInterno = ");
        sentenciaSQL.append("'" + id.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL.toString());
        } catch(SQLException ex) {
            System.out.println("Error al cambiar activa = " + nuevoEstado
                             + " la habitacion con código " + id.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }
    
    @Override
    public void inactivar(UUID id) throws IOException {
        modificarActiva(id, false);
    }

    @Override
    public void activar(UUID id) throws IOException {
        modificarActiva(id, true);
    }

    @Override
    public void cambiarTipoDeHabitacion(UUID id, TipoDeHabitacion nuevoTipo) throws IOException {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("UPDATE habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("SET tipo = ");
        sentenciaSQL.append("'" + nuevoTipo.name() + "'");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  WHERE idInterno = ");
        sentenciaSQL.append("'" + id.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL.toString());
        } catch(SQLException ex) {
            System.out.println("Error el tipo de habitacion a " + nuevoTipo.toString()
                             + " a la habitacion con código " + id.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }

    @Override
    public void cambiarTipoDeBaño(UUID id, TipoDeBaño nuevoTipo) throws IOException {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("UPDATE habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("SET tipoDeBaño = ");
        sentenciaSQL.append("'" + nuevoTipo.name() + "'");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  WHERE idInterno = ");
        sentenciaSQL.append("'" + id.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL.toString());
        } catch(SQLException ex) {
            System.out.println("Error el tipo de baño a " + nuevoTipo.toString()
                             + " a la habitacion con código " + id.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }

    
}
