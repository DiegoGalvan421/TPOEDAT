package CargaDeArchivosYmain;

import Estructuras.*;
import EstructurasAuxiliares.*;
import Objetos.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
public class General {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DigrafoEtiquetado grafo = new DigrafoEtiquetado();
        TablaAVL ciudades = Archivo.cargarCiudades("RedDeDistribucion/src/Datos/Ciudades.txt", grafo);
        HashMap<ClaveTuberia, Tuberia> Tuberias = Archivo.cargarTuberias("RedDeDistribucion/src/Datos/Tuberias.txt",
                grafo);
        System.out.println(ciudades.toString());
        System.out.println(Tuberias.toString());
        System.out.println(grafo.toString());
        cargarHabCiudad("RedDeDistribucion/src/Datos/Habitantes historicos.txt", ciudades);
        Ciudad aux = (Ciudad) ciudades.obtenerInformacion("Buenos Aires");

        System.out.println(aux.verHab(2021));
        int opcion;
        Character sigue = 's';

        do {
            System.out.println("---------------------Menu---------------------");
            System.out.println("1) Altas, bajas y modificaciones de ciudades");
            System.out.println("2) Atlas, bajas y modificaciones de tuberias");
            System.out.println("3) Alta de información de la cantidad de habitantes para año dado y ciudad dada");
            System.out.println("4) Consultas sobre ciudades");
            System.out.println("5) Consultas sobre transporte de agua");
            System.out.println("6) Listado de ciudades ordenadas por consumo");
            System.out.println("7) Mostrar Sistema");
            System.out.println("8) Detener Programa");
            System.out.println("Elija una opcion");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Altas, bajas y modificaciones de ciudades");
                    System.out.println("1) Altas");
                    System.out.println("2) Bajas");
                    System.out.println("3) Modificaciones");
                    System.out.println("Elija una opcion");
                    opcion = sc.nextInt();
                    sc.nextLine();
                    switch (opcion) {
                        case 1:
                            altaDeCiudades(grafo, ciudades);
                            break;
                        case 2:
                            bajaDeCiudades(grafo, ciudades);
                            break;
                        case 3:
                            modificarCiudades(ciudades);
                            break;
                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Altas, bajas y modificaciones de Tuberias");
                    System.out.println("1) Altas");
                    System.out.println("2) Bajas");
                    System.out.println("3) Modificaciones");
                    System.out.println("Elija una opcion");
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpia el escaner
                    switch (opcion) {
                        case 1:
                            altaDeTuberia(grafo, Tuberias);
                            break;
                        case 2:
                            bajaDeTuberias(grafo, Tuberias);
                            break;
                        case 3:
                            modificarTuberias(grafo, Tuberias);
                            break;

                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                    break;
                case 3:
                    System.out.println(
                            "Alta de información de la cantidad de habitantes para año dado y ciudad dada");
                    altaDeInformacionCanHab(ciudades);
                    break;
                case 4:
                    System.out.println("Consultas sobre ciudades");
                    System.out.println("1) Cantidad de habitantes y volumen de agua distribuído");
                    System.out.println("2) Rango de ciudades con un consumo de agua en rango");
                    System.out.println("Elija una opcion");
                    opcion = sc.nextInt();
                    sc.nextLine();
                    switch (opcion) {
                        case 1:
                            habYVolDeUnaCiudad(ciudades);
                            break;
                        case 2:
                            ciudadesSegunVolyHab(ciudades);
                            break;
                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Consultas sobre transporte de agua");
                    System.out.println("1) Camino minimo con caudal pleno");
                    System.out.println("2) Camino con la cantidad minima de ciudades");
                    System.out.println("Elija una opcion");
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpia el escaner
                    switch (opcion) {
                        case 1:
                            caminoMenorCaudal(grafo, Tuberias, ciudades);
                            break;

                        case 2:
                            caminoMenosCiudades(grafo, Tuberias, ciudades);
                            break;

                        default:
                            System.out.println("Opcion invalida");
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Listado de ciudades ordenadas por consumo anual");
                    System.out.println("Ingrese el año");
                    int anio = sc.nextInt();
                    sc.nextLine();
                    System.out.println(consumoAnual(anio, ciudades));
                    break;
                /*
                 * NOTA JUAN: Si una ciudad no tiene datos registrados en el año especificado,
                 * no se inserta en el heap.
                 */

                case 7:
                    System.out.println("Mostrar Todas las estructuras");
                    mostrarEstructuras(Tuberias, grafo, ciudades);
                    break;
                case 8:
                    System.out.println("Hasta luego ;)");
                    Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Estado del Sistema:" + "\n"
                            + grafo.toString() + "\n" + ciudades.toString() + "\n" + Tuberias.toString());
                    System.exit(0); // Termina el programa
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
            System.out.println("¿Desea continuar? S/N");
            sigue = sc.nextLine().charAt(0);

        } while (sigue == 's' || sigue == 'S');
        System.out.println("Hasta luego ;)");
        Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Estado del Sistema:" + "\n" + grafo.toString() + "\n"
                + ciudades.toString() + "\n" + Tuberias.toString());
    }

    /*----------PUNTO 1----------*/

    /**
     * Crea y guarda los datos de una ciudad nueva.
     * <p>
     * La ciudad se guarda en la tabla avl, con el nombre como clave.
     * <p>
     * En el grafo se guarda su nomenclatura.
     * 
     * @param grafo
     * @param ciudades
     * @param nombre
     * @param nomenclatura
     * @param unaSup
     * @param unConsumo
     */
    public static void altaDeCiudades(DigrafoEtiquetado grafo, TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        // Solicita al usuario los datos de la ciudad a agregar.
        System.out.println("Agregar Ciudad");
        System.out.println("Ingrese el nombre de la ciudad:");
        String nombre = sc.nextLine().trim();
        System.out.println("Ingrese la nomenclatura de la ciudad:");
        String nomenclatura = sc.nextLine().trim();
        System.out.println("Ingrese la superficie de la ciudad:");
        double unaSup = sc.nextDouble();
        System.out.println("Ingrese el consumo de la ciudad:");
        double unConsumo = sc.nextDouble();

        // Crea una nueva ciudad y la agrega a la tabla y al grafo.
        Ciudad nuevaCiudad = new Ciudad(nombre, nomenclatura, unaSup, unConsumo);
        if (ciudades.insertar(nombre, nuevaCiudad)) {
            grafo.insertarVertice(nomenclatura); // si ya está cargada la ciudad que devuelva un mensaje
            System.out.println("Ciudad agregada correctamente.");
            System.out.println(nuevaCiudad.toString());
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Se insertó la ciudad " + nombre);
        } else {
            System.out.println("No se pudo dar de alta la ciudad");
        }

    }

    /**
     * Quita la ciudad de la tabla y del grafo.
     * 
     * @param grafo
     * @param ciudades
     */
    public static void bajaDeCiudades(DigrafoEtiquetado grafo, TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        // Solicita al usuario el nombre de la ciudad a eliminar.
        System.out.println("Eliminar Ciudad");
        System.out.println("Ingrese el nombre de la ciudad a eliminar:");
        String nombre = sc.nextLine().trim();

        // Busca la ciudad en la tabla AVL.
        Ciudad tempCiudad = (Ciudad) ciudades.obtenerInformacion(nombre);
        if (tempCiudad != null) { // Verifica si la ciudad existe.
            grafo.eliminarVertice(tempCiudad.getNomenclatura());
            ciudades.eliminar(tempCiudad.getNombre());
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Se eliminó la ciudad " + nombre);
        } else {
            System.out.println("Ciudad no encontrada.");
        }
    }

    /**
     * Modifica el consumo de una ciudad existente.
     * 
     * @param ciudades
     */
    public static void modificarCiudades(TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        // Solicita al usuario el nombre de la ciudad a modificar.
        System.out.println("Modificar Ciudad");
        System.out.println("Ingrese el nombre de la ciudad a modificar:");
        String nombre = sc.nextLine().trim();

        Ciudad tempCiudad = (Ciudad) ciudades.obtenerInformacion(nombre);
        if (tempCiudad != null) { // Verifica si la ciudad existe.
            System.out.println("Ingrese el nuevo consumo de la ciudad:");
            double nuevoConsumo = sc.nextDouble();
            tempCiudad.setConsumo(nuevoConsumo);
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "Se modificó el consumo de la ciudad " + nombre + " (nuevo: " + nuevoConsumo + ")");
        } else {
            System.out.println("Ciudad no encontrada.");
        }
    }

