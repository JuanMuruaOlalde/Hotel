package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public interface PersistenciaDeHabitaciones {
	
	public void añadirUnaNueva(Habitacion habitacion) throws IOException;
	
	public Habitacion get(java.util.UUID id);
	public Habitacion get(String numeroDeHabitacion);
	public java.util.List<Habitacion> getTodas();
	public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio);
	
	public void guardarCambios(Habitacion datosAGuardar) throws IOException;

	public void inactivar(java.util.UUID id) throws IOException;
	public void activar(java.util.UUID id) throws IOException;
	//public void eliminar(java.util.UUID id); // a no ser que el historico crezca demasiado, mejor inactivar que eliminar.	

}
