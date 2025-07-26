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
     * ACLARACION: El casteo de String a Int se hace al momento de bootear desde
     * el txt.
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

    //metodo agregado para prueba de codigo
    public String verHab(int anio) {
        String mostrar = "Anio:";
        for (int i = 0; i < 12; i++) {
            mostrar += "mes " + i + ": ";
            mostrar += ((habitantesHistoricos.get(anio)[i]));
            mostrar += "\n";
        }

        return mostrar;
    }

    public boolean anioRegistrado(int anio) {
        return habitantesHistoricos.containsKey(anio); //verifica que haya una clave con este año (si se ha registrado información sobre este año)
    }

    public boolean equals(Ciudad otra) {
        return (this.nomenclatura.equals(otra.nomenclatura));

    }

    //este metodo nos sera de utilidad para el punto 4.
    public double consumoMensual(int anio, int mes) {
        //teniendo en cuenta que la cantidad de habitantes es en 1 mes
        //y que las profes dijeron que podemos usar una cantidad de dias estandar para todos los meses
        //tomo en cuenta en este caso eso y uso 30.
        double consumoMensual = 0;

        // Verificar primero si el año está registrado
        if (this.anioRegistrado(anio)) {
            if (mes > 0 && mes < 13) { // Mismo control de mes
                int[] arregloAnual = habitantesHistoricos.get(anio);
                int habAux = arregloAnual[mes - 1];

                if (habAux > 0 && consumoPromedio > 0) {
                    consumoMensual = (habAux * consumoPromedio) * 30;
                }
            }
            // Si el mes es inválido, consumoMensual queda en 0
        }
        // Si el año no está registrado, consumoMensual queda en 0

        return consumoMensual;
    }

    public double consumoAnual(int anio) {
        double consumoAnual = 0;
        int[] arregloAnual = habitantesHistoricos.get(anio);//validacion del año en el main!!!
        for (int i = 0; i < 12; i++) {//asumo que no hay año en el que no se haya registrado la cantidad de habitantes en algún mes
            consumoAnual += arregloAnual[i] * consumoPromedio;
        }
        return consumoAnual;
    }
}
