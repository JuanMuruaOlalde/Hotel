package es.susosise.hotel.elementosComunesCompartidos;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;



public class OpcionesYConstantesTest {
    
    @Test
    void hayConfiguradaUnaCarpetaDeDatosValida() throws SQLException {
        OpcionesYConstantes opciones = new OpcionesYConstantes();
        assertTrue(opciones.carpetaDeDatos.toFile().exists());
    }
    
    @Test
    void hayConfiguradoUnServidorDeDatosValido() throws SQLException {
        OpcionesYConstantes opciones = new OpcionesYConstantes();
        java.sql.ResultSet tablas = opciones.servidorDeDatos.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
        assertTrue(tablas.getFetchSize() > 0);
    }
    

}
