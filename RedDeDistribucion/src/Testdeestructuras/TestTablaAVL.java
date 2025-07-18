package Testdeestructuras;
import Estructuras.*;
public class TestTablaAVL {
    public static void main(String[] args) {
        TablaAVL tabla = new TablaAVL();

        System.out.println("=== TEST DE TABLA AVL ===");

        // Test 1: Estructura recién creada está vacía
        System.out.println("Test 1 - esVacio() debe ser true: " + tabla.esVacia());

        // Test 2: Insertar elementos
        System.out.println("\nTest 2 - Insertar elementos");
        System.out.println("Insertar (10, 'A'): " + tabla.insertar(10, "A"));
        System.out.println("Insertar (20, 'B'): " + tabla.insertar(20, "B"));
        System.out.println("Insertar (5, 'C'): " + tabla.insertar(5, "C"));
        System.out.println("Insertar (15, 'D'): " + tabla.insertar(15, "D"));

        // Test 3: Intentar insertar clave duplicada
        System.out.println("\nTest 3 - Insertar clave duplicada (10, 'Z'):");
        System.out.println("Insertar (10, 'Z'): " + tabla.insertar(10, "Z")); // debe ser false

        // Test 4: Verificar existencia de claves
        System.out.println("\nTest 4 - existeClave()");
        System.out.println("Existe clave 10: " + tabla.existeClave(10)); // true
        System.out.println("Existe clave 99: " + tabla.existeClave(99)); // false

        // Test 5: Obtener información asociada
        System.out.println("\nTest 5 - obtenerInformacion()");
        System.out.println("Info clave 10: " + tabla.obtenerInformacion(10));
        System.out.println("Info clave 15: " + tabla.obtenerInformacion(15));

        // Test 6: Listar claves (debe estar ordenado)
        System.out.println("\nTest 6 - listarClaves()");
        System.out.println("Claves: " + tabla.listarClaves());

        // Test 7: Listar datos
        System.out.println("\nTest 7 - listarDatos()");
        System.out.println("Datos: " + tabla.listarDatos());

        // Test 8: Eliminar claves
        System.out.println("\nTest 8 - eliminar()");
        System.out.println("Eliminar 10: " + tabla.eliminar(10)); // true
        System.out.println("Eliminar 99 (inexistente): " + tabla.eliminar(99)); // false

        // Test 9: esVacio() tras eliminar
        System.out.println("\nTest 9 - esVacio()");
        System.out.println("¿Está vacío?: " + tabla.esVacia()); // false

        // Test 10: Mostrar estructura (toString)
        System.out.println("\nTest 10 - toString() (estructura):");
        System.out.println(tabla);

        // Test 11: Vaciar el tabla
        System.out.println("\nTest 11 - Vaciar tabla");
        tabla.vaciar();
        System.out.println("¿Está vacío después de vaciar?: " + tabla.esVacia());
        System.out.println("Claves tras vaciar: " + tabla.listarClaves());
    }
}