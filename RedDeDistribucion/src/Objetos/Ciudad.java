package Objetos;

import java.util.HashMap;

public class Ciudad {
    private String nombre;
    private HashMap<Integer, int[]> habitantesHistoricos;
    private String nomenclatura;
    private double superficie;
    private double consumoPromedio;

    /**
     * Constructor.
     * 
     * @param unNombre
     * @param unaNom
     * @param unaSup
     * @param unConsumo
     */
    public Ciudad(String unNombre, String unaNom, double unaSup, double unConsumo) {
        nombre = unNombre;
        nomenclatura = unaNom;
        superficie = unaSup;
        consumoPromedio = unConsumo;
        habitantesHistoricos = new HashMap<>();
    }

    /* OBSERVADORAS */

    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene los habitantes segun un año y un mes.
     * <p>
     * NOTA: Las valiadciones de mes y anio se hacen en el main.
     * <p>
     * ACLARACION: El casteo de String a Int se hace al momento de bootear desde el txt.
     * 
     * @param anio
     * @param mes
     * @return cantHabitantes
     */
    public int getHabitantesMes(int anio, int mes) {
        return habitantesHistoricos.get(anio)[mes - 1];
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public double getSuperficie() {
        return superficie;
    }

    public double getConsumo() {
        return consumoPromedio;
    }

    /* MODIFICADORAS */

    /**
     * Agrega o Modifica la cantidad de habitantes del mes y el año dado.
     * <p>
     * NOTA: Las validaciones de mes y anio se hacen en el main.
     * 
     * @param anio
     * @param mes
     * @param cantidad
     * @return true
     */
    public boolean setHabitantesMes(int anio, int mes, int cantidad) {
        int[] arregloAnual = habitantesHistoricos.get(anio);

        if (arregloAnual == null) {
            // Si no existe lo crea.
            arregloAnual = new int[12];
            // Lo agrega a la tabla.
            habitantesHistoricos.put(anio, arregloAnual);
        }

        arregloAnual[mes - 1] = cantidad; // Sobrescribe valor del mes.

        return true;
    }

    public void setConsumo(double unConsumo) {
        consumoPromedio = unConsumo;
    }

    @Override
    public String toString() {
        return "\nCiudad: " + nombre + "\nNomenclatura: " + nomenclatura + "\nSuperficie: "
                + superficie + "\nConsumo Promedio: " + consumoPromedio + "\n";
    }
    public boolean equals(Ciudad otra){
        return (this.nomenclatura.equals(otra.nomenclatura));

    }
    public double consumoAnual (int anio){
        double consumoAnual = 0; 
        int [] arregloAnual = habitantesHistoricos.get(anio);//validacion del año en el main!!!
        for (int i = 0; i < 12; i++){//asumo que no hay año en el que no se haya registrado la cantidad de habitantes en algún més
            consumoAnual += arregloAnual[i] * consumoPromedio;
        }
        return consumoAnual;
    }
}
