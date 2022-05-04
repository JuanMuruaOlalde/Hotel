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
	
	public void cambiarTipoDeHabitacion(java.util.UUID idInterno, TipoDeHabitacion nuevoTipo) throws IOException {
	    persistencia.cambiarTipoDeHabitacion(idInterno, nuevoTipo);
	}
	
	public void cambiarTipoDeBaño(UUID idInterno, TipoDeBaño nuevoTipo) throws IOException {
        persistencia.cambiarTipoDeBaño(idInterno, nuevoTipo);
    }

}
