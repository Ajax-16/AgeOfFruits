package minijuegos;

import exceptions.HayError;
import game.AdministrarColonos;
import game.Colono;
import game.Eventos;
import resources.Fruto;

import java.util.Random;

public class Nucleo {
    char[][] mapaResuelto;              //Mapas
    char[][] mapaUsuario;

    char[] caracteres;
    public static int filas = 10;              //Opciones
    public static int columnas = 10;
    public static char sinTocar = '.';
    public static char bandera = 'f';
    public static char mina = 'X';
    public static char camino = 'C';
    public static char casillaFinal = 'W';
    public static int cantidadMinas = 20;


    static Random rand = new Random();  //Utilidades

    int cantidadMinasSincolocar = cantidadMinas;

    public boolean juegoAcabado = false;
    int casillasTocadas = 0;

    int turno = 0;
    boolean ganado = false;

    public int[] posicionInicial;
    public int[] posicionFinal;

    enum EstadoTiro{
        sinTocar,
        bandera,
        mina,
        tocado,
        victoria,
        caracterInvalido
    }

    public enum Accion{
        minar,
        ponerBandera,
        quitarBandera
    }


    public Nucleo(){                            //Metodo constructor
        mapaResuelto = new char[filas][columnas];
        mapaUsuario = new char[filas][columnas];
        caracteres = new char[filas];

        posicionInicial = new int[] {-1,-1};
        posicionFinal = new int[]{-1, -1};

        IniciarMapa(mapaResuelto);
        IniciarMapa(mapaUsuario);

        for (int i = 0; i < filas; i++) {
            caracteres[i] = (char) ('A' + i);
        }
    }

