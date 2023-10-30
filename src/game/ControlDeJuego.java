package game;

import resources.Fruto;

import java.util.Scanner;

public class ControlDeJuego {

    private static int turnoGlobal;
    private static int turnosNoche;
    private static int turnosDia;
    private static final int turnosIntervaloDiaNoche = 3;     // El intervalo de turnos que han de pasar para cambiar de día a noche
    private static int probabilidadEvento = 30;
    public static int turnoEventoEnano = 0;
    private static double numEvento = 0;
    private static boolean hayEvento = false;
    public static boolean eventoEnanoForzado = false;
    public static boolean esDeNoche = false;
    public static Eventos evento = new Eventos();
    public static void pasaTurno() {

        turnoGlobal++;

        if(!esDeNoche){

            turnosDia++;

            if(turnosDia==turnosIntervaloDiaNoche){

                esDeNoche = true;

                turnosDia = 0;

            }

        } else {

            turnosNoche++;

            if(turnosNoche==turnosIntervaloDiaNoche){

                esDeNoche = false;

                turnosNoche = 0;

            }

        }

    }

    public static void pasaTurnoEnano(){

        if(Eventos.isAceptaEnano()){

            turnoEventoEnano++;

            eventoEnanoForzado = setEventoForzado(turnoEventoEnano, 10);

        }

    }

    public static void ocurreEvento(){

        numEvento = AdministrarColonos.aleatorio(100);

        if( (int) numEvento <= probabilidadEvento || eventoEnanoForzado){

            hayEvento = true;

        }

        if(hayEvento){

            evento.EventosController();

            hayEvento = false;

        }

    }

    public static boolean setEventoForzado(int turnoInicioEvento, int numTurnos){

        return turnoInicioEvento == numTurnos;

    }

    public static int getTurnoGlobal(){
        return turnoGlobal;
    }

    public static void getGameOver(){
        if(AdministrarColonos.getCantidadColonos()==0){
            System.out.println("Te has quedado sin colonos. Sin ellos, tu asentamiento no puede prosperar.\n        ***** GAME OVER *****");
            System.exit(0);
        }
    }

}
