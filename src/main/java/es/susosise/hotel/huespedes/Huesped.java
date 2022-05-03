package es.susosise.hotel.huespedes;

import java.util.UUID;


public class Huesped {
    
    private UUID idInterno;
    private String idLegal; //por ejemplo: pasaporte, CIF, etc.
    private String nombre;
    private String letraIntermediaDelNombre;
    private String apellidos;
    
    public UUID getIdInterno() { return idInterno; }
    public String getIdLegal() { return idLegal; }
    public String getNombre() { return nombre; }
    public String getNombreCompleto() {
        if (apellidos.isEmpty()) {
            return nombre;
        }
        else if (letraIntermediaDelNombre.isEmpty()) {
            return nombre + " " + apellidos;
        }
        else {
            return nombre + " " + letraIntermediaDelNombre + " " + apellidos;
        }
    }

    public Huesped(UUID idInterno, String idLegal, String nombre, String letraIntermedia, String apellidos) {
        this.idInterno = idInterno;
        this.idLegal = idLegal;
        this.nombre = nombre;
        this.letraIntermediaDelNombre = letraIntermedia;
        this.apellidos = apellidos;
    }
   
    public Huesped(String idLegal, String nombre, UUID idInterno)  {
        this.idInterno = idInterno;
        this.idLegal = idLegal;
        this.nombre = nombre;
    }
    
    public Huesped(UUID idInterno, String nombre) {
        this.idInterno = idInterno;
        this.nombre = nombre;
    }

    public Huesped(UUID idInterno, String nombre, String apellidos) {
        this.idInterno = idInterno;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    
    public Huesped(UUID idInterno, String nombre, String letraIntermedia, String apellidos) {
        this.idInterno = idInterno;
        this.nombre = nombre;
        this.letraIntermediaDelNombre = letraIntermedia;
        this.apellidos = apellidos;
    }

}
