package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

import es.susosise.hotel.ManejoDePreferenciasYConfiguracion;

import static org.junit.jupiter.api.Assertions.assertTrue;



class HerramientasTest {
    
    private static java.sql.Connection baseDeDatos;

    @BeforeAll
     static void abrirConexionConLaBaseDeDatos() throws SQLException {
        baseDeDatos = ManejoDePreferenciasYConfiguracion.getServidorDeDatosParaPruebas();
    }
    
    @AfterAll
    static void cerrarConexionConLaBaseDeDatos() throws SQLException {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
    }
    
    
    @Disabled("El creador de tablas es una herramienta auxiliar que solo se usa en momentos puntuales")
    void seCreanTodasLasTablasNecesariasEnLaBD() throws SQLException {
        CreadorDeLasTablasEnLaBD creador = new CreadorDeLasTablasEnLaBD(ManejoDePreferenciasYConfiguracion.getServidorDeDatosParaPruebas());
        creador.crear();
        
        String[] filtroDeTipos = {"TABLE"};
        java.sql.ResultSet respuesta = baseDeDatos.getMetaData().getTables(null, null, "%", filtroDeTipos);
        java.util.HashSet<String> tablas = new java.util.HashSet<>();
        while (respuesta.next()) {
            tablas.add(respuesta.getString("TABLE_NAME"));
        }
        assertTrue(tablas.contains("habitaciones"));
        assertTrue(tablas.contains("estancias"));
        assertTrue(tablas.contains("estancias_habitaciones"));
        assertTrue(tablas.contains("estancias_huespedes"));
        // TODO  aqui iremos comprobando el resto de tablas... a medida que vayamos completando cada entidad del dominio...
        
    }

    
    
}
