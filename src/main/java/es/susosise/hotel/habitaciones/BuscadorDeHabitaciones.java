package es.susosise.hotel.habitaciones;

public class BuscadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public BuscadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion get(String numeroDeHabitacion) {
		return persistencia.get(numeroDeHabitacion);
	}
	
	public Habitacion get(java.util.UUID idInterno) {
		return persistencia.get(idInterno);
	}
	
	public java.util.List<Habitacion> getTodas() {
		return persistencia.getTodas();
	}
	
	public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
		return persistencia.getAquellasCuyoNumeroComiencePor(criterio);
	}
	
}
