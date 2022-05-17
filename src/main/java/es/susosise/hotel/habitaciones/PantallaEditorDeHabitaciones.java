package es.susosise.hotel.habitaciones;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import es.susosise.hotel.App;
import javafx.event.ActionEvent;


public class PantallaEditorDeHabitaciones {

    @FXML private TextField numeroDeHabitacionABuscar;
    @FXML private TextField numeroDeHabitacion;
    @FXML private TextField tipoDeHabitacion;
    @FXML private TextField tipoDeBaño;
    
    
    @FXML
    private void buscarHabitacion(ActionEvent event) {
        Habitacion habitacion = App.buscadorDeHabitaciones.get(numeroDeHabitacionABuscar.getText());
        if (habitacion != null) {
            numeroDeHabitacion.setText(habitacion.getNumeroDeHabitacion());
            tipoDeHabitacion.setText(habitacion.getTipo().toString());
            tipoDeBaño.setText(habitacion.getTipoDeBaño().toString());
        }
    }

}
