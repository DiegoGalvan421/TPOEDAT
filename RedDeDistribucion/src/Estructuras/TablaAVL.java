package Estructuras;

import EstructurasAuxiliares.*;

@SuppressWarnings("rawtypes")
public class TablaAVL {

    /* Atributos */
    private NodoAVLDicc raiz;

    /**
     * Constructor de la tabla AVL.
     */
    public TablaAVL() {
        this.raiz = null;
    }

    /**
     * Verifica si una clave existe en la tabla.
     * 
     * @param clave
     * @return true si la clave existe, false en caso contrario.
     */
    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    /**
     * Estructura auxiliar para ver si una clave pertenece a la tabla
     * 
     * @param nodoActual
     * @param clave
     * @return true si la clave existe, false si no existe.
     */
    @SuppressWarnings("unchecked")
    private boolean existeClaveAux(NodoAVLDicc nodoActual, Comparable clave) {
        boolean exito = false;
        if (nodoActual != null) {
            if ((clave.compareTo(nodoActual.getClave()) == 0)) {
                // Clave encontrada.
                exito = true;
            } else if (clave.compareTo(nodoActual.getClave()) < 0) {
                // Clave es menor que nodoActual.getClave().
                // Busca a la izquierda de nodoActual.
                exito = existeClaveAux(nodoActual.getIzquierdo(), clave);
            } else {
                // Clave es mayor que nodoActual.getClave().
                // Busca a la derecha de nodoActual.
                exito = existeClaveAux(nodoActual.getDerecho(), clave);
            }
        }
        return exito;
    }

    /**
     * Obtiene la información asociada a una clave en la tabla.
     * 
     * @param clave
     * @return La información asociada a la clave, o null si no existe.
     */
    public Object obtenerInformacion(Comparable clave) {
        return obtenerInformacionAux(this.raiz, clave);
    }

    /**
     * Metodo auxiliar que recorre el arbol en busca de la informacion asociada a una clave.
     * 
     * @param nodoActual
     * @param clave
     * @return Objeto contenedor de la informacion, si no existe retorna null.
     */
    @SuppressWarnings("unchecked")
    private Object obtenerInformacionAux(NodoAVLDicc nodoActual, Comparable clave) {
        Object dato = null;
        if (nodoActual != null) {
            if (clave.compareTo(nodoActual.getClave()) == 0) {
                // Clave encontrada.
                dato = nodoActual.getDato();
            } else if (clave.compareTo(nodoActual.getClave()) < 0) {
                // Clave es menor que nodoActual.getClave()
                // Busca a la izquierda de nodoActual.
                dato = obtenerInformacionAux(nodoActual.getIzquierdo(), clave);
            } else {
                // Clave es mayor que nodoActual.getClave()
                // Busca a la derecha de nodoActual.
                dato = obtenerInformacionAux(nodoActual.getDerecho(), clave);
            }
        }
        return dato;
    }

    /**
     * Inserta un nuevo nodo en la tabla AVL.
     * 
     * @param clave
     * @param dato
     * @return true si se inserta correctamente, false si ya existe.
     */
    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = new boolean[] {false};
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    /**
     * Metodo auxiliar que inserta un nuevo nodo en la tabla AVL.
     * 
     * @param nodoActual
     * @param clave
     * @param dato
     * @param exito
     * @return NodoAVLDicc
     */
    @SuppressWarnings("unchecked")
    private NodoAVLDicc insertarAux(NodoAVLDicc nodoActual, Comparable clave, Object dato,
            boolean[] exito) {
        // Inserta solo si no esta en la tabla.
        if (nodoActual == null) {
            nodoActual = new NodoAVLDicc(clave, dato, null, null);
            exito[0] = true;
            // Si llego al final y no se encontro, se inserta.
        } else if (clave.compareTo(nodoActual.getClave()) < 0) {
            // Se mueve a izquierda.
            nodoActual.setIzquierdo(insertarAux(nodoActual.getIzquierdo(), clave, dato, exito));
        } else if (clave.compareTo(nodoActual.getClave()) > 0) {
            // Se mueve a derecha.
            nodoActual.setDerecho(insertarAux(nodoActual.getDerecho(), clave, dato, exito));
        }
        // Balancea y recalcula altura despues de que finaliza cada llamado recursivo.
        if (exito[0]) {
            nodoActual.recalcularAltura();
            nodoActual = balancear(nodoActual);
        }
        return nodoActual;
    }

    /**
     * Elimina un elemento de la tabla AVL.
     * 
     * @param elem
     * @return true si se elimina correctamente, false si no existe.
     */
    public boolean eliminar(Comparable elem) {
        boolean[] exito = new boolean[] {false};
        if (this.raiz != null) {
            this.raiz = eliminarAux(this.raiz, elem, exito);
        }
        return exito[0];
    }

