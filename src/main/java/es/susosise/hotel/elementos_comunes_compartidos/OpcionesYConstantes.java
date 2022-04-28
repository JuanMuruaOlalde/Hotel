package es.susosise.hotel.elementos_comunes_compartidos;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.JsonNode;

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
    


}
