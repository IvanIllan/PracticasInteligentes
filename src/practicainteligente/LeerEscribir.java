package practicainteligente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LeerEscribir {
    public static Estado LecturaTeclado(){
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();
        System.out.println("Introduce la posicion x y del tractor");
        Casilla tractor =new Casilla(sc.nextInt(),sc.nextInt());
        System.out.println("Introduce el numero que debe contener cada casilla");
        int k=sc.nextInt();
        System.out.println("Introduce el numero maximo que puede contener");
        int max=sc.nextInt();
        System.out.println("Introduce el numero de dimensiones del tablero");
        int f=sc.nextInt();
        int c=sc.nextInt();
        Estado terreno=new Estado(tractor,k,max,f,c);
        int v[]=new int[f*c];
        int indice=0;
        System.out.println("Introduce las cantidades de las casillas");
        for(int i=0;i<f;i++){
            for(int j=0;j<c;j++){
                v[indice]=sc.nextInt();
                indice++;
            }
        }
        
        terreno.iniciarTerreno(v);
        return terreno;
    }
    public static Estado leerTerreno (String cadena) throws IOException{	
        String linea;        
        BufferedReader br = new BufferedReader (new FileReader (cadena));
        linea= br.readLine();
        StringTokenizer st = new StringTokenizer (linea); 
        Casilla tractor=new Casilla (Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        int k = Integer.parseInt(st.nextToken());
        int max=Integer.parseInt(st.nextToken());
        int f=Integer.parseInt(st.nextToken());
        int c=Integer.parseInt(st.nextToken());
        Estado terreno=new Estado(tractor,k,max,f,c); 
        int v[]=new int[f*c];
        int indice=0;
        while ((linea = br.readLine())!=null){
            st = new StringTokenizer (linea);  
            for (int i =0;i<c;i++) {
                v[indice]=Integer.parseInt(st.nextToken());
                indice++;
            }
        }
        br.close();
        terreno.iniciarTerreno(v);
        return terreno;
  }
    public static Estado Random(){
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();
        System.out.println("Introduce la posicion x y del tractor");
        Casilla tractor =new Casilla(sc.nextInt(),sc.nextInt());
        System.out.println("Introduce el numero que debe contener cada casilla");
        int k=sc.nextInt();
        System.out.println("Introduce el numero maximo que puede contener");
        int max=sc.nextInt();
        System.out.println("Introduce el numero de dimensiones del tablero");
        int f=sc.nextInt();
        int c=sc.nextInt();
        Estado terreno=new Estado(tractor,k,max,f,c);
        int v[]=new int[f*c];
        int indice=0;
        System.out.println("Se inicializará el tablero aleatoriamente");
        for(int i=0;i<f;i++){
            for(int j=0;j<c;j++){
                v[indice]=rn.nextInt(10);
                indice++;
            }
        }
        
        terreno.iniciarTerreno(v);
        return terreno;
    }
    public static void escribirTerreno (Estado terreno) throws IOException{
            File archivo = new File("DistribucionesTerreno.txt");
	                
            FileWriter file = new FileWriter(archivo);
            PrintWriter pw = new PrintWriter(file);            
            pw.print(terreno.getTractor().getFila() + " " + terreno.getTractor().getColumna() + " " + terreno.getK() + 
                    " " + terreno.getMax() + " " + terreno.getFilas() + " " + terreno.getColumnas());
            pw.println("");
            Casilla aux;
            for(int i = 0; i < terreno.getFilas(); i++){
                for(int j = 0; j < terreno.getColumnas(); j++){
                    aux=new Casilla(i,j);
                    pw.print(" " + terreno.getCasilla(aux).getCantidad());
                }
                pw.println();
            } 
        pw.close();        
    }
}
