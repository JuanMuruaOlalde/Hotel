package es.susosise.hotel.estancias;

import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huesped;

import java.time.LocalDate;
import java.util.List;


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
    
    public Estancia(UUID idInterno,
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


}
