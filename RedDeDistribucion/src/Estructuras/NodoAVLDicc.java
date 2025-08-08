package Estructuras;

@SuppressWarnings("rawtypes")
public class NodoAVLDicc {


    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    /**
     * Constructor de NodoAVLDicc.
     * 
     * @param clave La clave del nodo.
     * @param dato El dato almacenado en el nodo.
     * @param izquierdo El hijo izquierdo del nodo.
     * @param derecho El hijo derecho del nodo.
     */
    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho) {
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }

    /* OBSERVADORAS */
    public Comparable getClave() {
        return clave;
    }

    public Object getDato() {
        return dato;
    }

    public int getAltura() {
        return altura;
    }

    public NodoAVLDicc getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return this.derecho;
    }

    /* MODIFICADORAS */
    public void setClave(Comparable elem) {
        this.clave = elem;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVLDicc derecho) {
        this.derecho = derecho;
    }

    /* PROPIAS DEL TIPO */

    /**
     * Recalcula la altura del nodo.
     */
    public void recalcularAltura() {
        int altIzq = -1, altDer = -1;
        if (this.izquierdo != null) {
            altIzq = this.izquierdo.getAltura();
        }
        if (this.derecho != null) {
            altDer = this.derecho.getAltura();
        }
        this.altura = 1 + Math.max(altIzq, altDer);
    }
}
