package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class CreadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public CreadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public Habitacion crearUnaNueva(String numeroDeHabitacion) throws IllegalArgumentException, IOException {
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
	
	
	public static Habitacion getLaHabitacionDePrueba() {
	    return new Habitacion(java.util.UUID.fromString("b163aed7-1606-49e0-87d5-399db7a76037"),
                              true, "101", TipoDeHabitacion.SUITE, TipoDeBaño.JACUZZI);
	}
	public static java.util.List<Habitacion> getElGrupoDeHabitacionesDePrueba() {
	    java.util.ArrayList<Habitacion> habitaciones = new java.util.ArrayList<>();
        habitaciones.add(getLaHabitacionDePrueba());
	    habitaciones.add(new Habitacion(java.util.UUID.fromString("9bbdac7d-4d53-49eb-b814-3c26594ae949"),
	                                    true, "102", TipoDeHabitacion.SENCILLA, TipoDeBaño.DUCHA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("f0361fc2-87f8-428c-b86a-d3b55b0ad6dc"),
                                        true, "103", TipoDeHabitacion.SENCILLA, TipoDeBaño.BAÑERA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("088c4963-f15d-4545-b036-d5c5e42e776e"),
                                        true, "104", TipoDeHabitacion.DOBLE, TipoDeBaño.DUCHA));
        habitaciones.add(new Habitacion(java.util.UUID.fromString("2f47ebcd-c4b7-4ffc-985f-86f924219a81"),
                                        true, "105", TipoDeHabitacion.DOBLE, TipoDeBaño.DUCHA));
	    return habitaciones;
	}

}
