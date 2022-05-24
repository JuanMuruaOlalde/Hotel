package es.susosise.hotel.estancias;

import java.util.UUID;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Estancia {
    
    private UUID idInterno;
    private List<UUID> habitaciones;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private List<UUID> huespedes;

    public UUID getIdInterno() { return idInterno; }
    public List<UUID> getHabitaciones() { return habitaciones; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public List<UUID> getHuespedes() { return huespedes; }
    
    protected Estancia(UUID idInterno,
                       List<UUID> habitaciones,
                       LocalDate fechaEntrada,
                       LocalDate fechaSalida,
                       List<UUID> huespedes) {
        this.idInterno = idInterno;
        this.habitaciones = habitaciones;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.huespedes = huespedes;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.idInterno,
                            this.fechaEntrada,
                            this.fechaSalida);
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
        Estancia estancia = (Estancia)unObjeto;
        return Objects.equals(this.idInterno, estancia.idInterno)
            && Objects.equals(this.fechaEntrada, estancia.fechaEntrada)
            && Objects.equals(this.fechaSalida, estancia.fechaSalida);
    }

}
