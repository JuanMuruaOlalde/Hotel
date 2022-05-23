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
	
	public void guardarCambios(Habitacion datosAGuardar) throws IOException {
	    persistencia.guardarCambios(datosAGuardar);
	}
	

}
