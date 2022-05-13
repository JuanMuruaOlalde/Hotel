package es.susosise.hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;


public class PantallaPrincipal {

    @FXML
    protected void irAlEditorDeEstancias(ActionEvent evento) throws IOException {
        
        Stage stage = (Stage)((Node)evento.getSource()).getScene().getWindow();
        java.net.URL location = getClass().getResource("estancias/PantallaEditorDeEstancias.fxml");
        Parent pantallaEditorDeEstancias = FXMLLoader.load(location);
        stage.getScene().setRoot(pantallaEditorDeEstancias);
        stage.show();
    }
}
