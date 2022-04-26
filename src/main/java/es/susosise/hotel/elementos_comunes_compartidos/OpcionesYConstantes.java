package es.susosise.hotel.elementos_comunes_compartidos;

public final class OpcionesYConstantes {
	
	private OpcionesYConstantes() {
		throw new IllegalStateException("Se supone que esta clase no se instancia.");
	}
	
	public static java.nio.file.Path getCarpetaDeDatosPorDefecto() {
		java.nio.file.Path path = java.nio.file.Paths.get("C:\\Users\\Public", "Hotel_data");
		if (!path.toFile().exists()) {
			path.toFile().mkdir();
		}
		return path;
	}


}
