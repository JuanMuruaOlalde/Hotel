package es.susosise.hotel.estancias;

import java.util.UUID;


public class BuscadorDeEstancias {

    private PersistenciaDeEstancias persistencia;
    
    public BuscadorDeEstancias(PersistenciaDeEstancias persistencia) {
        this.persistencia = persistencia;
    }
    
    public Estancia get(UUID id) {
        return null;
    }

}
