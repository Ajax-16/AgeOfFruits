package game;

import exceptions.HayError;
import resources.Fruto;
import resources.Madera;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class AdministrarColonos {

    public static ArrayList<Colono> listaColonosContratados = new ArrayList<>();
    private static ArrayList<Colono> listaColonosMuertos = new ArrayList<>();
    ;
    private static int cantidadColonos = listaColonosContratados.size();
    private static String[] nombres;
    private static String[] apellido1;
    public static int tamanyoColonia = 3;  //Se asigna el valor de 3 colonos máximos iniciales

    public AdministrarColonos() {

    }

    public static ArrayList<Colono> getListaColonosContratados() {
        return listaColonosContratados;
    }

    public static ArrayList<Colono> getListaColonosMuertos() {
        return listaColonosMuertos;
    }

    public static void contrataColono() {

        Colono colonoTemp = new Colono();

        if (listaColonosContratados.size() < tamanyoColonia) {

            listaColonosContratados.add(colonoTemp);

            asignaNombreYApellidoColono(colonoTemp);

        }

    }

    public static void contrataColonoConEdad() {

        Colono colonoTemp = new Colono();

        if (listaColonosContratados.size() <= tamanyoColonia) {

            listaColonosContratados.add(colonoTemp);

            asignaNombreYApellidoColonoConEdad(colonoTemp, 3);

        }

    }

    public static void eliminaColono(int id) {

        for (Colono c : listaColonosContratados) {

            if (getID(c) == id) {

                listaColonosMuertos.add(c);

            }

        }

        listaColonosContratados.removeIf(c -> getID(c) == id);

    }

    public static void convierteColonoRabia(Colono c) {

        int id = getID(c);

        ColonoRabia colonoRabioso = new ColonoRabia(c);

        int contador = 0;

        for (Colono colono : listaColonosContratados) {

            if (getID(colono) == id) {

                listaColonosContratados.set(contador, colonoRabioso);

            }

            contador++;

        }

    }

    public static int getID(Colono c) {

        return listaColonosContratados.indexOf(c) + 1;

    }

    public static StringBuilder muestraListaColonos() {

        StringBuilder msj = new StringBuilder();

        for (Colono c : listaColonosContratados) {

            msj.append(c.toString());

        }

        return msj;

    }

    public static StringBuilder muestraIDnombreColonos() {

        StringBuilder msj = new StringBuilder();

        for (Colono c : listaColonosContratados) {

            msj.append(c.mostrarIDYNombreStats());

        }

        return msj;

    }

    public static StringBuilder muestraIDnombreColonosDisponibles() {

        StringBuilder msj = new StringBuilder();

        for (Colono c : listaColonosContratados) {

            if(c.estado.equals("De pie")){

                msj.append(c.mostrarIDYNombreDisponibles());

            }

        }

        return msj;

    }

    public static Colono buscaColonoPorId(int id) {

        Colono colonoTemp = new Colono();

        for (Colono c : listaColonosContratados) {

            if (id == getID(c)) {

                colonoTemp = c;

            } else {

                HayError.esValido(true, "No existe ningún colono con dicho ID");

            }

        }

        return colonoTemp;

    }

    public static void asignaNombreYApellidoColono(Colono c) {

        nombres = new String[]{"David", "Jesús", "Diego", "Andrei", "Javi", "Antonio"
                , "José", "Francisco", "Juan", "Ángel", "Noelia", "Cristina", "Lucía", "Celia", "Gabriela"
                , "Andrea", "Irene", "Nuria", "Nerea", "María"};

        apellido1 = new String[]{"García", "Rodríguez", "Davidson", "Johnson", "Hernández", "Martínez"
                , "Sánchez", "Pérez", "François", "López", "Guerrero", "Zapatero", "Cano", "Díaz", "Ruíz", "Muñoz"
                , "Bernárdez", "Álvarez", "Jiménez", "Lopera"};

        String nombre = nombres[(int) (aleatorio(nombres.length))];
        String apellido1S = apellido1[(int) (aleatorio(apellido1.length))];
        c.setNombre(nombre);
        c.setApellido(apellido1S);
        c.setEdad((int) (aleatorio(80)));

        while (c.getEdad() <= 18) {

            c.setEdad((int) (aleatorio(65)));

        }

    }

    public static void asignaNombreYApellidoColonoConEdad(Colono c, int edad) {

        nombres = new String[]{"David", "Jesús", "Diego", "Andrei", "Javi", "Antonio"
                , "José", "Francisco", "Juan", "Ángel", "Noelia", "Cristina", "Lucía", "Celia", "Gabriela"
                , "Andrea", "Irene", "Nuria", "Nerea", "María"};

        apellido1 = new String[]{"García", "Rodríguez", "Davidson", "Johnson", "Hernández", "Martínez"
                , "Sánchez", "Pérez", "François", "López", "Guerrero", "Zapatero", "Cano", "Díaz", "Ruíz", "Muñoz"
                , "Bernárdez", "Álvarez", "Jiménez", "Lopera"};

        String nombre = nombres[(int) (aleatorio(nombres.length))];
        String apellido1S = apellido1[(int) (aleatorio(apellido1.length))];
        c.setNombre(nombre);
        c.setApellido(apellido1S);

        switch (edad) {

            case 1:

                c.setEdad((int) (aleatorio(80)));

                while (c.getEdad() >= 0) {

                    c.setEdad((int) (aleatorio(18)));

                }

                break;

            case 2:

                c.setEdad((int) (aleatorio(80)));

                while (c.getEdad() >= 18) {

                    c.setEdad((int) (aleatorio(65)));

                }

                break;

            case 3:

                c.setEdad((int) (aleatorio(80)));

                while (c.getEdad() <= 65) {

                    c.setEdad((int) (aleatorio(80)));

                }

                break;

        }

    }

    public static void pasaTurnoIndividual() {

        for (Colono i : listaColonosContratados) {

            if (i.empiezaTurno) {

                i.incrementaTurno();

            }

        }

        compruebaDePie();

    }

    public static void raya() {

        System.out.println("--------------------------------------------");

    }

    public static void rayaConsola() {

        System.out.println("----------------- !Consola -----------------");

    }

    public static void setEstado(int id, int opcion) {

        switch (opcion) {

            case 1:

                listaColonosContratados.get(id - 1).estado = "De pie";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                Vista.salirEstado = true;

                break;

            case 2:

                listaColonosContratados.get(id - 1).estado = "Sentado en el suelo";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 3:

                listaColonosContratados.get(id - 1).estado = "Paseando";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 4:

                listaColonosContratados.get(id - 1).estado = "Cantando";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 5:

                listaColonosContratados.get(id - 1).estado = "Recolectando frutos";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 6:

                listaColonosContratados.get(id - 1).estado = "Durmiendo";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 7:

                listaColonosContratados.get(id - 1).estado = "Comiendo";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 8:

                listaColonosContratados.get(id - 1).estado = "Recolectando Madera";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 20:

                listaColonosContratados.get(id - 1).estado = "Construyendo casa";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 21:

                listaColonosContratados.get(id - 1).estado = "Construyendo estación de frutos";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 22:

                listaColonosContratados.get(id - 1).estado = "Construyendo estación de madera";

                listaColonosContratados.get(id - 1).empiezaTurno = true;

                listaColonosContratados.get(id - 1).ocupado = true;

                Vista.salirEstado = true;

                break;

            case 0:

                Vista.salirEstado = true;

                break;

            default:

                System.out.println("La opción seleccionada es inválida.");

                break;
        }
    }

    public static void setForcedEstado() {

        for (Colono i : listaColonosContratados) {

            if (i.getTurnoIdividual() >= 3 && i.estado.equals("Recolectando frutos")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                if(!MapaColonia.hayEstacionDeFrutos) {

                    Fruto.setCantidadFruto(Fruto.getCantidadFruto() + 60);

                    raya();

                    System.out.println("El colono "+ i.getNombre() + " " + i.getApellido() + " ha recogido 60 frutos.");

                    raya();

                }
                else{

                    Fruto.setCantidadFruto( (int) (Fruto.getCantidadFruto() + (60 + (40 * MapaColonia.cantidadEstacionDeFrutos))));

                    raya();

                    System.out.println("El colono "+ i.getNombre() + " " + i.getApellido() + " ha recogido" + (60 + (40 * MapaColonia.cantidadEstacionDeFrutos)) + "frutos.");

                    raya();

                }

                i.energia -= 40;

                i.cordura -= 30;

                i.hambre += 25;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 3 && i.estado.equals("Cantando")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia -= 20;

                i.cordura += 50;

                i.hambre += 30;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 2 && i.estado.equals("Paseando")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia -= 20;

                i.cordura += 34;

                i.hambre += 15;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 4 && i.estado.equals("Durmiendo")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia += 40;

                i.cordura += 10;

                i.hambre += 10;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 2 && i.estado.equals("Sentado en el suelo")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia += 20;

                i.hambre += 5;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 1 && i.estado.equals("Comiendo")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                Fruto.setCantidadFruto(Fruto.getCantidadFruto() - 30);

                i.energia += 20;

                i.cordura += 5;

                i.hambre -= 60;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 3 && i.estado.equals("Recolectando Madera")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                if(!MapaColonia.hayEstacionDeMadera) {

                    Madera.setCantidadMadera(Madera.getCantidadMadera() + 40);

                    raya();

                    System.out.println("El colono "+ i.getNombre() + " " + i.getApellido() + " ha recogido 40 de madera.");

                    raya();

                }
                else{

                    Madera.setCantidadMadera( (int) (Madera.getCantidadMadera() + (40 + (30 * MapaColonia.cantidadEstacionDeMadera))));

                    raya();

                    System.out.println("El colono "+ i.getNombre() + " " + i.getApellido() + " ha recogido " + (40 + (30 * MapaColonia.cantidadEstacionDeMadera)) + " de madera.");

                    raya();

                }

                i.energia -= 30;

                i.cordura -= 30;

                i.hambre += 25;

                i.ocupado = false;

            }

            if (i.getTurnoIdividual() >= 4 && i.estado.equals("Construyendo casa")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia -= 30;

                i.cordura -= 20;

                i.hambre += 40;

                tamanyoColonia+=3;

                i.ocupado = false;

                raya();

                System.out.println("La construcción de la casa ha finalizado. \nLas nuevas ventajas ahora están disponibles.");

                raya();

            }

            if (i.getTurnoIdividual() >= 6 && i.estado.equals("Construyendo estación de frutos")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia -= 30;

                i.cordura -= 20;

                i.hambre += 40;

                MapaColonia.hayEstacionDeFrutos = true;

                MapaColonia.cantidadEstacionDeFrutos++;

                i.ocupado = false;

                raya();

                System.out.println("La construcción de la estación de frutos ha finalizado. \nLas nuevas ventajas ahora están disponibles.");

                raya();

            }

            if (i.getTurnoIdividual() >= 6 && i.estado.equals("Construyendo estación de madera")) {

                i.estado = "De pie";

                i.turnoIdividual = 0;

                i.empiezaTurno = false;

                i.energia -= 30;

                i.cordura -= 20;

                i.hambre += 40;

                MapaColonia.hayEstacionDeMadera = true;

                MapaColonia.cantidadEstacionDeMadera++;

                i.ocupado = false;

                raya();

                System.out.println("La construcción de la estación de frutos ha finalizado. \nLas nuevas ventajas ahora están disponibles.");

                raya();

            }

        }

    }

    public static void compruebaDePie() {

        for (Colono i : listaColonosContratados) {

            if (i.estado.equals("De pie")) {

                i.energia -= 10;

                i.cordura -= 10;

                i.hambre += 10;

                i.ocupado = false;

            }

        }

    }

    public static Colono getColonoAleatorio(){

        double colonoAfectado = aleatorio(getCantidadColonos()+1);

        while((int) colonoAfectado == 0){

            colonoAfectado = aleatorio(getCantidadColonos()+1);

        }

        return buscaColonoPorId((int)(colonoAfectado));

    }

    public static double aleatorio(int rango){

        return Math.random() * rango;

    }

    public static int getCantidadColonos(){

        return listaColonosContratados.size();

    }

    public static void compruebaCredenciales(){

        for(int i = 0; i< listaColonosContratados.size(); i++){

            if(AdministrarColonos.buscaColonoPorId(i+1).getEnergia() <= 0){

                raya();

                System.out.println("El colono " + AdministrarColonos.buscaColonoPorId(i+1).getNombre() + " " + AdministrarColonos.buscaColonoPorId(i+1).getApellido() + " ha muerto del agotamiento.");

                raya();

                eliminaColono(getID(AdministrarColonos.buscaColonoPorId(i+1)));

                i--;

            }

            else if(AdministrarColonos.buscaColonoPorId(i+1).getCordura() <= 0) {

                raya();

                System.out.println("El colono " + AdministrarColonos.buscaColonoPorId(i+1).getNombre() + " " + AdministrarColonos.buscaColonoPorId(i+1).getApellido() + " se ha sumido en la locura ");

                raya();

                // AÑADIR ENFERMEDAD LOCURA

            }

            else if(AdministrarColonos.buscaColonoPorId(i+1).getHambre() >=100) {

                raya();

                System.out.println("El colono " + AdministrarColonos.buscaColonoPorId(i+1).getNombre() + " " + AdministrarColonos.buscaColonoPorId(i+1).getApellido() + " ha muerto de inanición.");

                raya();

                eliminaColono(getID(AdministrarColonos.buscaColonoPorId(i+1)));

                i--;

            }

            AdministrarColonos.buscaColonoPorId(i+1).gestionaEnergia();

            AdministrarColonos.buscaColonoPorId(i+1).gestionaHambre();

            AdministrarColonos.buscaColonoPorId(i+1).gestionaCordura();

            if(Fruto.getCantidadFruto()<=0){

                Fruto.setCantidadFruto(0);

            }

        }

    }

}