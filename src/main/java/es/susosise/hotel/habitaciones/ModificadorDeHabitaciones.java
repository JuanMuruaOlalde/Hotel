package es.susosise.hotel.habitaciones;

import java.io.IOException;

public class ModificadorDeHabitaciones {
	
	private PersistenciaDeHabitaciones persistencia;
	
	public ModificadorDeHabitaciones(PersistenciaDeHabitaciones persistencia) {
		this.persistencia = persistencia;
	}
	
	public void guardarCambios(Habitacion datosAGuardar) throws IOException {
	    persistencia.guardarCambios(datosAGuardar);
	}
	

}
