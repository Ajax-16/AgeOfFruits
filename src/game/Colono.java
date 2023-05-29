package game;

public class Colono {
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected String estado;
    protected String enfermedad;
    protected int energia;
    protected int cordura;
    protected int hambre;
    protected String sexo;
    protected boolean ocupado = false;
    protected boolean empiezaTurno = false;
    protected int turnoIdividual;


    public Colono(String nombre, String apellido, int edad) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = "De pie";
        this.enfermedad = "Ninguna";
        this.energia = 100;
        this.cordura = 100;
        this.hambre = 0;
        this.ocupado = false;
        this.turnoIdividual = 0;

    }

    public Colono() {

        this.nombre = "";
        this.apellido = "";
        this.edad = 0;
        this.estado = "De pie";
        this.enfermedad = "Ninguna";
        this.energia = 100;
        this.cordura = 100;
        this.hambre = 0;
        this.ocupado = false;
        this.turnoIdividual = 0;

    }

    public void setNombre(String nombre){

        this.nombre = nombre;

    }

    public String getNombre(){

        return nombre;

    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getCordura() {
        return cordura;
    }

    public void setCordura(int cordura) {
        this.cordura = cordura;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void incrementaTurno(){

        turnoIdividual++;

    }

    public int getTurnoIdividual() {
        return turnoIdividual;
    }

    @Override
    public String toString() {

        return "\n--------\nColono nº " + AdministrarColonos.getID(this) +"\nNombre: " + this.nombre + "\nApellido: " + this.apellido
                + "\nEdad: " + this.edad + "\nEstado: " + this.estado + "\nEnfermedad: " + this.enfermedad +
                "\nEnergía: " + this.energia + "\nCordura: " + this.cordura + "\nHambre: " +this.hambre
                + "\n--------\n";
    }

    public String mostrarIDYNombre() {

        return "\n--------\nColono nº " + AdministrarColonos.getID(this) +"\nNombre: " + this.nombre + "\nApellido: " + this.apellido
               + "\n--------\n";
    }

    public String mostrarIDYNombreDisponibles() {

        return "\n--------------------------------------------------------------------------------------------------------------\n" +
                "Colono nº " + AdministrarColonos.getID(this) +" | Nombre: " + this.nombre + " | Apellido: " + this.apellido + " | Energía: " + this.energia + " | Cordura: " + this.cordura + " | Hambre: " +this.hambre
                + "\n-------------------------------------------------------------------------------------------------------------\n";
    }

    public String mostrarIDYNombreStats() {

        return "\n-------------------------------------------------------------------------------------------------------------\n" +
                "Colono nº " + AdministrarColonos.getID(this) + " | Estado: " + this.estado + " | Nombre: " + this.nombre + " | Apellido: " + this.apellido + " | Energía: " + this.energia + " | Cordura: " + this.cordura + " | Hambre: " +this.hambre
                + "\n-------------------------------------------------------------------------------------------------------------\n";
    }

    public void gestionaEnergia(){

        if(this.energia >= 100) {

        this.energia = 100;

        }

    }

    public void gestionaHambre(){

        if(this.hambre <= 0) {

            this.hambre = 0;

        }

    }

    public void gestionaCordura(){

        if(this.cordura >= 100){

            this.cordura = 100;

        }

    }

}