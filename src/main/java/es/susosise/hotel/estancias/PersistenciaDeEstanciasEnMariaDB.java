package es.susosise.hotel.estancias;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.PersistenciaDeHuespedesEnMariaDB;


public final class PersistenciaDeEstanciasEnMariaDB implements PersistenciaDeEstancias {
    private java.sql.Connection baseDeDatos;
    
    public PersistenciaDeEstanciasEnMariaDB(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    public static String sqlParaBorrarLaTablaDeEstancias() {
        return "DROP TABLE IF EXISTS estancias ;";
    }
    public static String sqlParaCrearLaTablaDeEstancias() {
        return "CREATE TABLE estancias ( " + System.lineSeparator()
             + "    idInterno CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    fechaEntrada DATE NOT NULL, " + System.lineSeparator()
             + "    fechaSalida DATE NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    
    public static String sqlParaBorrarLaTablaDeEstanciasHabitaciones() {
        return "DROP TABLE IF EXISTS estancias_habitaciones ;";
    }
    public static String sqlParaCrearLaTablaDeEstanciasHabitaciones() {
        return "CREATE TABLE estancias_habitaciones ( " + System.lineSeparator()
             + "    idEstancia CHAR(36) NOT NULL, " + System.lineSeparator()
             + "    idHabitacion CHAR(36) NOT NULL " + System.lineSeparator()
             + ")"
             ;
    }
    
    protected void crearLasTablas() throws SQLException {
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sqlParaCrearLaTablaDeEstancias());
            comando.execute(sqlParaCrearLaTablaDeEstanciasHabitaciones());
            comando.execute(PersistenciaDeHuespedesEnMariaDB.sqlParaCrearLaTablaDeEstanciasHuespedes());
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }
 
    @Override
    public void añadirUnaNueva(Estancia estancia) throws IOException {
        StringBuilder sentenciaSQL_principal = new StringBuilder();
        sentenciaSQL_principal.append("INSERT INTO estancias");
        sentenciaSQL_principal.append(System.lineSeparator());
        sentenciaSQL_principal.append("  SET idInterno = ");
        sentenciaSQL_principal.append("'" + estancia.getIdInterno().toString() + "'");
        sentenciaSQL_principal.append(" ," + System.lineSeparator());
        sentenciaSQL_principal.append("      fechaEntrada = ");
        sentenciaSQL_principal.append("'" + estancia.getFechaEntrada() + "'");
        sentenciaSQL_principal.append(" ," + System.lineSeparator());
        sentenciaSQL_principal.append("      fechaSalida = ");
        sentenciaSQL_principal.append("'" + estancia.getFechaSalida() + "'");
        sentenciaSQL_principal.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        try {
            comando = baseDeDatos.createStatement();
            comando.execute(sentenciaSQL_principal.toString());
            for (UUID idHabitacion : estancia.getHabitaciones()) {
                comando.execute(getSQL_secundaria_Habitacion(estancia.getIdInterno(), idHabitacion));
            }
            for (UUID idHuesped : estancia.getHuespedes()) {
                comando.execute(getSQL_secundaria_Huesped(estancia.getIdInterno(), idHuesped));
            }
        } catch(SQLException ex) {
            throw new IOException("Error al añadir la estancia a la base de datos.");
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
    }
    private String getSQL_secundaria_Habitacion(UUID idEstancia, UUID idHabitacion) {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("INSERT INTO estancias_habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  SET idEstancia = ");
        sentenciaSQL.append("'" + idEstancia.toString() + "'");
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      idHabitacion = ");
        sentenciaSQL.append("'" + idHabitacion.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        return sentenciaSQL.toString();
    }
    private String getSQL_secundaria_Huesped(UUID idEstancia, UUID idHabitacion) {
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("INSERT INTO estancias_huespedes");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  SET idEstancia = ");
        sentenciaSQL.append("'" + idEstancia.toString() + "'");
        sentenciaSQL.append(" ," + System.lineSeparator());
        sentenciaSQL.append("      idHuesped = ");
        sentenciaSQL.append("'" + idHabitacion.toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        return sentenciaSQL.toString();
    }


    @Override
    public Estancia get(UUID idInterno) {
        Estancia estancia = null;
        
        StringBuilder sentenciaSQL_principal = new StringBuilder();
        sentenciaSQL_principal.append("SELECT * FROM estancias");
        sentenciaSQL_principal.append(System.lineSeparator());
        sentenciaSQL_principal.append("  WHERE idInterno = ");
        sentenciaSQL_principal.append("'" + idInterno.toString() + "'");
        sentenciaSQL_principal.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL_principal.toString());
            if (respuesta.next()) {
                ArrayList<UUID> habitaciones = new ArrayList<>();
                ArrayList<UUID> huespedes = new ArrayList<>();
                estancia = new Estancia(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                        habitaciones,
                                        respuesta.getDate("fechaEntrada").toLocalDate(),
                                        respuesta.getDate("fechaSalida").toLocalDate(),
                                        huespedes);
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la estancia con código " + idInterno.toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + java.util.Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        return completarInformacionDeHabitacionesYHuespedes(estancia);
    }
    
    private Estancia completarInformacionDeHabitacionesYHuespedes(Estancia estanciaIncompleta) {
        StringBuilder sentenciaSQL_habitaciones = new StringBuilder();
        sentenciaSQL_habitaciones.append("SELECT idHabitacion ");
        sentenciaSQL_habitaciones.append(System.lineSeparator());
        sentenciaSQL_habitaciones.append("  FROM estancias_habitaciones");
        sentenciaSQL_habitaciones.append(System.lineSeparator());
        sentenciaSQL_habitaciones.append("  WHERE idEstancia = ");
        sentenciaSQL_habitaciones.append("'" + estanciaIncompleta.getIdInterno().toString() + "'");
        sentenciaSQL_habitaciones.append(System.lineSeparator());
        
        StringBuilder sentenciaSQL_huespedes = new StringBuilder();
        sentenciaSQL_huespedes.append("SELECT idHuesped ");
        sentenciaSQL_huespedes.append(System.lineSeparator());
        sentenciaSQL_huespedes.append("  FROM estancias_huespedes");
        sentenciaSQL_huespedes.append(System.lineSeparator());
        sentenciaSQL_huespedes.append("  WHERE idEstancia = ");
        sentenciaSQL_huespedes.append("'" + estanciaIncompleta.getIdInterno().toString() + "'");
        sentenciaSQL_huespedes.append(System.lineSeparator());
        
        ArrayList<UUID> habitaciones = new ArrayList<>();
        ArrayList<UUID> huespedes = new ArrayList<>();
        java.sql.Statement comando = null;
        java.sql.ResultSet respuestaHabitaciones = null;
        java.sql.ResultSet respuestaHuespedes = null;
        try {
            comando = baseDeDatos.createStatement();
            
            respuestaHabitaciones = comando.executeQuery(sentenciaSQL_habitaciones.toString());
            while (respuestaHabitaciones.next()) {
                habitaciones.add(java.util.UUID.fromString(respuestaHabitaciones.getString("idHabitacion")));
            }
            
            respuestaHuespedes = comando.executeQuery(sentenciaSQL_huespedes.toString());
            while (respuestaHuespedes.next()) {
                huespedes.add(java.util.UUID.fromString(respuestaHuespedes.getString("idHuesped")));
            }
            
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "la estancia con código " + estanciaIncompleta.getIdInterno().toString()
                             + System.lineSeparator() + System.lineSeparator()
                             + java.util.Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuestaHabitaciones != null) respuestaHabitaciones.close(); } catch (Exception ex) {}
            try { if (respuestaHuespedes != null) respuestaHuespedes.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        
        return new Estancia(estanciaIncompleta.getIdInterno(),
                            habitaciones,
                            estanciaIncompleta.getFechaEntrada(),
                            estanciaIncompleta.getFechaSalida(),
                            huespedes);

    }
    
    
    @Override
    public List<Estancia> getEstanciasActivasEnEsteMomento() {
        List<Estancia> estancias = new ArrayList<>();
        
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT * FROM estancias");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append(" WHERE FechaEntrada <= ");
        sentenciaSQL.append("'" + LocalDate.now().toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("   AND FechaSalida >= ");
        sentenciaSQL.append("'" + LocalDate.now().toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            ArrayList<UUID> habitaciones = new ArrayList<>();
            ArrayList<UUID> huespedes = new ArrayList<>();
            while (respuesta.next()) {
                Estancia estanciaIncompleta = new Estancia(java.util.UUID.fromString(respuesta.getString("idInterno")),
                                                           habitaciones,
                                                           respuesta.getDate("fechaEntrada").toLocalDate(),
                                                           respuesta.getDate("fechaSalida").toLocalDate(),
                                                           huespedes);
                estancias.add(completarInformacionDeHabitacionesYHuespedes(estanciaIncompleta));
            }
        } catch(SQLException ex) {
            System.out.println("Error al recuperar de la base de datos "
                             + "las estancias activas en este momento."
                             + System.lineSeparator() + System.lineSeparator()
                             + java.util.Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        
        return estancias;
    }


    @Override
    public List<UUID> getEstanciasActivasAsociadasAAlgunaDeEstasHabitaciones(List<Habitacion> habitaciones) {
        ArrayList<UUID> estancias = new ArrayList<>();
        
        StringBuilder listaDeHabitaciones = new StringBuilder();
        for (Habitacion habitacion : habitaciones) {
            listaDeHabitaciones.append("'" + habitacion.getIdInterno().toString() + "', ");
        }
        if (listaDeHabitaciones.length() > 0) {
            //quitar la coma y el espacio extras que quedan al final
            listaDeHabitaciones.deleteCharAt(listaDeHabitaciones.length() - 1);
            listaDeHabitaciones.deleteCharAt(listaDeHabitaciones.length() - 1);
        }
        StringBuilder sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT UNIQUE idInterno");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  FROM estancias");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("  JOIN estancias_habitaciones");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("    ON estancias.idInterno = estancias_habitaciones.idEstancia");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append(" WHERE estancias.FechaEntrada <= ");
        sentenciaSQL.append("'" + LocalDate.now().toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("   AND estancias.FechaSalida >= ");
        sentenciaSQL.append("'" + LocalDate.now().toString() + "'");
        sentenciaSQL.append(System.lineSeparator());
        sentenciaSQL.append("   AND estancias_habitaciones.idHabitacion IN ");
        sentenciaSQL.append("(" + listaDeHabitaciones.toString() + ")");
        sentenciaSQL.append(System.lineSeparator());
        
        java.sql.Statement comando = null;
        java.sql.ResultSet respuesta = null;
        try {
            comando = baseDeDatos.createStatement();
            respuesta = comando.executeQuery(sentenciaSQL.toString());
            while (respuesta.next()) {
                estancias.add(java.util.UUID.fromString(respuesta.getString("idInterno")));
            }
        } catch(SQLException ex) {
            System.out.println("Error buscando estancias activas para las habitaciones "
                             + "(" + listaDeHabitaciones.toString() + ")"
                             + System.lineSeparator() + System.lineSeparator()
                             + java.util.Arrays.toString(ex.getStackTrace()));
        } finally {
            try { if (respuesta != null) respuesta.close(); } catch (Exception ex) {}
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
        }
        
        return estancias;
    }

}
