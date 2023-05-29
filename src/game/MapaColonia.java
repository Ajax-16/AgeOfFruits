package game;

import exceptions.HayError;

import java.util.Arrays;

public class MapaColonia {

    private char [][] mapaColonia;
    private String [][] espacioInfo;
    private char[] letras;
    private int tamanyoColonia;
    public final int maximoCeldas = 26;
    private char pasto = '.';
    private char esquinaParedAbajoIzq = '╚';
    private char esquinaParedAbajoDer = '╝';
    private char esquinaParedArribaIzq = '╔';
    private char esquinaParedArribaDer = '╗';
    private char paredH = '═';
    private char paredV = '║';
    private String infoEnBlanco = "   ";
    private String infoCasa = " C ";
    private String infoEstacionFrutos = " F ";
    private String infoEstacionMadera = " M ";
    private String infoGestion = " G ";
    public boolean noPuedeColocarCasa = false;
    public static boolean hayEstacionDeFrutos = false;
    public static int cantidadEstacionDeFrutos = 0;
    public static boolean hayEstacionDeMadera = false;
    public static int cantidadEstacionDeMadera = 0;
    public MapaColonia(int tamanyoColonia){

        this.tamanyoColonia = tamanyoColonia;

        mapaColonia = new char[tamanyoColonia][tamanyoColonia];

        espacioInfo = new String[tamanyoColonia][tamanyoColonia];

        iniciaColonia();

    }

    public void iniciaColonia(){

        for(int i = 0; i<tamanyoColonia; i++){

            for(int j = 0; j<tamanyoColonia; j++){

                mapaColonia[i][j] = pasto;

                espacioInfo[i][j] = infoEnBlanco;

            }

        }

    }

    public void muestraColonia(){

        generaLetrasdeCoordenadas();

        System.out.print("    ");

     for(int i = 0; i<tamanyoColonia; i++){

         System.out.print("[" + letras[i] + "] ");

     }

        System.out.println("");

        for (int i = 0; i < tamanyoColonia; i++) {
            System.out.print("[" + letras[i] + "]  ");
            for (int j = 0; j < tamanyoColonia; j++) {
                System.out.print(mapaColonia[i][j] + espacioInfo[i][j]);
            }
            System.out.println("");
        }
    }

    public void generaLetrasdeCoordenadas() {

        letras = new char[tamanyoColonia];
        for (int i = 0; i < tamanyoColonia; i++)
            letras[i] = (char) ('A' + i);

    }

    public int [] asignaPosicion(String posicion){

        int [] coordenadas = new int[2];

        if(posicion.matches("^[A-z][A-z]*$")) {

            int fila = pasaLetraACodigo(posicion.charAt(0));

            int columna = pasaLetraACodigo(posicion.charAt(1));

            if (fila >= 0 && fila < tamanyoColonia && columna >= 0 && columna < tamanyoColonia) {

                coordenadas = new int[]{fila, columna};

            } else {

                coordenadas = new int[]{-1, -1};

            }

        }else{

            coordenadas = new int[]{-1, -1};

        }

        return coordenadas;

    }

    public void añadeCasa(int [] posicion){

        if(posicion[0] != -1 && posicion[1] != -1) {

            int contador = 0;

            for(int i = posicion[0]; i == posicion[0]+contador; i++){

                for(int j = posicion[1]; j == posicion[1]+contador; i++){

                    if(mapaColonia[i][j] == pasto && mapaColonia[i][j+1] == pasto  && mapaColonia[i+1][j] == pasto  && mapaColonia[i+1][j+1] == pasto){

                        mapaColonia[i][j] = esquinaParedArribaIzq;

                        espacioInfo[i][j] = infoCasa;

                        mapaColonia[i][j+1] = esquinaParedArribaDer;

                        mapaColonia[i+1][j] = esquinaParedAbajoIzq;

                        espacioInfo[i+1][j] = infoCasa;

                        mapaColonia[i+1][j+1] = esquinaParedAbajoDer;



                    }else{

                        noPuedeColocarCasa = true;

                    }

                    contador++;

                }

            }

        }
        else  {

            System.out.println("Coordenada inválida");

        }

    }

    public void añadeEstacionFrutos(int [] posicion){

        if(posicion[0] != -1 && posicion[1] != -1) {

            int contador = 0;

            for(int i = posicion[0]; i == posicion[0]+contador; i++){

                for(int j = posicion[1]; j == posicion[1]+contador; i++){

                    if(mapaColonia[i][j] == pasto && mapaColonia[i][j+1] == pasto  && mapaColonia[i+1][j] == pasto  && mapaColonia[i+1][j+1] == pasto && mapaColonia[i+2][j] == pasto && mapaColonia[i+2][j+1] == pasto && mapaColonia[i+2][j+2] == pasto && mapaColonia[i][j+2] == pasto && mapaColonia[i+1][j+2] == pasto){

                        mapaColonia[i][j] = esquinaParedArribaIzq;

                        espacioInfo[i][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+1] = paredH;

                        espacioInfo[i][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+2] = esquinaParedArribaDer;

                        mapaColonia[i+1][j] = paredV;

                        espacioInfo[i+1][j] = infoEstacionFrutos;

                        mapaColonia[i+1][j+1] = 'F';

                        espacioInfo[i+1][j+1] = infoEstacionFrutos;

                        mapaColonia[i+1][j+2] = paredV;

                        mapaColonia[i+2][j] = esquinaParedAbajoIzq;

                        espacioInfo[i+2][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+1] = paredH;

                        espacioInfo[i+2][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+2] = esquinaParedAbajoDer;

                    }else{

                        noPuedeColocarCasa = true;

                    }

                    contador++;

                }

            }

        }
        else  {

            System.out.println("Coordenada inválida");

        }

    }

