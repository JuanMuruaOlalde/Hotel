package es.susosise.hotel.habitaciones;

import java.util.Objects;
import java.util.UUID;

public class Habitacion {
	
	UUID idInterno;
	String numeroDeHabitacion;
	
	public Habitacion(String numeroDeHabitacion) {
		this.idInterno = UUID.randomUUID();
		this.numeroDeHabitacion = numeroDeHabitacion;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(this.idInterno,
				            this.numeroDeHabitacion);
	}
	
	@Override
	public boolean equals(Object unObjeto) {
		if (this == unObjeto) {
			return true;
		}
		if (unObjeto == null) {
			return false;
		}
		if (getClass() != unObjeto.getClass()) {
			return false;
		}
		Habitacion unaHabitacion = (Habitacion)unObjeto;
		return Objects.equals(this.idInterno, unaHabitacion.idInterno)
			&& Objects.equals(this.numeroDeHabitacion, unaHabitacion.numeroDeHabitacion);
	}
	

}
