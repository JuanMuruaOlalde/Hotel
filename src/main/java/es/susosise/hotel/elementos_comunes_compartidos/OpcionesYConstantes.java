package es.susosise.hotel.elementos_comunes_compartidos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import es.susosise.hotel.habitaciones.PersistenciaDeHabitacionesEnArchivoJSON;

public final class OpcionesYConstantes {
    
    private java.nio.file.Path carpetaDeDatos;
    public java.nio.file.Path getCarpetaDeDatos() { return carpetaDeDatos; }

    private java.sql.Connection servidorDeDatos;
    public java.sql.Connection getServidorDeDatos() { return servidorDeDatos; }
    
   
    
	public OpcionesYConstantes(java.nio.file.Path archivoDeConfiguracion) throws IOException, SQLException {
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
	                CredencialesDeConexion credenciales;
	                credenciales = mapper.treeToValue(nodo.fields().next().getValue(), CredencialesDeConexion.class);
	                servidorDeDatos = getConexionConElServidorDeDatos(credenciales);
	                break;
	        }
	    }
	}
	
    
    private java.sql.Connection getConexionConElServidorDeDatos(CredencialesDeConexion credenciales) throws SQLException {
         return java.sql.DriverManager.getConnection("jdbc:" + credenciales.baseDeDatos + "?user=" + credenciales.usuario + "&password=" + credenciales.contraseña);
    }
    
    
    
    
    
    public static java.sql.Connection getServidorDeDatosParaPruebas() throws SQLException {
        java.sql.Connection conexionTemporal = java.sql.DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=root&password=89Pruebasymedia");
        java.sql.Statement comando = conexionTemporal.createStatement();
        comando.execute("CREATE OR REPLACE DATABASE Pruebas");
        comando.close();
        conexionTemporal.close();
        return java.sql.DriverManager.getConnection("jdbc:mariadb://localhost:3306/Pruebas?user=usuarioDePruebas&password=89Pruebasymedia");
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