    /**
     * Metodo auxiliar que elimina un nodo de la tabla AVL.
     * <p>
     * Elimina recursivamente y balancea el árbol.
     * 
     * @param nodo
     * @param elem
     * @param exito
     * @return NodoAVLDicc
     */
    @SuppressWarnings("unchecked")
    private NodoAVLDicc eliminarAux(NodoAVLDicc nodo, Comparable elem, boolean[] exito) {
        if (nodo != null) {
            if (elem.compareTo(nodo.getClave()) < 0) {
                nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), elem, exito));
            } else if (elem.compareTo(nodo.getClave()) > 0) {
                nodo.setDerecho(eliminarAux(nodo.getDerecho(), elem, exito));
            } else {
                exito[0] = true;
                // Nodo encontrado.
                if (nodo.getDerecho() == null && nodo.getIzquierdo() == null) {
                    nodo = null; // Caso hoja.
                } else if (nodo.getIzquierdo() == null) {
                    nodo = nodo.getDerecho(); // Solo hijo derecho.
                } else if (nodo.getDerecho() == null) {
                    nodo = nodo.getIzquierdo(); // Solo hijo izquierdo.
                } else {
                    // Nodo con dos hijos, busca el sucesor y lo elimina.
                    NodoAVLDicc sucesor = obtenerMenor(nodo.getDerecho());
                    nodo.setClave(sucesor.getClave());
                    nodo.setDato(sucesor.getDato());
                    nodo.setDerecho(eliminarMenorDirecto(nodo.getDerecho()));

                }
            }
            // Recalcula altura y balancea si el nodo no es null.
            if (nodo != null) {
                nodo.recalcularAltura();
                nodo = balancear(nodo);
            }
        }
        return nodo;
    }

    /**
     * Obtiene el nodo con la clave menor en el subárbol.
     * 
     * @param nodo
     * @return Nodo con la menor clave.
     */
    private NodoAVLDicc obtenerMenor(NodoAVLDicc nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

    /**
     * Metodo auxiliar que elimina el nodo con la menor clave en el subárbol.
     * 
     * @param nodo
     * @return Nodo con la menor clave.
     */
    private NodoAVLDicc eliminarMenorDirecto(NodoAVLDicc nodo) {
        NodoAVLDicc resultado;
        if (nodo.getIzquierdo() == null) {
            resultado = nodo.getDerecho();
        } else {
            nodo.setIzquierdo(eliminarMenorDirecto(nodo.getIzquierdo()));
            nodo.recalcularAltura();
            resultado = balancear(nodo);
        }
        return resultado;
    }

    /**
     * Metodo que lista las claves de la tabla AVL.
     * 
     * @return Lista con las claves.
     */
    public Lista listarClaves() {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarClaveAux(this.raiz, lis);
        }
        return lis;
    }

    /**
     * Metodo auxiliar que lista las claves de un nodo y sus hijos.
     * 
     * @param nodoActual
     * @param listaClaves
     */
    private void listarClaveAux(NodoAVLDicc nodoActual, Lista listaClaves) {
        if (nodoActual != null) {
            if (nodoActual.getDerecho() != null) {
                listarClaveAux(nodoActual.getDerecho(), listaClaves);
            }
            listaClaves.insertar(nodoActual.getClave(), listaClaves.longitud() + 1);
            if (nodoActual.getIzquierdo() != null) {
                listarClaveAux(nodoActual.getIzquierdo(), listaClaves);
            }
        }
    }

    /**
     * Lista los datos guardados en la tabla AVL.
     * 
     * @return Lista con los datos.
     */
    public Lista listarDatos() {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarDatosAux(this.raiz, lis);
        }
        return lis;
    }

    /**
     * Metodo auxiliar que lista los datos de un nodo y sus hijos.
     * 
     * @param nodoActual
     * @param listaDatos
     */
    private void listarDatosAux(NodoAVLDicc nodoActual, Lista listaDatos) {
        if (nodoActual != null) {
            if (nodoActual.getDerecho() != null) {
                listarDatosAux(nodoActual.getDerecho(), listaDatos);
            }
            listaDatos.insertar(nodoActual.getDato(), listaDatos.longitud() + 1);
            if (nodoActual.getIzquierdo() != null) {
                listarDatosAux(nodoActual.getIzquierdo(), listaDatos);
            }
        }
    }

    /**
     * Clona la tabla AVL.
     * 
     * @return TablaAVL clonada.
     */
    public TablaAVL clone() {
        TablaAVL arbClon = new TablaAVL();
        if (this.raiz != null) {
            arbClon.raiz = this.clonarRec(this.raiz);
        }
        return arbClon;
    }

    /**
     * Metodo auxiliar que clona recursivamente los nodos de la tabla.
     * 
     * @param nodoActual
     * @return NodoAVLDicc
     */
    private NodoAVLDicc clonarRec(NodoAVLDicc nodoActual) {
        NodoAVLDicc nuevo = null;
        if (nodoActual != null) {
            nuevo = new NodoAVLDicc(nodoActual.getClave(), nodoActual.getDato(), null, null);
            if (nodoActual.getIzquierdo() != null) {
                nuevo.setIzquierdo(this.clonarRec(nodoActual.getIzquierdo()));
            }

            if (nodoActual.getDerecho() != null) {
                nuevo.setDerecho(this.clonarRec(nodoActual.getDerecho()));
            }
        }
        return nuevo;
    }

    /**
     * Metodo que devuelve una representacion en cadena de la tabla AVL.
     * 
     * @return String.
     */
    public String toString() {
        String cadena;
        if (this.raiz == null) {
            cadena = "Tabla vacia";
        } else {
            cadena = this.toStringRec(this.raiz);
        }
        return cadena;
    }

    /**
     * Metodo auxiliar que genera recursivamente la representacion en cadena de la tabla.
     * 
     * @param nodoActual
     * @return String.
     */
    private String toStringRec(NodoAVLDicc nodoActual) {
        String cadena = "";
        if (nodoActual != null) {
            cadena += "Clave: " + nodoActual.getClave().toString();
            cadena += ", Altura: " + nodoActual.getAltura();
            cadena += "\n HI: ";
            if (nodoActual.getIzquierdo() != null) {
                cadena += nodoActual.getIzquierdo().getClave().toString();
            } else {
                cadena += "null";
            }
            cadena += "\n HD: ";
            if (nodoActual.getDerecho() != null) {
                cadena += nodoActual.getDerecho().getClave().toString();
            } else {
                cadena += "null";
            }
            cadena += "\n";
            cadena += toStringRec(nodoActual.getIzquierdo());
            cadena += toStringRec(nodoActual.getDerecho());
        }
        return cadena;
    }

    /**
     * Verifica si la tabla AVL esta vacia.
     * 
     * @return true si la tabla esta vacia, false en caso contrario.
     */
    public boolean esVacia() {
        return this.raiz == null;
    }

    /**
     * Vacía la tabla AVL.
     */
    public void vaciar() {
        this.raiz = null;
    }

    /**
     * Lista los elementos en un rango dado.
     * 
     * @param min
     * @param max
     * @return Lista con los elementos en el rango [min, max].
     */
    public Lista listarRango(Comparable min, Comparable max) {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarRangoAux(this.raiz, min, max, lis);
        }
        return lis;
    }

    /**
     * Metodo auxiliar que recorre el arbol listando los elementos en el rango especificado.
     * 
     * @param nodoActual
     * @param min
     * @param max
     * @param lista
     */
    @SuppressWarnings("unchecked")
    public void listarRangoAux(NodoAVLDicc nodoActual, Comparable min, Comparable max,
            Lista lista) {
        if (nodoActual != null) {
            if (nodoActual.getClave().compareTo(min) > 0) {
                // recorro el sub arbol izquierdo
                listarRangoAux(nodoActual.getIzquierdo(), min, max, lista);
            }
            if (nodoActual.getClave().compareTo(min) >= 0
                    && nodoActual.getClave().compareTo(max) <= 0) {
                lista.insertar(nodoActual.getDato(), lista.longitud() + 1);
            }
            // recorro el sub arbol derecho
            if (nodoActual.getClave().compareTo(max) < 0) {
                listarRangoAux(nodoActual.getDerecho(), min, max, lista);
            }
        }
    }

    /* Estructuras auxiliares para balancear el avl */

    private NodoAVLDicc balancear(NodoAVLDicc nodo) {
        int balance = obtenerBalance(nodo);
        NodoAVLDicc aux = nodo;
        if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) >= 0) {
            aux = rotacionSimpleDerecha(nodo);
        } else if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) < 0) {
            aux = rotacionDobleDerecha(nodo);
        } else if (balance < -1 && obtenerBalance(nodo.getDerecho()) <= 0) {
            aux = rotacionSimpleIzquierda(nodo);
        } else if (balance < -1 && obtenerBalance(nodo.getDerecho()) > 0) {
            aux = rotacionDobleIzquierda(nodo);
        }
        return aux;
    }

    private int obtenerBalance(NodoAVLDicc nodo) {
        int altI = -1, altD = -1;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                altI = nodo.getIzquierdo().getAltura();
            }
            if (nodo.getDerecho() != null) {
                altD = nodo.getDerecho().getAltura();
            }
        }
        return altI - altD;
    }

    private NodoAVLDicc rotacionSimpleDerecha(NodoAVLDicc r) {
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    private NodoAVLDicc rotacionSimpleIzquierda(NodoAVLDicc r) {
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotacionDobleDerecha(NodoAVLDicc nodo) {
        nodo.setIzquierdo(rotacionSimpleIzquierda(nodo.getIzquierdo()));
        return rotacionSimpleDerecha(nodo);
    }

    private NodoAVLDicc rotacionDobleIzquierda(NodoAVLDicc nodo) {
        nodo.setDerecho(rotacionSimpleDerecha(nodo.getDerecho()));
        return rotacionSimpleIzquierda(nodo);
    }

}
