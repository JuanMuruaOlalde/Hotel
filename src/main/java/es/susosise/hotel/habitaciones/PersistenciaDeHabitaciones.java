package es.susosise.hotel.habitaciones;

public interface PersistenciaDeHabitaciones {
	
	public void guardar(Habitacion unaHabitacion);
	
	public Habitacion get(java.util.UUID id);
	public Habitacion get(String numeroDeHabitacion);
	public java.util.List<Habitacion> getTodas();
	public java.util.List<Habitacion> getAquellasQueComiencenPor(String criterio);
	
	public void inactivar(java.util.UUID id);
	public void activar(java.util.UUID id);
	//public void eliminar(java.util.UUID id); // a no ser que el historico crezca demasiado, mejor inactivar que eliminar.	

}
