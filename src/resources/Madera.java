package resources;

public class Madera {

    private static int cantidad = 0;
    private static final String descripcion = "Recurso de gran utilidad para la construcción. "
            + "Con él se pueden construir casas, estaciones de recolección... etc.";

    public static int getCantidadMadera(){

        return cantidad;

    }

    public static void setCantidadMadera(int c){

        cantidad = c;

    }

    public static String getDescripcion(){

        return descripcion;

    }

}