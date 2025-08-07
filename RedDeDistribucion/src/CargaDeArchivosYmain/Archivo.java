package CargaDeArchivosYmain;

// Permite leer texto de un archivo de manera eficiente, línea por línea.
import Estructuras.*;
import Objetos.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Archivo {

    /**
     * Lee todas las líneas de un archivo de texto y las devuelve como <estructura>
     * <p>
     * NOTA : Tengo que ver de como hacerlo generico para no repetir codigo y que no sea para una
     * estructura especifica.
     *
     * @param rutaArchivo Ruta del archivo a leer.
     * @return Retorna la estructura que se desee llenar.
     */
    public static TablaAVL cargarCiudades(String rutaArchivo, DigrafoEtiquetado grafo) {
        FileReader archivo; // Para abrir el archivo.
        BufferedReader lector; // Para leer el archivo línea por línea.
        TablaAVL salida = new TablaAVL(); // Lista donde se guardarán las ciudades leídas.
        int i = 1; // Posición donde se insertará cada ciudad en la lista (posicional).
        int j = 0; // Índice base para acceder a los campos del arreglo `info`.

        try {
            archivo = new FileReader(rutaArchivo); // Intenta abrir el archivo.
            if (archivo.ready()) { // Verifica que esté listo para ser leído.
                lector = new BufferedReader(archivo); // Crea el lector de líneas.
                String cadena;

                // Lee cada línea del archivo
                while ((cadena = lector.readLine()) != null) {
                    // Divide la línea por el carácter ';' y obtiene los datos como arreglo.
                    String[] info = cadena.split(";");
                    // Crea un nuevo objeto Ciudad con los datos obtenidos.
                    // info[0] = nombre.
                    // info[1] = nomenclatura.
                    // info[2] = superficie.
                    // info[3] = consumo.
                    salida.insertar(info[j], new Ciudad(info[j], // nombre.
                            info[j + 1], // nomenclatura.
                            Double.parseDouble(info[j + 2]), // superficie.
                            Double.parseDouble(info[j + 3])) // consumo.
                    );
                    grafo.insertarVertice(info[j + 1]);

                    log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", i + ": " + "Ciudad Cargada");
                    i++; // Incrementa la posición para el siguiente elemento.
                }
            } else {
                System.out.println("Archivo no listo para ser leído");
            }

        } catch (FileNotFoundException ex) {
            log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "El archivo no existe." + ex.getMessage());
        } catch (IOException ex) {
            log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "Error leyendo o escribiendo en algún archivo.");
        }

        // Devuelve la tabla con las ciudades cargadas.
        return salida;
    }

    public static HashMap<ClaveTuberia, Tuberia> cargarTuberias(String rutaArchivo,
            DigrafoEtiquetado grafo) {
        FileReader archivo; // Para abrir el archivo.
        BufferedReader lector; // Para leer el archivo línea por línea.
        HashMap<ClaveTuberia, Tuberia> salida = new HashMap<>();
        // Diccionario donde se guardarán las tuberías leídas.
        int i = 1; // Contador de líneas para el log.
        int j = 0; // Índice base para acceder a los campos del arreglo `info`.

        try {
            archivo = new FileReader(rutaArchivo); // Intenta abrir el archivo.
            if (archivo.ready()) { // Verifica que esté listo para ser leído.
                lector = new BufferedReader(archivo); // Crea el lector de líneas.
                String cadena;

                // Lee cada línea del archivo
                while ((cadena = lector.readLine()) != null) {
                    // Divide la línea por el carácter ';' y obtiene los datos como arreglo.
                    String[] info = cadena.split(";");

                    // info[0] = "NOMENCLATURAORIGEN - NOMENCLATURADESTINO"
                    // info[1] = caudal mínimo.
                    // info[2] = caudal máximo.
                    // info[3] = diámetro.
                    // info[4] = estado.

                    // Separa la nomenclatura origen y destino
                    String[] ciudades = info[j].split(" - ");
                    String origen = ciudades[0].trim();
                    String destino = ciudades[1].trim();

                    // Crea una nueva clave de tubería
                    ClaveTuberia clave = new ClaveTuberia(origen, destino);

                    // Crea un nuevo objeto Tuberia con los datos obtenidos
                    salida.put(clave, new Tuberia((origen + "-" + destino).trim(), // nomenclatura
                                                                                   // completa.
                            Double.parseDouble(info[j + 1]), // caudal mínimo.
                            Double.parseDouble(info[j + 2]), // caudal máximo.
                            Double.parseDouble(info[j + 3]), // diámetro.
                            info[j + 4].trim() // estado.
                    ));
                    grafo.insertarArco(origen, destino, Double.parseDouble(info[j + 2]));

                    // Registra en el log que se cargó una tubería
                    log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                            i + ": " + "Tubería cargada entre " + origen + " y " + destino);
                    i++; // Incrementa el contador de líneas para el siguiente elemento.
                }
            } else {
                System.out.println("Archivo no listo para ser leído");
            }

        } catch (FileNotFoundException ex) {
            log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "El archivo no existe." + ex.getMessage());
        } catch (IOException ex) {
            log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "Error leyendo o escribiendo en algún archivo.");
        }

        // Devuelve el diccionario con las tuberías cargadas
        return salida;
    }

    /**
     * Escribe una línea en el archivo log.
     *
     * @param rutaLog Ruta del archivo log.
     * @param mensaje Mensaje a registrar.
     */
    public static void log(String rutaLog, String mensaje) {
        try (FileWriter fw = new FileWriter(rutaLog, true)) {
            // Obtiene la fecha y hora actual como cadena.
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String timestamp =
                    "Fecha:" + LocalDate.now().toString() + " Hora:" + dateFormat.format(date);

            // Escribe en el archivo: [fecha y hora] mensaje.
            fw.write("[" + timestamp + "] " + mensaje + "\n");

        } catch (IOException e) {
            // Si ocurre un error al escribir en el archivo, lo muestra por consola de error.
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}
