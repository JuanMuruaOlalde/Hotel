package es.susosise.hotel.estancias;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;
import es.susosise.hotel.habitaciones.GestorDeHabitaciones;
import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.CreadorDeHuespedesDePrueba;
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
    private GestorDeEstancias gestorDeEstancias;

    private static List<Habitacion> habitaciones = GestorDeHabitaciones.getUnGrupoDeHabitacionesDePrueba();
    private static LocalDate fechaEntrada = java.time.LocalDate.now();
    private static LocalDate fechaSalida = fechaEntrada.plusDays(1);
    private static List<Huesped> huespedes = CreadorDeHuespedesDePrueba.getUnGrupoDeHuespedesDePrueba();
    
    @BeforeAll
    static void prepararConstantes() {
        habitaciones = GestorDeHabitaciones.getUnGrupoDeHabitacionesDePrueba();
        fechaEntrada = java.time.LocalDate.now();
        fechaSalida = fechaEntrada.plusDays(1);
        huespedes = CreadorDeHuespedesDePrueba.getUnGrupoDeHuespedesDePrueba();
    }
    
    
    @BeforeEach
    void prepararEntorno() throws SQLException {
        //persistencia = new PersistenciaDeEstanciasMocParaAgilizarLosTest();
        baseDeDatos = OpcionesYConstantes.getServidorDeDatosParaPruebas();
        PersistenciaDeEstancias persistencia = new PersistenciaDeEstanciasEnBaseDeDatosSQL(baseDeDatos);
        ((PersistenciaDeEstanciasEnBaseDeDatosSQL) persistencia).crearLasTablas();

        gestorDeEstancias = new GestorDeEstancias(persistencia);
    }
    
    @AfterEach
    void limpiarEntorno() {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
        //por ahora, el resto de persistencias no requieren limpieza.
    }

    
    @Test
    void seCreaYSeRecuperaUnaNuevaEstancia() throws IOException {
        Estancia estanciaCreada = gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        Estancia estanciaRecuperada = gestorDeEstancias.get(estanciaCreada.getIdInterno());
        
        assertEquals(estanciaCreada, estanciaRecuperada);
    }
    
    @Test
    void daErrorCrearNuevaEstanciaSobreAlgunaHabitacionQueEstabaYaOcupada() throws IOException {
        Estancia primeraEstancia = gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia segundaEstancia = gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
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
                    Estancia estancia = gestorDeEstancias.crear(listaVaciaDeHabitaciones, fechaEntrada, fechaSalida, huespedes);
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
                    Estancia estancia = gestorDeEstancias.crear(habitaciones, fechaEntradaFutura, fechaSalida, huespedes);
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
                    Estancia estancia = gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalidaErronea, huespedes);
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
                    Estancia estancia = gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalida, listaVaciaDeHuespedes);
                }
             }
        );
    }

    
    @Test
    void seRecuperanTodasLasEstanciasActivasEnEsteMomento() throws IOException {
        LocalDate fechaEntradaAntigua = fechaEntrada.plusDays(-25);
        LocalDate fechaSalidaAntigua = fechaSalida.plusDays(-25);
        
        gestorDeEstancias.crear(habitaciones, fechaEntradaAntigua, fechaSalidaAntigua, huespedes);
        gestorDeEstancias.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
       
        List<Habitacion> unaHabitacion = new ArrayList<>();
        unaHabitacion.add(GestorDeHabitaciones.getUnaHabitacionDePrueba());

        gestorDeEstancias.crear(unaHabitacion, fechaEntradaAntigua, fechaSalidaAntigua, huespedes);
        gestorDeEstancias.crear(unaHabitacion, fechaEntrada, fechaSalida, huespedes);
        
        List<Estancia> estanciasActivas = gestorDeEstancias.getEstanciasActivasEnEsteMomento();
        assertEquals(2, estanciasActivas.size());
    }
    
    
}
