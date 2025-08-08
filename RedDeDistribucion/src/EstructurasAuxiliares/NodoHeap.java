package EstructurasAuxiliares;

@SuppressWarnings("rawtypes")
public class NodoHeap {

    /* Atributos */
    Comparable clave;
    Object dato;

    /**
     * Constructor NodoHeap
     * 
     * @param clave
     * @param dato
     */
    NodoHeap(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
    }

    /* OBSERVADORAS */
    public Comparable getClave() {
        return clave;
    }

    public Object getDato() {
        return dato;
    }

    /* MODIFICADORAS */
    public void setDato(Object dato) {
        this.dato = dato;
    }

    /* PROPIAS DEL TIPO */
    public String toStringDato() {// para no hacer un getDato().toString()
        return dato.toString();
    }

    public String toStringClave() {// para no hacer un getClave().toString()
        return clave.toString();
    }

}
