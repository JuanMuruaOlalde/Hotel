package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;

import java.util.UUID;
import java.util.List;


public class BuscadorDeEstancias {

    private PersistenciaDeEstancias persistencia;
    
    public BuscadorDeEstancias(PersistenciaDeEstancias persistencia) {
        this.persistencia = persistencia;
    }
    
    public Estancia get(UUID idInterno) {
        return persistencia.get(idInterno);
    }
    
    
    public boolean algunaDeLasHabitacionesEstaOcupada(List<Habitacion> habitaciones) {
        List<UUID> estancias = persistencia.getEstanciasActivasAsociadasAAlgunaDeEstasHabitaciones(habitaciones);
        if (estancias.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

}
