package practicainteligente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

enum tipoBusqueda {
    anchura, profundidad, costeuniforme, Aasterisco
};

public class PracticaInteligente {

    public static void main(String[] args) throws IOException {

//        // Crear terreno mediante la lectura del fichero
//        System.out.println("TERRENO INICIALMENTE");
//        Estado inicio=LeerEscribir.leerTerreno("terreno.txt");        
//        System.out.println(inicio);
//        
//        System.out.println("ACCION, ESTADO, COSTO");
//        // Prueba de generacion de sucesores
//        ArrayList<accion> acciones=new ArrayList<accion>(inicio.getAcciones());          
//        EspaciodeEstados espaciodeestados=new EspaciodeEstados();                
//        ArrayList<Sucesor> sucesores=new ArrayList<Sucesor>(espaciodeestados.sucesor(inicio));        
//        for (int i=0;i<sucesores.size();i++) 
//            System.out.println(sucesores.get(i));            
//        
//        
//        System.out.println("PRUEBA DE FRONTERA");
//        // Prueba insertando 1000 nodos.
//        Frontera frontera=new Frontera();
//        frontera.CrearFrontera();
//        
//        for (int i=0;i<10;i++) {
//            frontera.Insertar(new Nodo());
//        }
//        while (!frontera.q.isEmpty()) {
//            System.out.println(frontera.q.poll().getValor());
//        }
        Scanner sc = new Scanner(System.in);
        int n;
        Estado inicio=null;
        System.out.println("Seleccione la opcion que desea \n1.-Lectura por teclado\n2.-Lectura por fichero\n3.-Inicialización random");
        n = sc.nextInt();
        if (n == 1) {
            inicio = LeerEscribir.LecturaTeclado();
        } else if (n == 2) {
            inicio = LeerEscribir.leerTerreno("terreno2.txt");
        } else if (n == 3) {
            inicio = LeerEscribir.Random();
        }
        System.out.println(inicio);
        Problema prob = new Problema(new EspaciodeEstados(), inicio);
        int Prof_Max = 17;
        int Inc_Prof = 1;
        boolean solucion = false;

        System.out.println("Escribe 1,2,3,4 (Anchura, Profundidad, Coste Uniforme, A*): ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                solucion = Busqueda_Acotada(prob, tipoBusqueda.anchura, Prof_Max);
                break;
            case 2:
                solucion = Busqueda(prob, tipoBusqueda.profundidad, Prof_Max, Inc_Prof);
                break;
            case 3:
                solucion = Busqueda_Acotada(prob, tipoBusqueda.costeuniforme, Prof_Max);
                break;
            case 4:
                solucion = Busqueda_Acotada(prob, tipoBusqueda.Aasterisco, Prof_Max);
                break;
            default:
                System.out.println("Opcion invalida");
        }

        if (!solucion) {
            System.out.println("SOLUCION NO ENCONTRADA");
        }

    }

    public static boolean Busqueda_Acotada(Problema prob, tipoBusqueda estrategia, int Prof_Max) {
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        Nodo n_inicial = new Nodo(null, prob.getEstadoinicial(), null, 0, 0, 0);
        frontera.Insertar(n_inicial);
        boolean solucion = false;
        Nodo n_actual = null;

        while (!solucion && !frontera.EsVacia()) {
            n_actual = frontera.Elimina();
            //System.out.println(n_actual.getE());
            if (prob.Objetivo(n_actual.getE())) {
                solucion = true;
            } else {
                ArrayList<Sucesor> LS = prob.getEspaciodeestados().sucesor(n_actual.getE());
                ArrayList<Nodo> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia);
                for (int i = 0; i < LN.size(); i++) {
                    frontera.Insertar(LN.get(i));
                }
            }
        }
        if (solucion) {
            String textoSolucion = "";
            while (n_actual.getPadre() != null) {
                textoSolucion += "Accion: " + n_actual.getA() + " \nEstado: " + n_actual.getE() + "F: " + n_actual.getValor()
                        + " Profundidad: " + n_actual.getProfundidad() + " Coste: " + n_actual.getCosto() + "\n";
                n_actual = n_actual.getPadre();
            }

            try {
                System.out.println(textoSolucion);
                LeerEscribir.escribirTerreno(textoSolucion);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return solucion;
    }

    public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Nodo n_actual, int Prof_Max, tipoBusqueda estrategia) {
        ArrayList<Nodo> LN = new ArrayList<Nodo>();
        if (n_actual.getProfundidad() < Prof_Max) {
            Nodo aux = null;
            Sucesor s = null;
            for (int i = 0; i < LS.size(); i++) {
                s = (Sucesor) LS.get(i);
                if (estrategia == tipoBusqueda.anchura) {
                    aux = new Nodo(n_actual, s.getE(), s.getA(), n_actual.getCosto() + s.getCoste(), n_actual.getProfundidad() + 1, n_actual.getProfundidad() + 1);
                }
                if (estrategia == tipoBusqueda.profundidad) {
                    aux = new Nodo(n_actual, s.getE(), s.getA(), n_actual.getCosto() + s.getCoste(), n_actual.getProfundidad() + 1, Prof_Max - (n_actual.getProfundidad() + 1));
                }
                if (estrategia == tipoBusqueda.costeuniforme) {
                    aux = new Nodo(n_actual, s.getE(), s.getA(), n_actual.getCosto() + s.getCoste(), n_actual.getProfundidad() + 1, n_actual.getCosto() + s.getCoste());
                }
                if (estrategia == tipoBusqueda.Aasterisco) {
                    aux = new Nodo(n_actual, s.getE(), s.getA(), n_actual.getCosto() + s.getCoste(), n_actual.getProfundidad() + 1, n_actual.getCosto() + s.getCoste() + n_actual.getHeuristica(n_actual.getE()));
                }
                LN.add(aux);
            }
        }
        return LN;
    }

    public static boolean Busqueda(Problema prob, tipoBusqueda estrategia, int Prof_Max, int Inc_Prof) {
        int Prof_Actual = Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual <= Prof_Max) {
            solucion = Busqueda_Acotada(prob, estrategia, Prof_Actual);
            Prof_Actual += Inc_Prof;
        }
        return solucion;
    }
}