    /*----------PUNTO 2----------*/

    /**
     * Crea y agrega al grafo una tuberia nueva.
     * <p>
     * NOTA: El estado no se valida.
     * <p>
     * La ciudad de origen y la ciudad de destino deben existir en el grafo.
     * 
     * @param grafo
     * @param tuberias
     * @param ciudades
     */
    public static void altaDeTuberia(DigrafoEtiquetado grafo, HashMap tuberias) {

        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;
        double caudalMinimo, caudalMaximo, diametro;
        // Solicita al usuario los datos de la tubería a agregar.
        System.out.println("Agregar Tubería");
        System.out.println("Ingrese el nombre de la ciudad origen");
        origen = sc.nextLine().trim();
        System.out.println("Ingrese el nombre de la ciudad destino");
        destino = sc.nextLine().trim();
        System.out.println("Ingrese el caudal mínimo");
        caudalMinimo = sc.nextDouble();
        System.out.println("Ingrese el caudal máximo");
        caudalMaximo = sc.nextDouble();
        System.out.println("Ingrese el diámetro");
        diametro = sc.nextDouble();
        System.out.println("Ingrese el estado");
        estado = sc.nextLine().trim();
        // Debe verificar que ya no exista una tubería entre estas ciudades
        ClaveTuberia clave = new ClaveTuberia(origen, destino);
        Tuberia tuberiaNueva = new Tuberia((origen + "-" + destino).trim(), caudalMinimo,
                caudalMaximo, diametro, estado.trim());
        if (tuberias.containsKey(clave)) {
            tuberias.put(clave, tuberiaNueva);
            grafo.insertarArco(origen, destino, caudalMaximo);
            System.out.println("Tubería agregada correctamente.");
            System.out.println(tuberiaNueva.toString());
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Se insertó la tubería " + clave);
        } else {
            System.out.println("No se pudo dar de alta la tuberia");
        }

    }

