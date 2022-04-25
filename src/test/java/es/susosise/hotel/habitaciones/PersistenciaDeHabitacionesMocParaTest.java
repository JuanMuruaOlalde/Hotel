package es.susosise.hotel.habitaciones;

import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class PersistenciaDeHabitacionesMocParaTest implements PersistenciaDeHabitaciones {

	@Override
	public void añadirUnaNueva(Habitacion unaHabitacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Habitacion get(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habitacion get(String numeroDeHabitacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Habitacion> getTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inactivar(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activar(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiarTipo(UUID id, TipoDeHabitacion tipo) {
		// TODO Auto-generated method stub
		
	}
	
}
