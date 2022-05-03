package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;


final class PersistenciaDeHabitacionesEnArchivoJSON implements PersistenciaDeHabitaciones {

	java.nio.file.Path pathDelArchivo;
	private com.fasterxml.jackson.databind.ObjectMapper mapper;
	java.util.ArrayList<Habitacion> habitaciones;
	
	public PersistenciaDeHabitacionesEnArchivoJSON(java.nio.file.Path carpetaDondeUbicarArchivo) throws IOException {
		mapper = new com.fasterxml.jackson.databind.ObjectMapper();
		
		habitaciones = new ArrayList<Habitacion>();
		
		pathDelArchivo = carpetaDondeUbicarArchivo.resolve("habitaciones.json");
		if(!pathDelArchivo.toFile().exists()) {
			pathDelArchivo.toFile().createNewFile();
		}
		JsonNode datos;
		datos = mapper.readTree(pathDelArchivo.toFile());
		java.util.Iterator<JsonNode> nodos = datos.elements();
		while (nodos.hasNext()) {
			JsonNode nodo = nodos.next();
			habitaciones.add(mapper.treeToValue(nodo, Habitacion.class));
		}
	}
	
	private void guardarTodasLasHabitaciones() throws IOException {
		try {
            mapper.writeValue(pathDelArchivo.toFile(), habitaciones);
        } catch (StreamWriteException e) {
            throw new IOException("Error al escribir en el archivo " + pathDelArchivo.toString());
        } catch (DatabindException e) {
            throw new IOException("Error al procesar formato JSON para " + pathDelArchivo.toString());
        } catch (IOException e) {
            throw new IOException("Error en el archivo " + pathDelArchivo.toString());
        }
	}
	
	@Override
	public void añadirUnaNueva(Habitacion habitacion) throws IOException {
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
	public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
		java.util.ArrayList<Habitacion> encontradas = new ArrayList<>();
		for (Habitacion habitacion : habitaciones) {
			if (habitacion.getNumeroDeHabitacion().startsWith(criterio)) {
				 encontradas.add(habitacion);
			}
		}
		return encontradas;
	}

	@Override
	public void inactivar(UUID id) throws IOException {
		get(id).setEstaActiva(false);
		guardarTodasLasHabitaciones();
	}

	@Override
	public void activar(UUID id) throws IOException {
		get(id).setEstaActiva(true);
		guardarTodasLasHabitaciones();
	}

	@Override
	public void cambiarTipo(java.util.UUID id, TipoDeHabitacion tipo) throws IOException {
		get(id).setTipo(tipo);
		guardarTodasLasHabitaciones();
	}

	@Override
	public void cambiarTipoDeBaño(UUID id, TipoDeBaño tipo) throws IOException {
		get(id).setTipoDeBaño(tipo);
		guardarTodasLasHabitaciones();
	}

}
