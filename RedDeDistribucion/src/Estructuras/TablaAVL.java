package Estructuras;

import EstructurasAuxiliares.*;
import Objetos.Ciudad;

public class TablaAVL {

    private NodoAVLDicc raiz;

    public TablaAVL() {
        this.raiz = null;
    }

    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    //estructura auxiliar para ver si una clave pertenece a la tabla
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
        boolean[] exito = new boolean[]{false};
        NodoAVLDicc nuevoNodo = new NodoAVLDicc(clave, dato, null, null);
        this.raiz = insertarAux(this.raiz, nuevoNodo, exito);
        return exito[0];
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc r, NodoAVLDicc nuevoNodo, boolean[] exito) {
        //inserta solo si no esta en la tabla
        if (r == null) {
            r = nuevoNodo;
            exito[0] = true;
            //si llego al final y no se encontro, se inserta
        } else if (nuevoNodo.getClave().compareTo(r.getClave()) < 0) {
            //se mueve a izquierda
            r.setIzquierdo(insertarAux(r.getIzquierdo(), nuevoNodo, exito));
        } else if (nuevoNodo.getClave().compareTo(r.getClave()) > 0) {
            //se mueve a derecha
            r.setDerecho(insertarAux(r.getDerecho(), nuevoNodo, exito));
        }
        //balancea y recalcula altura despues de que finaliza cada llamado recursivo
        r.recalcularAltura();
        return balancear(r);
    }

    public boolean eliminar(Comparable elem) {
        boolean[] exito = new boolean[]{false};
        if (this.raiz != null) {
            this.raiz = eliminarAux(this.raiz, elem, exito);
        }
        return exito[0];
    }

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
                    nodo = null;
                } else if (nodo.getIzquierdo() == null) {
                    nodo = nodo.getDerecho();
                } else if (nodo.getDerecho() == null) {
                    nodo = nodo.getIzquierdo();
                } else {
                    // Nodo con dos hijos que busca el sucesor
                    nodo = reemplazarMenorDerecho(nodo);

                }
            }
            // Verificar si el nodo no es null antes de recalcular altura y balancear
            if (nodo != null) {
                nodo.recalcularAltura();
                nodo = balancear(nodo);
            }
        }
        return nodo;
    }

    private NodoAVLDicc reemplazarMenorDerecho(NodoAVLDicc nodo) {
        NodoAVLDicc actual = nodo.getDerecho();

        if (actual.getIzquierdo() == null) {
            // Copia clave y dato del sucesor
            nodo.setClave(actual.getClave());
            nodo.setDato(actual.getDato());
            nodo.setDerecho(actual.getDerecho());
        } else {
            // Busca el menor del subárbol derecho
            NodoAVLDicc anterior = actual;
            while (actual.getIzquierdo() != null) {
                anterior = actual;
                actual = actual.getIzquierdo();
            }
            nodo.setClave(actual.getClave());
            nodo.setDato(actual.getDato());
            anterior.setIzquierdo(actual.getDerecho());
            // Recalcula altura y balancea desde anterior
            anterior.recalcularAltura();
            anterior = balancear(anterior);
        }
        // Recalcula altura y balancea el nodo raíz de este subárbol
        nodo.recalcularAltura();
        nodo = balancear(nodo);
        return nodo;
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
            res = "tabla vacia";
        } else {
            res = this.toStringRec(this.raiz);
        }

        return res;
    }

    private String toStringRec(NodoAVLDicc n) {
        String s = "";
        if (n != null) {
            s += "Clave:" + n.getClave().toString() + " \n"
                    + //
                    "Dato:" + n.getDato();
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

    //estructuras auxiliares para balancear el avl
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
        h.recalcularAltura();
        r.recalcularAltura();
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

//para el punto 7, listado de ciudades  ordenadas por consumo de agua anual
    public String consumoAnual(int anio) {
        TablaAVL nuevaTabla = new TablaAVL();
        Lista ciudades = this.listarDatos(); // Listo las ciudades originales para ir calculando el consumo anual
        for (int i = 1; i <= ciudades.longitud(); i++) {
            Ciudad ciudad = (Ciudad) ciudades.recuperar(i);
            double consumo = ciudad.consumoAnual(anio); //el año se controla en el main
            nuevaTabla.insertar(consumo, ciudad); // usa como clave el consumo y se ordena comparando este
        }
        String listado = nuevaTabla.toStringDeMayorAMenor();
        return listado;
    }

    private String toStringDeMayorAMenor() {
        String retorno = "[]"; //si no hay ciudades se imprime [ ] (?)
        if (raiz != null) {
            StringBuilder sb = new StringBuilder(); //aparentemente es mas eficiente que concatenar strings (?)
            toStringDeMayorAMenorAux(raiz, sb);
            if (sb.length() >= 4) { //si hay contenido
                sb.setLength(sb.length() - 4); // Elimina el último " -> " (los ultimos 4 caracteres)
            }
            retorno = sb.toString();
        }
        return retorno;
    }

    private void toStringDeMayorAMenorAux(NodoAVLDicc nodo, StringBuilder sb) { //inOrder inverso (derecha medio izquierda)
        if (nodo != null) {

            toStringDeMayorAMenorAux(nodo.getDerecho(), sb);// Primero valores mayores
            sb.append(nodo.getDato().toString()).append(" -> "); //no muestra el consumo anual, se podría agregar
            toStringDeMayorAMenorAux(nodo.getIzquierdo(), sb); // Ultimo los menores

        }
    }

}
