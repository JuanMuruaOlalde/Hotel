package es.susosise.hotel.huespedes;

import java.util.ArrayList;
import java.util.List;

public class CreadorDeHuespedes {
    
    
    
    
    
    public static Huesped getElHuespedDePrueba() {
        return new Huesped(java.util.UUID.fromString("08b67057-5cce-4275-84cb-0183fdaea3fb"), "HuespedDePruebas");
    }
    
    public static List<Huesped> getElGrupoDeHuespedesDePrueba() {
        ArrayList<Huesped> huespedes = new ArrayList<>();
        huespedes.add(getElHuespedDePrueba());
        return huespedes;
    }

}
