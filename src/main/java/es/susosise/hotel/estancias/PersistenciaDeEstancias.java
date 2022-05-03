package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public interface PersistenciaDeEstancias {

    public void añadirUnaNueva(Estancia estancia) throws IOException;
    
    public List<Estancia> getEstanciasAsociadasAAlgunaDeEstasHabitaciones(List<Habitacion> habitaciones);

    public Estancia get(UUID id);

}
