package es.susosise.hotel.estancias;

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
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



class EstanciasTest {
    
    private PersistenciaDeEstancias persistencia;

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
    void prepararPersistencia() {
        persistencia = new PersistenciaDeEstanciasMocParaAgilizarLosTest();
    }
    
    @AfterEach
    void eliminarPersistencia() {
        // De la persistencia Moc ya se encarga el recolector de basura.
    }

    
    @Test
    void seCreaYSeRecuperaUnaNuevaEstancia() throws IOException {
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        Estancia estanciaCreada = creador.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        BuscadorDeEstancias buscador = new BuscadorDeEstancias(persistencia);
        Estancia estanciaEncontrada = buscador.get(estanciaCreada.getIdInterno());
        
        assertEquals(estanciaCreada, estanciaEncontrada);
    }
    
    @Test
    void daErrorIntentarCrearEstanciaSinHabitacionAOcuparDaError() {
        ArrayList<Habitacion> listaVaciaDeHabitaciones = new ArrayList<>();
 
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
             new Executable() {
                @Override
                public void execute() throws Throwable {
                    Estancia estanciaCreada = creador.crear(listaVaciaDeHabitaciones, fechaEntrada, fechaSalida, huespedes);
                }
             }
                                                         );
    }
    
    @Test
    void daErrorIntentarCrearEstanciaSiLaFechaDeEntradaEstaEnElFuturo() {
        fail();
    }
    
    @Test
    void daErrorIntentarCrearEstanciaSiLaFechaDeSalidaEsAnteriorALaDeEntrada() {
        fail();
    }
    
    @Test
    void daErrorIntentarCrearEstanciaSinHuespedAlQueFacturar() {
        fail();
    }

}
