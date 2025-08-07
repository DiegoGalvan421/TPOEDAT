package Estructuras;

import EstructurasAuxiliares.*;

public class TablaAVL {

    private NodoAVLDicc raiz;

    public TablaAVL() {
        this.raiz = null;
    }

    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    // estructura auxiliar para ver si una clave pertenece a la tabla
    private boolean existeClaveAux(NodoAVLDicc n, Comparable clave) {
        boolean exito = false;
        if (n != null) {
            if ((clave.compareTo(n.getClave()) == 0)) {
                // clave encontrado
                exito = true;
            } else if (clave.compareTo(n.getClave()) < 0) {
                // clave es menor que n.getClve
                // busca a la izquierda de n
                exito = existeClaveAux(n.getIzquierdo(), clave);
            } else {
                // clave es mayo que n.getClave
                // busca a la derecha de n
                exito = existeClaveAux(n.getDerecho(), clave);
            }
        }
        return exito;
    }

    public Object obtenerInformacion(Comparable clave) {
        return obtenerInformacionAux(this.raiz, clave);
    }

    private Object obtenerInformacionAux(NodoAVLDicc n, Comparable clave) {
        Object dato = null;
        if (n != null) {
            if (clave.compareTo(n.getClave()) == 0) {
                // clave encontrado
                dato = n.getDato();
            } else if (clave.compareTo(n.getClave()) < 0) {
                // clave es menor que n.getClave
                // busca a la izquierda de n
                dato = obtenerInformacionAux(n.getIzquierdo(), clave);
            } else {
                // clave es mayor que n.getClave
                // busca a la derecha de n
                dato = obtenerInformacionAux(n.getDerecho(), clave);
            }
        }
        return dato;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = new boolean[] {false};
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc r, Comparable clave, Object dato, boolean[] exito) {
        // inserta solo si no esta en la tabla
        if (r == null) {
            r = new NodoAVLDicc(clave, dato, null, null);
            exito[0] = true;
            // si llego al final y no se encontro, se inserta
        } else if (clave.compareTo(r.getClave()) < 0) {
            // se mueve a izquierda
            r.setIzquierdo(insertarAux(r.getIzquierdo(), clave, dato, exito));
        } else if (clave.compareTo(r.getClave()) > 0) {
            // se mueve a derecha
            r.setDerecho(insertarAux(r.getDerecho(), clave, dato, exito));
        }
        // balancea y recalcula altura despues de que finaliza cada llamado recursivo
        if (exito[0]) {
            r.recalcularAltura();
            r = balancear(r);
        }
        return r;

    }

    public boolean eliminar(Comparable elem) {
        boolean[] exito = new boolean[] {false};
        if (this.raiz != null) {
            this.raiz = eliminarAux(this.raiz, elem, exito);
        }
        return exito[0];
    }

    // Elimina recursivamente y balancea el árbol
    private NodoAVLDicc eliminarAux(NodoAVLDicc nodo, Comparable elem, boolean[] exito) {
        if (nodo != null) {
            if (elem.compareTo(nodo.getClave()) < 0) {
                nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), elem, exito));
            } else if (elem.compareTo(nodo.getClave()) > 0) {
                nodo.setDerecho(eliminarAux(nodo.getDerecho(), elem, exito));
            } else {
                exito[0] = true;
                // Nodo encontrado
                if (nodo.getDerecho() == null && nodo.getIzquierdo() == null) {
                    nodo = null; // Caso hoja
                } else if (nodo.getIzquierdo() == null) {
                    nodo = nodo.getDerecho(); // Solo hijo derecho
                } else if (nodo.getDerecho() == null) {
                    nodo = nodo.getIzquierdo(); // Solo hijo izquierdo
                } else {
                    // Nodo con dos hijos, busca el sucesor y lo elimina
                    NodoAVLDicc sucesor = obtenerMenor(nodo.getDerecho());
                    nodo.setClave(sucesor.getClave());
                    nodo.setDato(sucesor.getDato());
                    nodo.setDerecho(eliminarMenorDirecto(nodo.getDerecho()));

                }
            }
            // Recalcula altura y balancea si el nodo no es null
            if (nodo != null) {
                nodo.recalcularAltura();
                nodo = balancear(nodo);
            }
        }
        return nodo;
    }

    private NodoAVLDicc obtenerMenor(NodoAVLDicc nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

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

    public Lista listarClaves() {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarClaveAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarClaveAux(NodoAVLDicc n, Lista lis) {
        // supongo que ordenada de mayor a menor
        if (n != null) {
            if (n.getDerecho() != null) {
                listarClaveAux(n.getDerecho(), lis);
            }
            lis.insertar(n.getClave(), lis.longitud() + 1);
            if (n.getIzquierdo() != null) {
                listarClaveAux(n.getIzquierdo(), lis);
            }
        }
    }

    public Lista listarDatos() {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarDatosAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista lis) {
        // supongo que ordenada de menor a mayor
        if (n != null) {
            if (n.getDerecho() != null) {
                listarDatosAux(n.getDerecho(), lis);
            }
            lis.insertar(n.getDato(), lis.longitud() + 1);
            if (n.getIzquierdo() != null) {
                listarDatosAux(n.getIzquierdo(), lis);
            }
        }
    }

    public TablaAVL clone() {
        TablaAVL arbClon = new TablaAVL();
        if (this.raiz != null) {
            arbClon.raiz = this.clonarRec(this.raiz);
        }

        return arbClon;
    }

    private NodoAVLDicc clonarRec(NodoAVLDicc n) {
        NodoAVLDicc nuevo = null;
        if (n != null) {
            nuevo = new NodoAVLDicc(n.getClave(), n.getDato(), null, null);
            if (n.getIzquierdo() != null) {
                nuevo.setIzquierdo(this.clonarRec(n.getIzquierdo()));
            }

            if (n.getDerecho() != null) {
                nuevo.setDerecho(this.clonarRec(n.getDerecho()));
            }
        }

        return nuevo;
    }

    public String toString() {
        String res;
        if (this.raiz == null) {
            res = "Tabla vacia";
        } else {
            res = this.toStringRec(this.raiz);
        }

        return res;
    }

    private String toStringRec(NodoAVLDicc n) {
        String s = "";
        if (n != null) {
            s += "Clave: " + n.getClave().toString();
            s += ", Altura: " + n.getAltura();
            s += "\n HI: ";
            if (n.getIzquierdo() != null) {
                s += n.getIzquierdo().getClave().toString();
            } else {
                s += "null";
            }
            s += "\n HD: ";
            if (n.getDerecho() != null) {
                s += n.getDerecho().getClave().toString();
            } else {
                s += "null";
            }
            s += "\n";
            s += toStringRec(n.getIzquierdo());
            s += toStringRec(n.getDerecho());
        }
        return s;
    }

    public boolean esVacia() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarRangoAux(this.raiz, min, max, lis);
        }
        return lis;
    }

    public void listarRangoAux(NodoAVLDicc n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {
            if (n.getClave().compareTo(min) > 0) {
                // recorro el sub arbol izquierdo
                listarRangoAux(n.getIzquierdo(), min, max, lis);
            }
            if (n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) <= 0) {
                lis.insertar(n.getDato(), lis.longitud() + 1);
            }
            // recorro el sub arbol derecho
            if (n.getClave().compareTo(max) < 0) {
                listarRangoAux(n.getDerecho(), min, max, lis);
            }
        }
    }

    // estructuras auxiliares para balancear el avl
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
