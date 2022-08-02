package es.susosise.hotel;

import org.junit.jupiter.api.Test;

import es.susosise.hotel.PreferenciasGeneralesDeLaAplicacion;

import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;



public class PreferenciasGeneralesDeLaAplicacionTest {
    
    
    @Disabled("Solo lo uso para lanzar el debugger y hacer pruebas") 
    void seLeeLaConfiguracionDesdeUnArchivo() throws IOException, SQLException {
        PreferenciasGeneralesDeLaAplicacion preferencias = new PreferenciasGeneralesDeLaAplicacion(java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_pruebas", "_configuracion_.json"));
    }
    

}
