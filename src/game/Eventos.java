package game;

import minijuegos.Nucleo;
import resources.Fruto;

import java.util.ArrayList;
import java.util.Scanner;

public class Eventos {

    static Scanner sc = new Scanner(System.in);
    private static boolean viejoConocido = false;
    private static boolean aceptaEnano = false;
    private static ArrayList<Integer> listaEventos;
    private ArrayList<Integer> listaEventosNocturnos;
    private boolean eliminaEventoViejo = false;
    public static boolean isViejoConocido() {
        return viejoConocido;
    }

    public static boolean isAceptaEnano() {
        return aceptaEnano;
    }

    public Eventos() {

        listaEventos  = new ArrayList<>();

        listaEventosNocturnos = new ArrayList<>();

        listaEventos.add(0);

        listaEventosNocturnos.add(0);

        agregaEvento(listaEventos, 100, 2);

        agregaEvento(listaEventos, 1, 5);

        agregaEvento(listaEventos, 2, 5);

        agregaEvento(listaEventos, 4, 2);

        agregaEvento(listaEventosNocturnos, 100, 3);

        agregaEvento(listaEventosNocturnos, 3, 2);

    }

    public void EventosController(){

        double numAleatorio = AdministrarColonos.aleatorio(listaEventos.size());

        double numAleatorioNocturno = AdministrarColonos.aleatorio(listaEventosNocturnos.size());

        Integer numEvento = (int) numAleatorio;

        Integer numEventoNocturno = (int) numAleatorioNocturno;

        numEvento = listaEventos.get(numEvento);

        numEventoNocturno = listaEventosNocturnos.get(numEventoNocturno);

        if(numEvento == 1 && ControlDeJuego.getTurnoGlobal() >= 10) {

            EventoColonoViejo();

        }

        if(numEvento == 2 && ControlDeJuego.getTurnoGlobal() >= 8){

            EventoEnanoCabezon1();

            eliminaEvento(2);

        }

        if(numEventoNocturno == 3 && ControlDeJuego.esDeNoche){

            EventoMercader();

        }

        if(numEvento == 4 && ControlDeJuego.getTurnoGlobal()>=15){

            EventoPlantasCarnivoras();

        }

        if(ControlDeJuego.eventoEnanoForzado){

            EventoEnanoCabezon2();

            ControlDeJuego.eventoEnanoForzado = false;

        }

    }

    public void agregaEvento(ArrayList <Integer> listaEventos, int numEvento, int cantidadVeces){

        for(int i = 0; i<cantidadVeces;i++){

            listaEventos.add(numEvento);

        }

    }

    public static void eliminaEvento(int numEvento){;

        if(numEvento != 0) {

            listaEventos.removeIf(num -> num == numEvento);

        }

    }

    public static void rayaEvento(){

        System.out.println("----------------- !Evento -----------------");

    }

    public static void EventoPlantasCarnivoras(){

       Nucleo.filas = (int) (AdministrarColonos.aleatorio(5) + 5);

       Nucleo.columnas = (int) (AdministrarColonos.aleatorio(5) + 5);

       int tamayoMapa = Nucleo.filas * Nucleo.columnas;

       Nucleo.cantidadMinas = (int) Math.round(tamayoMapa*0.2);

       Nucleo controlador = new Nucleo();

       int [] posicionIncial = {Nucleo.filas-1,  Nucleo.columnas-1};

       int filaFinalAleatoria = (int) AdministrarColonos.aleatorio(Nucleo.filas - 3);

       int columnaFinalAleatoria = (int) AdministrarColonos.aleatorio(Nucleo.columnas - 3);

       int [] posicionFinal = {filaFinalAleatoria, columnaFinalAleatoria};

       controlador.ColocarCamino(controlador.getMapaResuelto(), posicionIncial, posicionFinal);

       controlador.ColocarMinas(controlador.getMapaResuelto());

       controlador.Acciones(posicionIncial, Nucleo.Accion.minar);

        controlador.getMapaUsuario()[controlador.posicionFinal[0]][controlador.posicionFinal[1]] = controlador.casillaFinal;

       controlador.pasarTurno();

       rayaEvento();

        System.out.println("Un colono dice haber descubierto un sendero cuyo final\n" +
                "parece agurdar un brillante cofre. Parece un trampa, pero quizá\n" +
                "podamos hallar la forma de llegar hasta la recompensa.\n" +
                "¿Quieres mandar al colono a intentar alcanzarlo? SI / NO");

        AdministrarColonos.raya();

        boolean eleccion = false;

        String opcion = "";

        do {

            opcion = sc.nextLine();

            switch (opcion.toUpperCase()) {

                case "SI":

                    System.out.println("MAPA SENDERO MISTERIOSO");

                    do{

                        Scanner leer = new Scanner(System.in);
                        char fila;
                        char columna;
                        boolean finalizarTurno = false;
                        int[] coordenadas;
                        Nucleo.Accion accion = null;
                        String mensaje;

                        while(!finalizarTurno) {
                            try {
                                accion = Nucleo.Accion.minar;

                                System.out.println(controlador.MapaToTexto(controlador.getMapaUsuario()));
                                System.out.println("¿Cual es la letra de la fila que quieres seleccionar? (La letra en mayuscula)");
                                fila = leer.nextLine().toCharArray()[0];

                                System.out.println("¿Cual es el numero de la columna que quieres seleccionar?");
                                columna = leer.nextLine().toCharArray()[0];

                                coordenadas = controlador.TransformarCordenadas(fila, columna);

                                if (coordenadas[0] == -1 || coordenadas[1] == -1) {
                                    continue;
                                }

                                mensaje = controlador.Acciones(coordenadas, accion);

                                if (mensaje == "") {
                                    controlador.pasarTurno();
                                    finalizarTurno = true;
                                } else {

                                    AdministrarColonos.raya();

                                    System.out.println("El colono no puede llegar hasta esa posición, ya que aún no ha\n" +
                                            "explorado esa zona.");

                                    AdministrarColonos.raya();
                                }
                            }catch (Exception e)
                            {
                                System.out.println(e);
                            }
                        }

                    }while(!controlador.isJuegoAcabado());

                    System.out.println(controlador.PantallaFinal());

                    eleccion = true;

                    break;

                case "NO":


                    eleccion = true;

                    break;

                default:

                    System.out.println("Eleccion inválida, vuelve a elegir");

                    break;

            }


        }while (!eleccion);

        }