    public void añadeEstacionMadera(int [] posicion){

        if(posicion[0] != -1 && posicion[1] != -1) {

            int contador = 0;

            for(int i = posicion[0]; i == posicion[0]+contador; i++){

                for(int j = posicion[1]; j == posicion[1]+contador; i++){

                    if(mapaColonia[i][j] == pasto && mapaColonia[i][j+1] == pasto  && mapaColonia[i+1][j] == pasto  && mapaColonia[i+1][j+1] == pasto && mapaColonia[i+2][j] == pasto && mapaColonia[i+2][j+1] == pasto && mapaColonia[i+2][j+2] == pasto && mapaColonia[i][j+2] == pasto && mapaColonia[i+1][j+2] == pasto){

                        mapaColonia[i][j] = esquinaParedArribaIzq;

                        espacioInfo[i][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+1] = paredH;

                        espacioInfo[i][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+2] = esquinaParedArribaDer;

                        mapaColonia[i+1][j] = paredV;

                        espacioInfo[i+1][j] = infoEstacionMadera;

                        mapaColonia[i+1][j+1] = 'M';

                        espacioInfo[i+1][j+1] = infoEstacionMadera;

                        mapaColonia[i+1][j+2] = paredV;

                        mapaColonia[i+2][j] = esquinaParedAbajoIzq;

                        espacioInfo[i+2][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+1] = paredH;

                        espacioInfo[i+2][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+2] = esquinaParedAbajoDer;

                    }else{

                        noPuedeColocarCasa = true;

                    }

                    contador++;

                }

            }

        }
        else  {

            System.out.println("Coordenada inválida");

        }

    }

    public void añadeCentroDeGestion(int [] posicion){

        if(posicion[0] != -1 && posicion[1] != -1) {

            int contador = 0;

            for(int i = posicion[0]; i == posicion[0]+contador; i++){

                for(int j = posicion[1]; j == posicion[1]+contador; i++){

                    if(mapaColonia[i][j] == pasto && mapaColonia[i][j+1] == pasto  && mapaColonia[i+1][j] == pasto  && mapaColonia[i+1][j+1] == pasto && mapaColonia[i+2][j] == pasto && mapaColonia[i+2][j+1] == pasto && mapaColonia[i+2][j+2] == pasto && mapaColonia[i][j+2] == pasto && mapaColonia[i+1][j+2] == pasto){

                        mapaColonia[i][j] = esquinaParedArribaIzq;

                        espacioInfo[i][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+1] = paredH;

                        espacioInfo[i][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i][j+2] = esquinaParedArribaDer;

                        mapaColonia[i+1][j] = paredV;

                        espacioInfo[i+1][j] = infoGestion;

                        mapaColonia[i+1][j+1] = 'G';

                        espacioInfo[i+1][j+1] = infoGestion;

                        mapaColonia[i+1][j+2] = paredV;

                        mapaColonia[i+2][j] = esquinaParedAbajoIzq;

                        espacioInfo[i+2][j] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+1] = paredH;

                        espacioInfo[i+2][j+1] = " " + String.valueOf(paredH) + " ";

                        mapaColonia[i+2][j+2] = esquinaParedAbajoDer;

                    }else{

                        noPuedeColocarCasa = true;

                    }

                    contador++;

                }

            }

        }
        else  {

            System.out.println("Coordenada inválida");

        }

    }

    public int pasaLetraACodigo(char letra){

        return Character.getNumericValue(letra) - Character.getNumericValue('A');

    }

    public void agrandaColonia(int tamanyoNuevo) {

        copiaYAgrandaColonia(tamanyoNuevo, this);

    }

    public void copiaColonia(int tamanyoNuevo, MapaColonia mapaAntiguo){

        char [][] mapaColoniaNuevo = new char[tamanyoNuevo][tamanyoNuevo];

        String [][] espacioInfoNueva = new String [tamanyoNuevo][tamanyoNuevo];

        for(int i = 0; i<tamanyoColonia; i++){

            for (int j = 0; j<tamanyoColonia; j++){

                mapaColoniaNuevo[i][j] = mapaAntiguo.mapaColonia[i][j];

                espacioInfoNueva[i][j] = mapaAntiguo.espacioInfo[i][j];

            }

        }

        mapaColonia = mapaColoniaNuevo;

        espacioInfo = espacioInfoNueva;

    }

    public void copiaYAgrandaColonia(int tamanyoNuevo, MapaColonia mapaAntiguo){

        char [][] mapaColoniaNuevo = new char[tamanyoNuevo][tamanyoNuevo];

        String [][] espacioInfoNueva = new String [tamanyoNuevo][tamanyoNuevo];

        for(int i = 0; i<tamanyoColonia; i++){

            for (int j = 0; j<tamanyoColonia; j++){

                mapaColoniaNuevo[i][j] = mapaAntiguo.mapaColonia[i][j];

                espacioInfoNueva[i][j] = mapaAntiguo.espacioInfo[i][j];

            }

        }

        for (int i = 0; i < tamanyoNuevo; i++) {

            for (int j = 0; j < tamanyoNuevo; j++) {

                if (i >= tamanyoColonia || j >= tamanyoColonia) {

                    mapaColoniaNuevo[i][j] = pasto;

                    espacioInfoNueva[i][j] = infoEnBlanco;

                }

            }

        }

        mapaColonia = mapaColoniaNuevo;

        espacioInfo = espacioInfoNueva;

        tamanyoColonia = tamanyoNuevo;

    }

}
