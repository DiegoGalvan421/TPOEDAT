package Testdeestructuras;
import Estructuras.DigrafoEtiquetado;
import EstructurasAuxiliares.Lista;
import Objetos.Ciudad;

public class testGeneral {
    public static void main(String[] args) {
        DigrafoEtiquetado grafo = new DigrafoEtiquetado();

        Ciudad c1 = new Ciudad("Buenos Aires", "BA", 203.5, 140.0);
        Ciudad c2 = new Ciudad("Córdoba", "CBA", 576.2, 160.5);
        Ciudad c3 = new Ciudad("Rosario", "RS", 310.3, 180.9);
        Ciudad c4 = new Ciudad("Mendoza", "MDZ", 240.0, 150.0);
        Ciudad c5 = new Ciudad("La Plata", "LP", 190.6, 135.3);

        // Insertar vértices
        grafo.insertarVertice(c1);
        grafo.insertarVertice(c2);
        grafo.insertarVertice(c3);
        grafo.insertarVertice(c4);
        grafo.insertarVertice(c5);

        // Insertar arcos con etiquetas (pesos)
        grafo.insertarArco(c1, c2, 120);  // BA → CBA
        grafo.insertarArco(c1, c3, 180);  // BA → RS
        grafo.insertarArco(c2, c4, 300);  // CBA → MDZ
        grafo.insertarArco(c3, c4, 200);  // RS → MDZ
        grafo.insertarArco(c4, c5, 150);  // MDZ → LP
        grafo.insertarArco(c2, c5, 480);  // CBA → LP

        // Pruebas de recorrido
        System.out.println("Recorrido en profundidad:");
        System.out.println(grafo.listarEnProfundidad());

        System.out.println("Recorrido en anchura:");
        System.out.println(grafo.listarEnAnchura());

        // Probar camino más corto
        Lista camino = grafo.caminoMasCorto(c1, c5);
        System.out.println("Camino más corto de BA a LP (ignorando peso):");
        System.out.println(camino);

        Lista caminoMin = grafo.caminoMasChico(c1, c5);
        System.out.println("Camino mínimo de BA a LP (con peso):");
        System.out.println(caminoMin);
    }
}