    public static void EventoColonoViejo(){

        rayaEvento();

        System.out.println("Ha aparecido un colono anciano que afirma ser un chamán. " +
                "Quizá está un poco loco pero sus innegables\n" +
                "conocimientos sobre especias y alimentos puede sernos de gran ayuda. ¿Lo acogemos? SI / NO");

        AdministrarColonos.raya();

        boolean eleccion = false;

        String opcion = "";

        do{

            opcion = sc.nextLine();

            switch (opcion.toUpperCase()) {

                case "SI":
                    if(AdministrarColonos.listaColonosContratados.size() < AdministrarColonos.tamanyoColonia) {

                        AdministrarColonos.contrataColonoConEdad();

                        AdministrarColonos.rayaConsola();

                        System.out.println("Contratado colono chamán");

                        AdministrarColonos.raya();

                        rayaEvento();

                        System.out.println("El viejo chamán te agradece tu hospitalidad y te otorga un pequeño saquito con diferentes tipos de frutos");

                        Fruto.setCantidadFruto(Fruto.getCantidadFruto()+20);

                        System.out.println("El saquito contenía 20 frutos silvestres diferentes. Huelen un poco mal, pero no le das mucha importancia");

                        AdministrarColonos.raya();

                        eliminaEvento(1);

                    }

                    else{

                        AdministrarColonos.rayaConsola();

                        System.out.println("No hay suficientes casas para albergar más colonos.\nConstruye más viviendas para aumentar la capacidad de la colonia.");

                        AdministrarColonos.raya();

                    }

                    eleccion = true;

                    break;

                case "NO":

                    rayaEvento();

                    System.out.println("El viejo chamán huye despavorido sin razón aparente. Escuchas como \ngrita en un idioma extraño. Quizá tomaste la decisión correcta, o no...");

                    AdministrarColonos.raya();

                    eleccion = true;

                    break;

                default:

                    System.out.println("Eleccion inválida, vuelve a elegir");

                    break;

            }

        }while(!eleccion);

    }

    public static void EventoEnanoCabezon1(){

        rayaEvento();

        System.out.println("Un enanano cabezón aparece con un gran saco a la puertas de " + Vista.nombreColonia + ". \nInsiste en otorgarte parte de su reserva" +
                " de frutos porque pretende que le ayudes en un futuro, \npero no te dice para qué. ¿Lo aceptas? SI / NO");

        AdministrarColonos.raya();

        boolean eleccion = false;

        String opcion = "";

        do{

            opcion = sc.nextLine();

            switch (opcion.toUpperCase()) {

                case "SI":

                    rayaEvento();

                    System.out.println("El enano cabezón te da su saco, el cual contiene la cantidad de 200 frutos. \nActo seguido, se escabuye entre los árboles" +
                            " sin mediar palabra.");

                    AdministrarColonos.raya();

                    Fruto.setCantidadFruto(Fruto.getCantidadFruto()+200);

                    aceptaEnano = true;

                    eleccion = true;

                    break;

                case "NO":

                    rayaEvento();

                    System.out.println("El enano cabezón se va con su saco entre murmullos poco amigables");

                    AdministrarColonos.raya();

                    eleccion = true;

                    break;

                default:

                    System.out.println("Eleccion inválida, vuelve a elegir");

                    break;

            }

        }while(!eleccion);

    }

