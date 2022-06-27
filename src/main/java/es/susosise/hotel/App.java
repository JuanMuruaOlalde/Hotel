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
        launch(args); // esto llama a start(...)
    }

    private java.sql.Connection baseDeDatos;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            baseDeDatos = obtenerLaConexionConLaBD();
            
            PersistenciaDeHabitaciones habitaciones = new PersistenciaDeHabitacionesEnBaseDeDatosSQL(baseDeDatos);
            GestionDeHabitaciones gestorDeHabitaciones = new GestorDeHabitaciones(habitaciones);
            ControlParaPantallaEditorDeHabitaciones controladorEditorDeHabitaciones = new ControlParaPantallaEditorDeHabitaciones(gestorDeHabitaciones);
            FXMLLoader loaderEditorDeHabitaciones = new FXMLLoader(getClass().getResource("habitaciones/PantallaEditorDeHabitaciones.fxml"));
            loaderEditorDeHabitaciones.setController(controladorEditorDeHabitaciones);
            Parent pantallaEditorDeHabitaciones = loaderEditorDeHabitaciones.load();
            
            // TODO Aquí seguiremos poniendo el resto (estancias, reservas, huespedes, servicios, empleados, roles, avisos,...)
            //      según vayamos completando las distintas partes de la aplicación.
            
            ControlParaPantallaPrincipal controladorPrincipal = new ControlParaPantallaPrincipal(pantallaEditorDeHabitaciones);
            FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("PantallaPrincipal.fxml"));
            loaderPrincipal.setController(controladorPrincipal);
            Parent pantallaPrincipal = loaderPrincipal.load();
            Scene scene = new Scene(pantallaPrincipal,800,600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotel");
            primaryStage.show();

        } catch (Exception ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Error al inicializar los módulos internos y las pantallas del interfaz.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait().ifPresent( respuesta -> { 
                if (respuesta == ButtonType.OK) { 
                    Platform.exit(); 
                }
            } );
        }
        
    }

    
    @Override
    public void stop() {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
    }
 
    // TODO Esto de abrir una conexion al principio y cerrarla al final puede servir para una pequeña aplicacion.
    //      Pero lo suyo es utilizar un pool de conexiones, utilizando java.sql.DataSource;
    //      e irlas "abriendo" (usar una del pool) y "cerrando" (devolver al pool) cada vez que ejecutamos un comando.
    //      (https://mariadb.com/kb/en/pool-datasource-implementation/)
    
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
