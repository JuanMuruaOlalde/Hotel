package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


public class PersistenciaDeHabitacionesEnArchivoJSON implements PersistenciaDeHabitaciones {

	java.nio.file.Path pathDelArchivo;
	private com.fasterxml.jackson.databind.ObjectMapper mapper;
	java.util.ArrayList<Habitacion> habitaciones;
	
	public PersistenciaDeHabitacionesEnArchivoJSON(java.nio.file.Path carpetaDondeUbicarArchivo) throws IOException {
		pathDelArchivo = carpetaDondeUbicarArchivo.resolve("habitaciones.json");
		if(!pathDelArchivo.toFile().exists()) {
			pathDelArchivo.toFile().createNewFile();
		}
		mapper = new com.fasterxml.jackson.databind.ObjectMapper();
		habitaciones = new ArrayList<Habitacion>();
		try {
			JsonNode datos;
			datos = mapper.readTree(pathDelArchivo.toFile());
			java.util.Iterator<JsonNode> nodos = datos.elements();
			while (nodos.hasNext()) {
				JsonNode nodo = nodos.next();
				habitaciones.add(mapper.treeToValue(nodo, Habitacion.class));
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void guardarTodasLasHabitaciones() {
		try {
			mapper.writeValue(pathDelArchivo.toFile(), habitaciones);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void añadirUnaNueva(Habitacion habitacion) {
		habitaciones.add(habitacion);
		guardarTodasLasHabitaciones();
	}

	@Override
	public Habitacion get(UUID id) {
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getIdInterno().equals(id)) {
				return habitacion;
			}
		}
		return null;
	}

	@Override
	public Habitacion get(String numeroDeHabitacion) {
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getNumeroDeHabitacion().equals(numeroDeHabitacion)) {
				return habitacion;
			}
		}
		return null;
	}

	@Override
	public java.util.List<Habitacion> getTodas() {
		return habitaciones;
	}

	@Override
	public java.util.List<Habitacion> getAquellasQueComiencenPor(String criterio) {
		java.util.ArrayList<Habitacion> encontradas = new ArrayList<>();
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getNumeroDeHabitacion().startsWith(criterio)) {
				 encontradas.add(habitacion);
			}
		}
		return encontradas;
	}

	@Override
	public void inactivar(UUID id) {
		get(id).setEstaActiva(false);
		guardarTodasLasHabitaciones();
	}

	@Override
	public void activar(UUID id) {
		get(id).setEstaActiva(true);
		guardarTodasLasHabitaciones();
	}

}