    public static void EventoMercader(){

        rayaEvento();

        System.out.println("Ha llegado a la colonia un famoso mercader. Te ofrece la compra de" +
                " una caja misteriosa \ncuyo contenido es desconocido pero es posible que te ayude o te " +
                "lleve a la ruina.\n" +
                "El precio de la caja es de 300 frutos ¿La tomas? SI / NO");

        AdministrarColonos.raya();

        boolean eleccion = false;

        String opcion = "";

            do{

                opcion = sc.nextLine();

                switch (opcion.toUpperCase()) {

                    case "SI":

                        if(Fruto.hayFrutosSuficientes(300)) {

                            Fruto.setCantidadFruto(Fruto.getCantidadFruto()-300);

                        double resultado = AdministrarColonos.aleatorio(100);

                        if(resultado>=67){

                            Colono colonoTemp = AdministrarColonos.getColonoAleatorio();

                            rayaEvento();

                            System.out.println("Un colono abre la caja misteriosa y dentro de ella hay un conejito muy mono.\n" +
                                    "El colono no duda en acariciarlo, pero este salta sobre él, lo muerde y sale corriendo.\n" +
                                    "El colono " + colonoTemp.getNombre() + " " +  colonoTemp.getApellido() + " ha sido infectado con la rabia.");

                            AdministrarColonos.raya();

                            AdministrarColonos.convierteColonoRabia(colonoTemp);

                        }

                        else if(resultado>=33){

                            rayaEvento();

                            System.out.println("¡Enhorabuena! ¡El cofre misterioso contenía la exorbitada cantidad de 1000 frutos!\n" +
                                    "El mercader te felicita y se escabuye entre los árboles, pero no será la última vez que lo veas...");

                            AdministrarColonos.raya();

                            Fruto.setCantidadFruto(Fruto.getCantidadFruto()+1000);

                        }
                        else{

                            rayaEvento();

                            System.out.println("Un colono abre la caja y, pese a la expectación, el cofre estaba totalmente vacío.\n" +
                                    "El mercader se aleja de tu colonia mientras asegura que la próxima vez os veáis\n" +
                                    "quizá la suerte os sonría.");

                            AdministrarColonos.raya();

                        }

                        }else{

                            rayaEvento();

                            System.out.println("No tienes cantidad suficientes de frutos para pagar la caja.\nEl mercader se aleja de tu colonia, asegurando que no será la última vez que os encotréis");

                            AdministrarColonos.raya();

                        }

                        eleccion = true;

                        break;

                    case "NO":

                        rayaEvento();

                        System.out.println("El mercader se aleja de tu colonia, asegurando que no será la última vez que os encotréis");

                        AdministrarColonos.raya();

                        eleccion = true;

                        break;

                    default:

                        System.out.println("Eleccion inválida, vuelve a elegir");

                        break;

                }

            }while(!eleccion);

    }

    public static void EventoEnanoCabezon2(){

        rayaEvento();

        System.out.println("De repente, vuelve a aparecer el generoso enano cabezón que te dio su bolsa de frutos. \nEntre gritos poco entendibles, reclama tu ayuda. " +
                "La reserva de frutos de su aldea ha sido devastada y necesita 300 frutos. \nTe asegura que no aceptar, tendrá consecuencias terribles. Además," +
                " estás en deuda con él. ¿Darle los 300 frutos? SI / NO");

        AdministrarColonos.raya();

        boolean eleccion = false;

        String opcion = "";

        do{

            opcion = sc.nextLine();

            switch (opcion.toUpperCase()) {

                case "SI":

                    if(Fruto.hayFrutosSuficientes(300)) {

                        Fruto.setCantidadFruto(Fruto.getCantidadFruto() - 300);

                        rayaEvento();

                        System.out.println("Pierdes 300 frutos, pero te ganas la confianza de Rigoberto II, el enano cabezón");

                        AdministrarColonos.raya();

                        aceptaEnano = false;

                        ControlDeJuego.turnoEventoEnano = ControlDeJuego.getTurnoGlobal();

                    }else{

                        Colono colonoTemp = AdministrarColonos.getColonoAleatorio();

                        AdministrarColonos.eliminaColono(AdministrarColonos.getID(colonoTemp));

                        rayaEvento();

                        System.out.println("No tienes la cantidad suficiente de frutos. Rigoberto II, decide apresar a uno de tus colonos\nEl colono "+ colonoTemp.getNombre() + " " + colonoTemp.getApellido() + " ha sido apresado para nunca volver.");

                        AdministrarColonos.raya();

                    }

                    eleccion = true;

                    break;

                case "NO":

                    if(Fruto.hayFrutosSuficientes(700)) {

                        Fruto.setCantidadFruto(Fruto.getCantidadFruto() - 700);

                        rayaEvento();

                        System.out.println("El enano cabezón Rigoberto II, lanza un grito de guerra que desemboca en una \nbrutal emboscada con cientos" +
                                " de enanos cabezones que saquean tu colonia. Pierdes 700 frutos");

                        AdministrarColonos.raya();

                    }else{

                        rayaEvento();

                        System.out.println("El enano cabezón Rigoberto II, lanza un grito de guerra que desemboca en una \nbrutal emboscada con cientos" +
                                " de enanos cabezones que saquean tu colonia. Pierdes todos tus frutos.");

                        AdministrarColonos.raya();

                        Fruto.setCantidadFruto(0);

                    }

                    aceptaEnano = false;

                    eleccion = true;

                    break;

                default:

                    System.out.println("Eleccion inválida, vuelve a elegir");

                    break;

            }

        }while(!eleccion);

    }

}
