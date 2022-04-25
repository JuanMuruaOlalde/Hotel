package es.susosise.hotel.habitaciones;

import java.util.Objects;
import java.util.UUID;

public class Habitacion {
	
	public enum TipoDeHabitacion {
		_SIN_ASIGNAR_AUN_,
		SIMPLE,
		DOBLE,
		SUITE
	}
	
	private UUID idInterno;
	private Boolean activa;
	private String numeroDeHabitacion;
	private TipoDeHabitacion tipo;
	
	public UUID getIdInterno() {
		return idInterno;
	}
	public Boolean getEstaActiva() {
		//nota: este getter es para el mecanismo de serializacion de la persistencia
		return activa;
	}
	public Boolean estaActiva() {
		//nota: este getter es para que quede más fácil de leer allá donde se compruebe si xxx.estaActiva()
		return activa;
	}
	public String getNumeroDeHabitacion() {
		return numeroDeHabitacion;
	}
	public TipoDeHabitacion getTipo() {
		return tipo;
	}
	
	protected void setEstaActiva(Boolean estado) {
		this.activa = estado;
	}
	protected void setTipo(TipoDeHabitacion tipo) {
		this.tipo = tipo;
	}

	
	public Habitacion(UUID idInterno, String numeroDeHabitacion) {
		this.idInterno = idInterno;
		this.activa = true;
		this.numeroDeHabitacion = numeroDeHabitacion;
		this.tipo = TipoDeHabitacion._SIN_ASIGNAR_AUN_;
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
