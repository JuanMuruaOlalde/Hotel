package es.susosise.hotel;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;
import es.susosise.hotel.habitaciones.*;
import es.susosise.hotel.estancias.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class App extends Application {
    
    
    public static void main(String[] args) {
        launch(args);
    }
   

    @Override
    public void start(Stage primaryStage) {
        
        java.sql.Connection baseDeDatos = obtenerLaConexionConLaBD();
        prepararControladoresDelModelo(baseDeDatos);
        
        try {
            java.net.URL location = getClass().getResource("PantallaPrincipal.fxml");
            Parent pantallaPrincipal = FXMLLoader.load(location);
            Scene scene = new Scene(pantallaPrincipal,800,600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotel");
            primaryStage.show();
        } catch(Exception ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al crear la pantalla principal.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
        
    }

    
    public static BuscadorDeHabitaciones buscadorDeHabitaciones;
    public static CreadorDeHabitaciones creadorDeHabitaciones;
    public static ModificadorDeHabitaciones modificadorDeHabitaciones;
    public static EliminadorDeHabitaciones eliminadorDeHabitaciones;
    
    public static BuscadorDeEstancias buscadorDeEstancias;
    public static CreadorDeEstancias creadorDeEstancias;
 
    private void prepararControladoresDelModelo(java.sql.Connection baseDeDatos) {
        try {
            buscadorDeHabitaciones = new BuscadorDeHabitaciones(new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos));
            creadorDeHabitaciones = new CreadorDeHabitaciones(new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos));
            modificadorDeHabitaciones = new ModificadorDeHabitaciones(new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos));
            eliminadorDeHabitaciones = new EliminadorDeHabitaciones(new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos));
            
            buscadorDeEstancias = new BuscadorDeEstancias(new PersistenciaDeEstanciasEnBaseDeDatosSQL(baseDeDatos));
            creadorDeEstancias = new CreadorDeEstancias(new PersistenciaDeEstanciasEnBaseDeDatosSQL(baseDeDatos));

            // TODO Aquí iremos poniendo las distintas herramientas del modelo según las vayamos desarrollando.
            
        } catch (Exception ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al inicializar los módulos internos.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
    }


    private java.sql.Connection obtenerLaConexionConLaBD() {
        String archivoDeOpciones = "_configuracion_.json";
        OpcionesYConstantes opciones = null;
        try {
            opciones = new OpcionesYConstantes(new java.io.File(archivoDeOpciones).toPath());
        } catch (Exception ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al leer el archivo de opciones.");
            avisos.setContentText("[" + archivoDeOpciones + "]" 
                                  + System.lineSeparator()
                                  + ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
        java.sql.Connection baseDeDatos = null;
        try {
            baseDeDatos = opciones.getServidorDeDatos();
        } catch (Exception ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al inicializar el soporte de datos.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
        return baseDeDatos;
    }

    
}
