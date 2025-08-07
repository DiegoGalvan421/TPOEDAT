package EstructurasAuxiliares;

public class Lista {
    private Nodo cabecera;
    private int longitud;

    public Lista() {
        cabecera = null;
        longitud = 0;
    }

    public boolean insertar(Object unElemento, int unaPosicion) {
        boolean inserto = false;
        if ((1 <= unaPosicion) && (unaPosicion <= longitud + 1)) {
            Nodo nuevoNodo = new Nodo(unElemento, null);
            if (unaPosicion == 1) {
                nuevoNodo.setEnlace(cabecera);
                this.cabecera = nuevoNodo;
            } else {
                Nodo aux = cabecera;
                int contador = 1;
                while (contador < unaPosicion - 1) {
                    aux = aux.getEnlace();
                    contador++;
                }
                nuevoNodo.setEnlace(aux.getEnlace());
                aux.setEnlace(nuevoNodo);
            }
            longitud++;
            inserto = true;
        }
        return inserto;
    }

    public boolean eliminar(int unaPosicion) {
        boolean elimino = false;
        if ((1 <= unaPosicion) && (unaPosicion <= longitud)) {
            if (unaPosicion == 1) {
                cabecera = cabecera.getEnlace();
            } else {
                Nodo aux = cabecera;
                int contador = 1;
                while (contador < unaPosicion - 1) {
                    aux = aux.getEnlace();
                    contador++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace()); // ?
            }
            elimino = true;
            longitud--;
        }

        return elimino;
    }

    /**
     * Retorna el elemento de una posicion.
     * 
     * @param pos
     * @return Object
     */
    public Object recuperar(int pos) {
        Object elementoRecuperado = null; // Si la posicion no es valida, retorna elemento null.

        if (pos > 0 && pos < this.longitud() + 1) { // Verifica que sea una posicion valida.
            Nodo nodoAux = this.cabecera;
            int i = 1;
            while (i < pos) {
                nodoAux = nodoAux.getEnlace();
                i++;
            }
            elementoRecuperado = nodoAux.getElem();
        }
        return elementoRecuperado;
    }

    public int localizar(Object unElemento) {
        int posicion = -1;
        int contador = 1;
        Nodo aux = cabecera;
        if (cabecera != null) {
            while (!(aux.getElem().equals(unElemento)) && aux.getEnlace() != null) {
                aux = aux.getEnlace();
                contador++;
            }
            if (aux.getElem().equals(unElemento)) { // ?
                posicion = contador;
            }
        }
        return posicion;
    }

    public int longitud() {
        return longitud;
    }

    public boolean esVacia() {
        boolean siEs = false;
        if (cabecera == null) {
            siEs = true;
        }
        return siEs;
    }

    public void vaciar() {
        cabecera = null;
        longitud = 0;
    }

    /*
     * public Lista clon(){//repetitiva, no recursiva Lista clonLista = new Lista();
     * clonLista.longitud = this.longitud; if (this.cabecera != null){ Nodo auxClon = new
     * Nodo(cabecera.getElem(), null); clonLista.cabecera = auxClon; Nodo auxOriginal =
     * cabecera.getEnlace(); while (auxOriginal.getEnlace()!= null){ Nodo aux = new Nodo
     * (auxOriginal.getEnlace(), null); auxClon.setEnlace(aux); auxClon = aux;
     * auxOriginal.setEnlace(auxOriginal.getEnlace()); } } return clonLista; }
     */
    public Lista clone() {
        Lista clonLista = new Lista();
        if (!this.esVacia()) {
            clonLista.cabecera = clonRec(this.cabecera);
            clonLista.longitud = this.longitud;
        }
        return clonLista;

    }

    private Nodo clonRec(Nodo aux1Nodo) {
        Nodo nuevoNodo = new Nodo(aux1Nodo.getElem());
        if (aux1Nodo.getEnlace() != null) {
            aux1Nodo = aux1Nodo.getEnlace();
            nuevoNodo.setEnlace(clonRec(aux1Nodo));
        }
        return nuevoNodo;
    }

