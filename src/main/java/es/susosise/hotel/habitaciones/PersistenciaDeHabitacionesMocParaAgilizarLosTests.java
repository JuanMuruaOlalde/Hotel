package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

final class PersistenciaDeHabitacionesMocParaAgilizarLosTests implements PersistenciaDeHabitaciones {

    java.util.ArrayList<Habitacion> habitaciones;
    
    public PersistenciaDeHabitacionesMocParaAgilizarLosTests() {
        habitaciones = new java.util.ArrayList<Habitacion>();
    }

    
    @Override
    public void añadirUnaNueva(Habitacion habitacion) throws IOException {
        habitaciones.add(habitacion);
    }

    @Override
    public Habitacion get(UUID id) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(id)) {
                return habitacion;
            }
        }
        return null;
    }

    @Override
    public Habitacion get(String numeroDeHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumeroDeHabitacion().equals(numeroDeHabitacion)) {
                return habitacion;
            }
        }
        return null;
    }

    @Override
    public java.util.List<Habitacion> getTodas() {
        return habitaciones;
    }

    @Override
    public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
        java.util.ArrayList<Habitacion> encontradas = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumeroDeHabitacion().startsWith(criterio)) {
                 encontradas.add(habitacion);
            }
        }
        return encontradas;
    }

    @Override
    public void inactivar(UUID id) throws IOException {
        get(id).setEstaActiva(false);
    }

    @Override
    public void activar(UUID id) throws IOException {
        get(id).setEstaActiva(true);
    }

    @Override
    public void cambiarTipoDeHabitacion(java.util.UUID id, TipoDeHabitacion nuevoTipo) throws IOException {
        get(id).setTipo(nuevoTipo);
    }

    @Override
    public void cambiarTipoDeBaño(UUID id, TipoDeBaño nuevoTipo) throws IOException {
        get(id).setTipoDeBaño(nuevoTipo);
    }
	
}
