package es.susosise.hotel.estancias;

import java.io.IOException;


public interface PersistenciaDeEstancias {

    public void añadirUnaNueva(Estancia estancia) throws IOException;

}
