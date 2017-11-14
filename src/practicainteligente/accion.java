package allanarterreno;

import java.util.ArrayList;

public class accion {
    Casilla movimiento;
    Casilla [] adyacentes;
    int [] unidades;
    
    public accion (Casilla movimiento,Casilla [] adyacentes, int [] unidades) {
        this.movimiento=movimiento;
        this.adyacentes=new Casilla [adyacentes.length];
        System.arraycopy(adyacentes, 0, this.adyacentes, 0, adyacentes.length);
        this.unidades=new int[unidades.length];
        System.arraycopy(unidades, 0, this.unidades, 0, unidades.length);                
    }

    public Casilla getMovimiento() {
        return movimiento;
    }

    public Casilla[] getAdyacentes() {
        return adyacentes;
    }

    public int[] getUnidades() {
        return unidades;
    }
    
    public int cantidadRepartir() {
        int total=0;
        for (int i=0;i<getUnidades().length;i++)
            total+=unidades[i];
        return total;
    }

    @Override
    public String toString() {
        String cadena="(("+movimiento.getFila()+","+movimiento.getColumna()+"), [";
        for (int j=0;j<adyacentes.length;j++)                 
            cadena+="("+unidades[j]+",("+adyacentes[j].getFila()+","+adyacentes[j].getColumna()+")),";               
                
        cadena+="],"+ cantidadRepartir()+")";
        
        return cadena;
    }
}