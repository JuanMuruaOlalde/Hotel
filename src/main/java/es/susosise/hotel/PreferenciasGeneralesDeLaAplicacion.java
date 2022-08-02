package es.susosise.hotel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

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
            baseDeDatos = java.sql.DriverManager.getConnection("jdbc:" + credenciales.getBaseDeDatos() + "?user=" + credenciales.getUsuario() + "&password=" + credenciales.getContraseña());
        } catch (SQLException ex) {
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
            conexionTemporal = java.sql.DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=root&password=89Pruebasymedia");
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
