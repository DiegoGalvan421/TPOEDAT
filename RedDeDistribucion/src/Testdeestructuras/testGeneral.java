package Testdeestructuras;

import Estructuras.*;
import EstructurasAuxiliares.Lista;
import Objetos.*;
import Archivo;
import java.util.HashMap;
import java.util.Scanner;

public class testGeneral {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DigrafoEtiquetado grafo = new DigrafoEtiquetado();
        TablaAVL ciudades = new TablaAVL();
        HashMap<ClaveTuberia, Tuberia> Tuberias = new HashMap<>();
        int opcion;
        Character sigue='s';

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
                    opcion= sc.nextInt();
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
                    opcion= sc.nextInt();
                    sc.nextLine(); //limpia el escaner
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
                    //llamado al modulo
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
                            //llamado al modulo
                            System.out.println(":p");
                            break;
                        case 2:
                            //llamado al modulo
                            System.out.println(":p");
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
                    opcion=sc.nextInt();
                     sc.nextLine(); //limpia el escaner
                    switch (opcion) {
                        case 1:
                            //llamada al módulo
                            System.out.println(":p");
                            break;

                        case 2:
                            //llamada al módulo
                            System.out.println(":p");
                            break;
                    
                        default:
                            System.out.println("Opcion invalida");
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Listado de ciudades ordenadas por consumo");
                    //llamada al modulo
                    break;
                case 7:
                    System.out.println("Mostrar Todas las estructuras");
                    //llamada al modulo
                    break;

                case 8:
                
                    System.out.println("Hasta luego ;)");
                    System.out.println();
                    //deja de compilar/detiene el programa
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción inválida");
                    break;
            }
            System.out.println("¿Desea continuar? S/N");
            sigue = sc.nextLine().charAt(0);
            
    
        } while (sigue =='s' || sigue == 'S');

    }

    public static Lista consumoDeAguaMesYAño(Lista lis, int minVol, int maxVol, int anio, int mes) {
        Lista cumplen = new Lista();
        int longLis = lis.longitud();
        Ciudad aux;
        double consumoAux;
        for (int i = 1; i < longLis; i++) {
            aux = (Ciudad) lis.recuperar(i);
            consumoAux = aux.consumoMensual(anio, mes);
            if (consumoAux > minVol && consumoAux < maxVol) {
                cumplen.insertar(aux, 1);
            }
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
}
