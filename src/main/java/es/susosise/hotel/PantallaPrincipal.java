package es.susosise.hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;


public class PantallaPrincipal {

    @FXML
    private javafx.scene.layout.HBox espacioCentral;
    @FXML
    private javafx.scene.control.Label lineaDeMensajes;
    
    
    @FXML
    public void initialize() {
        lineaDeMensajes.setText("");
    }

    
    @FXML
    protected void irAlEditorDeHabitaciones(ActionEvent evento) throws IOException {
        espacioCentral.getChildren().clear();
        java.net.URL location = getClass().getResource("habitaciones/PantallaEditorDeHabitaciones.fxml");
        Parent pantallaEditorDeHabitaciones = FXMLLoader.load(location);
        espacioCentral.getChildren().add(pantallaEditorDeHabitaciones);
        lineaDeMensajes.setText("Estamos trabajando con HABITACIONES");
    }

    
    @FXML
    protected void irAlEditorDeEstancias(ActionEvent evento) throws IOException {
        espacioCentral.getChildren().clear();
        java.net.URL location = getClass().getResource("estancias/PantallaEditorDeEstancias.fxml");
        Parent pantallaEditorDeEstancias = FXMLLoader.load(location);
        espacioCentral.getChildren().add(pantallaEditorDeEstancias);
        lineaDeMensajes.setText("Estamos trabajando con ESTANCIAS");
    }


}
