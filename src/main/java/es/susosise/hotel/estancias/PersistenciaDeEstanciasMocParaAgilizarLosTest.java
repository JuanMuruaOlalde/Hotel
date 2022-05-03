package es.susosise.hotel.estancias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Estancia> getEstanciasAsociadasAAlgunaDeEstasHabitaciones(List<Habitacion> habitaciones) {
        ArrayList<Estancia> estanciasEncontradas = new ArrayList<>();
        
        for(Estancia estancia : almacenDeEstancias) {
            for (java.util.UUID idHabitacion : estancia.getHabitaciones()) {
               for (Habitacion habitacion : habitaciones) {
                   if (habitacion.getIdInterno().equals(idHabitacion)) {
                       estanciasEncontradas.add(estancia);
                   }
               }
            }
        }
        
        return estanciasEncontradas;
    }

}
