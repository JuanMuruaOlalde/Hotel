package es.susosise.hotel.elementosComunesCompartidos;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;



public class OpcionesYConstantesTest {
    
    
    @Disabled("Solo lo uso para lanzar el debugger y hacer pruebas") 
    void seLeeLaConfiguracionDesdeUnArchivo() throws IOException, SQLException {
        OpcionesYConstantes opciones = new OpcionesYConstantes(java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_pruebas", "_configuracion_.json"));
    }
    

}
