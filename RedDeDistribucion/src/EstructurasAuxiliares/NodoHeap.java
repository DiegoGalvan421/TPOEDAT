package EstructurasAuxiliares;

@SuppressWarnings("rawtypes")
public class NodoHeap {

    Comparable clave;
    Object dato;

    NodoHeap(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
    }

    public String toString() {// para no hacer un getDato().toString()
        return dato.toString();
    }

    public Comparable getClave() {
        return clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

}
