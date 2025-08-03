package EstructurasAuxiliares;


public class TablaHeapMax {

    // Atributos.
    private NodoHeap[] heap;
    private int tamanio;
    private static final int CAPACIDAD = 50;

    /**
     * Constructor.
     * <p>
     * Crea una tabla heap de tamaño fijo.
     */
    public TablaHeapMax() {
        this.heap = new NodoHeap[CAPACIDAD + 1]; // Indexa desde 1
        this.tamanio = 0;
    }

    @SuppressWarnings("rawtypes")
    /**
     * Inserta un nuevo nodo en el heap.
     * 
     * @param clave
     * @param dato
     * @return true si se pudo insertar, false si el heap está lleno.
     */
    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = false;

        if (tamanio < CAPACIDAD) {
            tamanio++;
            heap[tamanio] = new NodoHeap(clave, dato);
            hacerSubir(tamanio);
            exito = true;
        }
        return exito;
    }

    @SuppressWarnings("unchecked")
    /**
     * Hace subir el nodo en la posición indicada para mantener la propiedad del heap.
     * 
     * @param pos posición del nodo a subir.
     */
    private void hacerSubir(int pos) {
        while (pos > 1) {
            int padre = pos / 2;
            if (heap[pos].clave.compareTo(heap[padre].clave) > 0) {
                NodoHeap temp = heap[pos];
                heap[pos] = heap[padre];
                heap[padre] = temp;
                pos = padre;
            } else {
                break; // REVISAR ESTO.
            }
        }
    }

    /**
     * Elimina y devuelve el nodo con la clave más alta (la cima del heap).
     * 
     * @return el dato del nodo eliminado, o null si el heap está vacío.
     */
    public Object eliminarCima() {
        if (tamanio == 0)
            return null;

        Object cima = heap[1].dato;
        heap[1] = heap[tamanio];
        tamanio--;
        hacerBajar(1);
        return cima;
    }

    @SuppressWarnings("unchecked")
    /**
     * Hace bajar el nodo en la posición indicada para mantener la propiedad del heap.
     * 
     * @param pos posición del nodo a bajar.
     */
    private void hacerBajar(int pos) {
        while (pos * 2 <= tamanio) {
            int hijoMayor = pos * 2;
            if (hijoMayor + 1 <= tamanio
                    && heap[hijoMayor + 1].clave.compareTo(heap[hijoMayor].clave) > 0) {
                hijoMayor++;
            }

            if (heap[hijoMayor].clave.compareTo(heap[pos].clave) > 0) {
                NodoHeap temp = heap[pos];
                heap[pos] = heap[hijoMayor];
                heap[hijoMayor] = temp;
                pos = hijoMayor;
            } else {
                break;
            }
        }
    }

    /**
     * Verifica si el heap está vacío.
     * 
     * @return true si el heap está vacío, false en caso contrario.
     */
    public boolean esVacio() {
        return tamanio == 0;
    }

    /**
     * Devuelve una representación en cadena de los elementos del heap ordenados.
     * 
     * <p>
     * Precondición: el heap no está vacío.
     * 
     * @return String con los elementos del heap ordenados de mayor a menor.
     */
    public String toStringOrdenado() {
        StringBuilder sb = new StringBuilder("[");// String builder trabaja más eficiente que las
                                                  // concatenaciones "+" que deben crear un nuevo
                                                  // objeto y copiar el anterior String caracter a
                                                  // caracter
        while (!this.esVacio()) {
            sb.append(this.eliminarCima().toString()).append(" -> ");
        }
        if (sb.length() >= 4) {
            sb.setLength(sb.length() - 4); // elimina el último " -> "
        }
        sb.append("]");
        return sb.toString();
    }
    /*
     * NO LO NECESITO private TablaHeapMax copiar() { TablaHeapMax nueva = new TablaHeapMax(); for
     * (int i = 1; i <= this.tamanio; i++) { nueva.heap[i] = new NodoHeap(this.heap[i].clave,
     * this.heap[i].dato); } nueva.tamanio = this.tamanio; return nueva; }
     */
}
