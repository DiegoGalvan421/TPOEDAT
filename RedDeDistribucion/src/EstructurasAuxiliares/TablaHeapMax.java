package EstructurasAuxiliares;

public class TablaHeapMax {

    private NodoHeap[] heap;
    private int tamanio;
    private static final int CAPACIDAD = 50;

    public TablaHeapMax() {
        this.heap = new NodoHeap[CAPACIDAD + 1]; // Indexa desde 1
        this.tamanio = 0;
    }

    public boolean insertar(Comparable clave, Object dato) {
        if (tamanio >= CAPACIDAD) {
            return false; // Heap lleno
        }

        tamanio++;
        heap[tamanio] = new NodoHeap(clave, dato);
        hacerSubir(tamanio);
        return true;
    }

    private void hacerSubir(int pos) {
        while (pos > 1) {
            int padre = pos / 2;
            if (heap[pos].clave.compareTo(heap[padre].clave) > 0) {
                NodoHeap temp = heap[pos];
                heap[pos] = heap[padre];
                heap[padre] = temp;
                pos = padre;
            } else {
                break;
            }
        }
    }

    public Object eliminarCima() {
        if (tamanio == 0)
            return null;

        Object cima = heap[1].dato;
        heap[1] = heap[tamanio];
        tamanio--;
        hacerBajar(1);
        return cima;
    }

    private void hacerBajar(int pos) {
        while (pos * 2 <= tamanio) {
            int hijoMayor = pos * 2;
            if (hijoMayor + 1 <= tamanio && heap[hijoMayor + 1].clave.compareTo(heap[hijoMayor].clave) > 0) {
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

    public boolean esVacio() {
        return tamanio == 0;
    }

    public String toStringOrdenado() {
        StringBuilder sb = new StringBuilder("[");
        TablaHeapMax copia = this.copiar();
        while (!copia.esVacio()) {
            sb.append(copia.eliminarCima().toString()).append(" -> ");
        }
        if (sb.length() >= 4) {
            sb.setLength(sb.length() - 4); // elimina el último " -> "
        }
        sb.append("]");
        return sb.toString();
    }

    private TablaHeapMax copiar() {
        TablaHeapMax nueva = new TablaHeapMax();
        for (int i = 1; i <= this.tamanio; i++) {
            nueva.heap[i] = new NodoHeap(this.heap[i].clave, this.heap[i].dato);
        }
        nueva.tamanio = this.tamanio;
        return nueva;
    }
}
