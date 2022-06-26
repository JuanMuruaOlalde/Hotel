package es.susosise.hotel.estancias;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huesped;

public interface GestionDeEstancias {
    
    Estancia get(UUID idInterno);

    List<Estancia> getEstanciasActivasEnEsteMomento();

    boolean algunaDeLasHabitacionesEstaOcupada(List<Habitacion> habitaciones);

    
    Estancia crear(List<Habitacion> habitaciones, 
                   LocalDate fechaEntrada, LocalDate fechaSalida,
                   List<Huesped> huespedes) 
                           throws IOException;

    
}
