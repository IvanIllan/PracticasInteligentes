
package practicainteligente;
import java.util.PriorityQueue;

public class Frontera {
    PriorityQueue<Nodo> q;

    public Frontera() {
        q = new PriorityQueue();
    }
    
    public void CrearFrontera() {
        q.clear();
    }
    
    public void Insertar (Nodo e) {
        PriorityQueue<Nodo> qaux=new PriorityQueue();
        qaux.clear();
        boolean encontrado=false;
        Nodo aux=null;
        while (!q.isEmpty()) {            
            aux=(Nodo) q.poll();
            qaux.add(aux);
            if (e.getE().equals(aux.getE()) && e.getValor()>aux.getValor()) {
                encontrado=true;
            }
        }        
        if (!encontrado)           
            qaux.add(e);
        q.clear();
        while (!qaux.isEmpty())
            q.add(qaux.poll());
          q.add(e);
    }
    
    public Nodo Elimina() {
        return (Nodo) q.poll();
    }
    
    public boolean EsVacia() {
        return q.isEmpty();
    }
            
}
