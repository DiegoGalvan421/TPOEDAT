package Estructuras;

import EstructurasAuxiliares.*;

public class DigrafoEtiquetado {

    /* Atributos. */
    private NodoVert inicio;

    /**
     * Crea un grafo vacío.
     * <p>
     * El digrafo etiquetado guardara, solo como vertice las nomenclaturas de las ciudades y solo el
     * caudal de las tuberias.
     */
    public DigrafoEtiquetado() {
        this.inicio = null;
    }

    /**
     * Inserta un nuevo vértice en el grafo. Si el vértice ya existe, no lo inserta.
     * <p>
     * El vértice se inserta al inicio de la lista de vértices, por lo que el orden de inserción no
     * importa.
     * 
     * @param nuevoVertice
     * @return true si se insertó el vértice, false si ya existía.
     */
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        // Verifica que no exista ya el vertice.
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            // Si el vertice no existe, inserta al inicio (el orden de insercion da igual).
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    /**
     * Metodo PRIVADO para ubicar un vertice en el grafo.
     * 
     * @param nuevoVertice
     * @return el nodo del vertice si existe, null si no.
     */
    private NodoVert ubicarVertice(Object nuevoVertice) {
        NodoVert nodoActual = this.inicio;

        // Recorre los nodos del grafo hasta encontrar el vertice buscado.
        while (nodoActual != null && !nodoActual.getElem().equals(nuevoVertice)) {
            nodoActual = nodoActual.getSigVertice();
        }
        return nodoActual;
    }

    /**
     * Elimina un vértice del grafo.
     * 
     * @param vertice
     * @return true si se eliminó el vértice, false si no existía.
     */
    public boolean eliminarVertice(Object vertice) {
        boolean exito = false;
        if (this.inicio != null) {
            if (this.inicio.getElem().equals(vertice)) {
                // Si el vertice a eliminar es el primero
                this.inicio = this.inicio.getSigVertice();
                eliminarAdy(this.inicio, vertice);
                exito = true;
            } else {
                // elimina el vertice y devuelve T o F, si se logro o no
                /*
                 * tener en cuenta que por como funciona el garbage collector de java a la hora de
                 * eliminar el vertice, todos los arcos que salen de el se eliminan, ya que no los
                 * esta apuntando nadie.
                 */
                exito = eliminarVerticeAux(this.inicio, vertice);
                if (exito) {
                    // si se eliminó el vertice, elimina los adyacentes que lo apunten
                    eliminarAdy(this.inicio, vertice);
                }
            }
        }
        return exito;
    }

    /**
     * Metodo PRIVADO para eliminar un vertice de la lista de vertices.
     * 
     * @param actual
     * @param vertice
     * @return true si se eliminó el vertice, false si no se encontró.
     */
    private boolean eliminarVerticeAux(NodoVert actual, Object vertice) {
        boolean exito = false;
        if (actual != null && actual.getSigVertice() != null) {
            if (actual.getSigVertice().getElem().equals(vertice)) {
                // Si el que sigue es el buscado, enlaza el actual con el que esta despues del
                // siguiente
                actual.setSigVertice(actual.getSigVertice().getSigVertice());
                exito = true;
            } else {
                // Si no es el buscado avanza.
                exito = eliminarVerticeAux(actual.getSigVertice(), vertice);
            }
        }
        return exito;
    }

    /**
     * Metodo PRIVADO para eliminar los adyacentes de un vertice.
     * 
     * @param actual
     * @param vertice
     */
    private void eliminarAdy(NodoVert actual, Object vertice) {
        if (actual != null) {
            // Si el primer adyacente es el que se debe eliminar, lo elimina.
            if (actual.getPrimerAdy() != null
                    && actual.getPrimerAdy().getVertice().getElem().equals(vertice)) {
                actual.setPrimerAdy(actual.getPrimerAdy().getSigAdyacente());
            } else {
                // Si no es el primero, recorre la lista de adyacentes.
                NodoAdy ady = actual.getPrimerAdy();
                NodoAdy anterior = null;
                // Supongo que hay 1 solo arco por conexion.
                boolean exito = false;
                while (ady != null && !exito) {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                    // Recorre la lista de adyacentes hasta eliminar el correcto o llegar al final.
                    if (ady != null && ady.getVertice().getElem().equals(vertice)) {
                        anterior.setSigAdyacente(ady.getSigAdyacente());
                        exito = true;
                    }
                }
            }
            // Repite con todos los vertices.
            eliminarAdy(actual.getSigVertice(), vertice);
        }
    }

