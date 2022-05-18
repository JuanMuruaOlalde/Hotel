package es.susosise.hotel.habitaciones;

import java.util.Objects;
import java.util.UUID;

public class Habitacion {
	
	public enum TipoDeHabitacion {
		_SIN_ASIGNAR_AUN_,
		SENCILLA,
		DOBLE,
		SUITE
	}
	public enum TipoDeBaño {
		_SIN_ASIGNAR_AUN_,
		SINBAÑO,
		DUCHA,
		BAÑERA,
		JACUZZI
	}
	
	private UUID idInterno;
	private Boolean activa;
	private String numeroDeHabitacion;
	private TipoDeHabitacion tipoDeHabitacion;
	private TipoDeBaño tipoDeBaño;
	
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
	public TipoDeHabitacion getTipoDeHabitacion() {
		return tipoDeHabitacion;
	}
	public TipoDeBaño getTipoDeBaño() {
		return tipoDeBaño;
	}
	
	protected void setEstaActiva(Boolean estado) {
		this.activa = estado;
	}
	protected void setTipoDeHabitacion(TipoDeHabitacion tipo) {
		this.tipoDeHabitacion = tipo;
	}
	protected void setTipoDeBaño(TipoDeBaño tipo) {
		this.tipoDeBaño = tipo;
	}

	protected Habitacion(UUID idInterno,
                         Boolean activa,
                         String numeroDeHabitacion,
                         TipoDeHabitacion tipo,
                         TipoDeBaño tipoDeBaño) {
        this.idInterno = idInterno;
        this.activa = activa;
        this.numeroDeHabitacion = numeroDeHabitacion;
        this.tipoDeHabitacion = tipo;
        this.tipoDeBaño = tipoDeBaño;
    }

	protected Habitacion(UUID idInterno, String numeroDeHabitacion) {
		this.idInterno = idInterno;
		this.activa = true;
		this.numeroDeHabitacion = numeroDeHabitacion;
		this.tipoDeHabitacion = TipoDeHabitacion._SIN_ASIGNAR_AUN_;
		this.tipoDeBaño = TipoDeBaño._SIN_ASIGNAR_AUN_;
	}
	
	

	@Override
    public int hashCode() {
		return Objects.hash(this.idInterno,
				            this.activa,
				            this.numeroDeHabitacion,
				            this.tipoDeHabitacion,
				            this.tipoDeBaño);
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
			&& Objects.equals(this.activa, unaHabitacion.activa)
			&& Objects.equals(this.numeroDeHabitacion, unaHabitacion.numeroDeHabitacion)
			&& Objects.equals(this.tipoDeHabitacion, unaHabitacion.tipoDeHabitacion)
			&& Objects.equals(this.tipoDeBaño, unaHabitacion.tipoDeBaño);
	}
	

}
