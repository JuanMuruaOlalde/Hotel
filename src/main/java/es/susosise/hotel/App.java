package es.susosise.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            
            java.net.URL location = getClass().getResource("PantallaPrincipal.fxml");
            Parent pantallaPrincipal = FXMLLoader.load(location);
            
            Scene scene = new Scene(pantallaPrincipal,800,600);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotel");
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
   
}
