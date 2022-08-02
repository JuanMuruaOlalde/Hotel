package es.susosise.hotel;

import org.junit.jupiter.api.Test;

import es.susosise.hotel.PreferenciasGeneralesDeLaAplicacion;

import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class PreferenciasGeneralesDeLaAplicacionTest {
    
    // notas: Esto no son test...
    //  ("Solo lo uso para lanzar el debugger y hacer pruebas")
    //  ("Solo lo uso para realizar e/d a mano cuando lo necesite")
    
    
    @Disabled
    void seLeeLaConfiguracionDesdeUnArchivo() throws IOException, SQLException {
        PreferenciasGeneralesDeLaAplicacion preferencias = new PreferenciasGeneralesDeLaAplicacion(java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_pruebas", "_configuracion_.json"));
    }
    
    @Disabled
    void funcionaLaEncriptacionYLaDesencriptacion() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        CredencialesDeConexionConLaBD credenciales = new CredencialesDeConexionConLaBD();
        //hola
        String obscuro1 = credenciales.encriptar("hola");
        String claro1 = credenciales.desencriptar(obscuro1);
        System.out.println("[" + claro1 + "]  [" + obscuro1 + "]");
        //xjKv/80a6sI=
        String claro2 = credenciales.desencriptar("xjKv/80a6sI=");
        String obscuro2 = credenciales.encriptar(claro2);
        System.out.println("[" + obscuro2 + "]  [" + claro2 + "]");
    }

}
