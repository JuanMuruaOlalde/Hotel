package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huesped;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;


public class CreadorDeEstancias {
    
    private PersistenciaDeEstancias persistencia;
    
    public CreadorDeEstancias(PersistenciaDeEstancias persistencia) {
        this.persistencia = persistencia;
    }
    
    public Estancia crear(List<Habitacion> habitaciones, 
                          LocalDate fechaEntrada, LocalDate fechaSalida, 
                          List<Huesped> huespedes)
                        throws IOException {
        
        BuscadorDeEstancias buscador = new BuscadorDeEstancias(persistencia);
        
        if (fechaEntrada.isAfter(fechaSalida)) {
            throw new IllegalArgumentException("No se puede registrar la estancia. Fechas incorrectas:" 
                                             + " entrada ["+ fechaEntrada.toString() + "]"
                                             + " y salida [" + fechaSalida.toString() + "]");
        }
        else if (habitaciones.isEmpty()){
            throw new IllegalArgumentException("No se puede registrar una estancia sin habitación a ocupar."); 
        }
        else if (huespedes.isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una estancia sin huesped al que facturar."); 
        }
        else if (buscador.algunaDeLasHabitacionesEstaOcupada(habitaciones)) {
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
