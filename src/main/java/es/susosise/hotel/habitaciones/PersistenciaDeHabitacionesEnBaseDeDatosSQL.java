package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class PersistenciaDeHabitacionesEnBaseDeDatosSQL implements PersistenciaDeHabitaciones {

    private java.sql.Connection baseDeDatos;
    
    public PersistenciaDeHabitacionesEnBaseDeDatosSQL(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    
    @Override
    public void añadirUnaNueva(Habitacion unaHabitacion) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Habitacion get(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Habitacion get(String numeroDeHabitacion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Habitacion> getTodas() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void inactivar(UUID id) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void activar(UUID id) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void cambiarTipo(UUID id, TipoDeHabitacion tipo) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void cambiarTipoDeBaño(UUID id, TipoDeBaño tipo) throws IOException {
        // TODO Auto-generated method stub

    }

}
