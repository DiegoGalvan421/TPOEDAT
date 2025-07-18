package Estructuras;

import EstructurasAuxiliares.*;

public class DigrafoEtiquetado {

    private NodoVert inicio;

    // esto es un digrafo etiquetado
    public DigrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object nuevoVertice) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(nuevoVertice)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public Object ubicarTuberia(String nomenclatura) {
        NodoVert aux = this.inicio;
        Object tuberia;
        while (aux != null && !aux.getElem().getNom().equals(nomenclatura)) {
            aux = aux.getSigVertice();
        }
        if(aux==null){
            tuberia=null;
        }else{
            tuberia=aux.getElem();
        }
        return tuberia;
    }
//verificar cuanndo hago los saltos al otro vertice una vez eliminado el arco
//modificar a la hora de ingresar y eliminar las nomenclaturas.
    public boolean eliminarVertice(Object vertice) {
        boolean exito = false;
        if (this.inicio != null) {
            if (this.inicio.getElem().equals(vertice)) {
                // si el vertice a eliminar es el primero
                this.inicio = this.inicio.getSigVertice();
                eliminarAdy(this.inicio, vertice);
                exito = true;
            } else {
                exito = eliminarVerticeAux(this.inicio, vertice);
                if (exito) {
                    // si se eliminó el vertice, eliminar sus adyacentes
                    eliminarAdy(this.inicio, vertice);
                }
            }
        }
        return exito;
    }

    private boolean eliminarVerticeAux(NodoVert actual, Object vertice) {
        boolean exito = false;
        if (actual != null && actual.getSigVertice() != null) {
            if (actual.getSigVertice().getElem().equals(vertice)) {
                actual.setSigVertice(actual.getSigVertice().getSigVertice());
                exito = true;
            } else {
                exito = eliminarVerticeAux(actual.getSigVertice(), vertice);
            }
        }
        return exito;
    }

    private void eliminarAdy(NodoVert actual, Object vertice) {
        if (actual != null) {
            if (actual.getPrimerAdy() != null && actual.getPrimerAdy().getVertice().getElem().equals(vertice)) {
                actual.setPrimerAdy(actual.getPrimerAdy().getSigAdyacente());
            } else {
                // si no es el primero, recorre la lista de adyacentes
                NodoAdy ady = actual.getPrimerAdy();
                NodoAdy anterior = null;
                // supongo que hay 1 solo arco por conexion
                boolean exito = false;
                while (ady != null && !exito) {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                    if (ady != null && ady.getVertice().getElem().equals(vertice)) {
                        anterior.setSigAdyacente(ady.getSigAdyacente());
                        exito = true;
                    }
                }
            }
            eliminarAdy(actual.getSigVertice(), vertice);
        }
    }

    public boolean existeVertice(Object vertice) {
        boolean exito = false;
        if (this.inicio != null) {
            NodoVert aux = this.ubicarVertice(vertice);
            if (aux != null) {
                exito = true;
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, Object etiqueta) {
        boolean exito = false;
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si ya existe el arco
            NodoAdy ady = vertOrigen.getPrimerAdy();
            boolean existe = false;
            while (ady != null && !existe) {
                if (ady.getVertice().getElem().equals(destino)) {
                    existe = true;
                }
                ady = ady.getSigAdyacente();
            }
            if (!existe) {
                NodoAdy nuevoAdy = new NodoAdy(vertDestino, null, etiqueta);
                if (vertOrigen.getPrimerAdy() == null) {
                    // si la lista está vacía, se inserta como primer adyacente
                    vertOrigen.setPrimerAdy(nuevoAdy);
                } else {
                    // insertar al final de la lista de adyacentes
                    NodoAdy actual = vertOrigen.getPrimerAdy();
                    while (actual.getSigAdyacente() != null) {
                        actual = actual.getSigAdyacente();
                    }
                    actual.setSigAdyacente(nuevoAdy);
                }
                exito = true;
            }
        }
        return exito;
    }

    // suponiendo que no es un multiDigrafo.
    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        // se puede usar el ubicar vertice asi? es como el encontrar nodo de otras
        // estructuras
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            exito = eliminarArcoAux(vertOrigen, destino);
        }
        return exito;
    }

    // es parecido al eliminar vertice ady, pero con algunos cambios
    private boolean eliminarArcoAux(NodoVert actual, Object destino) {
        boolean exito = false;
        if (actual.getPrimerAdy() != null) {
            if (actual.getPrimerAdy().getVertice().getElem().equals(destino)) {
                // si es el primero
                actual.setPrimerAdy(actual.getPrimerAdy().getSigAdyacente());
                exito = true;
            } else {
                // si no es el primero, recorre la lista de adyacentes
                NodoAdy ady = actual.getPrimerAdy();
                NodoAdy anterior = null;
                // supongo que hay 1 solo arco por conexion
                while (ady != null && !exito) {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                    if (ady != null && ady.getVertice().getElem().equals(destino)) {
                        anterior.setSigAdyacente(ady.getSigAdyacente());
                        exito = true;
                    }
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            NodoAdy ady = vertOrigen.getPrimerAdy();
            while (ady != null && !exito) {
                if (ady.getVertice().getElem().equals(destino)) {
                    exito = true;
                }
                ady = ady.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public void vaciar() {
        this.inicio = null;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // define un vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si no fue visitado, lo visita
                listarEnProfundidaAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    public void listarEnProfundidaAux(NodoVert n, Lista vis) {
        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            // recorre los adyacentes de n
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                // si el adyacente no fue visitado, lo visita
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidaAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si no fue visitado, lo visita
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert n, Lista vis) {
        lineales.dinamica.Cola cola = new lineales.dinamica.Cola();
        cola.poner(n);
        vis.insertar(n.getElem(), vis.longitud() + 1);

        while (!cola.esVacia()) {
            NodoVert actual = (NodoVert) cola.obtenerFrente();
            cola.sacar();

            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                if (vis.localizar(vecino.getElem()) < 0) {
                    vis.insertar(vecino.getElem(), vis.longitud() + 1);
                    cola.poner(vecino);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // en la teoria hacen distinto esta parte, pero es lo mismo que poner el modulo
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si existe un camino desde origen a destino
            exito = existeCaminoAux(vertOrigen, destino, new Lista());
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object destino, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: no hay camino
            if (n.getElem().equals(destino)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay un camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null && !exito) {
                    // si el adyacente no fue visitado, lo visita
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), destino, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si existe un camino desde origen a destino
            caminoMasCortoAux(vertOrigen, destino, new Lista(), camino);
        }
        return camino;
    }

    // se le debe agregar que tome en cuenta la ponderacion de los nodos visitados,
    // ya que la etiqueta tiene peso
    private void caminoMasCortoAux(NodoVert n, Object destino, Lista vis, Lista caminoActual) {
        if (n != null) {
            vis.insertar(n.getElem(), vis.longitud() + 1);
            if (n.getElem().equals(destino)) {
                if (caminoActual.esVacia() || vis.longitud() < caminoActual.longitud()) {
                    caminoActual.vaciar();
                    for (int i = 1; i <= vis.longitud(); i++) {
                        caminoActual.insertar(vis.recuperar(i), i);
                    }
                }
            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        caminoMasCortoAux(ady.getVertice(), destino, vis, caminoActual);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            vis.eliminar(vis.longitud()); // backtrack
        }
    }

    public Lista caminoMasChico(Object origen, Object destino) {//esta vez busca el camino de menor peso de etiquetas
    Lista camino = new Lista();
    NodoVert vertOrigen = this.ubicarVertice(origen);
    NodoVert vertDestino = this.ubicarVertice(destino);
    if (vertOrigen != null && vertDestino != null) {
        int[] pesoMin = {-1}; // suponiendo que la minima etiqueta es 0
        caminoMasChicoAux(vertOrigen, destino, new Lista(), camino, 0, pesoMin);
    }
    return camino;
    }

    private void caminoMasChicoAux(NodoVert n, Object destino, Lista vis, Lista caminoActual, int pesoActual, int[] pesoMin) {
    if (n != null) {

        vis.insertar(n.getElem(), vis.longitud() + 1);
        if (n.getElem().equals(destino)) {//si estamos en destino
            if (pesoMin[0] == -1 || pesoActual < pesoMin[0]) {// Si es el primer camino encontrado o el peso total es menor

                pesoMin[0] = pesoActual; 
                caminoActual.vaciar(); 
                for (int i = 1; i <= vis.longitud(); i++) {
                    caminoActual.insertar(vis.recuperar(i), i); // Guardamos la copia del camino actual como el menor
                }
            }

        } else {// Si no llegamos recorremos los adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                if (vis.localizar(ady.getVertice().getElem()) < 0) { //seguimos si el vértice adyacente no fue visitado 
                    int pesoEtiqueta = (int) ady.getEtiqueta();
                    caminoMasChicoAux(ady.getVertice(), destino, vis, caminoActual, pesoActual + pesoEtiqueta, pesoMin); //recursivo y acumulamos el peso total
                }
                // Avanzamos al siguiente adyacente
                ady = ady.getSigAdyacente();
            }
        }

        vis.eliminar(vis.longitud()); //backtrack
    }
}

//para el ejercicio de menor caudal pleno
public Lista caminoMenorCaudalPleno(Object origen, Object destino) {
    Lista camino = new Lista();
    NodoVert vertOrigen = this.ubicarVertice(origen);
    NodoVert vertDestino = this.ubicarVertice(destino);

    if (vertOrigen != null && vertDestino != null) {
        int[] caudalMin = { -1 };
        caminoMenorCaudalPlenoAux(vertOrigen, destino, new Lista(), camino, -1, caudalMin);
    }

    return camino;
}

private void caminoMenorCaudalPlenoAux(NodoVert n, Object destino, Lista vis, Lista caminoActual, int caudalActual, int[] caudalMin) {
    if (n != null) {

        vis.insertar(n.getElem(), vis.longitud() + 1);
        if (n.getElem().equals(destino)) {
            if (caudalMin[0] == -1 || caudalActual < caudalMin[0]) {//llegamos al destino y actualizamos si tenemos un caudal pleno menor (si quedó en -1 es porque el origen y destino  era el mismo)
                caudalMin[0] = caudalActual;

                caminoActual.vaciar();
                for (int i = 1; i <= vis.longitud(); i++) {
                    caminoActual.insertar(vis.recuperar(i), i);//copiamos el mejor camino
                }
            }

        } else {
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                if (vis.localizar(ady.getVertice().getElem()) < 0) {

                    int caudalTuberia = (int) ady.getEtiqueta();

                    // Si es el primer tramo, usamos el caudal de esta tuberia lLuego vamos comparando para mantener el mínimo caudal
                    int nuevoCaudal;
                    if (caudalActual == -1) {
                        nuevoCaudal = caudalTuberia; // Primer tramo del camino
                    } else {
                        nuevoCaudal = Math.min(caudalActual, caudalTuberia); // Tomo el menor entre lo que ya venía y el nuevo
                    }
                    caminoMenorCaudalPlenoAux(ady.getVertice(), destino, vis, caminoActual, nuevoCaudal, caudalMin);
                }
                ady = ady.getSigAdyacente();
            }
        vis.eliminar(vis.longitud());//backtraking
        }
    }
}

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si existe un camino desde origen a destino
            caminoMasLargoAux(vertOrigen, destino, new Lista(), camino);
        }
        return camino;
    }

    // se le debe agregar que tome en cuenta la ponderacion de los nodos visitados,
    // ya que la etiqueta tiene peso
    private void caminoMasLargoAux(NodoVert n, Object destino, Lista vis, Lista caminoActual) {
        if (n != null) {
            vis.insertar(n.getElem(), vis.longitud() + 1);
            if (n.getElem().equals(destino)) {
                if (caminoActual.esVacia() || vis.longitud() > caminoActual.longitud()) {
                    caminoActual.vaciar();
                    for (int i = 1; i <= vis.longitud(); i++) {
                        caminoActual.insertar(vis.recuperar(i), i);
                    }
                }
            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        caminoMasLargoAux(ady.getVertice(), destino, vis, caminoActual);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            vis.eliminar(vis.longitud()); // backtrack
        }
    }
    //chusmear el clonar, esta medio sus
    public DigrafoEtiquetado clonar() {
        DigrafoEtiquetado clon = new DigrafoEtiquetado();
        clon.inicio = clonarVertices(this.inicio); // Paso 1
        clonarAdyacencias(this.inicio, clon.inicio, clon.inicio); // Paso 2
        return clon;
    }

    private NodoVert clonarVertices(NodoVert original) {
        NodoVert copia = null;
        if (original != null) {
            copia = new NodoVert(original.getElem(), null, null);
            copia.setSigVertice(clonarVertices(original.getSigVertice()));
            // No copiamos primerAdy todavía
        }
        return copia;
    }

    private NodoVert ubicarVerticeEnCopia(NodoVert inicioCopia, Object elem) {
        NodoVert aux = inicioCopia;
        while (aux != null && !aux.getElem().equals(elem)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }


    private void clonarAdyacencias(NodoVert original, NodoVert copia, NodoVert copiaRaiz) {
        if (original != null && copia != null) {
            NodoAdy adyOriginal = original.getPrimerAdy();
            NodoAdy ultimoAdy = null;
            while (adyOriginal != null) {
                // Buscar vértice destino en la copia (por elemento)
                NodoVert destinoCopia = ubicarVerticeEnCopia(copiaRaiz, adyOriginal.getVertice().getElem());
                NodoAdy nuevoAdy = new NodoAdy(destinoCopia, null, adyOriginal.getEtiqueta());
                if (copia.getPrimerAdy() == null) {
                    copia.setPrimerAdy(nuevoAdy);
                } else {
                    ultimoAdy.setSigAdyacente(nuevoAdy);
                }
                ultimoAdy = nuevoAdy;
                adyOriginal = adyOriginal.getSigAdyacente();
            }
            clonarAdyacencias(original.getSigVertice(), copia.getSigVertice(), copiaRaiz);
        }
    }

    public String toString() {
        String resultado = "";
        NodoVert actual = this.inicio;
        while (actual != null) {
            resultado += "Vértice: " + actual.getElem() + "\n";
            NodoAdy ady = actual.getPrimerAdy();
            if (ady == null) {
                resultado += "  Sin adyacentes\n";
            } else {
                resultado += "  Arcos hacia:\n";
                while (ady != null) {
                    resultado += "    -> " + ady.getVertice().getElem();
                    if (ady.getEtiqueta() != null) {
                        resultado += " (Etiqueta: " + ady.getEtiqueta() + ")";
                    }
                    resultado += "\n";
                    ady = ady.getSigAdyacente();
                }
            }
            actual = actual.getSigVertice();
        }
        return resultado;
    }
}
