package es.susosise.hotel.elementos_comunes_compartidos;

import java.sql.SQLException;

public final class OpcionesYConstantes {
    
    public java.nio.file.Path carpetaDeDatos;
    public java.sql.Connection servidorDeDatos;
    
    private final class CredencialesDeConexionJDBC {
        public String basededatos;
        protected String usuario;
        protected String contraseña;
    }
	
    
	public OpcionesYConstantes() throws SQLException {
		carpetaDeDatos = getCarpetaDeDatosPorDefecto();
		servidorDeDatos = getConexionPorDefectoConElServidorDeDatos();
	}

	
	private java.nio.file.Path getCarpetaDeDatosPorDefecto() {
		java.nio.file.Path path = java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_data");
		if (!path.toFile().exists()) {
			path.toFile().mkdir();
		}
		return path;
	}

    private java.sql.Connection getConexionPorDefectoConElServidorDeDatos() throws SQLException {
        CredencialesDeConexionJDBC credenciales = new CredencialesDeConexionJDBC();
        credenciales.basededatos = "mariadb://localhost:3306/Hotel";
        credenciales.usuario = "root";
        credenciales.contraseña = "89Pruebasymedia";
        return java.sql.DriverManager.getConnection("jdbc:" + credenciales.basededatos + "?user=" + credenciales.usuario + "&password=" + credenciales.contraseña);
    }

}
