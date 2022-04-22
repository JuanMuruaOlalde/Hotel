package es.susosise.hotel.habitaciones;

public class EliminadorDeHabitaciones {
		
	private PersistenciaDeHabitaciones persistencia;
	
	public EliminadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}

	public void inactivar(java.util.UUID id) {
		persistencia.inactivar(id);
	}
	
	public void activar(java.util.UUID id) {
		persistencia.activar(id);
	}

	// Una función para eliminar (borrar) completamente 
	// solo será necesaria en caso de estar haciendo limpieza
	// si los historicos crecen demasiado.
	// Eliminar registros siempre es complicado,
	// ya que otros registros de otro tipo pueden depender de los borrados.
	// Cuando se decida implementar el borrado, hay que pensarlo bien.
	
}
