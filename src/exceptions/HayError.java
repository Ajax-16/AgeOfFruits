package exceptions;

public class HayError{

    public static void esValido(boolean valido, String mensaje)  throws Error {

        if(!valido){

            throw new Error(mensaje);

        }

    }

}