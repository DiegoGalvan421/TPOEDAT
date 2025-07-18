

// Permite leer texto de un archivo de manera eficiente, línea por línea.
import java.io.BufferedReader;

// Excepción que se lanza cuando no se encuentra el archivo al intentar abrirlo.
import java.io.FileNotFoundException;

// Clase para leer archivos de texto carácter por carácter.
import java.io.FileReader;

// Clase que permite escribir texto en archivos.
import java.io.FileWriter;

// Excepción general de entrada/salida (lectura o escritura de archivos).
import java.io.IOException;

// Clase abstracta para formatear fechas y horas (por ejemplo: dd/MM/yyyy).
import java.text.DateFormat;

// Clase concreta para definir un formato de fecha personalizado.
import java.text.SimpleDateFormat;

// Representa una fecha (año-mes-día) sin hora (nueva API de Java 8+).
import java.time.LocalDate;

// Clase antigua para trabajar con fechas y horas (menos recomendada que LocalDate).
import java.util.Date;


import EstructurasAuxiliares.Lista;
import Objetos.*;

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
    public static Lista cargarCiudades(String rutaArchivo) {
        FileReader archivo; // Para abrir el archivo.
        BufferedReader lector; // Para leer el archivo línea por línea.
        Lista salida = new Lista(); // Lista donde se guardarán las ciudades leídas.
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
                    salida.insertar(new Ciudad(info[j], // nombre.
                            info[j + 1], // nomenclatura.
                            Double.parseDouble(info[j + 2]), // superficie.
                            Double.parseDouble(info[j + 3]) // consumo.
                    ), i // posición en la lista.
                    );
                    log("U:\\Universidad\\LICENCIATURA EN CIENCIAS DE LA COMPUTACION\\SEGUNDO AÑO\\CURSADO-2025\\PRIMER CUATRIMESTRE\\11-ESTRUCTURA DE DATOS\\Estructura de Datos - 2025\\tpFinal\\log.txt",
                            i + ": " + "Ciudad Cargada");
                    i++; // Incrementa la posición para el siguiente elemento.
                }
            } else {
                System.out.println("Archivo no listo para ser leído");
            }

        } catch (FileNotFoundException ex) {
            log("U:\\Universidad\\LICENCIATURA EN CIENCIAS DE LA COMPUTACION\\SEGUNDO AÑO\\CURSADO-2025\\PRIMER CUATRIMESTRE\\11-ESTRUCTURA DE DATOS\\Estructura de Datos - 2025\\tpFinal\\log.txt",
                    "El archivo no existe." + ex.getMessage());
        } catch (IOException ex) {
            log("U:\\Universidad\\LICENCIATURA EN CIENCIAS DE LA COMPUTACION\\SEGUNDO AÑO\\CURSADO-2025\\PRIMER CUATRIMESTRE\\11-ESTRUCTURA DE DATOS\\Estructura de Datos - 2025\\tpFinal\\log.txt",
                    "Error leyendo o escribiendo en algún archivo.");
        }

        // Devuelve la lista con las ciudades cargadas
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

