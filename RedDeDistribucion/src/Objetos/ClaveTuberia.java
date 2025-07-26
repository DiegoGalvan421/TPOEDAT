package Objetos;

public class ClaveTuberia {

    private String origen;
    private String destino;

    public ClaveTuberia(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String toString() {
        return origen + "-" + destino;
    }

    public boolean equals(Object obj) {
        return obj != null
                && this.origen.equals(((ClaveTuberia) obj).origen)
                && this.destino.equals(((ClaveTuberia) obj).destino);
    }

    @Override
    public int hashCode() {
        int i, suma = 0;
        for (i = 0; i < origen.length(); i++) {
            suma += (int) origen.charAt(i);
        }
        for (i = 0; i < destino.length(); i++) {
            suma += (int) destino.charAt(i);
        }
        return suma;
    }

}
