
package practicainteligente;

public class Sucesor {
    private accion a;
    private Estado e;
    private int coste;

    public Sucesor(accion a, Estado e, int coste) {
        this.a = a;
        this.e = e;
        this.coste = coste;
    }

    public accion getA() {
        return a;
    }

    public Estado getE() {
        return e;
    }

    public int getCoste() {
        return coste;
    }

    public void setA(accion a) {
        this.a = a;
    }

    public void setE(Estado e) {
        this.e = e;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    @Override
    public String toString() {
        return "Accion: " + a.toString() + "\n" + e.toString() + "Coste: " + getCoste() +"\n";
    }
    
    
}
