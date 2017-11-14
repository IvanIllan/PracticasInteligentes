package practicainteligente;

public class Problema {
    EspaciodeEstados espaciodeestados;
    Estado estadoinicial;

    public Problema(EspaciodeEstados espaciodeestados, Estado estadoinicial) {
        this.espaciodeestados = espaciodeestados;
        this.estadoinicial = estadoinicial;
    }

    public boolean Objetivo(Estado e) {
        return e.testObjetivo();
    }
    
    public EspaciodeEstados getEspaciodeestados() {
        return espaciodeestados;
    }

    public Estado getEstadoinicial() {
        return estadoinicial;
    }

    public void setEspaciodeestados(EspaciodeEstados espaciodeestados) {
        this.espaciodeestados = espaciodeestados;
    }

    public void setEstadoinicial(Estado estadoinicial) {
        this.estadoinicial = estadoinicial;
    }

    
}
