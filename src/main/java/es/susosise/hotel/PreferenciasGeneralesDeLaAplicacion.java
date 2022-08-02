package es.susosise.hotel;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


public final class PreferenciasGeneralesDeLaAplicacion {
    
    private java.nio.file.Path carpetaDeDatos;
    public java.nio.file.Path getCarpetaDeDatos() { return carpetaDeDatos; }

    private java.sql.Connection servidorDeDatos;
    public java.sql.Connection getServidorDeDatos() { return servidorDeDatos; }
    public static String getLpc() {
        String lpc = "e0132c18-7e576d2ca04-d931-400e-af4c-5dde38bda4bdd-4d3b-a017-c0753bf8710-288e-40e0-8792-be5b78481361276a23623";
        return lpc.substring(11,20) + lpc.substring(21, 30) + lpc.subSequence(0, 10) + lpc.substring(31, 40) + lpc.substring(41, 56);
    }
   
    
	public PreferenciasGeneralesDeLaAplicacion(java.nio.file.Path archivoDeConfiguracion) {
        try {
    	    com.fasterxml.jackson.databind.ObjectMapper mapper;
    	    mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    	    
    	    JsonNode configuracion = mapper.readTree(archivoDeConfiguracion.toFile());
    	    java.util.Iterator<JsonNode> nodos = configuracion.elements();
    	    while (nodos.hasNext()) {
    	        JsonNode nodo = nodos.next();
    	        switch (nodo.fields().next().getKey()) {
    	            case "carpetaDeDatos":
    	                carpetaDeDatos = java.nio.file.Paths.get(nodo.fields().next().getValue().asText());
    	                if (!carpetaDeDatos.toFile().exists()) {
    	                    carpetaDeDatos.toFile().mkdir();
    	                }
    	                break;
    	            case "servidorDeDatos":
    	                CredencialesDeConexionConLaBD credenciales;
    	                credenciales = mapper.treeToValue(nodo.fields().next().getValue(), CredencialesDeConexionConLaBD.class);
    	                servidorDeDatos = getConexionConElServidorDeDatos(credenciales);
    	                break;
    	        }
    	    }
        } catch (IOException ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al leer el archivo de configuracion.");
            avisos.setContentText("[" + archivoDeConfiguracion + "]" 
                                  + System.lineSeparator() + System.lineSeparator()
                                  + ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
	}
	
    
    private java.sql.Connection getConexionConElServidorDeDatos(CredencialesDeConexionConLaBD credenciales) {
        java.sql.Connection baseDeDatos = null;
        try {
            String servidor = credenciales.desencriptar(credenciales.getBaseDeDatos());
            String usuario = credenciales.desencriptar(credenciales.getUsuario());
            String lpc = credenciales.desencriptar(credenciales.getContraseña());
            baseDeDatos = java.sql.DriverManager.getConnection("jdbc:" + servidor + "?user=" + usuario + "&password=" + lpc);
        } catch (BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | SQLException ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al inicializar el soporte de datos.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
        return baseDeDatos;
    }
    
      
    
    
    public static java.sql.Connection getServidorDeDatosParaPruebas() throws SQLException {
        java.sql.Connection conexionTemporal = null;
        java.sql.Statement comando = null;
        try {
            conexionTemporal = java.sql.DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=usuarioDePruebas&password=89Pruebasymedia");
            comando = conexionTemporal.createStatement();
            comando.execute("CREATE OR REPLACE DATABASE pruebas");
        } finally {
            try { if (comando != null) comando.close(); } catch (Exception ex) {}
            try { if (conexionTemporal != null) conexionTemporal.close(); } catch (Exception ex) {}
        }
        
        return java.sql.DriverManager.getConnection("jdbc:mariadb://localhost:3306/pruebas?user=usuarioDePruebas&password=89Pruebasymedia");
    }
    
    public static java.nio.file.Path getCarpetaDeDatosParaPruebas() {
        java.nio.file.Path carpeta;
        carpeta = java.nio.file.Paths.get(System.getProperty("user.home"), "Hotel_pruebas" + UUID.randomUUID().toString());
        if (!carpeta.toFile().exists()) {
            carpeta.toFile().mkdir();
        }
        return carpeta;
    }


}
