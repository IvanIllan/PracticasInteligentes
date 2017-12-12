/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicainteligente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

enum tipoBusqueda {anchura,profundidad,costeuniforme,Aasterisco};

public class PracticaInteligente {

    public static void main(String[] args) throws IOException {
    Scanner sc=new Scanner(System.in);
    int n;
    Estado inicio=null;
    System.out.println("Seleccione la opcion que desea \n1.-Lectura por teclado\n2.-Lectura por fichero\n3.-Inicialización random");
    n=sc.nextInt();
    if(n==1){
        inicio=LeerEscribir.LecturaTeclado(); 
    }else if(n==2){
        inicio=LeerEscribir.leerTerreno("terreno.txt"); 
    }else if(n==3){
        inicio=LeerEscribir.Random();
    }
    
    System.out.println(inicio);
    Problema prob = new Problema (new EspaciodeEstados(),inicio);
    int Prof_Max=5;
    int Inc_Prof=1;
    boolean solucion=false;
   

    System.out.println ("Escribe 1,2,3,4(Anchura, Profundidad, Coste Uniforme, A*): ");
    int opcion=sc.nextInt();
    switch (opcion) {
        case 1: 
            solucion=Busqueda_Acotada (prob, tipoBusqueda.anchura, Prof_Max, 1);
            break;
        case 2:
            solucion=Busqueda (prob, tipoBusqueda.profundidad, Prof_Max,Inc_Prof);
            break;
        case 3:
            solucion=Busqueda_Acotada (prob, tipoBusqueda.costeuniforme, Prof_Max, 3);
            break;
        case 4:
            solucion=Busqueda_Acotada (prob, tipoBusqueda.Aasterisco, Prof_Max, 4);
            break;
        default:
            System.out.println("Opcion invalida");
    }
    
    if (!solucion)
        System.out.println("SOLUCION NO ENCONTRADA");
    
    }
    
    public static boolean Busqueda_Acotada (Problema prob, tipoBusqueda estrategia, int Prof_Max, int opcion) {        
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        Nodo n_inicial= new Nodo(null,prob.getEstadoinicial(),null,0,0,0);
        frontera.Insertar(n_inicial);
        boolean solucion=false;
        Nodo n_actual=null;
        boolean heurist=true;
        
        while (!solucion && !frontera.EsVacia()) {
            n_actual=frontera.Elimina();
              if(n_actual.getHeuristica(n_actual.getE())==0){
                solucion=true;
            }    
            
            if (prob.Objetivo(n_actual.getE()))
                solucion=true;
            else {
               ArrayList<Sucesor> LS = prob.getEspaciodeestados().sucesor(n_actual.getE());               
               ArrayList<Nodo> LN= CreaListaNodosArbol(LS,n_actual,Prof_Max,estrategia);                
               for (int i=0;i<LN.size();i++) 
                   frontera.Insertar(LN.get(i));               
            }
                  
        }
        if (solucion) {
            while (heurist && n_actual.getPadre()!=null) {
                 System.out.println(""+n_actual.getHeuristica(n_actual.getE()));
                if((n_actual.getHeuristica(n_actual.getE())==0) && opcion==4){
                    heurist=false;
                }
                System.out.print ("Accion: " + n_actual.getA()+ " \nEstado: " + n_actual.getE() + "Coste " + n_actual.getValor()+ "\n");
                n_actual=n_actual.getPadre();

               
            }
               
        }        
        return solucion;
    }
    
    public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Nodo n_actual, int Prof_Max, tipoBusqueda estrategia) {
        ArrayList<Nodo> LN = new ArrayList<Nodo>();
        if (n_actual.getProfundidad()<Prof_Max) {
            Nodo aux=null;
            Sucesor s=null;
            for (int i=0;i<LS.size();i++) {
               s= (Sucesor) LS.get(i);
               if (estrategia==tipoBusqueda.anchura)
                    aux= new Nodo(n_actual, s.getE(), s.getA(),n_actual.getProfundidad()+1,n_actual.getProfundidad()+1, n_actual.getProfundidad()+1);
               if (estrategia==tipoBusqueda.profundidad)
                    aux= new Nodo(n_actual, s.getE(), s.getA(),n_actual.getProfundidad()+1,n_actual.getProfundidad()+1, (n_actual.getProfundidad()+1)*-1);
               if (estrategia==tipoBusqueda.costeuniforme)
                    aux= new Nodo(n_actual, s.getE(), s.getA(),s.getCoste(),n_actual.getProfundidad()+1, n_actual.getCosto()+s.getCoste());
               if (estrategia==tipoBusqueda.Aasterisco)
                    aux= new Nodo(n_actual, s.getE(), s.getA(),s.getCoste(),n_actual.getProfundidad()+1, n_actual.getCosto()+s.getCoste()+ n_actual.getHeuristica(n_actual.getE()));
               LN.add(aux);
            }
        }
        return LN;
    }
    
    public static boolean Busqueda (Problema prob, tipoBusqueda estrategia, int Prof_Max, int Inc_Prof) {        
        int Prof_Actual=Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual<=Prof_Max) {
            solucion=Busqueda_Acotada(prob,estrategia,Prof_Actual, 2);
            Prof_Actual+=Inc_Prof;
        }
        return solucion;        
    }
}
