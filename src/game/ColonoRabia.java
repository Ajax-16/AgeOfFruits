package game;

public class ColonoRabia extends Colono{

    public ColonoRabia(Colono c){

        super(c.nombre, c.apellido, c.edad);

        enfermedad = "Rabia";

    }

}
