package es.susosise.hotel.habitaciones;

public class CreadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public CreadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion crear(String numeroDeHabitacion) {
		Habitacion nueva = new Habitacion(numeroDeHabitacion);
		persistencia.guardar(nueva);
		return nueva;
	}

}
