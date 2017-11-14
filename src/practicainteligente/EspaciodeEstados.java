package practicainteligente;

import practicainteligente.Sucesor;
import java.util.ArrayList;

public class EspaciodeEstados {
 
    public ArrayList<Sucesor> sucesor (Estado e) {
        ArrayList<Sucesor> sucesores=new ArrayList<Sucesor>();
        ArrayList<accion> acciones=new ArrayList<accion>(e.getAcciones());        
        for (int i=0;i<acciones.size();i++) {         
            sucesores.add(new Sucesor(acciones.get(i),e.getEstado(acciones.get(i)),e.Costo(acciones.get(i))));
        }
        return sucesores;       
    }
}
