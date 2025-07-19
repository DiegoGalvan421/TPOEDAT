/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstructurasAuxiliares;

/**
 *
 * @author Diego Galvan
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        frente = null;
        fin = null;
    }

    public boolean poner(Object elem) {
        boolean puso = true;
        Nodo nuevo = new Nodo(elem,null);
        if (this.esVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setEnlace(nuevo);
            fin = fin.getEnlace();
        }
        return puso;
    }

    public boolean sacar() {
        boolean exito = true;
        if (this.esVacia()) {
            exito = false;
        } else {
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object aux;
        if (this.esVacia()) {
            aux = null;
        } else {
            aux = this.frente.getElem();
        }
        return aux;
    }

    public boolean esVacia() {
        boolean esVac=false;;
        if((this.frente==null)&&(this.fin==null)){
            esVac=true;
        }
        return esVac;
    }
    public void vaciar(){
        frente=null;
        fin=null;
    }
    public Cola clone(){
        Cola clon = new Cola();
        if(!this.esVacia()){
            Nodo aux1=this.frente;
            clon.frente=new Nodo(aux1.getElem(),null);
            aux1=aux1.getEnlace();
            Nodo aux2 = clon.frente;
            while(aux1!=null){
                aux2.setEnlace(new Nodo(aux1.getElem(),null));
                aux2=aux2.getEnlace();
                aux1=aux1.getEnlace();
                if(aux1==null){
                    clon.fin=aux2;
                }
            }
        }
       
        return clon;
    }
    public String toString(){
         String cadena = "";
        if (this.esVacia()) {
            cadena = "[]";
        } else {
            //se ubica para recorrer la cola
            Nodo aux = this.frente;
            cadena += "[";
            while (aux != null) {
                //agrega el texto del elem y avanza
                cadena += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    cadena += ",";
                }
            }
            cadena += "]";
        }
        return cadena;
    }

}
