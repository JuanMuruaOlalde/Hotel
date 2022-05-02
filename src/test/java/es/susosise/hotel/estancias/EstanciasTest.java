package es.susosise.hotel.estancias;

import es.susosise.hotel.habitaciones.Habitacion;
import es.susosise.hotel.huespedes.Huesped;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;



class EstanciasTest {
    
    private PersistenciaDeEstancias persistencia;
    
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
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        Date fechaEntrada = new Date();
        Date fechaSalida = new Date();
        ArrayList<Huesped> huespedes = new ArrayList<>();
        
        CreadorDeEstancias creador = new CreadorDeEstancias(persistencia);
        Estancia estanciaCreada = creador.crear(habitaciones, fechaEntrada, fechaSalida, huespedes);
        
        BuscadorDeEstancias buscador = new BuscadorDeEstancias(persistencia);
        Estancia estanciaEncontrada = buscador.get(estanciaCreada.getIdInterno());
        
        assertEquals(estanciaCreada, estanciaEncontrada);
    }

}
