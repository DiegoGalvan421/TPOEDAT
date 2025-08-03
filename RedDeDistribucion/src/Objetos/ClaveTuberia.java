package Objetos;

public class ClaveTuberia {

    // Atributos.
    private String origen;
    private String destino;

    /**
     * Constructor.
     * <p>
     * Crea una clave de tubería con el origen y destino especificados.
     * 
     * @param origen
     * @param destino
     */
    public ClaveTuberia(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
    }

    /* OBSERVADORAS */
    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    /* MODIFICADORAS */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Representa la clave de la tubería como una cadena.
     * 
     * @return String
     */
    public String toString() {
        return origen + "-" + destino;
    }

    /**
     * Compara dos claves de tubería para verificar si son iguales.
     * <p>
     * Dos claves son iguales si tienen el mismo origen y destino.
     * 
     * @param obj Objeto a comparar.
     * @return boolean
     */
    public boolean equals(Object obj) {
        return obj != null && this.origen.equals(((ClaveTuberia) obj).origen)
                && this.destino.equals(((ClaveTuberia) obj).destino);
    }

    @Override
    /**
     * Calcula el hashCode de la clave de la tubería.
     * <p>
     * Suma los valores ASCII de los caracteres de origen y destino.
     * 
     * @return hashCode calculado.
     */
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
