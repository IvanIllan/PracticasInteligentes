package practicainteligente;

public class Casilla {
    private int fila;
    private int columna;
    private int cantidad;
    //private boolean visitada;

     public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;       
    }
     
    public Casilla(int fila, int columna, int cantidad) {
        this.fila = fila;
        this.columna = columna;
        this.cantidad = cantidad;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Casilla{" + "fila=" + fila + ", columna=" + columna + ", cantidad=" + cantidad + '}';
    }
    
    
}
