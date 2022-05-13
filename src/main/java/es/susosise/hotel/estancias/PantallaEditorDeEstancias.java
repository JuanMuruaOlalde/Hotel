package es.susosise.hotel.estancias;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;


public class PantallaEditorDeEstancias {

    @FXML
    protected void volverALaPantallaPrincipal(ActionEvent evento) throws IOException {
        
        Stage stage = (Stage)((Node)evento.getSource()).getScene().getWindow();
        java.net.URL location = getClass().getResource("../PantallaPrincipal.fxml");
        Parent pantallaPrincipal = FXMLLoader.load(location);
        stage.getScene().setRoot(pantallaPrincipal);
        stage.show();
    }

}
