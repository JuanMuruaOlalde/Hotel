package herramientas_de_instalacion_y_mantenimiento;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;



class HerramientasTest {
    
    private static java.sql.Connection baseDeDatos;

    @BeforeAll
     static void abrirConexionConLaBaseDeDatos() throws SQLException {
        baseDeDatos = OpcionesYConstantes.getServidorDeDatosParaPruebas();
    }
    
    @AfterAll
    static void cerrarConexionConLaBaseDeDatos() throws SQLException {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
    }
    
    
    @Test
    void seCreanTodasLasTablasNecesariasEnLaBD() throws SQLException {
        CreadorDeLasTablasEnBD creador = new CreadorDeLasTablasEnBD(OpcionesYConstantes.getServidorDeDatosParaPruebas());
        creador.crear();
        
        String[] filtroDeTipos = {"TABLE"};
        java.sql.ResultSet respuesta = baseDeDatos.getMetaData().getTables(null, null, "%", filtroDeTipos);
        java.util.HashSet<String> tablas = new java.util.HashSet<>();
        while (respuesta.next()) {
            tablas.add(respuesta.getString("TABLE_NAME"));
        }
        assertTrue(tablas.contains("habitaciones"));
        // TODO  aqui iremos comprobando el resto de tablas... a medida que vayamos completando cada entidad del dominio...
        
    }

    
    
}
