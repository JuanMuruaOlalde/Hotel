package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.util.UUID;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;

public class ModificadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public ModificadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public void guardarCambios(Habitacion habitacion) {
	    
	}
	
	public void cambiarTipoDeHabitacion(java.util.UUID idInterno, TipoDeHabitacion nuevoTipo) throws IOException {
	    Habitacion habitacionAModificar = persistencia.get(idInterno);
	    habitacionAModificar.setTipoDeHabitacion(nuevoTipo);
	    persistencia.guardarCambios(habitacionAModificar);
	}
	
	public void cambiarTipoDeBaño(UUID idInterno, TipoDeBaño nuevoTipo) throws IOException {
        Habitacion habitacionAModificar = persistencia.get(idInterno);
        habitacionAModificar.setTipoDeBaño(nuevoTipo);
        persistencia.guardarCambios(habitacionAModificar);
    }

}
