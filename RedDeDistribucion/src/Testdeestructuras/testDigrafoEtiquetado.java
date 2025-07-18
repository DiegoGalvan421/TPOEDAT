package Testdeestructuras;

import Estructuras.DigrafoEtiquetado;

public class testDigrafoEtiquetado {
    public static void main(String[] args) {
        DigrafoEtiquetado grafo = new DigrafoEtiquetado();

        System.out.println("¿Grafo vacío?: " + grafo.esVacio());

        // Insertar vértices
        System.out.println("\n== Insertando vértices ==");
        System.out.println("Insertar A: " + grafo.insertarVertice("A"));
        System.out.println("Insertar B: " + grafo.insertarVertice("B"));
        System.out.println("Insertar C: " + grafo.insertarVertice("C"));
        System.out.println("Insertar D: " + grafo.insertarVertice("D"));
        System.out.println("Insertar L: " + grafo.insertarVertice("L"));
        System.out.println("Insertar T: " + grafo.insertarVertice("T"));
        System.out.println("Insertar R: " + grafo.insertarVertice("R"));

        System.out.println("Insertar A (repetido): " + grafo.insertarVertice("A")); // debe fallar

        System.out.println("\nGrafo:\n" + grafo.toString());

        // Insertar arcos
        System.out.println("A -> D: " + grafo.insertarArco("A", "D", 4)); // primero A → D
        System.out.println("A -> B: " + grafo.insertarArco("A", "B", 1)); // después A → B
        System.out.println("D -> L: " + grafo.insertarArco("D", "L", 7)); // D → L antes
        System.out.println("D -> B: " + grafo.insertarArco("D", "B", 6)); // D → B después
        System.out.println("B -> C: " + grafo.insertarArco("B", "C", 2));
        System.out.println("C -> D: " + grafo.insertarArco("C", "D", 3));
        System.out.println("L -> R: " + grafo.insertarArco("L", "R", 9)); // L → R antes
        System.out.println("L -> T: " + grafo.insertarArco("L", "T", 8)); // L → T después
        System.out.println("R -> A: " + grafo.insertarArco("R", "A", 9));
        System.out.println("X -> Y (inexistentes): " + grafo.insertarArco("X", "Y", 6)); // debe fallar

        System.out.println("\nGrafo:\n" + grafo.toString());

        // Existe vértice
        System.out.println("\n== Existencia de vértices ==");
        System.out.println("¿Existe A?: " + grafo.existeVertice("A"));
        System.out.println("¿Existe Z?: " + grafo.existeVertice("Z"));

        // Existe arco
        System.out.println("\n== Existencia de arcos ==");
        System.out.println("¿Existe arco A -> B?: " + grafo.existeArco("A", "B"));
        System.out.println("¿Existe arco B -> A?: " + grafo.existeArco("B", "A")); // no dirigido

        // Eliminar arco
        System.out.println("\n== Eliminando arcos ==");
        System.out.println("Eliminar B -> C: " + grafo.eliminarArco("B", "C"));
        System.out.println("Eliminar B -> C otra vez: " + grafo.eliminarArco("B", "C")); // debe fallar

        System.out.println("\nGrafo:\n" + grafo.toString());

        // Eliminar vértice
        System.out.println("\n== Eliminando vértice ==");
        System.out.println("Eliminar C: " + grafo.eliminarVertice("C"));
        System.out.println("Eliminar X (inexistente): " + grafo.eliminarVertice("X"));

        System.out.println("\nGrafo:\n" + grafo.toString());

        // Consultas de caminos
        System.out.println("\n== Consultas de caminos ==");
        System.out.println("¿Existe camino A -> D?: " + grafo.existeCamino("A", "D"));
        System.out.println("¿Existe camino B -> D?: " + grafo.existeCamino("B", "D"));

        System.out.println("Camino más corto A -> B: " + grafo.caminoMasCorto("A", "B"));
        System.out.println("Camino más largo A -> B: " + grafo.caminoMasLargo("A", "B"));

        // Recorridos
        System.out.println("\n== Recorridos ==");
        System.out.println("En profundidad: " + grafo.listarEnProfundidad());
        System.out.println("En anchura: " + grafo.listarEnAnchura());

        // Clonado
        System.out.println("\n== Clonando grafo ==");
        DigrafoEtiquetado clon = grafo.clonar();
        System.out.println("Grafo original:\n" + grafo.toString());
        System.out.println("Grafo clonado:\n" + clon.toString());

        // Confirmar independencia
        System.out.println("Eliminando vértice A del original");
        grafo.eliminarVertice("A");

        System.out.println("\nGrafo original modificado:\n" + grafo.toString());
        System.out.println("Grafo clonado (no debería haber cambiado):\n" + clon.toString());
    }
}
