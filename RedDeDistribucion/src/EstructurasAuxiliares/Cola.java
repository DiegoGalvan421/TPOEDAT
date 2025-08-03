package EstructurasAuxiliares;

public class Cola {

    // Atributos
    private Nodo frente;
    private Nodo fin;

    // ----------Basicas----------
    public Cola() { // Constructor
        // Crea y devuelve la cola vacia.
        this.frente = null;
        this.fin = null;
    }

    /**
     * Pone nuevoElem en la cola.
     * <p>
     * Nunca hay error de cola llena, entonces devuelve true.
     * 
     * @param nuevoElem
     * @return boolean
     */
    public boolean poner(Object nuevoElem) {
        Nodo nuevoNodo = new Nodo(nuevoElem, null);

        if (this.frente == null) { // Si esta vacia
            this.frente = nuevoNodo;
            this.fin = nuevoNodo;
        } else { // Caso normal
            this.fin.setEnlace(nuevoNodo); // Se pone primero el enlace al nuevo nodo.
            this.fin = nuevoNodo; // Se setea el fin en el nuevo nodo agregado.
        }
        return true;
    }

    /**
     * Saca el elmento del frente de la cola.
     * <p>
     * Devuelve true si se pudo sacar un elemento, false si la cola estaba vacia.
     * 
     * @return boolean
     */
    public boolean sacar() {
        boolean exito = true;

        if (this.frente == null) { // Si esta vacia.
            exito = false;
        } else { // Caso normal, saca el frente y lo actualiza.
            this.frente = this.frente.getEnlace();
            if (this.frente == null) { // En caso de ser vacia, actualiza el fin.
                this.fin = null;
            }
        }
        return exito;
    }

    /**
     * Devuelve el elemento en el frente de la cola.
     * <p>
     * Si la cola esta vacia, devuelve null.
     * 
     * @return Object
     */
    public Object obtenerFrente() {
        Object frente = null;
        if (this.frente != null) {
            frente = this.frente.getElem();
        }
        return frente;
    }

    /**
     * Verifica si la cola esta vacia.
     * 
     * @return boolean
     */
    public boolean esVacia() {
        return this.frente == null;
    }

    /**
     * Saca todos los elementos de la cola.
     */
    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }

    @Override
    /**
     * Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de
     * los mismos, en otra estructura del mismo tipo.
     * 
     * @return Cola
     */
    public Cola clone() {
        Cola colaClon = new Cola();

        if (this.frente != null) { // Si NO esta vacia.
            // Comienza desde el primer nodo de la cola original.
            Nodo nodoActual = this.frente; // Con este recorro la original.
            colaClon.frente = new Nodo(nodoActual.getElem(), null);
            colaClon.fin = colaClon.frente;

            // Avanza al siguiente nodo de la cola original.
            while ((nodoActual = nodoActual.getEnlace()) != null) {
                // Crea un nuevo nodo con el mismo elemento del nodo actual.
                Nodo nuevoNodo = new Nodo(nodoActual.getElem(), null);
                // Enlaza el nuevo nodo al final de la cola clonada.
                colaClon.fin.setEnlace(nuevoNodo);
                // Actualiza el fin de la cola clonada al nuevo nodo.
                colaClon.fin = nuevoNodo;
            }
        }
        return colaClon;
    }

    @Override
    /**
     * Devuelve una representación en cadena de la cola, mostrando los elementos en el orden en que
     * fueron agregados.
     * 
     * @return String
     */
    public String toString() {
        String cadena = "";

        if (esVacia()) {
            cadena += "[]";
        } else {
            cadena += "[";

            Nodo recorrido = this.frente;
            while (recorrido != null) {
                cadena += recorrido.getElem().toString();

                recorrido = recorrido.getEnlace();
                if (recorrido != null) {
                    cadena += ",";
                }
            }

            cadena += "]";
        }

        return cadena;
    }
}
