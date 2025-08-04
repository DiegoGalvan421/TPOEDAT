package Estructuras;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

     public NodoAVLDicc(Comparable clave,Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho){
        this.clave=clave;
        this.dato=dato;
        this.izquierdo=izquierdo;
        this.derecho=derecho;
        this.altura=0;
    }

    public Comparable getClave(){
        return clave;
    }
        
    public void setClave(Comparable elem){
        this.clave=elem;
    }
    public Object getDato(){
        return dato;
    }
    public void setDato(Object dato){
        this.dato=dato;
    }
    public int getAltura(){
        return altura;
    }
    
    public void recalcularAltura(){
        int altIzq=-1, altDer=-1;
        if(this.izquierdo !=null){
            altIzq=this.izquierdo.getAltura();
        }
        if(this.derecho!=null){
            altDer=this.derecho.getAltura();
        }
        this.altura=1+Math.max(altIzq, altDer);
    }

    public NodoAVLDicc getIzquierdo(){
        return this.izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo){
        this.izquierdo=izquierdo;
    }

    public NodoAVLDicc getDerecho(){
        return this.derecho;
    }

    public void setDerecho(NodoAVLDicc derecho){
        this.derecho=derecho;
    }
}