    private void IniciarMapa(char[][] mapa)     //Metodo que inicia el mapa
    {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                mapa[i][j] = sinTocar;
            }
        }
    }

    public void ColocarCamino(char[][] mapa, int[] posicionInicial, int[] posicionFinal)    //Metodo que coloca camino desde la posicion inicial hasta la final, recomendable solo utilizar con mapas vacios o mapas con caminos
    {
        if (this.posicionInicial[0] == -1 && this.posicionInicial[1] == -1)
        {
            this.posicionInicial = posicionInicial;
        }

        this.posicionFinal = posicionFinal;

        int filaActual = posicionInicial[0];
        int columnaActual = posicionInicial[1];

        int filaFinal = posicionFinal[0];
        int columnaFinal = posicionFinal[1];

        boolean colocacionAcabada = false;
        while(!colocacionAcabada) {
            mapa[filaActual][columnaActual] = camino;

            if (filaActual == filaFinal && columnaActual == columnaFinal) {
                colocacionAcabada = true;
            }else
            {
                double eleccion = Math.random()*2;
                if ((int) eleccion == 0) {
                    if (filaActual < filaFinal) filaActual++;
                    else if (filaActual > filaFinal) filaActual--;
                } else {
                    if (columnaActual < columnaFinal) columnaActual++;
                    else if (columnaActual > columnaFinal) columnaActual--;
                }
            }
        }
    }

    public void ColocarMinas(char[][] mapa)    //Metodo para colocar minas en un mapa
    {
        int contadorLoopsSinColocar = 0;
        while(cantidadMinasSincolocar > 0) {

            int fila = rand.nextInt(filas);
            int columna = rand.nextInt(columnas);

            int[] posicion = {fila, columna};

            switch (comprobarCordenadas(posicion, mapa)) {
                case sinTocar:
                    mapa[posicion[0]][posicion[1]] = mina;
                    cantidadMinasSincolocar--;

                    contadorLoopsSinColocar = 0;
                    break;

                default:
                    contadorLoopsSinColocar++;
                    if(contadorLoopsSinColocar == 20)
                    {
                        cantidadMinasSincolocar = 0;
                    }
            }
        }

    }

    private EstadoTiro comprobarCordenadas(int[] posicion, char[][] mapa) //Metodo para comprobar que hay en una posicion especifica de un mapa
    {
        char caracterPosicion = mapa[posicion[0]][posicion[1]];

        if (caracterPosicion == sinTocar) return EstadoTiro.sinTocar;

        if (caracterPosicion == bandera) return EstadoTiro.bandera;

        if (caracterPosicion == mina) return EstadoTiro.mina;
        if (caracterPosicion == casillaFinal) return EstadoTiro.victoria;

        if (caracterPosicion >= '0' && caracterPosicion <= '8') return EstadoTiro.tocado;

        return EstadoTiro.caracterInvalido;
    }


    public String MapaToTexto(char[][] mapa) {  //Metod que pasa un mapa a texto

        String mapamodoTexto = "   ";

        for (int i = 0; i < columnas; i++) {
            mapamodoTexto += " [" + i + "]";
        }

        mapamodoTexto += '\n';

        for (int filaActual = 0; filaActual < filas; filaActual++) {
            mapamodoTexto += "[" + caracteres[filaActual] + "]";

            for (int columnaActual = 0; columnaActual < columnas; columnaActual++) {
                mapamodoTexto += "  " + mapa[filaActual][columnaActual] + " ";
            }
            mapamodoTexto += '\n';
        }

        return mapamodoTexto;
    }

    public String Acciones(int[]posicion, Accion accion)        //Metodo que comprueba las acciones del usuario
    {
        if (accion == null ) return " ";

        EstadoTiro EstadoPosicion = comprobarCordenadas(posicion, mapaUsuario);

        if (EstadoPosicion == EstadoTiro.tocado)  return "Posicion invalida(ya se ha revelado esta posicion)";

        switch (accion)
        {
            case ponerBandera:
                switch (EstadoPosicion)
                {
                    case sinTocar:
                        mapaUsuario[posicion[0]][posicion[1]] = bandera;
                        return "";
                    case bandera:
                        return "Posicion invalida(ya hay una bandera en la posicion seleccionada)";
                    default:
                        HayError.esValido(true,"Fallo en la accion de poner bandera(valor inesperado)");
                }

            case quitarBandera:
                switch (EstadoPosicion)
                {
                    case sinTocar:
                        return "Posicion invalida(no hay ninguna bandera en la posicion seleccionada)";
                    case bandera:
                        mapaUsuario[posicion[0]][posicion[1]] = sinTocar;
                        return "";
                    default:
                        HayError.esValido(true,"Fallo en la accion de poner bandera(valor inesperado)");
                }
            case minar:
                switch (EstadoPosicion)
                {
                    case sinTocar:
                    case victoria:
                        Minar(posicion);
                        return "";
                    case bandera:
                        return "Posicion invalida(no puedes minar banderas, quita la bandera para picar)";

                    default:
                        HayError.esValido(true,"Fallo en alguna accion (valor inesperado)");

                }

        }

        HayError.esValido((true),"Fallo en la seleccion de acciones");
        return"";
    }

    private void Minar(int[] posicion)  //Metodo para minar
    {
        if(mapaResuelto[posicion[0]][posicion[1]] == mina)
        {
            juegoAcabado = true;
            casillasTocadas--;
        }

        int filaBuscar = posicion[0];
        int columnaBuscar = posicion[1];
        int cantidadMinas = 0;
        boolean posicionValida = false;
        int[] posicionesBuscar;

        for (int i = -1; i < 2; i++) {

            if (filaBuscar + i >= 0 && filaBuscar + i < filas){

                for (int j = -1; j < 2; j++) {
                    if(columnaBuscar + j >= 0 && columnaBuscar +j < columnas){
                        if (mapaResuelto[filaBuscar + i][columnaBuscar + j] == mina)
                        {
                            cantidadMinas++;
                        }
                        int[] coordenadasTemporales = new int [] {filaBuscar + i, columnaBuscar + j};

                        if (comprobarCordenadas(coordenadasTemporales, mapaUsuario) == EstadoTiro.tocado)
                        {
                            posicionValida = true;
                        }
                    }
                }
            }
        }

        if (posicionValida || turno == 0) {

            if (comprobarCordenadas(posicion, mapaUsuario) == EstadoTiro.victoria)
            {
                ganado = true;
                juegoAcabado = true;
            }


            mapaUsuario[posicion[0]][posicion[1]] = (char) ('0' + cantidadMinas);
            casillasTocadas++;
            if (cantidadMinas == 0) {
                for (int i = -1; i < 2; i++) {

                    if (filaBuscar + i >= 0 && filaBuscar + i < filas) {

                        for (int j = -1; j < 2; j++) {
                            if (columnaBuscar + j >= 0 && columnaBuscar + j < columnas) {
                                if (mapaUsuario[filaBuscar + i][columnaBuscar + j] == sinTocar) {
                                    posicionesBuscar = new int[]{filaBuscar + i, columnaBuscar + j};
                                    Minar(posicionesBuscar);
                                }
                            }
                        }
                    }
                }
            }
        }

        if(turno == 0) pasarTurno();
    }

    public int[] TransformarCordenadas(char fila, char columna)     //Metodo que transforma dos char a cordenadas
        {

            if(fila < 'A' || fila > 'Z' || columna < '0' || columna > '9')
            {
                    return new int[]{-1,-1};
            }

            int posicionFila = fila - 'A';
            int posicionColumna = columna - '0';

            return new int[]{posicionFila,posicionColumna};
        }

        public void pasarTurno()    //Metodo utilizado para pasar de turno
        {
            turno++;
        }

    public boolean isJuegoAcabado() {
        return juegoAcabado;
    }
    public char[][] getMapaResuelto() {
        return mapaResuelto;
    }

    public char[][] getMapaUsuario() {
        return mapaUsuario;
    }
    public String PantallaFinal()   //Metodo que muestra un mensaje u otro por pantalla si se ha ganado o no
    {
        if (ganado)
        {
            Fruto.setCantidadFruto(Fruto.getCantidadFruto()+700);

            Eventos.rayaEvento();

            return MapaToTexto(mapaUsuario) + "\n¡El colono ha llegado a la posición del tesoro! Este abre el cofre\n" +
                    "y se encuentra la cantidad de 700 frutos. \n--------------------------------------------";
        }

        Colono colonoTemp = AdministrarColonos.getColonoAleatorio();

        AdministrarColonos.eliminaColono(AdministrarColonos.getID(colonoTemp));

        Eventos.rayaEvento();

        return MapaToTexto(mapaResuelto) + "\nEl colono " + colonoTemp.getNombre() + " " + colonoTemp.getApellido() + " ha pisado una planta carnívora mutante por accidente\n " +
                "y ha sido brutalmente devorado por tu incompetencia.\n--------------------------------------------";
    }
}
