package Estructuras;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    NodoVert(Object elem,NodoVert sigVertice, NodoAdy primerAdy) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = primerAdy;
    }
    public Object getElem() {
        return elem;
    }
    public void setElem(Object elem) {
        this.elem = elem;
    }
    public NodoVert getSigVertice() {
        return sigVertice;
    }
    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }   
    public boolean equals(NodoVert nuevo){
        return (this.elem.equals(nuevo.elem));
    }
}
