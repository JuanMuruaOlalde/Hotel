package es.susosise.hotel.estancias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion;


final class PersistenciaDeEstanciasMocParaAgilizarLosTest implements PersistenciaDeEstancias {
    
    private ArrayList<Estancia> almacenDeEstancias;
    
    public PersistenciaDeEstanciasMocParaAgilizarLosTest() {
        almacenDeEstancias = new ArrayList<>();
    }

    
    @Override
    public void añadirUnaNueva(Estancia estancia) throws IOException {
        almacenDeEstancias.add(estancia);
    }



    @Override
    public Estancia get(UUID idInterno) {
        for(Estancia estancia : almacenDeEstancias) {
            if (estancia.getIdInterno().equals(idInterno)) {
                return estancia;
            }
        }
        return null;
    }


    @Override
    public List<Estancia> getEstanciasActivasEnEsteMomento() {
        List<Estancia> estanciasEncontradas = new ArrayList<>();
        
        for(Estancia estancia : almacenDeEstancias) {
            java.time.LocalDate hoy = java.time.LocalDate.now();
            if (estancia.getFechaSalida().isAfter(hoy)) {
                estanciasEncontradas.add(estancia);
            }
        }
        
        return estanciasEncontradas;
    }

    

    @Override
    public List<UUID> getEstanciasActivasAsociadasAAlgunaDeEstasHabitaciones(List<Habitacion> habitaciones) {
        ArrayList<UUID> estanciasEncontradas = new ArrayList<>();
        
        for(Estancia estancia : almacenDeEstancias) {
            if (estancia.getFechaSalida().isAfter(java.time.LocalDate.now())) {
                for (java.util.UUID idHabitacion : estancia.getHabitaciones()) {
                   for (Habitacion habitacion : habitaciones) {
                       if (habitacion.getIdInterno().equals(idHabitacion)) {
                           estanciasEncontradas.add(estancia.getIdInterno());
                       }
                   }
                }
            }
        }
        
        return estanciasEncontradas;
    }

    
}
