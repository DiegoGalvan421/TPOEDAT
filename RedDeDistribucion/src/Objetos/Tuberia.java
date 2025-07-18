package Objetos;

public class Tuberia {
    private String nomenclatura;
    private double caudalMin;
    private double caudalMax;
    private double diametro;
    private char estado; // "A(ctivo) R(eparación) D(iseño) I(nactivo)"

    /**
     * Constructor.
     * 
     * @param nom
     * @param min
     * @param max
     * @param unDiametro
     * @param unEstado
     */
    public Tuberia(String nom, double min, double max, double unDiametro, char unEstado) {
        nomenclatura = nom;
        caudalMin = min;
        caudalMax = max;
        diametro = unDiametro;
        estado = unEstado;
    }

    /* OBSERVADORAS */

    public String getNomenclatura() {
        return nomenclatura;
    }


    public double getCaudalMin() {
        return caudalMin;
    }

    public double getCaudalMax() {
        return caudalMax;
    }

    public double getDiametro() {
        return diametro;
    }

    public char getEstado() {
        return estado;
    }

    /* MODIFICADORAS */

    public void setEstado(char unEstado) {
        estado = unEstado;
    }

    @Override
    public String toString() {
        return "\nTuberia: " + nomenclatura + "\nCaudal Minimo: " + caudalMin + "\nCaudal Maximo: "
                + caudalMax + "\nDiametro: " + diametro + "\nEstado: " + estado + "\n";
    }
}
