package es.susosise.hotel.habitaciones;

import java.io.IOException;

public interface GestionDeHabitaciones {

    // Buscador
    
    Habitacion get(String numeroDeHabitacion);

    java.util.List<Habitacion> getTodas();

    java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio);

    
    // Modificador
    
    void guardarCambios(Habitacion datosAGuardar) throws IOException;
    
    
    // Creador
    
    Habitacion crearUnaNueva(String numeroDeHabitacion) throws IllegalArgumentException, IOException;

    
    // Eliminador
    
    void desactivarHabitacion(java.util.UUID id) throws IOException;

    void reactivarHabitacion(java.util.UUID id) throws IOException;

    
    
}
