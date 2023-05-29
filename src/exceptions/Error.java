package exceptions;

public class Error extends RuntimeException{

    public Error(String mensaje) {

        System.out.println(mensaje);

    }

}