    public String toString() {
        String cadena = "";
        if (this.cabecera == null) {
            cadena = "[]";
        } else {
            // se ubica para recorrer la lista
            Nodo aux = this.cabecera;
            cadena += "[";
            while (aux != null) {
                // agrega el texto del elem y avanza
                cadena = cadena + ((aux.getElem()).toString());
                aux = aux.getEnlace();
                if (aux != null) {
                    cadena += ",";
                }
            }
            cadena += "]";
        }
        return cadena;
    }

    public void invertir() {
        if (!(cabecera == null || cabecera.getEnlace() == null)) {
            Nodo anterior = null;
            Nodo actual = cabecera;
            Nodo siguiente;
            // avanza, cambiando de 1 a 1 los enlaces de los nodos para invertirlos.
            while (actual != null) {
                siguiente = actual.getEnlace(); // Guardamos referencia al siguiente nodo
                actual.setEnlace(anterior); // Invertimos el enlace
                anterior = actual; // Movemos el puntero "anterior" al actual
                actual = siguiente; // Avanzamos al siguiente nodo
            }
            cabecera = anterior; // La nueva cabecera es el último nodo visitado
        }
    }

    public void eliminarApariciones(Object elem) {
        if (cabecera != null) {
            while (cabecera.getElem().equals(elem)) {
                cabecera = cabecera.getEnlace();

            }
            Nodo aux = cabecera;
            // lo que hace es, si el elemento que sigue es un elemento que se debe eliminar,
            // conecta directamente el actual con el que le sigue
            while (aux.getEnlace() != null) {

                if (aux.getEnlace().getElem().equals(elem)) {
                    aux.setEnlace(aux.getEnlace().getEnlace());
                } else {
                    aux = aux.getEnlace();
                }

            }

        }

    }

    public Lista intercalar(Lista l1, Lista l2) {
        Lista resultado = new Lista();
        int i = 1, j = 1, pos = 1;

        while (i <= l1.longitud() || j <= l2.longitud()) {
            if (i <= l1.longitud()) {
                resultado.insertar(l1.recuperar(i), pos);
                pos++;
                i++;
            }
            if (j <= l2.longitud()) {
                resultado.insertar(l2.recuperar(j), pos);
                pos++;
                j++;
            }
        }

        return resultado;
    }

    public int contarIterativo(Object elem) {
        int contador = 0;
        int i = 1;

        while (i <= this.longitud()) {
            if (this.recuperar(i).equals(elem)) {
                contador++;
            }
            i++;
        }

        return contador;
    }

    public int contarRecursivo(Object elem) {
        int cantidad = contarRecursivoAux(elem, 1);
        return cantidad;
    }

    private int contarRecursivoAux(Object elem, int pos) {
        int cantidad = 0;

        if (pos <= this.longitud()) {
            if (this.recuperar(pos).equals(elem)) {
                cantidad = 1;
            }
            cantidad += contarRecursivoAux(elem, pos + 1);
        }

        return cantidad;
    }

    public boolean esCapicua() {
        boolean resultado = esCapicuaAux(1, this.longitud());
        return resultado;
    }

    private boolean esCapicuaAux(int ini, int fin) {
        boolean resultado;

        if (ini >= fin) {
            resultado = true;
        } else if (!this.recuperar(ini).equals(this.recuperar(fin))) {
            resultado = false;
        } else {
            resultado = esCapicuaAux(ini + 1, fin - 1);
        }

        return resultado;
    }

    public Lista obtenerMultiplos(int num) {
        Lista lis = new Lista();
        int pos = 1;
        Nodo aux1 = this.cabecera;
        Nodo aux3 = new Nodo();
        while (aux1 != null) {
            if (pos % num == 0) {
                Nodo aux2 = new Nodo(aux1.getElem());
                if (lis.cabecera == null) {
                    lis.cabecera = aux2;
                    aux3 = aux2;
                } else {
                    aux3.setEnlace(aux2);
                    aux3 = aux2;
                }
                lis.longitud++;
            }

            pos++;
            aux1 = aux1.getEnlace();

        }
        return lis;

    }
}
