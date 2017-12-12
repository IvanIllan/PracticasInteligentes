package practicainteligente;

import static java.lang.Math.abs;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Estado {
    private Casilla tractor;
    private int k;
    private int max;
    private int filas;
    private int columnas;
    private Casilla casillas[][];

    public Estado(Casilla tractor, int k, int max, int filas, int columnas) {
        this.tractor = tractor;
        this.k = k;
        this.max = max;
        this.filas = filas;
        this.columnas = columnas;
        casillas=new Casilla[filas][columnas];       
    }

    boolean isEquals(Estado e) {
        return this.getMD5(this.toString()).equals(e.getMD5(e.toString()));
    }
    
    public void iniciarTerreno (int v[]) {        
        for (int i=0;i<filas;i++) {
            for (int j=0;j<columnas;j++) {
                casillas[i][j]=new Casilla(i,j,v[getFilas()*i+j]);             
            }
        }    
    }
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
 
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Casilla getTractor() {
        return tractor;
    }

    public int getK() {
        return this.k;
    }

    public int getMax() {
        return max;
    }

    public int getFilas() {
        return filas;
    }
    
    public Casilla[][] getCasillas(){
        return casillas;
    }
    
    public int getColumnas() {
        return columnas;
    }   
    
    public Casilla getCasilla (Casilla c) {
        return (Casilla) casillas[c.getFila()][c.getColumna()];
    }
    
    public void setCasilla(Casilla c) {
        casillas[c.getFila()][c.getColumna()]=c;     
    }
    
    public int cantidadPosible(Casilla c) {
          return ((getCasilla(c).getCantidad()<max) ? max-getCasilla(c).getCantidad() : 0);
    }
    
    public int getS() {
        return (Math.max(getCasilla(tractor).getCantidad()-getK(),0));
    }
    
    public boolean estaDentro(Casilla c) {
        return (c.getFila()>=0 && c.getFila()<getFilas() && c.getColumna()>=0 && c.getColumna()<getColumnas());
    }
    
    //casillas adyacentes a la posicion del tractor
    public Casilla [] casillasAdyacentes() {
        ArrayList<Casilla> casillas = new ArrayList <Casilla>();
        Casilla aux;
        for (int i=-1;i<=1;i++) {
            for (int j=-1;j<=1;j++) {
                aux=new Casilla(tractor.getFila()+i,tractor.getColumna()+j);                
                if (estaDentro(aux) && abs(i+j)==1) {                   
                    casillas.add(getCasilla(aux));
                }
            }
        }
        return casillas.toArray(new Casilla[casillas.size()]);
    }
   
    // todas las posibles asignaciones a las casillas adyacentes.
    public void backrepartir(int etapa,int s,Casilla adyacentes [], int sol[],ArrayList soluciones) {
        int i;
        if (etapa==sol.length) {
            if (esSolucion(sol,adyacentes,s)) {
                int copia[]=new int[sol.length];
                System.arraycopy(sol, 0, copia, 0, sol.length);
                soluciones.add(copia);              
            }
        }
        else {
            for (i=0;i<=s;i++) {
                if (i<=cantidadPosible(getCasilla(adyacentes[etapa])) && esPosible(i,etapa,sol,s)) {
                    sol[etapa]=i;
                    backrepartir (etapa+1,s,adyacentes,sol,soluciones);
                }                    
            }  
        }
    }
    
    public boolean esPosible(int i,int etapa,int sol[],int s) {
        int valor=0;
        for (int k=0;k<etapa;k++) 
            valor+=sol[k];
        
        return ((valor+i)<=s);
    }
    
    public boolean esSolucion(int sol[], Casilla adyacentes[], int s) {
        int valor=0,total=0;        
        for (int k=0;k<sol.length;k++) {
            valor+=sol[k];
            total+=cantidadPosible(getCasilla(adyacentes[k]));
        }
        int elegido = Integer.min(total, s);
        
        return (valor==elegido);
    }
    
    //lista de acciones posibles
    public ArrayList<accion> getAcciones() {
        Casilla [] adyacentes = casillasAdyacentes();       
        int sol[]=new int[adyacentes.length];
        ArrayList soluciones = new ArrayList();           
        
        backrepartir(0,getS(),adyacentes,sol,soluciones);
        
        ArrayList<accion> acciones=new ArrayList<accion>();
        
        int aux[];  
        for(int k=0;k<adyacentes.length;k++) {
            for (int i=0; i<soluciones.size();i++){
                aux=(int[]) soluciones.get(i);
                acciones.add(new accion(adyacentes[k],adyacentes,aux));
            }  
        }        
        return acciones;
    }    
    
    //Estado nuevo del terreno generado a partir de una accion
    public Estado getEstado(accion a) {
        
        Estado nuevo=new Estado(getTractor(),getK(),getMax(),getFilas(),getColumnas());
        for (int i=0;i<getFilas();i++) {
            for (int j=0;j<getColumnas();j++) {
                nuevo.casillas[i][j]=new Casilla(i,j,casillas[i][j].getCantidad());                 
            }
        }        
        nuevo.getCasilla(nuevo.tractor).setCantidad(nuevo.getCasilla(nuevo.tractor).getCantidad()-a.cantidadRepartir());
        nuevo.tractor=(Casilla) a.getMovimiento();
        Casilla [] adyacentes=a.getAdyacentes();
        int [] unidades= a.getUnidades();
        
        for (int i =0; i<adyacentes.length;i++) {
            nuevo.getCasilla(adyacentes[i]).setCantidad(nuevo.getCasilla(adyacentes[i]).getCantidad()+unidades[i]);
        }
        return nuevo;
    }
    
    public int Costo (accion a) {
        return a.cantidadRepartir() +1;
    }
    
    // si el estado es el objetivo a conseguir
     public boolean testObjetivo(){
        boolean repartido=true;
        for (int i=0;i<getFilas() && repartido;i++) {
            for (int j=0;j<getColumnas() && repartido;j++) {                
                if (casillas[i][j].getCantidad()!=getK()) 
                    repartido=false;
            }
        }           
        return repartido;
    }
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
 
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
     
    @Override
    public String toString() {
        String terreno=tractor.getFila() + " " + tractor.getColumna() + " " + getK() + " " + getMax() + " " + getFilas() + " " + getColumnas() + "\n";
        for (int i=0;i<casillas.length;i++) {
            for (int j=0;j<casillas[0].length;j++) {
                terreno+=casillas[i][j].getCantidad() + " ";
            }
            terreno+="\n";
        }    
        return terreno;
    }
    
    boolean isEquals(Estado e) {
        return this.getMD5(this.toString()).equals(e.getMD5(e.toString()));
    }
    
    
}

