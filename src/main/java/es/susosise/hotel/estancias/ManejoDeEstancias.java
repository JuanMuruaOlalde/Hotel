package es.susosise.hotel.estancias;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huesped;

public class ManejoDeEstancias {
    private PersistenciaDeEstancias persistencia;
    
    public ManejoDeEstancias(PersistenciaDeEstancias persistencia) {
        this.persistencia = persistencia;
    }
    
    public Estancia get(UUID idInterno) {
        return persistencia.get(idInterno);
    }

    public List<Estancia> getEstanciasActivasEnEsteMomento() {
        return persistencia.getEstanciasActivasEnEsteMomento();
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

    
    
    public Estancia crearUnaNueva(List<Habitacion> habitaciones, 
                                  LocalDate fechaEntrada, LocalDate fechaSalida, 
                                  List<Huesped> huespedes)
                                throws IOException {
        
        if (fechaEntrada.isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar una estancia que comienza en el futuro, " 
                                             + " con fecha de entrada ["+ fechaEntrada.toString() + "]");
        }
        else if (fechaEntrada.isAfter(fechaSalida)) {
            throw new IllegalArgumentException("No se puede registrar una estancia que termina antes de que empiece, " 
                                             + " con fecha de entrada ["+ fechaEntrada.toString() + "] "
                                             + " y de salida [" + fechaSalida.toString() + "]");
        }
        else if (habitaciones.isEmpty()){
            throw new IllegalArgumentException("No se puede registrar una estancia sin habitación a ocupar."); 
        }
        else if (huespedes.isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una estancia sin huesped al que facturar."); 
        }
        else if (algunaDeLasHabitacionesEstaOcupada(habitaciones)) {
            throw new IllegalArgumentException("No se puede registrar una estancia en una habitación ya ocupada.");
        }
        else {
            ArrayList<UUID> listaDeHabitaciones = new ArrayList<>();
            for (Habitacion habitacion : habitaciones) {
                listaDeHabitaciones.add(habitacion.getIdInterno());
            }
            ArrayList<UUID> listaDeHuespedes = new ArrayList<>();
            for (Huesped huesped : huespedes) {
                listaDeHuespedes.add(huesped.getIdInterno());
            }
            Estancia estancia = new Estancia(java.util.UUID.randomUUID(),
                                             listaDeHabitaciones, 
                                             fechaEntrada, fechaSalida, 
                                             listaDeHuespedes);
            persistencia.añadirUnaNueva(estancia);
            return estancia;
        }
    }

}
