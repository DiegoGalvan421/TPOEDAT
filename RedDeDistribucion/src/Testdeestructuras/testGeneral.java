package Testdeestructuras;

import Estructuras.*;
import EstructurasAuxiliares.Lista;
import Objetos.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class testGeneral {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DigrafoEtiquetado grafo = new DigrafoEtiquetado();
        TablaAVL ciudades = Archivo.cargarCiudades("C:\\Users\\HOLA\\Desktop\\Ciudades.txt", grafo);
        HashMap<ClaveTuberia, Tuberia> Tuberias = Archivo.cargarTuberias("C:\\Users\\HOLA\\Desktop\\Tuberias.txt", grafo);
        System.out.println(ciudades.toString());
        System.out.println(Tuberias.toString());
        System.out.println(grafo.toString());
        cargarHabCiudad("C:\\Users\\HOLA\\Desktop\\Habitantes historicos.txt", ciudades);
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
                            System.out.println(":p");
                            break;
                        case 2:
                            System.out.println(":p");
                            break;
                        case 3:
                            System.out.println(":p");
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
                            System.out.println(":p");
                            break;
                        case 2:
                            System.out.println(":p");
                            break;
                        case 3:
                            System.out.println(":p");
                            break;

                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Alta de información de la cantidad de habitantes para año dado y ciudad dada");
                    // llamado al modulo
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
                    System.out.println("Listado de ciudades ordenadas por consumo");
                    // llamada al modulo
                    break;
                case 7:
                    System.out.println("Mostrar Todas las estructuras");
                    mostrarEstructuras(Tuberias, grafo, ciudades);
                    break;
                case 8:
                    System.out.println("Hasta luego ;)");
                    System.out.println();
                    // deja de compilar/detiene el programa
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción inválida");
                    break;
            }
            System.out.println("¿Desea continuar? S/N");
            sigue = sc.nextLine().charAt(0);

        } while (sigue == 's' || sigue == 'S');
        System.out.println("Hasta luego ;)");

    }

    // separamos los ejercicios en modulos para que se entienda mejor, despues se
    // modificaran si no se quieren asi.
    //punto 5a
    public static void caminoMenorCaudal(DigrafoEtiquetado grafo, HashMap tuberias, TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;
        Lista camino = new Lista();

        System.out.println("ingrese el nombre de la primera ciudad");
        origen = sc.nextLine().trim();

        System.out.println("ingrese el nombre de la segunda ciudad");
        destino = sc.nextLine().trim();

        if (ciudades.obtenerInformacion(origen) != null && ciudades.obtenerInformacion(destino) != null && !origen.equals(destino)) {
            camino = grafo.caminoMasChico(((Ciudad) ciudades.obtenerInformacion(origen)).getNomenclatura(), ((Ciudad) ciudades.obtenerInformacion(destino)).getNomenclatura());

            if (!camino.esVacia() && camino.longitud() > 1) {
                estado = verEstadoCam(tuberias, camino, ciudades);

                if (estado != null) {
                    System.out.println("El camino con menor caudal Pleno es:");
                    System.out.println(camino.toString());
                    System.out.println("su estado es: " + estado);
                } else {
                    System.out.println("Error al verificar el estado del camino");
                }
            } else {
                System.out.println("ese camino no existe");
            }
        } else {
            if (ciudades.obtenerInformacion(origen) == null) {
                System.out.println("La ciudad origen '" + origen + "' no existe");
            } else if (ciudades.obtenerInformacion(destino) == null) {
                System.out.println("La ciudad destino '" + destino + "' no existe");
            } else if (origen.equals(destino)) {
                System.out.println("Las ciudades origen y destino son la misma");
            }
        }
    }

    //punto 5b
    public static void caminoMenosCiudades(DigrafoEtiquetado grafo, HashMap tuberias, TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String origen, destino, estado;
        Lista camino = new Lista();

        System.out.println("ingrese el nombre de la primera ciudad");
        origen = sc.nextLine().trim();

        System.out.println("ingrese el nombre de la segunda ciudad");
        destino = sc.nextLine().trim();

        if (ciudades.obtenerInformacion(origen) != null && ciudades.obtenerInformacion(destino) != null && !origen.equals(destino)) {
            camino = grafo.caminoMasCorto(((Ciudad) ciudades.obtenerInformacion(origen)).getNomenclatura(), ((Ciudad) ciudades.obtenerInformacion(destino)).getNomenclatura());

            if (!camino.esVacia() && camino.longitud() > 1) {
                estado = verEstadoCam(tuberias, camino, ciudades);

                if (estado != null) {
                    System.out.println("El camino con menor cantidad de ciudades es:");
                    System.out.println(camino.toString());
                    System.out.println("su estado es: " + estado);
                } else {
                    System.out.println("Error al verificar el estado del camino");
                }
            } else {
                System.out.println("ese camino no existe");
            }
        } else {
            if (ciudades.obtenerInformacion(origen) == null) {
                System.out.println("La ciudad origen '" + origen + "' no existe");
            } else if (ciudades.obtenerInformacion(destino) == null) {
                System.out.println("La ciudad destino '" + destino + "' no existe");
            } else if (origen.equals(destino)) {
                System.out.println("Las ciudades origen y destino son la misma");
            }
        }
    }

    //ya se hizo el punto 5, faltaria que considere, cuando hay mas de un estado
    //es decir, diseño-reparacion,reparacion-inactivo y asi.
    public static String verEstadoCam(HashMap<ClaveTuberia, Tuberia> tuberias, Lista camino, TablaAVL ciudades) {
        String estado = "ACTIVO";
        int i = 1, j = 2;

        while (estado.equals("ACTIVO") && j <= camino.longitud()) {
            String origen = (String) camino.recuperar(i);
            String destino = (String) camino.recuperar(j);

            // Buscar tubería
            ClaveTuberia clave = new ClaveTuberia(origen, destino);
            Tuberia tuberia = tuberias.get(clave);

            if (tuberia != null) {
                estado = tuberia.getEstado();
            } else {
                // Si no encuentra la tubería, retornar error
                estado = "ERROR: TUBERIA NO ENCONTRADA";
            }

            i++;
            j++;
        }

        return estado;
    }
    // punto 7

    public static void mostrarEstructuras(HashMap tuberias, DigrafoEtiquetado grafo, TablaAVL ciudades) {
        System.out.println("Estructura del grafo:");
        System.out.println(grafo.toString());
        System.out.println(" \n");
        System.out.println("Estructura de la tablaAVL:");
        System.out.println(ciudades.toString());
        System.out.println(" \n");
        System.out.println("Estructura del hashMap:");
        System.out.println(tuberias.toString());

    }

    // ejercicio 4a
    public static void habYVolDeUnaCiudad(TablaAVL ciudades) {
        Scanner sc = new Scanner(System.in);
        String nombre;
        Ciudad aux;
        int anio, mes;
        System.out.println("ingrese el nombre de la ciudad");
        nombre = sc.nextLine();
        aux = (Ciudad) ciudades.obtenerInformacion(nombre);
        System.out.println("ingrese el año");
        anio = sc.nextInt();
        if (!aux.anioRegistrado(anio)) {
            System.out.println("El año ingresado no tiene datos ingresados");
        } else {
            System.out.println("ingrese el mes");
            mes = sc.nextInt();
            if (!(mes > 0 && mes < 13)) { //si el mes no está entre 1 y 12
                System.out.println("mes inválido");
            } else {
                /* 
                 *importante aclarar que no vuelve a pedir los datos 
                 *solo se pregunta si se quiere volver a elegir otra opcion en el menú 
                 *esta decisión es para no complicarnos tanto, se puede cambiar 
                 */
                System.out.println("Cantidad de habitantes:" + aux.getHabitantesMes(anio, mes));
                System.out.println("Consumo de agua:" + aux.consumoMensual(anio, mes));
            }
        }
    }

    // ejercicio 4b
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
        System.out.println("ingrese el mes");
        mes = sc.nextInt();
        System.out.println(consumoDeAguaMesYAño(ciudades.listarRango(minNomb, maxNomb), minVol, maxVol, anio, mes));
    }

    //los volumenes son bastante altos, podriamos modificarlos para que sean un poco mas chicos
    public static Lista consumoDeAguaMesYAño(Lista lis, int minVol, int maxVol, int anio, int mes) {
        Lista cumplen = new Lista();
        //verifica que la lista contenga algo
        if (lis != null && lis.longitud() > 0) {
            //verifica que los volumenes ingresados sean correctos
            if (minVol < maxVol) {
                //verifica los meses dentro del estandar
                if (mes > 0 && mes < 13) {
                    int longLis = lis.longitud();
                    Ciudad aux;
                    double consumoAux;
                    for (int i = 1; i <= longLis; i++) {
                        try {
                            aux = (Ciudad) lis.recuperar(i);
                            if (aux != null) {
                                // Solo procesar si la ciudad tiene datos para ese año

                                if (aux.anioRegistrado(anio)) {
                                    /* 
                                    esto es solo una operacion para chequear algo
                                    System.out.println("Ciudad: " + aux.getNombre());
                                    System.out.println("Habitantes sept 2021: " + aux.getHabitantesMes(2021, 9));
                                    System.out.println("Consumo promedio: " + aux.getConsumo());
                                    double consumo = aux.consumoMensual(2021, 9);
                                    System.out.println("Consumo calculado: " + consumo);
                                    System.out.println("¿Entra en rango " + minVol + "-" + maxVol + "? " + (consumo > minVol && consumo < maxVol));
                                    System.out.println("---"); */
                                    consumoAux = aux.consumoMensual(anio, mes);

                                    if (consumoAux > 0 && consumoAux > minVol && consumoAux < maxVol) {
                                        cumplen.insertar(aux, cumplen.longitud() + 1);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al acceder al elemento " + i);
                        }
                    }
                } else {
                    System.out.println("mes inválido");
                }
            } else {
                System.out.println("Rango de volúmenes inválido: minVol debe ser menor que maxVol");
            }
        } else {
            System.out.println("Lista vacía o nula");
        }
        return cumplen;
    }

    // para el punto 7, listado de ciudades ordenadas por consumo de agua anual
    public static String consumoAnual(int anio, TablaAVL ciudades) {
        TablaAVL nuevaTabla = new TablaAVL();
        Lista listaC = ciudades.listarDatos(); // Listo las ciudades originales para ir calculando el consumo anual
        for (int i = 1; i <= listaC.longitud(); i++) {
            Ciudad ciudad = (Ciudad) listaC.recuperar(i);
            double consumo = ciudad.consumoAnual(anio); // el año se controla en el main
            nuevaTabla.insertar(consumo, ciudad); // usa como clave el consumo y se ordena comparando este
        }
        String listado = nuevaTabla.toStringDeMayorAMenor();
        return listado;
    }

    // carga de habitantes historicos por ciudad
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
                        aux.setHabitantesMes(Integer.parseInt(info[1]), mes, Integer.parseInt(info[i]));
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
