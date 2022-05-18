package es.susosise.hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javax.swing.GroupLayout.Alignment;

import javafx.event.ActionEvent;
import javafx.scene.Parent;


public class PantallaPrincipal {

    @FXML private javafx.scene.layout.VBox submenuDeConsultas;
    @FXML private javafx.scene.layout.VBox submenuDeMantenimiento;
    @FXML private javafx.scene.layout.HBox espacioCentral;
    @FXML private javafx.scene.control.Label lineaDeMensajes;
    
    @FXML
    public void initialize() {
        lineaDeMensajes.setText("");
        submenuDeConsultas.managedProperty().bind(submenuDeConsultas.visibleProperty());
        submenuDeMantenimiento.managedProperty().bind(submenuDeMantenimiento.visibleProperty());
    }

    @FXML
    private void mostrarSubmenuDeConsultas(ActionEvent evento) {
        ocultarSubmenusLaterales();
        submenuDeConsultas.setVisible(true);
    }
    @FXML
    private void mostrarSubmenuDeMantenimiento(ActionEvent evento) {
        ocultarSubmenusLaterales();
        submenuDeMantenimiento.setVisible(true);
    }
    
    private void ocultarSubmenusLaterales( ) {
        submenuDeConsultas.setVisible(false);
        submenuDeMantenimiento.setVisible(false);
    }
    
    
    @FXML
    protected void mostrarPantallaDeHabitaciones(ActionEvent evento) throws IOException {
        espacioCentral.getChildren().clear();
        java.net.URL location = getClass().getResource("habitaciones/PantallaDeHabitaciones.fxml");
        Parent pantallaEditorDeHabitaciones = FXMLLoader.load(location);
        espacioCentral.getChildren().add(pantallaEditorDeHabitaciones);
    }

    
    
    
    @FXML
    private void mostrarAvisoDeQueEstaAunSinHacer(ActionEvent evento) {
        ocultarSubmenusLaterales();
        espacioCentral.getChildren().clear();
        espacioCentral.getChildren().add(new javafx.scene.control.Label(evento.getSource().toString() + "     ¡ ESTÁ AÚN SIN HACER !"));
    }
    

}
