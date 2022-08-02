package es.susosise.hotel.estancias;

import es.susosise.hotel.PreferenciasGeneralesDeLaAplicacion;
import es.susosise.hotel.habitaciones.Habitaciones;
import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huespedes;
import es.susosise.hotel.huespedes.Huesped;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



class EstanciasTest {
 
    private java.sql.Connection baseDeDatos;
    private Estancias estancias;

    private static List<Habitacion> habitaciones = Habitaciones.getUnasCuantasParaPruebas();
    private static LocalDate fechaEntrada = java.time.LocalDate.now();
    private static LocalDate fechaSalida = fechaEntrada.plusDays(1);
    private static List<Huesped> huespedes = Huespedes.getUnosCuantosParaPruebas();
    
    @BeforeAll
    static void prepararConstantes() {
        habitaciones = Habitaciones.getUnasCuantasParaPruebas();
        fechaEntrada = java.time.LocalDate.now();
        fechaSalida = fechaEntrada.plusDays(1);
        huespedes = Huespedes.getUnosCuantosParaPruebas();
    }
    
    
    @BeforeEach
    void prepararEntorno() throws SQLException {
        //persistencia = new PersistenciaDeEstanciasMocParaAgilizarLosTest();
        baseDeDatos = PreferenciasGeneralesDeLaAplicacion.getServidorDeDatosParaPruebas();
        PersistenciaDeEstancias persistencia = new PersistenciaDeEstanciasEnMariaDB(baseDeDatos);
        ((PersistenciaDeEstanciasEnMariaDB) persistencia).crearLasTablas();

        estancias = new Estancias(persistencia);
    }
    
    @AfterEach
    void limpiarEntorno() {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
        //por ahora, el resto de persistencias no requieren limpieza.
    }

    
    @Test
    void seCreaYSeRecuperaUnaNuevaEstancia() throws IOException {
        Estancia estanciaCreada = estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        Estancia estanciaRecuperada = estancias.get(estanciaCreada.getIdInterno());
        
        assertEquals(estanciaCreada, estanciaRecuperada);
    }
    
    @Test
    void daErrorCrearNuevaEstanciaSobreAlgunaHabitacionQueEstabaYaOcupada() throws IOException {
        Estancia primeraEstancia = estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia segundaEstancia = estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSinNingunaHabitacionAOcupar() {
        ArrayList<Habitacion> listaVaciaDeHabitaciones = new ArrayList<>();
 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = estancias.crearUnaNueva(listaVaciaDeHabitaciones, fechaEntrada, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSiLaFechaDeEntradaEstaEnElFuturo() {
        LocalDate fechaEntradaFutura = java.time.LocalDate.now().plusDays(3);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = estancias.crearUnaNueva(habitaciones, fechaEntradaFutura, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSiLaFechaDeSalidaEsAnteriorALaDeEntrada() {
        LocalDate fechaSalidaErronea = fechaEntrada.plusDays(-3);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalidaErronea, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSinHuespedAlQueFacturar() {
        ArrayList<Huesped> listaVaciaDeHuespedes = new ArrayList<>();
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalida, listaVaciaDeHuespedes);
                }
             }
        );
    }

    
    @Test
    void seRecuperanTodasLasEstanciasActivasEnEsteMomento() throws IOException {
        LocalDate fechaEntradaAntigua = fechaEntrada.plusDays(-25);
        LocalDate fechaSalidaAntigua = fechaSalida.plusDays(-25);
        
        estancias.crearUnaNueva(habitaciones, fechaEntradaAntigua, fechaSalidaAntigua, huespedes);
        estancias.crearUnaNueva(habitaciones, fechaEntrada, fechaSalida, huespedes);
       
        List<Habitacion> unaHabitacion = new ArrayList<>();
        unaHabitacion.add(Habitaciones.getUnaParaPruebas());

        estancias.crearUnaNueva(unaHabitacion, fechaEntradaAntigua, fechaSalidaAntigua, huespedes);
        estancias.crearUnaNueva(unaHabitacion, fechaEntrada, fechaSalida, huespedes);
        
        List<Estancia> estanciasActivas = estancias.getEstanciasActivasEnEsteMomento();
        assertEquals(2, estanciasActivas.size());
    }
    
    
}
