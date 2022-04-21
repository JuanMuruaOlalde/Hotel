package es.susosise.hotel.habitaciones;

public class BuscadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public BuscadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion get(String numeroDeHabitacion) {
		return persistencia.get(numeroDeHabitacion);
	}

}
