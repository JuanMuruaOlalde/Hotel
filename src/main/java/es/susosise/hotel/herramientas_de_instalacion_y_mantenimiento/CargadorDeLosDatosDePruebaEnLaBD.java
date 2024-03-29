package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import java.io.IOException;

import es.susosise.hotel.habitaciones.*;


public class CargadorDeLosDatosDePruebaEnLaBD {
    
    private java.sql.Connection baseDeDatos;

    public CargadorDeLosDatosDePruebaEnLaBD(java.sql.Connection baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    
    public void cargar() throws IOException {
        
        PersistenciaDeHabitacionesEnMariaDB bdHabitaciones = new PersistenciaDeHabitacionesEnMariaDB(baseDeDatos);
        for (Habitacion habitacion : ManejoDeHabitaciones.getUnasCuantasParaPruebas()) {
            bdHabitaciones.añadirUnaNueva(habitacion);
        }
        
        // TODO  aqui iremos poniendo el resto de datos de prueba... a medida que vayamos completando cada entidad del dominio...
        
    }

}
