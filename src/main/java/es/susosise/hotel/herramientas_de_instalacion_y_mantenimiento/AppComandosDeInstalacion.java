package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import es.susosise.hotel.elementos_comunes_compartidos.OpcionesYConstantes;

public class AppComandosDeInstalacion {

    public static void main(String[] args) {
        
        String archivoDeOpciones = "_configuracion_.json";
        OpcionesYConstantes opciones = null;
        try {
            opciones = new OpcionesYConstantes(new java.io.File(archivoDeOpciones).toPath());
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de opciones [" + archivoDeOpciones + "]");
            e.printStackTrace();
        }
        if (opciones != null) {
            java.sql.Connection baseDeDatos = opciones.getServidorDeDatos();
            for (String arg : args) {
                switch (arg) {
                    case "--borrarEInicializarLaBaseDeDatos":
                        try {
                            System.out.println();
                            System.out.println("Se va a inicializar la base de datos:");
                            System.out.println("[" + baseDeDatos.getMetaData().getURL() + "]");
                            System.out.println("Aviso: si ya existe y contiene datos, se borraran todos.");
                            System.out.println("¿Hacerlo? (Si/No) ");
                            java.util.Scanner lectorDeTeclado = new java.util.Scanner(System.in);
                            String respuesta = lectorDeTeclado.nextLine();
                            lectorDeTeclado.close();
                            if (respuesta.equals("Si")) {
                                CreadorDeLasTablasEnLaBD creador = new CreadorDeLasTablasEnLaBD(baseDeDatos);
                                creador.crear();
                                System.out.println("¡Borrada e inicializada!");
                            } else {
                                System.out.println("No se ha hecho nada.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al inicializar la base de datos.");
                            System.out.println("======================================");
                            e.printStackTrace();
                        }
                        break;
                    case "--cargarDatosDePrueba":
                        try {
                            CargadorDeLosDatosDePruebaEnLaBD cargador = new CargadorDeLosDatosDePruebaEnLaBD(baseDeDatos);
                            cargador.cargar();
                            System.out.println("Se han cargado los datos de prueba en " + baseDeDatos.getMetaData().getURL());
                        } catch (Exception e) {
                            System.out.println("Error al cargar datos de prueba en la base de datos.");
                            System.out.println("====================================================");
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }
}
