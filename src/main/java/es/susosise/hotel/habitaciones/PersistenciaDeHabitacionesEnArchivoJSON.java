package es.susosise.hotel.habitaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class PersistenciaDeHabitacionesEnArchivoJSON implements PersistenciaDeHabitaciones {

	private java.io.File archivo;
	private com.fasterxml.jackson.databind.ObjectMapper mapper;
	
	public PersistenciaDeHabitacionesEnArchivoJSON(java.nio.file.Path carpetaDondeUbicarArchivo) {
		java.nio.file.Path pathDelArchivo = carpetaDondeUbicarArchivo.resolve("habitaciones.json");
		archivo = pathDelArchivo.toFile();
		mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	}
	
	
	@Override
	public void guardar(Habitacion unaHabitacion) {
		
		try {
			com.fasterxml.jackson.core.JsonGenerator generator;
			generator = mapper.getFactory().createGenerator(new java.io.FileOutputStream(archivo));
			mapper.writeValue(generator, unaHabitacion);
			generator.close();
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
	public List<Habitacion> getTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Habitacion> getAquellasQueComiencenPor(String criterio) {
		// TODO Auto-generated method stub
		return null;
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
