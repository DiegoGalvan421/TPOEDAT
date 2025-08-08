package Estructuras;

public class NodoVert {

    /* ATRIBUTOS */
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    /**
     * Constructor de NodoVert.
     * 
     * @param elem
     * @param sigVertice
     * @param primerAdy
     */
    NodoVert(Object elem, NodoVert sigVertice, NodoAdy primerAdy) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = primerAdy;
    }

    /* OBSERVADORAS */
    public Object getElem() {
        return elem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    /* MODIFICADORAS */
    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

    /* PROPIAS DEL TIPO */

    /**
     * Compara este nodo con otro nodo dado.
     * 
     * @param nuevo
     * @return true si el nodo es igual al nuevo nodo, false en caso contrario.
     */
    public boolean equals(NodoVert nuevo) {
        return (this.elem.equals(nuevo.elem));
    }
}
