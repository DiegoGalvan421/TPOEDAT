package EstructurasAuxiliares;
public class Nodo {

    private Object elem;
    private Nodo enlace;

    //constructores
    public Nodo() {
        this.elem = null;
        this.enlace = null;
    }

    public Nodo(Object elem) {
        this.elem = elem;
        this.enlace = null;
    }

    public Nodo(Object elem, Nodo enlace) {
        this.elem = elem;
        this.enlace = enlace;
    }

    //modificadores
    public void setElem(Object elem) {
        this.elem = elem;

    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

    //obsevadores
    public Object getElem() {
        return elem;
    }

    public Nodo getEnlace() {
        return enlace;
    }

}
