package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;


public class PersistenciaDeHabitacionesEnArchivoJSON implements PersistenciaDeHabitaciones {

	java.nio.file.Path pathDelArchivo;
	private com.fasterxml.jackson.databind.ObjectMapper mapper;
	
	public PersistenciaDeHabitacionesEnArchivoJSON(java.nio.file.Path carpetaDondeUbicarArchivo) throws IOException {
		pathDelArchivo = carpetaDondeUbicarArchivo.resolve("habitaciones.json");
		if(!pathDelArchivo.toFile().exists()) {
			pathDelArchivo.toFile().createNewFile();
		}
		mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	}
	
	
	@Override
	public void guardar(Habitacion unaHabitacion) {
		try {
			java.nio.file.Files.writeString(pathDelArchivo, 
					                        mapper.writeValueAsString(unaHabitacion) + System.getProperty("line.separator"), 
			                                StandardOpenOption.APPEND);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Habitacion get(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habitacion get(String numeroDeHabitacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Habitacion> getTodas() {
		ArrayList<Habitacion> habitaciones = new ArrayList<>();
		// TODO Auto-generated method stub
		return habitaciones;
	}

	@Override
	public java.util.List<Habitacion> getAquellasQueComiencenPor(String criterio) {
		ArrayList<Habitacion> habitaciones = new ArrayList<>();
		// TODO Auto-generated method stub
		return habitaciones;
	}

	@Override
	public void inactivar(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activar(UUID id) {
		// TODO Auto-generated method stub
		
	}

}
