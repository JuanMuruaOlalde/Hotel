package es.susosise.hotel.estancias;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;
import es.susosise.hotel.habitaciones.CreadorDeHabitaciones;
import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.CreadorDeHuespedes;
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
 
    private PersistenciaDeEstancias persistencia;

    java.sql.Connection baseDeDatos;

    private static List<Habitacion> habitaciones = CreadorDeHabitaciones.getElGrupoDeHabitacionesDePrueba();
    private static LocalDate fechaEntrada = java.time.LocalDate.now();
    private static LocalDate fechaSalida = fechaEntrada.plusDays(1);
    private static List<Huesped> huespedes = CreadorDeHuespedes.getElGrupoDeHuespedesDePrueba();
    
    @BeforeAll
    static void prepararConstantes() {
        habitaciones = CreadorDeHabitaciones.getElGrupoDeHabitacionesDePrueba();
        fechaEntrada = java.time.LocalDate.now();
        fechaSalida = fechaEntrada.plusDays(1);
        huespedes = CreadorDeHuespedes.getElGrupoDeHuespedesDePrueba();
    }
    
    
    @BeforeEach
    void prepararPersistencia() throws SQLException {
        
        //persistencia = new PersistenciaDeEstanciasMocParaAgilizarLosTest();
        
        baseDeDatos = OpcionesYConstantes.getServidorDeDatosParaPruebas();
        persistencia = new PersistenciaDeEstanciasEnBaseDeDatosSQL(baseDeDatos);
        ((PersistenciaDeEstanciasEnBaseDeDatosSQL) persistencia).crearLasTablas();
    }
    
    @AfterEach
    void eliminarPersistencia() {
        try { if (baseDeDatos != null) baseDeDatos.close(); } catch (Exception ex) {}
        
        //por ahora, el resto de persistencias no requieren limpieza.
    }

    
    @Test
    void seCreaYSeRecuperaUnaNuevaEstancia() throws IOException {
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        Estancia estanciaCreada = creador.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        BuscadorDeEstancias buscador = new BuscadorDeEstancias(persistencia);
        Estancia estanciaRecuperada = buscador.get(estanciaCreada.getIdInterno());
        
        assertEquals(estanciaCreada, estanciaRecuperada);
    }
    
    @Test
    void daErrorCrearNuevaEstanciaSobreAlgunaHabitacionQueEstabaYaOcupada() throws IOException {
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);

        Estancia primeraEstancia = creador.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia segundaEstancia = creador.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSinNingunaHabitacionAOcupar() {
        ArrayList<Habitacion> listaVaciaDeHabitaciones = new ArrayList<>();
 
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = creador.crear(listaVaciaDeHabitaciones, fechaEntrada, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSiLaFechaDeEntradaEstaEnElFuturo() {
        LocalDate fechaEntradaFutura = java.time.LocalDate.now().plusDays(3);
        
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = creador.crear(habitaciones, fechaEntradaFutura, fechaSalida, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSiLaFechaDeSalidaEsAnteriorALaDeEntrada() {
        LocalDate fechaSalidaErronea = fechaEntrada.plusDays(-3);
        
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = creador.crear(habitaciones, fechaEntrada, fechaSalidaErronea, huespedes);
                }
             }
        );
    }
    
    @Test
    void daErrorCrearEstanciaSinHuespedAlQueFacturar() {
        ArrayList<Huesped> listaVaciaDeHuespedes = new ArrayList<>();
        
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estancia = creador.crear(habitaciones, fechaEntrada, fechaSalida, listaVaciaDeHuespedes);
                }
             }
        );
    }

}