    /**
     * Quita una tuberia del grafo y del hashMap.
     * 
     * @param grafo
     * @param tuberias
     */
    public static void bajaDeTuberias(DigrafoEtiquetado grafo, HashMap tuberias) {
        Scanner sc = new Scanner(System.in);
        String origen, destino;
        // Solicita al usuario los datos de la tubería a eliminar.
        System.out.println("Eliminar Tubería");
        System.out.println("Ingrese el nombre de la ciudad origen");
        origen = sc.nextLine().trim();
        System.out.println("Ingrese el nombre de la ciudad destino");
        destino = sc.nextLine().trim();
        ClaveTuberia clave = new ClaveTuberia(origen, destino);

        if (tuberias.containsKey(clave)) { // Verifica si la tubería existe en el HashMap.
            tuberias.remove(clave);
            grafo.eliminarArco(origen, destino);
            System.out.println("Tubería eliminada correctamente.");
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt", "Se eliminó la tubería " + clave);
        } else {
            System.out.println("No se encontró la tubería con la clave especificada.");
        }
    }

    /**
     * Modifica el estado de una tubería existente.
     * <p>
     * NOTA: El estado no se valida, se asume que es un estado válido.1
     * 
     * @param grafo
     * @param tuberias
     */
    public static void modificarTuberias(DigrafoEtiquetado grafo, HashMap tuberias) {
        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;

        // Solicita al usuario los datos de la tubería a modificar.
        System.out.println("Modificar Tubería");
        System.out.println("Ingrese el nombre de la ciudad origen");
        origen = sc.nextLine().trim();
        System.out.println("Ingrese el nombre de la ciudad destino");
        destino = sc.nextLine().trim();
        ClaveTuberia clave = new ClaveTuberia(origen, destino);

        if (tuberias.containsKey(clave)) { // Verifica si la tubería existe en el HashMap.
            Tuberia tuberia = (Tuberia) tuberias.get(clave);
            System.out.println("Estado actual: " + tuberia.getEstado());
            System.out.println("Ingrese el nuevo estado:");

            // despues aclaremos formato de estados*************************************

            System.out.println("Estados posibles: (ACTIVO, REPARACION, DISENO, INACTIVO");
            estado = sc.nextLine().trim().toUpperCase();
            tuberia.setEstado(estado);
            System.out.println("Tubería modificada correctamente.");
            Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                    "Se modificó el estado de la tubería " + clave + " (ahora: " + estado + ")");
        } else {
            System.out.println("No se encontró la tubería con la clave especificada.");
        }
    }
    // decidimos que el caudal no se cambia
    /*----------PUNTO 3----------*/

    /**
     * Agrega la cantidad de habitantes a una ciudad para un mes y año dados.
     * 
     * @param ciudad
     */
    public static void altaDeInformacionCanHab(TablaAVL ciudad) {
        Scanner sc = new Scanner(System.in);
        int mes, anio, cant;
        String nombre;
        System.out.println("Ingrese la ciudad");
        nombre = sc.nextLine().trim();
        System.out.println("Ingrese el año");
        anio = sc.nextInt();

        Ciudad aux = (Ciudad) ciudad.obtenerInformacion(nombre);
        if (aux != null) { // Si la ciudad existe
            if (!aux.anioRegistrado(anio)) { // Si el año no está registrado, lo crea.
                for (mes = 1; mes <= 12; mes++) {// modifica tODO el año, aunque está hecho para alta, no
                                                 // modificaciones, así que está bien *****************
                    System.out.println("Ingrese la cantidad de habitantes para el mes " + mes);
                    cant = sc.nextInt();

                    if (aux.setHabitantesMes(anio, mes, cant)) { // Si se pudo registrar el mes
                        System.out.println("El mes se registró con éxito");
                    } else {
                        System.out.println("El mes no se pudo registrar");// siempre va a poder registrarse, hay que
                                                                          // quitarlo***************************
                    }
                }
                Archivo.log("C:\\Users\\JG\\Desktop\\txtTp\\log.txt",
                        "Se cargaron los datos de la cantidad de habitantes del año " + anio + " en la ciudad "
                                + nombre);
            } else {
                System.out.println("El año no es válido");
            }
        } else {
            System.out.println("La ciudad no existe");
        }
    }

    /*----------PUNTO 4 A----------*/

    /**
     * Muestra la cantidad de habitantes y el volumen de agua distribuido
     * <p>
     * NOTA: Las validaciones de mes y anio se hacen en el main.
     * 
     * @param ciudades
     */
    public static void habYVolDeUnaCiudad(TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String nombre;
        Ciudad aux;
        int anio, mes;
        System.out.println("ingrese el nombre de la ciudad");
        nombre = sc.nextLine().trim();
        aux = (Ciudad) ciudades.obtenerInformacion(nombre);
        System.out.println("ingrese el año");
        anio = sc.nextInt();

        if (!aux.anioRegistrado(anio)) { // Si el año no está registrado.
            System.out.println("El año ingresado no tiene datos ingresados");
        } else { // Si el año está registrado.
            System.out.println("Ingrese el mes");
            mes = sc.nextInt();
            if (!(mes > 0 && mes < 13)) { // Si el mes no está entre 1 y 12.
                System.out.println("Mes inválido");
            } else {
                /*
                 * Importante aclarar que no vuelve a pedir los datos solo se pregunta si se
                 * quiere
                 * volver a elegir otra opcion en el menú esta decisión es para no complicarnos
                 * tanto, se puede cambiar.
                 */
                System.out.println("Cantidad de habitantes:" + aux.getHabitantesMes(anio, mes));
                System.out.println("Consumo de agua:" + aux.consumoMensual(anio, mes));
            }
        }
    }

    /*----------PUNTO 4 B----------*/

    public static void ciudadesSegunVolyHab(TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        int minVol, maxVol, anio, mes;
        String minNomb, maxNomb;
        System.out.println("Ingrese el Nombre de la primer ciudad");
        minNomb = sc.nextLine();
        System.out.println("ingrese el nombre de la segunda ciudad");
        maxNomb = sc.nextLine();
        System.out.println("ingrese el volumen minimo");
        minVol = sc.nextInt();
        System.out.println("ingrese el volumen maximo");
        maxVol = sc.nextInt();
        System.out.println("ingrese el año");
        anio = sc.nextInt();
        System.out.println("ingrese el mes");// falta validar mes
        mes = sc.nextInt();
        System.out.println(consumoDeAguaMesYAño(ciudades.listarRango(minNomb, maxNomb), minVol,
                maxVol, anio, mes));
    }

    /**
     * NOTA: Como los volumenes de agua son muy altos, se podria modificar para que
     * sean un poco mas
     * chicos.
     * 
     * @param lis
     * @param minVol
     * @param maxVol
     * @param anio
     * @param mes
     * @return Lista de ciudades que cumplen con el rango de consumo de agua.
     *         <p>
     *         La lista se ordena por el consumo de agua mensual.
     */
    public static Lista consumoDeAguaMesYAño(Lista lis, int minVol, int maxVol, int anio, int mes) {
        Lista cumplen = new Lista();
        // Verifica que la lista contenga algo.
        if (lis != null && lis.longitud() > 0) {
            // Verifica que los volumenes ingresados sean correctos.
            if (minVol < maxVol) {
                // Verifica los meses dentro del estandar.
                if (mes > 0 && mes < 13) {// después quitamos ****************
                    int longLis = lis.longitud();
                    Ciudad aux;
                    double consumoAux;
                    for (int i = 1; i <= longLis; i++) {
                        try {
                            aux = (Ciudad) lis.recuperar(i);
                            if (aux != null) {
                                // Solo procesar si la ciudad tiene datos para ese año.

                                if (aux.anioRegistrado(anio)) {

                                    consumoAux = aux.consumoMensual(anio, mes);

                                    if (consumoAux > 0 && consumoAux > minVol
                                            && consumoAux < maxVol) {
                                        cumplen.insertar(aux, cumplen.longitud() + 1);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al acceder al elemento " + i);
                        }
                    }
                } else {
                    System.out.println("Mes inválido");// se puede verificar afuera************
                }
            } else {
                System.out.println("Rango de volúmenes inválido: minVol debe ser menor que maxVol");
            }
        } else {
            System.out.println("Lista vacía o nula");
        }
        return cumplen;
    }

    /*----------PUNTO 5 A----------*/

    /**
     * Busca el camino con menor caudal pleno entre dos ciudades.
     * <p>
     * Si el camino existe, muestra el camino y su estado.
     * 
     * @param grafo
     * @param tuberias
     * @param ciudades
     */
    public static void caminoMenorCaudal(DigrafoEtiquetado grafo, HashMap tuberias,
            TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;
        Lista camino = new Lista();

        System.out.println("Ingrese el nombre de la primera ciudad");
        origen = sc.nextLine().trim();

        System.out.println("Ingrese el nombre de la segunda ciudad");
        destino = sc.nextLine().trim();

        if (ciudades.obtenerInformacion(origen) != null
                && ciudades.obtenerInformacion(destino) != null && !origen.equals(destino)) {
            // Busca el camino con menor caudal pleno entre dos ciudades.
            camino = grafo.caminoMenorCaudalPleno(// *********************** chequear
                    ((Ciudad) ciudades.obtenerInformacion(origen)).getNomenclatura(),
                    ((Ciudad) ciudades.obtenerInformacion(destino)).getNomenclatura());

            if (!camino.esVacia() && camino.longitud() > 1) {
                // Verifica que el camino no esté vacío y tenga más de un nodo
                estado = verEstadoCam(tuberias, camino, ciudades);

                if (estado != null) { // Si se pudo verificar el estado del camino
                    // Muestra el camino y su estado.
                    System.out.println("El camino con menor caudal Pleno es:");
                    System.out.println(camino.toString());
                    System.out.println("Su estado es: " + estado);
                } else { // Si hubo un error al verificar el estado del camino.
                    System.out.println("Error al verificar el estado del camino");
                }
            } else { // Si el camino no existe o es inválido.
                System.out.println("Ese camino no existe");
            }
        } else { // Si las ciudades origen o destino no existen o son iguales.
            if (ciudades.obtenerInformacion(origen) == null) { // Si la ciudad origen no existe.
                System.out.println("La ciudad origen '" + origen + "' no existe");
                // Si la ciudad destino no existe.
            } else if (ciudades.obtenerInformacion(destino) == null) {
                System.out.println("La ciudad destino '" + destino + "' no existe");
            } else if (origen.equals(destino)) { // Si las ciudades origen y destino son iguales.
                System.out.println("Las ciudades origen y destino son la misma");
            }
        }
    }

    /*----------PUNTO 5 B----------*/

    /**
     * Busca el camino con la menor cantidad de ciudades entre dos ciudades.
     * <p>
     * Si el camino existe, muestra el camino y su estado.
     * 
     * @param grafo
     * @param tuberias
     * @param ciudades
     */
    public static void caminoMenosCiudades(DigrafoEtiquetado grafo, HashMap tuberias,
            TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;
        Lista camino = new Lista();

        System.out.println("Ingrese el nombre de la primera ciudad");
        origen = sc.nextLine().trim();

        System.out.println("Ingrese el nombre de la segunda ciudad");
        destino = sc.nextLine().trim();

        if (ciudades.obtenerInformacion(origen) != null
                && ciudades.obtenerInformacion(destino) != null && !origen.equals(destino)) {
            // Verifica que las ciudades existan y no sean iguales.
            // Busca el camino con la menor cantidad de ciudades entre dos ciudades.
            // Utiliza el método caminoMasCorto del grafo.
            camino = grafo.caminoMasCorto(
                    ((Ciudad) ciudades.obtenerInformacion(origen)).getNomenclatura(),
                    ((Ciudad) ciudades.obtenerInformacion(destino)).getNomenclatura());

            if (!camino.esVacia() && camino.longitud() > 1) {
                // Verifica que el camino no esté vacío y tenga más de un nodo.
                estado = verEstadoCam(tuberias, camino, ciudades);

                if (estado != null) { // Si se pudo verificar el estado del camino.
                    System.out.println("El camino con menor cantidad de ciudades es:");
                    System.out.println(camino.toString());
                    System.out.println("Su estado es: " + estado);
                } else {
                    System.out.println("Error al verificar el estado del camino");
                }
            } else { // Si el camino no existe o es inválido.
                System.out.println("Ese camino no existe");
            }
        } else { // Si las ciudades origen o destino no existen o son iguales.
            if (ciudades.obtenerInformacion(origen) == null) { // Si la ciudad origen no existe.
                System.out.println("La ciudad origen '" + origen + "' no existe");
                // Si la ciudad destino no existe.
            } else if (ciudades.obtenerInformacion(destino) == null) {
                System.out.println("La ciudad destino '" + destino + "' no existe");
            } else if (origen.equals(destino)) { // Si las ciudades origen y destino son iguales.
                System.out.println("Las ciudades origen y destino son la misma");
            }
        }
    }

    /**
     * Verifica el estado de un camino en función de las tuberías que lo componen.
     * 
     * @param tuberias
     * @param camino
     * @param ciudades
     * @return Estado del camino según las tuberías.
     */
    public static String verEstadoCam(HashMap<ClaveTuberia, Tuberia> tuberias, Lista camino,
            TablaAVL ciudades) {
        String estado = "ACTIVO";
        boolean error = false; // ?
        int i = 1, j = 2;

        while (!(estado.equals("DISENO")) && j <= camino.longitud() && !error) {
            /* [DISEÑO > // INACTIVO > REPARACIÓN > // ACTIVO]. */

            String origen = (String) camino.recuperar(i);
            String destino = (String) camino.recuperar(j);

            // Buscar tubería.
            ClaveTuberia clave = new ClaveTuberia(origen, destino);
            Tuberia tuberia = tuberias.get(clave);

            if (tuberia != null) {
                String nuevoEstado = tuberia.getEstado();
                if (prioridad(estado) < prioridad(nuevoEstado)) {
                    estado = nuevoEstado;
                }
            } else {
                // Si no encuentra la tubería, retornar error.
                error = true;
            }
            i++;
            j++;
        }
        if (error) {
            estado = "ERROR: TUBERIA NO ENCONTRADA";
        }

        return estado;
    }

    private static int prioridad(String estado) {
        int peso = 0; // declaro 0 solo para poder retornar, se supone que siempre va a entrar un
                      // etado valido
        switch (estado) { // IMPORTANTE ASEGURARSE QUE ESTOS SEAN LOS STRINGS QUE SE GUARDAN EN LAS
                          // TUBERIAS
            case "ACTIVO":
                peso = 1;
                break;
            case "REPARACION":
                peso = 2;
                break;
            case "INACTIVO":
                peso = 3;
                break;
            case "DISENO":
                peso = 4;
                break;
        }
        return peso;
    }

    /*----------PUNTO 6----------*/

    /**
     * Genera un listado de ciudades ordenadas por consumo anual.
     * <p>
     * Utiliza un heap para ordenar las ciudades por su consumo anual.
     * 
     * @param anio
     * @param ciudades
     * @return Listado de ciudades ordenadas por consumo anual.
     *         <p>
     *         Si una ciudad no tiene datos registrados en el año especificado, no
     *         se incluye en el listado.
     */
    public static String consumoAnual(int anio, TablaAVL ciudades) {
        // Heap para cubrir los casos en los que mas de 1 ciudad tenga el mismo consumo.
        TablaHeapMax heap = new TablaHeapMax();
        // Lista las ciudades originales para ir calculando el consumo anual.
        Lista listaC = ciudades.listarDatos();

        // Recorre la lista de ciudades y calcula el consumo anual.
        for (int i = 1; i <= listaC.longitud(); i++) {
            Ciudad ciudad = (Ciudad) listaC.recuperar(i);
            if (ciudad.anioRegistrado(anio)) {
                /*
                 * NOTA: Debatir si se debe considerar que las ciudades que no tengan registrado
                 * datos en este año directamente no se insertan.
                 */
                double consumo = ciudad.consumoAnual(anio);
                // Inserta la ciudad en el heap, ordenada por consumo anual.
                heap.insertar(consumo, ciudad);
            } // tener en cuenta un else que muestre la ciudad sin año registrado que muestre
              // 0
        }
        StringBuilder sb = new StringBuilder("[");// String builder trabaja más eficiente que las
                                                      // concatenaciones "+" que deben crear un nuevo
                                                      // objeto y copiar el anterior String caracter a
                                                      // caracter
        while (!heap.esVacio()) {
            sb.append(heap.eliminarCima().toString()).append(" -> ");//va pasando el string de la cima y la elimina
        }
        if (sb.length() >= 4) {
            sb.setLength(sb.length() - 4); // elimina el último " -> ", si la lista está vacía se imprime "[]"
        }
        sb.append("]");

        return sb.toString();
    }

    /*----------PUNTO 7----------*/

    /**
     * Muestra las estructuras del sistema.
     * <p>
     * Muestra la estructura del grafo, la tabla AVL de ciudades y el HashMap de
     * tuberías.
     * 
     * @param tuberias
     * @param grafo
     * @param ciudades
     */
    public static void mostrarEstructuras(HashMap tuberias, DigrafoEtiquetado grafo,
            TablaAVL ciudades) {
        System.out.println("Estructura del grafo:");
        System.out.println(grafo.toString());
        System.out.println(" \n");
        System.out.println("Estructura de la tablaAVL:");
        System.out.println(ciudades.toString());
        System.out.println(" \n");
        System.out.println("Estructura del hashMap:");
        System.out.println(tuberias.toString());

    }
    /*----------PUNTO 7 FIN----------*/

    /*----------CARGA DE ARCHIVOS----------*/

    /**
     * Carga la cantidad de habitantes de cada ciudad desde un archivo.
     * <p>
     * El archivo debe tener el formato: "Ciudad;Año;Mes1;Mes2;...;Mes12".
     * 
     * @param rutaArchivo
     * @param ciudades
     */
    public static void cargarHabCiudad(String rutaArchivo, TablaAVL ciudades) {
        FileReader archivo; // Para abrir el archivo.
        BufferedReader lector; // Para leer el archivo línea por línea.
        int i;// contador para los meses
        int j = 0; // Índice base para acceder a los campos del arreglo `info`.
        Ciudad aux;
        try {
            archivo = new FileReader(rutaArchivo); // Intenta abrir el archivo.
            if (archivo.ready()) { // Verifica que esté listo para ser leído.
                lector = new BufferedReader(archivo); // Crea el lector de líneas.
                String cadena;

                // Lee cada línea del archivo
                while ((cadena = lector.readLine()) != null) {
                    // Divide la línea por el carácter ';' y obtiene los datos como arreglo.
                    String[] info = cadena.split(";");
                    aux = (Ciudad) ciudades.obtenerInformacion(info[j].trim());
                    int mes = 1;
                    for (i = 2; i < info.length; i++) {
                        aux.setHabitantesMes(Integer.parseInt(info[1]), mes,
                                Integer.parseInt(info[i]));
                        mes++;
                    }
                }
            } else {
                System.out.println("El archivo no esta listo");
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("el archivo no esa listo");
        }
    }
}
