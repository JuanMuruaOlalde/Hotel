package es.susosise.hotel.habitaciones;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import es.susosise.hotel.habitaciones.Habitacion.TipoDeHabitacion;
import es.susosise.hotel.habitaciones.Habitacion.TipoDeBaño;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class ControladorParaEditarHabitaciones implements Initializable {
    
    private Habitaciones habitaciones;
    
    public ControladorParaEditarHabitaciones(Habitaciones habitaciones) {
        this.habitaciones = habitaciones;
    }
    
  
    @FXML private TableView<Habitacion> listaDeHabitaciones;
    @FXML private TableColumn<Habitacion, String> numeroDeHabitacion;
    @FXML private TableColumn<Habitacion, TipoDeHabitacion> tipoDeHabitacion;
    @FXML private TableColumn<Habitacion, TipoDeBaño> tipoDeBaño;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeroDeHabitacion.setCellValueFactory(new PropertyValueFactory<>("numeroDeHabitacion"));
        tipoDeHabitacion.setCellValueFactory(new PropertyValueFactory<>("tipoDeHabitacion"));
        tipoDeBaño.setCellValueFactory(new PropertyValueFactory<>("tipoDeBaño"));
        refrescar();
        paraModificar_tipoDeHabitacion.getItems().setAll(TipoDeHabitacion.values());
        paraModificar_tipoDeBaño.getItems().setAll(TipoDeBaño.values());
    }
    
    @FXML
    private void refrescarLista(ActionEvent evento) {
        refrescar();
    }
    private void refrescar() {
        List<Habitacion> habitacionesExistentes = habitaciones.getTodas();
        ObservableList<Habitacion> lista = FXCollections.observableArrayList(habitacionesExistentes);
        listaDeHabitaciones.setItems(lista);
    }

    
    @FXML private TextField numeroDeHabitacionABuscar;
    @FXML private TextField paraModificar_numeroDeHabitacion;
    @FXML private ChoiceBox<TipoDeHabitacion> paraModificar_tipoDeHabitacion;
    @FXML private ChoiceBox<TipoDeBaño> paraModificar_tipoDeBaño;
    
    private Habitacion habitacion = null;
    
    @FXML
    private void buscarHabitacion(ActionEvent evento) {
        buscar();
    }
    private void buscar() {
        habitacion = habitaciones.get(numeroDeHabitacionABuscar.getText());
        if (habitacion != null) {
            paraModificar_numeroDeHabitacion.setText(habitacion.getNumeroDeHabitacion());
            paraModificar_tipoDeHabitacion.setValue(habitacion.getTipoDeHabitacion());
            paraModificar_tipoDeBaño.setValue(habitacion.getTipoDeBaño());
        } else {
            Alert avisos = new Alert(AlertType.WARNING);
            avisos.setTitle("Habitación no encontrada.");
            avisos.setContentText("No hay ninguna habitación con el número [" + numeroDeHabitacionABuscar.getText() + "]");
            avisos.showAndWait();
        }
    }

    @FXML
    private void modificarHabitacion(ActionEvent evento) {
        if (habitacion != null) {
            habitacion.setNumeroDeHabitacion(paraModificar_numeroDeHabitacion.getText());
            habitacion.setTipoDeHabitacion(paraModificar_tipoDeHabitacion.getValue());
            habitacion.setTipoDeBaño(paraModificar_tipoDeBaño.getValue());
            try {
                habitaciones.guardarCambios(habitacion);
                refrescar();
            } catch (IOException ex) {
                Alert avisos = new Alert(AlertType.ERROR);
                avisos.setTitle("Error al guardar datos.");
                avisos.setContentText(ex.getMessage());
                avisos.showAndWait();
            } 
        } else {
            Alert avisos = new Alert(AlertType.WARNING);
            avisos.setTitle("Faltan datos.");
            avisos.setContentText("Buscar antes una habitación, para tener datos que guardar.");
            avisos.showAndWait();
        }
        
    }

    
    
    @FXML private TextField numeroDeHabitacionACrear;
    @FXML private TabPane pestañas;
    @FXML private Tab pestañaDeModificar;
    
    @FXML
    private void crearHabitacion(ActionEvent evento) {
        String numeroPropuesto = numeroDeHabitacionACrear.getText();
        try {
            habitaciones.crearUnaNueva(numeroPropuesto);
            refrescar();
            numeroDeHabitacionABuscar.setText(numeroPropuesto);
            buscar();
            pestañas.getSelectionModel().select(pestañaDeModificar);
       } catch (IllegalArgumentException ex) {
            Alert avisos = new Alert(AlertType.WARNING);
            avisos.setTitle("El numero de habitación propuesto no es válido.");
            avisos.setContentText("[" + numeroPropuesto + "]" 
                                  + System.lineSeparator()
                                  + ex.getMessage());
            avisos.showAndWait();
        } catch (IOException ex) {
            Alert avisos = new Alert(AlertType.ERROR);
            avisos.setTitle("Ha fallado la creación.");
            avisos.setContentText(ex.getMessage());
            avisos.showAndWait();
        }
    }

    
}
