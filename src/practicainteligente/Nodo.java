package practicainteligente;

import java.util.Random;

public class Nodo implements Comparable<Nodo>{
    private Nodo padre;
    private Estado e;
    private accion a;
    private int costo;    
    private int profundidad;
    private int valor;
    private int heuristica;
    
    public Nodo(Nodo padre, Estado e, accion a,int costo, int profundidad, int valor) {
        this.padre = padre;
        this.e = e;
        this.a = a;
        this.costo = costo;        
        this.profundidad = profundidad;        
        this.valor = valor;  
    }

    public Nodo () {
        Random rand = new Random();
        this.valor = rand.nextInt(3000)+1;  
    }
    
    public Nodo getPadre() {
        return padre;
    }

    public Estado getE() {
        return e;
    }

    public int getCosto() {
        return costo;
    }

    public accion getA() {
        return a;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public int getValor() {
        return valor;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setE(Estado e) {
        this.e = e;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setA(accion a) {
        this.a = a;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Nodo{" + "padre=" + padre + ", e=" + e + ", costo=" + costo + ", a=" + a + ", profundidad=" + profundidad + ", valor=" + valor + '}';
    }

       
    public int compareTo(Nodo e) {
        int r = 0;
        if (e.getValor()< getValor())
            r = +1;
        else
        if (e.getValor() > getValor())
            r = -1;
        return r;
    }

    int getHeuristica(Estado e) {
        heuristica = 0;
        Casilla[][] c = e.getCasillas();
        for (int i=0; i<c.length; i++){
            for (int j=0; j<c[0].length; j++){
                if(c[i][j].getCantidad()!=e.getK())
                    heuristica++;
            }
        }
        
        return heuristica;
    }
}
