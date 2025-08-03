package EstructurasAuxiliares;

public class Nodo {

    // Atributos
    private Object elem;
    private Nodo enlace;

    /**
     * Constructor de NODO.
     * <p>
     * Crea un nodo con elemento nulo y enlace nulo.
     */
    public Nodo() {
        this.elem = null;
        this.enlace = null;
    }

    /**
     * Constructor de NODO.
     * <p>
     * Crea un nodo con el elemento elem y enlace nulo.
     * 
     * @param elem
     */
    public Nodo(Object elem) {
        this.elem = elem;
        this.enlace = null;
    }

    /**
     * Constructor de NODO.
     * 
     * @param elem
     * @param enlace
     */
    public Nodo(Object elem, Nodo enlace) {
        this.elem = elem;
        this.enlace = enlace;
    }

    /* OBSERVADORAS */
    public Object getElem() {
        return elem;
    }

    public Nodo getEnlace() {
        return enlace;
    }

    /* MODIFICADORAS */
    public void setElem(Object elem) {
        this.elem = elem;

    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

}
