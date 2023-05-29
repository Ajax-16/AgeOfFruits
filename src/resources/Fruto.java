package resources;

public class Fruto {

    private static int cantidad = 0;
    private static final String descripcion = "Alimento nutritivo que sirve de fuente de energía a los colonos." +
            " Si no es almacenada correctamente puede llegar a deteriorarse rápidamente";;

    public static int getCantidadFruto(){

        return cantidad;

    }

    public static void setCantidadFruto(int c){

        cantidad = c;

    }

    public static String getDescripcion(){

        return descripcion;

    }

    public static void aumentaFrutos(int aumento){

        setCantidadFruto(cantidad+=aumento);

    }

    public static boolean hayFrutosSuficientes(int cantidadARestar){

        return (Fruto.getCantidadFruto() - cantidadARestar) >= 0;

    }

}