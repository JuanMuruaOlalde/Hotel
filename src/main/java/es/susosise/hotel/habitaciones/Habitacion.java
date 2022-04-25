package es.susosise.hotel.habitaciones;

import java.util.Objects;
import java.util.UUID;

public class Habitacion {
	
	private UUID idInterno;
	private Boolean activa;
	private String numeroDeHabitacion;
	
	public UUID getIdInterno() {
		return idInterno;
	}
	public Boolean getEstaActiva() {
		return activa;
	}
	public Boolean estaActiva() {
		//nota: este getter es solo para que quede más fácil de leer donde se compruebe si xxx.estaActiva()
		return activa;
	}
	protected void setEstaActiva(Boolean estado) {
		this.activa = estado;
	}
	public String getNumeroDeHabitacion() {
		return numeroDeHabitacion;
	}
	
	
	public Habitacion(UUID idInterno, String numeroDeHabitacion) {
		this.idInterno = idInterno;
		this.activa = true;
		this.numeroDeHabitacion = numeroDeHabitacion;
	}
	
	//nota: el que esté o no esté activa no influye en las comparaciones, sigue siendo la misma habitacion.
	
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
