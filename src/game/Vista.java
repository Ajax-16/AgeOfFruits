package game;

import exceptions.HayError;
import resources.Fruto;
import resources.Madera;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Vista {    

    static boolean juegoTerminado = false;
    public static boolean salirEstado = false;
    public static String nombreColonia;
    public static MapaColonia mapaColonia;
    public static MapaColonia mapaPlantilla;
    private static int tamanyoColonia = 9;
    private static String subOpcion;
    private static boolean puedePasarTurno = false;

    public static void main(String[] args) {

        mapaColonia = new MapaColonia(tamanyoColonia);

        mapaPlantilla = new MapaColonia(tamanyoColonia);

        Scanner sc = new Scanner(System.in);

        String Opcion = "";

        String esNoche = "";

        String msj = """
                2. Centro de gestiones
                3. Pasar turno
                Introduce una opción válida: """;

        System.out.print("                            ***** Bienvenido a Age Of Fruits 2 ***** \nCorre el año 3079, después de la gran crisis de las IAs, todo indicio de tecnología desapareció.\nEl mundo comenzó una era no tecnológica, y hubo una regresión total en todos los aspectos de la vida.\nLas máquinas son vistas aún después de cientos de años como seres malvados y nocivos para la humanidad.\nAhora, los pocos seres humanos que quedan, contrarios a cualquier implementación tecnológica no rudimentaria,\nse agrupan en pequeñas colonias, donde el único objetivo es la supervivencia." +
                "\n\nEres hijo de uno de los jefes de una colonia ubicada en la zona de Svenstorp. Tu padre murió recientemente \ny ahora tú debes continuar su legado, ayudando a tu gente a sobrevivir y labrarse una buena vida.\nPara comenzar, introduce un nuevo nombre para tu colonia: ");

        nombreColonia = sc.nextLine();

        int [] posicionAleatoria = {(int) AdministrarColonos.aleatorio(tamanyoColonia-2), (int) AdministrarColonos.aleatorio(tamanyoColonia-2)};

        mapaPlantilla.añadeCentroDeGestion(posicionAleatoria);

        mapaColonia.añadeCentroDeGestion(posicionAleatoria);

        AdministrarColonos.contrataColono();

        AdministrarColonos.contrataColono();

        do{

            try {

                System.out.println("        ***** Age Of Fruits 2 *****");

                System.out.println("    MAPA DE " + nombreColonia.toUpperCase() + ": ");

                mapaColonia.muestraColonia();

                if(puedePasarTurno) {

                    ControlDeJuego.ocurreEvento();

                    ControlDeJuego.pasaTurno();

                    ControlDeJuego.pasaTurnoEnano();

                    AdministrarColonos.pasaTurnoIndividual();

                    AdministrarColonos.setForcedEstado();

                    AdministrarColonos.compruebaCredenciales();

                    ControlDeJuego.getGameOver();

                    puedePasarTurno = false;

                }

                esNoche = ControlDeJuego.esDeNoche ? "Ahora mismo es de noche." : "Ahora mismo es de día.";

                System.out.println("Turno Global nº " + (ControlDeJuego.getTurnoGlobal()+1));

                System.out.println(esNoche);

                System.out.println("1. Mostrar información de " + nombreColonia);

                System.out.print(msj);

                Opcion = sc.nextLine();

                switch (Integer.parseInt(Opcion)) {

                    case 1:

                        do {

                            System.out.println("***** Estadísitcas de " + nombreColonia + " ******");

                            System.out.print("""
                                    1. Mostrar colonos
                                    2. Mostrar cantidad de frutos
                                    3. Mostrar cantidad de madera
                                    0. Atrás
                                    Introduce una opción válida: """);

                            subOpcion = sc.nextLine();

                            switch (Integer.parseInt(subOpcion)) {

                                case 1:

                                    if(AdministrarColonos.getListaColonosContratados().isEmpty()){

                                        System.out.println(nombreColonia + " no tiene aún ningún colono.");

                                        break;

                                    }else {

                                        System.out.println(AdministrarColonos.muestraListaColonos());

                                    }

                                    break;

                                case 2:

                                    System.out.println("Tienes la cantidad de " + Fruto.getCantidadFruto() + " fruto(s) en el almacén.");

                                    break;

                                case 3:

                                    System.out.println("Tienes la cantidad de " + Madera.getCantidadMadera() + " de madera en el almacén.");

                                    break;

                                case 0:

                                    break;

                                default:

                                    System.out.println("Opción inválida. Intenda de nuevo");

                            }

                        }while (Integer.parseInt(subOpcion) != 0);

                        break;

                    case 2:

                        do {

                            System.out.print("""
                                    ***** Centro de gestiones *****
                                    1. Cambiar el estado de un colono
                                    2. Realizar una nueva construcción
                                    3. Mandar a un colono a realizar una expedición
                                    4. Curar a un colono
                                    0. Atrás
                                    Introduce una opción válida: """);

                            subOpcion = sc.nextLine();

                            switch (Integer.parseInt(subOpcion)) {

                                case 1:

                                    do {

                                        String opcionColono;

                                        String mensaje = """
                                                ***** Establecer un estado *****
                                                1. De pie (-10E -10C +10H)

                                                2. Sentarse en el suelo (+10E +5H) Duracion: 2 Turnos

                                                3. Pasear (-34E + 34C + 15H) Duracion: 2 Turnos

                                                4. Cantar (-20E +50C +30H) Duracion: 3 Turnos

                                                5. Recolectar frutos (+F -40E -30C +25H) Duracion: 3 Turnos

                                                6. Dormir (+40E +10C +10H) Duracion: 4 Turnos

                                                7. Comer (-30F +20E + 10C -60H) Duracion: 1 Turno

                                                8. Recolectar madera (+M -30E -30C +25H) Duracion: 3 turnos

                                                0. Atras

                                                Escoge una opcion valida: """;

                                        if (!AdministrarColonos.listaColonosContratados.isEmpty()) {

                                            System.out.println("***** Colonos de " + nombreColonia + ": ");

                                            System.out.println(AdministrarColonos.muestraIDnombreColonos());

                                            System.out.print("Introduce el ID de un colono cuyo estado quieres que cambie: ");

                                            String id = "";

                                            id = sc.nextLine();

                                            int ID = Integer.parseInt(id);

                                            if (!AdministrarColonos.listaColonosContratados.get(ID - 1).ocupado) {

                                                AdministrarColonos.rayaConsola();

                                                System.out.println("Actualmente tienes " + Fruto.getCantidadFruto() + " frutos y " + Madera.getCantidadMadera() + " de madera.");

                                                System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " tiene "
                                                        + AdministrarColonos.listaColonosContratados.get(ID - 1).energia + " de energia, " + AdministrarColonos.listaColonosContratados.get(ID - 1).cordura
                                                        + " de cordura y " + AdministrarColonos.listaColonosContratados.get(ID-1).hambre + " de hambre.");

                                                System.out.println(mensaje);

                                                AdministrarColonos.raya();

                                                opcionColono = sc.nextLine();

                                                AdministrarColonos.setEstado(ID, Integer.parseInt(opcionColono));

                                            } else {

                                                System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " está ocupado ahora mismo");

                                            }

                                        } else {

                                            System.out.println("No existen ningún colono en " + nombreColonia);

                                        }


                                    }while (!salirEstado);

                                    salirEstado = false;

                                    break;

                                case 2:

                                    AdministrarColonos.rayaConsola();

                                    System.out.println("Tienes la cantidad de " + Madera.getCantidadMadera() + " de madera.");

                                    AdministrarColonos.raya();

                                    System.out.print("""
                                            ***** Nueva construcción *****
                                            1. Vivienda (-200M -30E -20C +40H) -> + 3 Espacios de vivienda (4 trunos)
                                            2. Estación de frutos (-300M -30E -20C +40H) -> +50 frutos * estación (6 trunos)
                                            3. Estación de madera (-400M -30E -20C +40H) -> +40 madera * estación (6 trunos)
                                            0. Atrás
                                            Introduce una opción válida o construcción a realizar: """);

                                    String opcionC = "";

                                    opcionC = sc.nextLine();

                                    switch (Integer.parseInt(opcionC)){

                                        case 1:

                                            if(Madera.getCantidadMadera()>=200) {

                                                if (!AdministrarColonos.listaColonosContratados.isEmpty()) {

                                                    System.out.println("Colonos disponibles para la construcción: ");

                                                    System.out.println(AdministrarColonos.muestraIDnombreColonosDisponibles());

                                                    System.out.print("Introduce el ID de un colono para que realice la construcción: ");

                                                    String id = "";

                                                    id = sc.nextLine();

                                                    int ID = Integer.parseInt(id);

                                                    if (!AdministrarColonos.listaColonosContratados.get(ID - 1).ocupado) {

                                                        System.out.println("***** Selecciona la ubicación de la nueva construcción *****");

                                                        String confirmacion = "";

                                                        System.out.print("Escribe una coordenada: ");

                                                        String pos = sc.nextLine();

                                                        int [] posicion;

                                                        posicion = mapaPlantilla.asignaPosicion(pos);

                                                        mapaPlantilla.añadeCasa(posicion);

                                                        if(!mapaPlantilla.noPuedeColocarCasa) {

                                                            System.out.println("Previsualización de colonia actualizada: ");

                                                            mapaPlantilla.muestraColonia();

                                                            System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                                            boolean salir = false;

                                                            do {

                                                                confirmacion = sc.nextLine();

                                                                confirmacion = confirmacion.toUpperCase();

                                                                switch (confirmacion) {

                                                                    case "SI":

                                                                        AdministrarColonos.setEstado(ID, 20);

                                                                        mapaColonia.añadeCasa(posicion);

                                                                        Madera.setCantidadMadera(Madera.getCantidadMadera()-200);

                                                                        AdministrarColonos.rayaConsola();

                                                                        System.out.println("Comenzada construcción de nueva casa en posición especificada");

                                                                        AdministrarColonos.raya();

                                                                        salir = true;

                                                                        break;

                                                                    case "NO":

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.println("Instrucción cancelada");

                                                                        salir = true;

                                                                        break;

                                                                    default:

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                                                }

                                                            }while(!salir);

                                                        }

                                                        else{

                                                            System.out.println("No puede colocarse la casa en la posición indicada \ndebido a que ya existen una construcción");

                                                            mapaPlantilla.noPuedeColocarCasa = false;

                                                        }

                                                    } else {

                                                        System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " está ocupado ahora mismo");

                                                    }

                                                } else {

                                                    System.out.println("No existen ningún colono en " + nombreColonia);

                                                }
                                            }else{

                                                System.out.println("Cantidad de madera insuficiente");

                                            }

                                            break;

                                        case 2:

                                            if(Madera.getCantidadMadera()>=300) {

                                                if (!AdministrarColonos.listaColonosContratados.isEmpty()) {

                                                    System.out.print("Introduce el ID de un colono para que realice la construcción: ");

                                                    String id = "";

                                                    id = sc.nextLine();

                                                    int ID = Integer.parseInt(id);

                                                    if (!AdministrarColonos.listaColonosContratados.get(ID - 1).ocupado) {

                                                        AdministrarColonos.rayaConsola();

                                                        System.out.println("Actualmente tienes " + Madera.getCantidadMadera() + " de madera");

                                                        System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " tiene "
                                                                + AdministrarColonos.listaColonosContratados.get(ID - 1).energia + " de energia y " + AdministrarColonos.listaColonosContratados.get(ID - 1).cordura
                                                                + " de cordura");

                                                        AdministrarColonos.raya();

                                                        System.out.println("***** Selecciona la ubicación de la nueva construcción *****");

                                                        String confirmacion = "";

                                                        System.out.print("Escribe una coordenada: ");

                                                        String pos = sc.nextLine();

                                                        int [] posicion;

                                                        posicion = mapaPlantilla.asignaPosicion(pos);

                                                        mapaPlantilla.añadeEstacionFrutos(posicion);

                                                        if(!mapaPlantilla.noPuedeColocarCasa) {

                                                            System.out.println("Previsualización de colonia actualizada: ");

                                                            mapaPlantilla.muestraColonia();

                                                            System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                                            boolean salir = false;

                                                            do {

                                                                confirmacion = sc.nextLine();

                                                                confirmacion = confirmacion.toUpperCase();

                                                                switch (confirmacion) {

                                                                    case "SI":

                                                                        AdministrarColonos.setEstado(ID, 21);

                                                                        mapaColonia.añadeEstacionFrutos(posicion);

                                                                        Madera.setCantidadMadera(Madera.getCantidadMadera()-300);

                                                                        System.out.println("Comenzada construcción de nueva estación de frutos en posición especificada");

                                                                        salir = true;

                                                                        break;

                                                                    case "NO":

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.println("Instrucción cancelada");

                                                                        salir = true;

                                                                        break;

                                                                    default:

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                                                }

                                                            }while(!salir);

                                                        }

                                                        else{

                                                            System.out.println("No puede colocarse la estación en la posición indicada \ndebido a que ya existen una construcción");

                                                            mapaPlantilla.noPuedeColocarCasa = false;

                                                        }

                                                    } else {

                                                        System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " está ocupado ahora mismo");

                                                    }

                                                } else {

                                                    System.out.println("No existen ningún colono en " + nombreColonia);

                                                }
                                            }else{

                                                System.out.println("Cantidad de madera insuficiente");

                                            }

                                            break;

                                        case 3:
                                            if(Madera.getCantidadMadera()>=400) {

                                                if (!AdministrarColonos.listaColonosContratados.isEmpty()) {

                                                    System.out.print("Introduce el ID de un colono para que realice la construcción: ");

                                                    String id = "";

                                                    id = sc.nextLine();

                                                    int ID = Integer.parseInt(id);

                                                    if (!AdministrarColonos.listaColonosContratados.get(ID - 1).ocupado) {

                                                        AdministrarColonos.rayaConsola();

                                                        System.out.println("Actualmente tienes " + Madera.getCantidadMadera() + " de madera");

                                                        System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " tiene "
                                                                + AdministrarColonos.listaColonosContratados.get(ID - 1).energia + " de energia y " + AdministrarColonos.listaColonosContratados.get(ID - 1).cordura
                                                                + " de cordura");

                                                        AdministrarColonos.raya();

                                                        System.out.println("***** Selecciona la ubicación de la nueva construcción *****");

                                                        String confirmacion = "";

                                                        System.out.print("Escribe una coordenada: ");

                                                        String pos = sc.nextLine();

                                                        int [] posicion;

                                                        posicion = mapaPlantilla.asignaPosicion(pos);

                                                        mapaPlantilla.añadeEstacionMadera(posicion);

                                                        if(!mapaPlantilla.noPuedeColocarCasa) {

                                                            System.out.println("Previsualización de colonia actualizada: ");

                                                            mapaPlantilla.muestraColonia();

                                                            System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                                            boolean salir = false;

                                                            do {

                                                                confirmacion = sc.nextLine();

                                                                confirmacion = confirmacion.toUpperCase();

                                                                switch (confirmacion) {

                                                                    case "SI":

                                                                        AdministrarColonos.setEstado(ID, 22);

                                                                        mapaColonia.añadeEstacionMadera(posicion);

                                                                        Madera.setCantidadMadera(Madera.getCantidadMadera()-400);

                                                                        System.out.println("Comenzada construcción de nueva estación de madera en posición especificada");

                                                                        salir = true;

                                                                        break;

                                                                    case "NO":

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.println("Instrucción cancelada");

                                                                        salir = true;

                                                                        break;

                                                                    default:

                                                                        mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                                        System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                                                }

                                                            }while(!salir);

                                                        }

                                                        else{

                                                            System.out.println("No puede colocarse la estación en la posición indicada \ndebido a que ya existen una construcción");

                                                            mapaPlantilla.noPuedeColocarCasa = false;

                                                        }

                                                    } else {

                                                        System.out.println("El colono " + AdministrarColonos.listaColonosContratados.get(ID - 1).getNombre() + " " + AdministrarColonos.listaColonosContratados.get(ID - 1).getApellido() + " está ocupado ahora mismo");

                                                    }

                                                } else {

                                                    System.out.println("No existen ningún colono en " + nombreColonia);

                                                }
                                            }else{

                                                System.out.println("Cantidad de madera insuficiente");

                                            }

                                            break;

                                        case 0:

                                            break;

                                    }

                                    break;

                                case 3:

                                    break;

                                default:

                                    break;

                            }

                        } while (Integer.parseInt(subOpcion) != 0);

                        break;


                    case 3:

                        puedePasarTurno = true;

                        break;



                    case 190805:

                        do {

                            System.out.print("""
                                    ***** Modo Debug *****
                                    1. Añadir frutos
                                    2. Añadir madera
                                    3. Añadir colono
                                    4. Eliminar colono
                                    5. Forzar Evento
                                    6. Crear casa
                                    7. Crear Estacion Frutos
                                    8. Crear Estación de Madera
                                    9. Agrandar colonia
                                    0. Atrás
                                    Introduce una opción válida: """);

                            subOpcion = sc.nextLine();

                            switch (Integer.parseInt(subOpcion)) {

                                case 1:

                                    String cantidad = "";

                                    System.out.println("Introduce la cantidad de frutos que quieres añadir");

                                    cantidad = sc.nextLine();

                                    Fruto.aumentaFrutos(Integer.parseInt(cantidad));

                                    break;

                                case 2:

                                    String cantidadM = "";

                                    System.out.println("Introduce la cantidad de madera que quieres añadir");

                                    cantidadM = sc.nextLine();

                                    Madera.setCantidadMadera(Madera.getCantidadMadera()+ (Integer.parseInt(cantidadM)));

                                    break;

                                case 3:

                                    if(AdministrarColonos.listaColonosContratados.size() < AdministrarColonos.tamanyoColonia) {

                                        AdministrarColonos.contrataColono();

                                        System.out.println("Contratado nuevo colono");

                                    }

                                    else{

                                        System.out.println("No hay suficientes casas para albergar más colonos.\nConstruye más viviendas para aumentar la capacidad de la colonia.");

                                    }

                                    break;

                                case 4:

                                    String id = "";

                                    System.out.println(AdministrarColonos.muestraIDnombreColonos());

                                    System.out.print("Selecciona el ID de colono que deseas eliminar: ");

                                    id = sc.nextLine();

                                    AdministrarColonos.eliminaColono(Integer.parseInt(id));

                                    break;

                                case 5:

                                    String codigoEvento;

                                    do {

                                        System.out.print("""
                                                ***** Forzar Evento *****
                                                001. Evento Colono Viejo
                                                002. Evento Enano Cabezón
                                                003. Evento Mercader
                                                004. Evento Planta Carnivora
                                                100. Evento Enano Cabezón 2
                                                0. Atrás
                                                Introduce el código de Evento que quieres añadir: """);

                                        codigoEvento = sc.nextLine();

                                        switch (codigoEvento) {

                                            case "001":

                                                Eventos.EventoColonoViejo();

                                                break;

                                            case "002":

                                                Eventos.EventoEnanoCabezon1();

                                                break;

                                            case "003":

                                                Eventos.EventoMercader();

                                                break;

                                            case "004":

                                                Eventos.EventoPlantasCarnivoras();

                                                break;

                                            case "100":

                                                Eventos.EventoEnanoCabezon2();

                                                break;

                                            case "0":

                                                break;

                                            default:

                                                System.out.println("Opción inválida. Intenda de nuevo");

                                        }

                                    }while (Integer.parseInt(codigoEvento) != 0);

                                    break;

                                case 6:

                                    String confirmacion = "";

                                    System.out.print("Escribe una coordenada: ");

                                    String pos = sc.nextLine();

                                    int [] posicion;

                                    posicion = mapaPlantilla.asignaPosicion(pos);

                                     mapaPlantilla.añadeCasa(posicion);

                                     if(!mapaPlantilla.noPuedeColocarCasa) {

                                         System.out.println("Previsualización de colonia actualizada: ");

                                         mapaPlantilla.muestraColonia();

                                             System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                         boolean salir = false;

                                         do {

                                             confirmacion = sc.nextLine();

                                             confirmacion = confirmacion.toUpperCase();

                                             switch (confirmacion) {

                                                 case "SI":

                                                     mapaColonia.añadeCasa(posicion);

                                                     AdministrarColonos.tamanyoColonia+=3;

                                                     System.out.println("Añadida casa en posición especificada");

                                                     salir = true;

                                                     break;

                                                 case "NO":

                                                     mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                     System.out.println("Instrucción cancelada");

                                                     salir = true;

                                                     break;

                                                 default:

                                                     mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                     System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                             }

                                         }while(!salir);

                                     }

                                     else{

                                         System.out.println("No puede colocarse la casa en la posición indicada \ndebido a que ya existen una construcción");

                                         mapaPlantilla.noPuedeColocarCasa = false;

                                     }

                                    break;

                                case 7:

                                    String confirmacionF = "";

                                    System.out.print("Escribe una coordenada: ");

                                    String posF = sc.nextLine();

                                    int [] posicionF;

                                    posicionF = mapaPlantilla.asignaPosicion(posF);

                                    mapaPlantilla.añadeEstacionFrutos(posicionF);

                                    if(!mapaPlantilla.noPuedeColocarCasa) {

                                        System.out.println("Previsualización de colonia actualizada: ");

                                        mapaPlantilla.muestraColonia();

                                        System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                        boolean salir = false;

                                        do {

                                            confirmacion = sc.nextLine();

                                            confirmacion = confirmacion.toUpperCase();

                                            switch (confirmacion) {

                                                case "SI":

                                                    mapaColonia.añadeEstacionFrutos(posicionF);

                                                    MapaColonia.hayEstacionDeFrutos = true;

                                                    MapaColonia.cantidadEstacionDeFrutos++;

                                                    System.out.println("Añadida estación de frutos en posición especificada. \n¡Las condiciones de trabajo han mejorado, y ahora la cantidad de frutos recolectados es mayor! ");

                                                    salir = true;

                                                    break;

                                                case "NO":

                                                    mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                    System.out.println("Instrucción cancelada");

                                                    salir = true;

                                                    break;

                                                default:

                                                    mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                    System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                            }

                                        }while(!salir);

                                    }

                                    else{

                                        System.out.println("No puede colocarse la estación de frutos en la posición \nindicada debido a que ya existen una construcción");

                                        mapaPlantilla.noPuedeColocarCasa = false;

                                    }

                                    break;

                                case 8:

                                    String confirmacionM = "";

                                    System.out.print("Escribe una coordenada: ");

                                    String posM = sc.nextLine();

                                    int [] posicionM;

                                    posicionM = mapaPlantilla.asignaPosicion(posM);

                                    mapaPlantilla.añadeEstacionMadera(posicionM);

                                    if(!mapaPlantilla.noPuedeColocarCasa) {

                                        System.out.println("Previsualización de colonia actualizada: ");

                                        mapaPlantilla.muestraColonia();

                                        System.out.println("¿Desea confirmar los cambios a la colonia? SI/NO");

                                        boolean salir = false;

                                        do {

                                            confirmacion = sc.nextLine();

                                            confirmacion = confirmacion.toUpperCase();

                                            switch (confirmacion) {

                                                case "SI":

                                                    mapaColonia.añadeEstacionMadera(posicionM);

                                                    MapaColonia.hayEstacionDeMadera = true;

                                                    MapaColonia.cantidadEstacionDeMadera++;

                                                    System.out.println("Añadida estación de madera en posición especificada. \n¡Las condiciones de trabajo han mejorado, y ahora la cantidad de madera recolectada es mayor! ");

                                                    salir = true;

                                                    break;

                                                case "NO":

                                                    mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                    System.out.println("Instrucción cancelada");

                                                    salir = true;

                                                    break;

                                                default:

                                                    mapaPlantilla.copiaColonia(tamanyoColonia, mapaColonia);

                                                    System.out.print("Instrucción no válida. Vuelve a introducir otra opción: ");

                                            }

                                        }while(!salir);

                                    }

                                    else{

                                        System.out.println("No puede colocarse la estación de frutos en la posición \nindicada debido a que ya existen una construcción");

                                        mapaPlantilla.noPuedeColocarCasa = false;

                                    }

                                    break;

                                case 9:

                                    System.out.print("Escribe el nuevo numero de celdas que quieres que tenga la colonia: ");

                                    String tamanyoNuevo = sc.nextLine();

                                    int tamanyoNuevoInt = Integer.parseInt(tamanyoNuevo);

                                    tamanyoColonia = tamanyoNuevoInt;

                                    if (tamanyoNuevoInt > mapaPlantilla.maximoCeldas) {

                                        System.out.println("Superado el límite de tamaño de mapa. Asignando máximo por defecto...");

                                        tamanyoNuevoInt = mapaPlantilla.maximoCeldas;

                                    }

                                    mapaPlantilla.agrandaColonia(tamanyoNuevoInt);

                                    mapaColonia.agrandaColonia(tamanyoNuevoInt);

                                    System.out.println("Colonia Actualizada: ");

                                    mapaColonia.muestraColonia();

                                    break;

                            }

                        } while (Integer.parseInt(subOpcion) != 0);

                }

            }catch (Exception e){

            }

        }while(!juegoTerminado);

    }

}