package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.UUID;

public class CreadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public CreadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion crearUnaNueva(String numeroDeHabitacion) throws IOException {
		Habitacion nueva = new Habitacion(UUID.randomUUID(), numeroDeHabitacion);
		persistencia.añadirUnaNueva(nueva);
		return nueva;
	}

}
