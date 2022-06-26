package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class GestorDeHabitaciones implements GestionDeHabitaciones {

    private PersistenciaDeHabitaciones persistencia;
    
    public GestorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
        this.persistencia = persistencia;
    }


    
    @Override
    public Habitacion get(String numeroDeHabitacion) {
        return persistencia.get(numeroDeHabitacion);
    }
    
    @Override
    public java.util.List<Habitacion> getTodas() {
        return persistencia.getTodas();
    }
    
    @Override
    public java.util.List<Habitacion> getAquellasCuyoNumeroComiencePor(String criterio) {
        return persistencia.getAquellasCuyoNumeroComiencePor(criterio);
    }
    
    protected Habitacion get(java.util.UUID idInterno) {
        return persistencia.get(idInterno);
    }

    
   
    
    @Override
    public Habitacion crearUnaNueva(String numeroDeHabitacion) throws IllegalArgumentException, IOException {
        if (numeroDeHabitacion.isBlank()) {
            throw new IllegalArgumentException("No se puede crear una habitación con número/nombre en blanco.");
        } else {
            Habitacion existente = persistencia.get(numeroDeHabitacion);
            if (existente == null) {
                Habitacion nueva = new Habitacion(UUID.randomUUID(), numeroDeHabitacion);
                persistencia.añadirUnaNueva(nueva);
                return nueva;
            }
            else {
                throw new IllegalArgumentException("Intento de duplicar la habitación. Ya existe una habitación [" + numeroDeHabitacion + "]");
            }
        }
    }
    

       
    @Override
    public void guardarCambios(Habitacion datosAGuardar) throws IOException {
        persistencia.guardarCambios(datosAGuardar);
    }
    
    
    
    @Override
    public void desactivarHabitacion(java.util.UUID id) throws IOException {
        persistencia.inactivar(id);
    }
    
    @Override
    public void reactivarHabitacion(java.util.UUID id) throws IOException {
        persistencia.activar(id);
    }

    // Una función para eliminar (borrar) completamente 
    // solo será necesaria en caso de estar haciendo limpieza
    // si los historicos crecen demasiado.
    // Eliminar registros siempre es complicado,
    // ya que otros registros de otro tipo pueden depender de los borrados.
    // Cuando se decida implementar el borrado, hay que pensarlo bien.
    

    
    
    public static Habitacion getUnaHabitacionDePrueba() {
        return new Habitacion(java.util.UUID.fromString("b163aed7-1606-49e0-87d5-399db7a76037"),
                              true, "101", TipoDeHabitacion.SUITE, TipoDeBaño.JACUZZI);
    }
    
    public static java.util.List<Habitacion> getUnGrupoDeHabitacionesDePrueba() {
        java.util.ArrayList<Habitacion> habitaciones = new java.util.ArrayList<>();
        habitaciones.add(new Habitacion(java.util.UUID.fromString("9bbdac7d-4d53-49eb-b814-3c26594ae949"),
                                        true, "201", TipoDeHabitacion.SENCILLA, TipoDeBaño.DUCHA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("f0361fc2-87f8-428c-b86a-d3b55b0ad6dc"),
                                        true, "202", TipoDeHabitacion.SENCILLA, TipoDeBaño.BAÑERA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("088c4963-f15d-4545-b036-d5c5e42e776e"),
                                        true, "203", TipoDeHabitacion.DOBLE, TipoDeBaño.DUCHA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("2f47ebcd-c4b7-4ffc-985f-86f924219a81"),
                                        true, "204", TipoDeHabitacion.DOBLE, TipoDeBaño.DUCHA));
        return habitaciones;
    }

    
}
