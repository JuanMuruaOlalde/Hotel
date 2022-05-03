package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;

import java.util.UUID;
import java.util.List;


public class BuscadorDeEstancias {

    private PersistenciaDeEstancias persistencia;
    
    public BuscadorDeEstancias(PersistenciaDeEstancias persistencia) {
        this.persistencia = persistencia;
    }
    
    public Estancia get(UUID id) {
        return persistencia.get(id);
    }
    
    public boolean algunaDeLasHabitacionesEstaOcupada(List<Habitacion> habitaciones) {
        List<Estancia> estancias = persistencia.getEstanciasAsociadasAAlgunaDeEstasHabitaciones(habitaciones);
        if (estancias.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

}
