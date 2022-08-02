package es.susosise.hotel.herramientas_de_instalacion_y_mantenimiento;

import es.susosise.hotel.PreferenciasGeneralesDeLaAplicacion;

public class AppComandosDeInstalacion {

    public static void main(String[] args) {
        
        String archivoDeOpciones = "_configuracion_.json";
        PreferenciasGeneralesDeLaAplicacion opciones = null;
        try {
            opciones = new PreferenciasGeneralesDeLaAplicacion(new java.io.File(archivoDeOpciones).toPath());
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
                            System.out.println("Se va a inicializar la base de datos, creando de nuevo las tablas en ella.");
                            System.out.println("[" + baseDeDatos.getMetaData().getURL() + "]");
                            System.out.println("Aviso: SE BORRARAN todos los datos que tuviera.");
                            System.out.println("¿Hacerlo? (Si/No) ");
                            java.util.Scanner lectorDeTeclado = new java.util.Scanner(System.in);
                            String respuesta = lectorDeTeclado.nextLine();
                            lectorDeTeclado.close();
                            if (respuesta.equals("Si")) {
                                CreadorDeLasTablasEnLaBD creador = new CreadorDeLasTablasEnLaBD(baseDeDatos);
                                creador.crear();
                                System.out.println("¡Borrada e inicializada!.");
                                System.out.println();
                           } else {
                                System.out.println("Se ha dejado la base de datos como estaba.");
                                System.out.println();
                            }
                        } catch (Exception e) {
                            System.out.println();
                            System.out.println("Error al inicializar la base de datos.");
                            System.out.println("======================================");
                            e.printStackTrace();
                            System.out.println();
                        }
                        break;
                    case "--cargarDatosDePrueba":
                        try {
                            CargadorDeLosDatosDePruebaEnLaBD cargador = new CargadorDeLosDatosDePruebaEnLaBD(baseDeDatos);
                            cargador.cargar();
                            System.out.println();
                            System.out.println("Se han cargado los datos de prueba en " + baseDeDatos.getMetaData().getURL());
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println();
                            System.out.println("Error al cargar datos de prueba en la base de datos.");
                            System.out.println("====================================================");
                            e.printStackTrace();
                            System.out.println();
                        }
                        break;
                }
            }
        }
    }
}
