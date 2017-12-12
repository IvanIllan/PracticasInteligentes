
package practicainteligente;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Frontera {
    PriorityQueue<Nodo> q;
    Map <String, Integer> estados;

    public Frontera() {
        q = new PriorityQueue();
        estados = new HashMap();
    }
    
    public void CrearFrontera() {
        q.clear();
    }
    
    public void Insertar (Nodo e) {
        // esto funciona mal? Es que en alguna estrategia funciona bien el de abajo, o este, pero en otras no
    /*    PriorityQueue<Nodo> qaux=new PriorityQueue();
        qaux.clear();
        boolean encontrado=false;
        Nodo aux=null;
        while (!q.isEmpty()) {    
            System.out.println("size de la cola " + q.size());
            aux=(Nodo) q.poll();
            qaux.add(aux);
            System.out.println("estamos en " + e.getE().toString());
            System.out.println("vamos a comprobar que getE es igual a aux getE ");
            // Si encontramos un nodo que es exactamente igual y encima este nodo tiene un valor mayor(es peor) pues entonces
            // no lo añadimos a la cola
            System.out.println(aux.getE());
            if (e.getE().isEquals(aux.getE()) && e.getValor()>aux.getValor()) {
                encontrado=true;
                System.out.println("iguales");
            }
        }        
        if (!encontrado)           
            qaux.add(e);
        q.clear();
        while (!qaux.isEmpty())
            q.add(qaux.poll());
        //System.out.println("SALE");
        */
        //q.add(e);
        
        
        
       /* 
          Este metodo tiene hash map 
        */
          
        
        String n;
        n=Estado.getMD5(e.getE().toString());
        
        if (estados.containsKey(n)) {// si el estado esta en el mapa
               
            
            // si en la lista anterior encontramos un valor MAYOR que el valor actual
            // significa que el anterior es PEOR, este nuevo valor es mejor, por lo que
            // se reemplaza la lista y se añade a la cola
            if (estados.get(n) > e.getValor()) {// si el valor del estado en el mapa es mayor

              estados.replace(n, e.getValor()); // actualizo el mapa
              q.add(e);
            }
          } else {// si no esta en la lista, lo añado a la frontera
            estados.put(n, e.getValor());
            q.add(e);
          }
    }
    
    /*
    
    File archivo = new File("DistribucionesTerreno.txt");
        FileWriter file = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(file);
    
    public static void escribirTerreno(Estado terreno) throws IOException{
    	File archivo = new File ("./terreno.txt");
		FileWriter w = new FileWriter(archivo);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter pw = new PrintWriter(bw);
		
		pw.write(terreno.getTractor().getFila() + " " + terreno.getTractor().getColumna() + " " + terreno.getK() + 
                    " " + terreno.getMax() + " " + terreno.getFilas() + " " + terreno.getColumnas());
		pw.append("\n");
		Casilla aux;
		for(int i=0; i<terreno.getFilas(); i++){
			for(int j=0; j<terreno.getColumnas(); j++){
				aux=new Casilla(i,j);
				pw.print(" " + terreno.getCasilla(aux).getCantidad());
			}
			pw.println();
		}
    }
    
    */
    
    public Nodo Elimina() {
        return (Nodo) q.poll();
    }
    
    public boolean EsVacia() {
        return q.isEmpty();
    }
            
}
