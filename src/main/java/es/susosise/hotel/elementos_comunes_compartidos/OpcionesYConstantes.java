package es.susosise.hotel.elementos_comunes_compartidos;

public class OpcionesYConstantes {
	
	public static java.nio.file.Path getDataFolderPath() {
		//java.nio.file.Path path = java.nio.file.Paths.get(System.getProperty("user.home"), "Hotel_data");
		java.nio.file.Path path = java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_data");
		if (!path.toFile().exists()) {
			path.toFile().mkdir();
		}
		return path;
	}


}