    /**
     * Verifica si un vértice existe en el grafo.
     * 
     * @param vertice
     * @return true si existe, false si no.
     */
    public boolean existeVertice(Object vertice) {
        boolean exito = false;
        if (this.inicio != null) { // Si el grafo no está vacío.
            NodoVert aux = this.ubicarVertice(vertice);
            if (aux != null) {
                exito = true;
            }
        }
        return exito;
    }

    /**
     * Inserta un arco en el grafo.
     * 
     * @param origen
     * @param destino
     * @param etiqueta
     * @return true si se insertó el arco, false si ya existía.
     */
    public boolean insertarArco(Object origen, Object destino, Object etiqueta) {
        boolean exito = false;
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si ya existe el arco.
            NodoAdy ady = vertOrigen.getPrimerAdy();
            boolean existe = false;
            while (ady != null && !existe) {
                if (ady.getVertice().getElem().equals(destino)) {
                    existe = true;
                }
                ady = ady.getSigAdyacente();
            }
            // Si no existe el arco lo inserta
            if (!existe) {
                NodoAdy nuevoAdy = new NodoAdy(vertDestino, null, etiqueta);
                if (vertOrigen.getPrimerAdy() == null) {
                    // Si la lista está vacía, se inserta como primer adyacente.
                    vertOrigen.setPrimerAdy(nuevoAdy);
                } else {
                    // Insertar al final de la lista de adyacentes.
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

    /**
     * Elimina un arco del grafo.
     * <p>
     * NOTA: Se asume que el grafo no es un multigrafo.
     * 
     * @param origen
     * @param destino
     * @return true si se eliminó el arco, false si no existía.
     */
    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        // verifica que existan los dos vertices antes de buscar el arco a eliminar.
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        // Si existen procede con la eliminacion.
        if (vertOrigen != null && vertDestino != null) {
            exito = eliminarArcoAux(vertOrigen, destino);
        }
        return exito;
    }

    /**
     * Metodo PRIVADO para eliminar un arco entre dos vertices.
     * 
     * @param actual
     * @param destino
     * @return true si se eliminó el arco, false si no existía.
     */
    private boolean eliminarArcoAux(NodoVert actual, Object destino) {
        boolean exito = false;
        if (actual.getPrimerAdy() != null) {
            if (actual.getPrimerAdy().getVertice().equals(destino)) {
                // Si es el primero.
                actual.setPrimerAdy(actual.getPrimerAdy().getSigAdyacente());
                exito = true;
            } else {
                // Si no es el primero, recorre la lista de adyacentes.
                NodoAdy ady = actual.getPrimerAdy();
                NodoAdy anterior = null;
                // Supongo que hay 1 solo arco por conexion.
                while (ady != null && !exito) {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                    // Usamos anterior y ady(como actual) para movernos en la lista.
                    if (ady != null && ady.getVertice().getElem().equals(destino)) {
                        anterior.setSigAdyacente(ady.getSigAdyacente());
                        exito = true;
                    }
                }
            }
        }
        return exito;
    }

    /**
     * Verifica si existe un arco entre dos vértices.
     * 
     * @param origen
     * @param destino
     * @return true si existe, false si no.
     */
    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        // Verifica que existan los vertices.
        if (vertOrigen != null && vertDestino != null) {
            NodoAdy ady = vertOrigen.getPrimerAdy();
            // Luego recorre la lista de adyacentes del origen.
            while (ady != null && !exito) {
                // Si lo encuentra corta, sino sigue hasta terminar.
                if (ady.getVertice().getElem().equals(destino)) {
                    exito = true;
                }
                ady = ady.getSigAdyacente();
            }
        }
        return exito;
    }

    /**
     * Verifica si el grafo está vacío.
     * 
     * @return true si está vacío, false si tiene vértices.
     */
    public boolean esVacio() {
        return this.inicio == null;
    }

    /**
     * Vacía el grafo, eliminando todos los vértices y arcos.
     */
    public void vaciar() {
        this.inicio = null;
    }

    /**
     * Lista todos los vértices del grafo en profundidad.
     * <p>
     * Visita cada rama hasta que termine y luego retrocede.
     * 
     * @return Lista con los elementos de los vértices visitados en orden de profundidad.
     */
    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // Define un vertice donde comenzar a recorrer.
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // Si no fue visitado, lo visita.
                listarEnProfundidaAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    /**
     * Método auxiliar recursivo para listar los vértices en profundidad.
     * <p>
     * Marca el vértice como visitado y recorre sus adyacentes, visitando aquellos que no hayan sido
     * visitados aún.
     * 
     * @param nodoActual
     * @param visitados
     */
    public void listarEnProfundidaAux(NodoVert nodoActual, Lista visitados) {
        if (nodoActual != null) {
            // Marca al vertice nodoActual como visitado.
            visitados.insertar(nodoActual.getElem(), visitados.longitud() + 1);
            // Recorre los adyacentes de nodoActual.
            NodoAdy ady = nodoActual.getPrimerAdy();
            while (ady != null) {
                // Si el adyacente no fue visitado, lo visita.
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidaAux(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    /**
     * Lista todos los vértices del grafo en anchura.
     * <p>
     * En anchura va por niveles, primero visita todos los vecinos y así.
     * 
     * @return Lista con los elementos de los vértices visitados en orden de anchura.
     */
    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // Si no fue visitado, lo visita.
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    /**
     * Método auxiliar recursivo para listar los vértices en anchura.
     * 
     * @param nodoActual
     * @param visitados
     */
    private void listarEnAnchuraAux(NodoVert nodoActual, Lista visitados) {
        Cola cola = new Cola();
        cola.poner(nodoActual);
        visitados.insertar(nodoActual.getElem(), visitados.longitud() + 1);
        // Inserta el primer nodo.
        while (!cola.esVacia()) {
            NodoVert actual = (NodoVert) cola.obtenerFrente();
            // Saca de la cola el que esta usando para ver los vecinos.
            cola.sacar();
            // Luego visita todos los vecinos.
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                // Hay que ver si se puede hacer con otra cosa en vez de localizar.
                if (visitados.localizar(vecino.getElem()) < 0) {
                    visitados.insertar(vecino.getElem(), visitados.longitud() + 1);
                    // Agrega en visitados los vecinos ya vistos y los agrega a la cola.
                    // Para poder ver sus vecinos.
                    cola.poner(vecino);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    /**
     * Verifica si existe un camino entre dos vértices.
     * 
     * @param origen
     * @param destino
     * @return true si existe un camino, false si no.
     */
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // En la teoria hacen distinto esta parte, pero es lo mismo que poner el modulo.
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            // Verificar si existe un camino desde origen a destino.
            exito = existeCaminoAux(vertOrigen, vertDestino, new Lista());
        }
        return exito;
    }

    /**
     * Método auxiliar recursivo para verificar si existe un camino entre dos vértices.
     * 
     * @param nodoActual
     * @param destino
     * @param visitados
     * @return
     */
    private boolean existeCaminoAux(NodoVert nodoActual, NodoVert destino, Lista visitados) {
        boolean exito = false;
        if (nodoActual != null) {
            // Si vertice nodoActual es el destino: no hay camino.
            if (nodoActual.equals(destino)) {
                exito = true;
            } else {
                // Si no es el destino verifica si hay un camino entre nodoActual y destino.
                visitados.insertar(nodoActual.getElem(), visitados.longitud() + 1);
                NodoAdy ady = nodoActual.getPrimerAdy();
                while (ady != null && !exito) {
                    // Si el adyacente no fue visitado, lo visita.
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), destino, visitados);
                    }
                    // Avanzamos al siguiente adyacente.
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    /**
     * Busca el camino más corto (en cantidad de vértices) entre origen y destino.
     * <p>
     * Este es para el punto de camino con con la menor cantidad de ciudades, de A a B.
     * 
     * @param origen
     * @param destino
     * @return Lista con el camino más corto desde origen a destino.
     */
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

    /**
     * Método auxiliar recursivo para encontrar el camino más corto (en cantidad de vértices) entre
     * origen y destino.
     * <p>
     * Utiliza backtracking para explorar todos los caminos posibles y guarda el más corto en
     * caminoActual.
     * 
     * @param origen
     * @param destino
     * @param visitados
     * @param caminoActual
     */
    private void caminoMasCortoAux(NodoVert origen, Object destino, Lista visitados,
            Lista caminoActual) {
        boolean continuar = true;
        if (origen != null) { // Si el vértice origen NO es nulo.
            if (!caminoActual.esVacia() && visitados.longitud() >= caminoActual.longitud()) {
                // Si caminoActual NO es vacia y visitados es mayor o igual a caminoActual.
                continuar = false;
            }
            if (continuar) {
                visitados.insertar(origen.getElem(), visitados.longitud() + 1);
                if (origen.getElem().equals(destino)) { // Si llegamos al destino.
                    if (caminoActual.esVacia() || visitados.longitud() < caminoActual.longitud()) {
                        // Si caminoActual ESTA vacia O visitados es menor que caminoActual.
                        caminoActual.copiarDesde(visitados);
                    }
                } else { // Si no llegamos al destino.
                    NodoAdy adyacente = origen.getPrimerAdy();
                    while (adyacente != null) { // Mientras el adyacente NO sea nulo.
                        if (visitados.localizar(adyacente.getVertice().getElem()) < 0) {
                            // Si retorna -1 es porque el adyacente no fue visitado.
                            caminoMasCortoAux(adyacente.getVertice(), destino, visitados,
                                    caminoActual);
                        }
                        // Avanzamos al siguiente adyacente.
                        adyacente = adyacente.getSigAdyacente();
                    }
                }
                // Backtracking: elimina el último vértice visitado.
                visitados.eliminar(visitados.longitud());
            }
        }
    }

    /**
     * Busca el camino de menor peso (suma de etiquetas) entre origen y destino.
     * <p>
     * Utiliza un método auxiliar recursivo que explora todos los caminos posibles y guarda el de
     * menor peso.
     * 
     * @param origen
     * @param destino
     * @return Lista con el camino de menor peso desde origen a destino.
     */
    public Lista caminoMasChico(Object origen, Object destino) {// esta vez busca el camino de menor
                                                                // peso de etiquetas
        Lista camino = new Lista();
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            Double[] pesoMin = {-1.0}; // suponiendo que la minima etiqueta es 0
            caminoMasChicoAux(vertOrigen, destino, new Lista(), camino, 0.0, pesoMin);
        }
        return camino;
    }

    /**
     * Método auxiliar recursivo para encontrar el camino de menor peso entre origen y destino.
     * <p>
     * Acumula el peso de las etiquetas y actualiza el camino si encuentra uno con menor peso.
     * 
     * @param origen
     * @param destino
     * @param visitados
     * @param caminoActual
     * @param pesoActual
     * @param pesoMin
     */
    private void caminoMasChicoAux(NodoVert origen, Object destino, Lista visitados,
            Lista caminoActual, Double pesoActual, Double[] pesoMin) {
        if (origen != null) { // Si el vértice origen NO es nulo.
            visitados.insertar(origen.getElem(), visitados.longitud() + 1);
            if (origen.getElem().equals(destino)) { // Si llegamos al destino.
                if (pesoMin[0] == -1 || pesoActual < pesoMin[0]) {
                    // Si es el primer camino encontrado o el peso total es menor.
                    pesoMin[0] = pesoActual;
                    caminoActual.copiarDesde(visitados);
                }
            } else { // Si no llegamos recorremos los adyacentes.
                NodoAdy ady = origen.getPrimerAdy();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        // Si retorna -1 es porque el adyacente no fue visitado.
                        Double pesoEtiqueta = (Double) ady.getEtiqueta();
                        caminoMasChicoAux(ady.getVertice(), destino, visitados, caminoActual,
                                pesoActual + pesoEtiqueta, pesoMin);
                    }
                    // Avanzamos al siguiente adyacente
                    ady = ady.getSigAdyacente();
                }
            }
            // Backtracking: elimina el último vértice visitado.
            visitados.eliminar(visitados.longitud());
        }
    }

    /**
     * Busca el camino de menor caudal pleno entre origen y destino. Utiliza un método auxiliar
     * recursivo que explora todos los caminos posibles y guarda el de menor caudal pleno.
     * <p>
     * El caudal pleno se define como el mínimo caudal de las tuberías en el camino.
     * <p>
     * Para el ejercicio de menor caudal pleno
     * 
     * @param origen
     * @param destino
     * @return Lista con el camino de menor caudal pleno desde origen a destino.
     */
    public Lista caminoMenorCaudalPleno(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert vertOrigen = this.ubicarVertice(origen);
        NodoVert vertDestino = this.ubicarVertice(destino);

        if (vertOrigen != null && vertDestino != null) {
            // Verificar si existe un camino desde origen a destino.
            double[] caudalMin = {-1};
            caminoMenorCaudalPlenoAux(vertOrigen, destino, new Lista(), camino, -1, caudalMin);
        }

        return camino;
    }

    /**
     * Metodo PRIVADO recursivo para encontrar el camino de menor caudal pleno entre origen y
     * destino.
     * <p>
     * Utiliza backtracking para explorar todos los caminos posibles y guarda el de menor caudal
     * pleno.
     * 
     * @param vertOrigen
     * @param destino
     * @param visitados
     * @param caminoActual
     * @param caudalActual
     * @param caudalMin
     */
    private void caminoMenorCaudalPlenoAux(NodoVert vertOrigen, Object destino, Lista visitados,
            Lista caminoActual, double caudalActual, double[] caudalMin) {
        if (vertOrigen != null) { // Si el vertice NO es nulo.
            visitados.insertar(vertOrigen.getElem(), visitados.longitud() + 1);
            if (vertOrigen.getElem().equals(destino)) { // Si llegamos al destino.
                if (caudalMin[0] == -1 || caudalActual < caudalMin[0]) {
                    // Preguntamos si el caudal actual es menor que el mínimo encontrado.
                    caudalMin[0] = caudalActual; // Actualizamos el mínimo.
                    caminoActual.copiarDesde(visitados);
                }
            } else { // En caso de no ser el destino.
                NodoAdy adyacente = vertOrigen.getPrimerAdy();
                while (adyacente != null) {
                    if (visitados.localizar(adyacente.getVertice().getElem()) < 0) {
                        // Si retorna -1 es porque el adyacente no fue visitado.
                        double caudalTuberia = (double) adyacente.getEtiqueta();
                        // Obtenemos el caudal de la tubería.
                        double nuevoCaudal;
                        if (caudalActual == -1) { // Si es el primer tramo del camino.
                            nuevoCaudal = caudalTuberia; // Primer tramo del camino.
                        } else { // En caso de que NO sea el primer tramo.
                            // Tomamos el mínimo entre el caudal actual y el de la tubería.
                            nuevoCaudal = Math.min(caudalActual, caudalTuberia);
                        }
                        caminoMenorCaudalPlenoAux(adyacente.getVertice(), destino, visitados,
                                caminoActual, nuevoCaudal, caudalMin);
                    }
                    // Avanzamos al siguiente adyacente.
                    adyacente = adyacente.getSigAdyacente();
                }
            }
            // Backtracking: elimina el último vértice visitado.
            visitados.eliminar(visitados.longitud());
        }
    }

    /**
     * Método para encontrar el camino más largo entre dos vértices en el grafo.
     * 
     * @param origen
     * @param destino
     * @return Lista con el camino más largo entre los vértices de origen y destino.
     */
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

    /**
     * Método auxiliar para encontrar el camino más largo en el grafo.
     * <p>
     * NOTA: Funciona igual que el camino mas corto, pero tomando en cuenta el camino mas largo
     * encontrado.
     * 
     * @param origen
     * @param destino
     * @param visitados
     * @param caminoActual
     */
    private void caminoMasLargoAux(NodoVert origen, Object destino, Lista visitados,
            Lista caminoActual) {
        if (origen != null) { // Si el vertice NO es nulo.
            visitados.insertar(origen.getElem(), visitados.longitud() + 1);
            if (origen.getElem().equals(destino)) { // Si llegamos al destino.
                if (caminoActual.esVacia() || visitados.longitud() > caminoActual.longitud()) {
                    // Si caminoActual ESTA vacia O visitados es MAYOR que caminoActual.
                    caminoActual.copiarDesde(visitados);
                }
            } else { // Si no llegamos al destino.
                NodoAdy ady = origen.getPrimerAdy();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        caminoMasLargoAux(ady.getVertice(), destino, visitados, caminoActual);
                    }
                    // Avanzamos al siguiente adyacente.
                    ady = ady.getSigAdyacente();
                }
            }
            // Backtracking: elimina el último vértice visitado.
            visitados.eliminar(visitados.longitud());
        }
    }

    /**
     * Genera y devuelve un grafo que es equivalente (igual estructura y contenido de los nodos) al
     * original.
     * 
     * @return Grafo.
     */
    public DigrafoEtiquetado clonar() {
        DigrafoEtiquetado clon = new DigrafoEtiquetado();
        clon.inicio = clonarVertices(this.inicio); // Paso 1
        clonarAdyacencias(this.inicio, clon.inicio, clon.inicio); // Paso 2
        return clon;
    }

    /**
     * Metodo PRIVADO que clona los vértices de un grafo.
     * 
     * @param original
     * @return NodoVert
     */
    private NodoVert clonarVertices(NodoVert original) {
        NodoVert copia = null;
        if (original != null) {
            copia = new NodoVert(original.getElem(), null, null);
            copia.setSigVertice(clonarVertices(original.getSigVertice()));
            // No copiamos primerAdy todavía
        }
        return copia;
    }

    /**
     * Método PRIVADO que ubica un vértice en la copia del grafo..
     * 
     * @param inicioCopia
     * @param elemento
     * @return NodoVert
     */
    private NodoVert ubicarVerticeEnCopia(NodoVert inicioCopia, Object elemento) {
        NodoVert aux = inicioCopia;
        while (aux != null && !aux.getElem().equals(elemento)) {
            // Avanzamos al siguiente adyacente.
            aux = aux.getSigVertice();
        }
        return aux;
    }

    /**
     * Método PRIVADO que clona las adyacencias de un vértice en el grafo.
     * 
     * @param original
     * @param copia
     * @param copiaRaiz
     */
    private void clonarAdyacencias(NodoVert original, NodoVert copia, NodoVert copiaRaiz) {
        if (original != null && copia != null) {
            NodoAdy adyOriginal = original.getPrimerAdy();
            NodoAdy ultimoAdy = null;
            while (adyOriginal != null) {
                // Buscar vértice destino en la copia (por elemento).
                NodoVert destinoCopia =
                        ubicarVerticeEnCopia(copiaRaiz, adyOriginal.getVertice().getElem());
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

    /**
     * Método que devuelve una representación en cadena del grafo.
     * 
     * @return String
     */
    public String toString() {
        String resultado = "";
        NodoVert actual = this.inicio;
        if (this.inicio != null) {
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
        } else {
            resultado = "Grafo vacio";
        }

        return resultado;
    }
}
