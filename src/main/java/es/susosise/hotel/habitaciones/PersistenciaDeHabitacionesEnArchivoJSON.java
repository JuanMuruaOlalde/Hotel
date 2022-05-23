package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.file.Path;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;


final class PersistenciaDeHabitacionesEnArchivoJSON implements PersistenciaDeHabitaciones {

	private Path pathDelArchivo;
	private File archivo;
	private ObjectMapper mapper;
	private ArrayList<Habitacion> cacheDeHabitaciones;
	
	public PersistenciaDeHabitacionesEnArchivoJSON(Path carpetaDondeUbicarArchivo) throws IOException {
		pathDelArchivo = carpetaDondeUbicarArchivo.resolve("habitaciones.json");
		archivo = pathDelArchivo.toFile();
		if(!archivo.exists()) {
			archivo.createNewFile();
		}
		mapper = new ObjectMapper();
		cacheDeHabitaciones = leerDatosDesdeElArchivo();
	}

    private ArrayList<Habitacion> leerDatosDesdeElArchivo() throws IOException {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        JsonNode datos;
		datos = mapper.readTree(archivo);
		java.util.Iterator<JsonNode> nodos = datos.elements();
		while (nodos.hasNext()) {
			JsonNode nodo = nodos.next();
			Habitacion habitacion = mapper.treeToValue(nodo, Habitacion.class);
			habitaciones.add(habitacion);
		}
		return habitaciones;
    }
	
	private void guardarDatosAlArchivo(ArrayList<Habitacion> habitaciones) throws IOException {
		try {
            mapper.writeValue(archivo, habitaciones);
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
	    ArrayList<Habitacion> habitaciones = leerDatosDesdeElArchivo();
		habitaciones.add(habitacion);
		guardarDatosAlArchivo(habitaciones);
		cacheDeHabitaciones = habitaciones;
	}

	@Override
	public Habitacion get(UUID id) {
		for (Habitacion habitacion : cacheDeHabitaciones) {
			if (habitacion.getIdInterno().equals(id)) {
				return habitacion.getUnDeepClon();
			}
		}
		return null;
	}

	@Override
	public Habitacion get(String numeroDeHabitacion) {
		for (Habitacion habitacion : cacheDeHabitaciones) {
			if (habitacion.getNumeroDeHabitacion().equals(numeroDeHabitacion)) {
				return habitacion.getUnDeepClon();
			}
		}
		return null;
	}

	@Override
	public java.util.List<Habitacion> getTodas() {
		return cacheDeHabitaciones;
	}

	@Override
	public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
		java.util.ArrayList<Habitacion> encontradas = new ArrayList<>();
		for (Habitacion habitacion : cacheDeHabitaciones) {
			if (habitacion.getNumeroDeHabitacion().startsWith(criterio)) {
				 encontradas.add(habitacion.getUnDeepClon());
			}
		}
		return encontradas;
	}


    @Override
    public void guardarCambios(Habitacion datosAGuardar) throws IOException {
        ArrayList<Habitacion> habitaciones = leerDatosDesdeElArchivo();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(datosAGuardar.getIdInterno())) {
                habitacion.copiarDatosDesde(datosAGuardar);
            }
        }
        guardarDatosAlArchivo(habitaciones);
        cacheDeHabitaciones = habitaciones;
    }
    
    
	@Override
	public void inactivar(UUID id) throws IOException {
        modificarActivacion(id, false);
	}

	@Override
	public void activar(UUID id) throws IOException {
	    modificarActivacion(id, true);
	}
	
	private void modificarActivacion(UUID id, Boolean nuevoEstado) throws IOException {
        ArrayList<Habitacion> habitaciones = leerDatosDesdeElArchivo();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(id)) {
                habitacion.setEstaActiva(nuevoEstado);
            }
        }
        guardarDatosAlArchivo(habitaciones);
        cacheDeHabitaciones = habitaciones;
	}


}
