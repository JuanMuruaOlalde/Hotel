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
	private Boolean estaActiva;
	private String numeroDeHabitacion;
	private TipoDeHabitacion tipoDeHabitacion;
	private TipoDeBaño tipoDeBaño;
	
	public UUID getIdInterno() {
		return idInterno;
	}
	public Boolean getEstaActiva() {
		//nota: este getter es para el mecanismo de serializacion de la persistencia
		return estaActiva;
	}
	public Boolean estaActiva() {
		//nota: este getter es para que quede más fácil de leer allá donde se compruebe si xxx.estaActiva()
		return estaActiva;
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
	
	protected void setEstaActiva(Boolean estaActiva) {
		this.estaActiva = estaActiva;
	}
    protected void setTipoDeHabitacion(TipoDeHabitacion tipoDeHabitacion) {
		this.tipoDeHabitacion = tipoDeHabitacion;
	}
	protected void setTipoDeBaño(TipoDeBaño tipoDeBaño) {
		this.tipoDeBaño = tipoDeBaño;
	}

	public Habitacion(UUID idInterno, String numeroDeHabitacion) {
		this.idInterno = idInterno;
		this.estaActiva = true;
		this.numeroDeHabitacion = numeroDeHabitacion;
		this.tipoDeHabitacion = TipoDeHabitacion._SIN_ASIGNAR_AUN_;
		this.tipoDeBaño = TipoDeBaño._SIN_ASIGNAR_AUN_;
	}
	
	protected Habitacion(UUID idInterno,
                         Boolean estaActiva,
                         String numeroDeHabitacion,
                         TipoDeHabitacion tipoDeHabitacion,
                         TipoDeBaño tipoDeBaño) {
        this.idInterno = idInterno;
        this.estaActiva = estaActiva;
        this.numeroDeHabitacion = numeroDeHabitacion;
        this.tipoDeHabitacion = tipoDeHabitacion;
        this.tipoDeBaño = tipoDeBaño;
    }
	
	protected void copiarDatosDesde(Habitacion habitacionConLosNuevosDatos) {
        this.estaActiva = habitacionConLosNuevosDatos.getEstaActiva();
        this.numeroDeHabitacion = habitacionConLosNuevosDatos.getNumeroDeHabitacion();
        this.tipoDeHabitacion = habitacionConLosNuevosDatos.getTipoDeHabitacion();
        this.tipoDeBaño = habitacionConLosNuevosDatos.getTipoDeBaño();
	}
	
	protected Habitacion getUnDeepClon() {
	    return new Habitacion (this.idInterno,
	                           this.estaActiva,
	                           this.numeroDeHabitacion,
	                           this.tipoDeHabitacion,
	                           this.tipoDeBaño);
	}
	
    //nota: estos dos setter son para el mecanismo de serializacion de la persistencia
    protected void setIdInterno(UUID idInterno) {
        this.idInterno = idInterno;
    }
    protected void setNumeroDeHabitacion(String numeroDeHabitacion) {
        this.numeroDeHabitacion = numeroDeHabitacion;
    }
	protected Habitacion() {
        //nota: este constructor es para el mecanismo de serializacion de la persistencia
	}
	

	@Override
    public int hashCode() {
		return Objects.hash(this.idInterno,
				            this.estaActiva,
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
			&& Objects.equals(this.estaActiva, unaHabitacion.estaActiva)
			&& Objects.equals(this.numeroDeHabitacion, unaHabitacion.numeroDeHabitacion)
			&& Objects.equals(this.tipoDeHabitacion, unaHabitacion.tipoDeHabitacion)
			&& Objects.equals(this.tipoDeBaño, unaHabitacion.tipoDeBaño);
	}
	

}
