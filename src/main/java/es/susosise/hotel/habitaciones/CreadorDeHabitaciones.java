package es.susosise.hotel.habitaciones;

import java.util.UUID;

public class CreadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public CreadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion crearUnaNueva(String numeroDeHabitacion) {
		Habitacion nueva = new Habitacion(UUID.randomUUID(), numeroDeHabitacion);
		persistencia.añadirUnaNueva(nueva);
		return nueva;
	}

}
