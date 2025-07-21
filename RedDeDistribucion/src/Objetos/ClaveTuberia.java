package Objetos;
public class ClaveTuberia {
    private String origen;
    private String destino;

    public ClaveTuberia(String origen, String destino){
        this.origen=origen;
        this.destino=destino;
    }

    public boolean equals(ClaveTuberia otra){
        return((this.origen.equals(otra.origen))&& (this.destino.equals(otra.destino)));
    }
    @Override
    public int hashCode(){
        int i, suma=0;
        for(i=0; i<origen.length();i++){
            suma+= (int) origen.charAt(i);
        }
        for(i=0; i<destino.length();i++){
            suma+= (int) destino.charAt(i);
        }
        return suma;
    }

}
