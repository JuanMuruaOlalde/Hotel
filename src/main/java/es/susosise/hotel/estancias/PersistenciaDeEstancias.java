package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;

import java.io.IOException;
import java.util.List;


public interface PersistenciaDeEstancias {

    public void añadirUnaNueva(Estancia estancia) throws IOException;
    
    public List<Estancia> getEstanciasAsociadasAAlgunaDeEstasHabitaciones(List<Habitacion> habitaciones);

}
