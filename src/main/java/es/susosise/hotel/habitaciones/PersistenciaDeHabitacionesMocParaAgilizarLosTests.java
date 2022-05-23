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
                return habitacion.getUnDeepClon();
            }
        }
        return null;
    }

    @Override
    public Habitacion get(String numeroDeHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumeroDeHabitacion().equals(numeroDeHabitacion)) {
                return habitacion.getUnDeepClon();
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
                 encontradas.add(habitacion.getUnDeepClon());
            }
        }
        return encontradas;
    }

    
    @Override
    public void guardarCambios(Habitacion datosAGuardar) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(datosAGuardar.getIdInterno())) {
                habitacion.copiarDatosDesde(datosAGuardar);
            }
        }
    }

	
    @Override
    public void inactivar(UUID id) throws IOException {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(id)) {
                habitacion.setEstaActiva(false);
            }
        }
    }

    @Override
    public void activar(UUID id) throws IOException {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdInterno().equals(id)) {
                habitacion.setEstaActiva(true);
            }
        }
    }

}
