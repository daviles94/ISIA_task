/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 

    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
    
    public static double getDeterminante (Matriz matriz) throws DimensionesIncompatibles{
        if(matriz.getDimension().width != matriz.getDimension().height) throw new DimensionesIncompatibles("Requiere matrices de las mismas dimensiones");
        double det;
        int filas = matriz.getDimension().width;
        
        if(filas==2){
            det = (matriz.datos[0][0] * matriz.datos[1][1])-(matriz.datos[1][0] * matriz.datos[0][1]);
            return det;
        }
        
        double suma=0;
        for(int i=0; i< filas; i++){
            Matriz nm = new Matriz(filas-1, filas-1, false);
                for(int j=0; j<filas; j++){
                    if(j!=i){
                        for(int k=1; k<filas; k++){
                            int indice=-1;
                            if(j<i)
                                indice=j;
                            else if(j>i)
                                indice=j-1;
                                nm.datos[indice][k-1]= matriz.datos[j][k];
                        }
                    }
                }
                if(i%2==0)
                    suma+=matriz.datos[i][0] * getDeterminante(nm);
                else
                    suma-=matriz.datos[i][0] * getDeterminante(nm);
        }
        return suma;
    
    }
    
    public static Matriz matrizCofactores(Matriz matriz) throws DimensionesIncompatibles{
        if(matriz.getDimension().width != 3 && matriz.getDimension().height != 3) throw new DimensionesIncompatibles("Requiere matrices de mínimo 3x3");
        int filas = matriz.getDimension().width;
        Matriz resultante = new Matriz(filas, filas, false);

        for(int i=0;i<filas;i++) {
            for(int j=0;j<filas;j++) {
                Matriz mdet = new Matriz(filas-1, filas-1, false);
                double det;
                for(int k=0;k<filas;k++) {
                    if(k!=i) {
                        for(int l=0;l<filas;l++) {
                            if(l!=j){
                                int indice1=k<i ? k : k-1 ;
                                int indice2=l<j ? l : l-1 ;
                                mdet.datos[indice1][indice2]=matriz.datos[k][l];
                            }
                        }
                    }
                }
                det=getDeterminante(mdet);
                resultante.datos[i][j]= det * (double)Math.pow(-1, i+j+2);
            }
        }
        return resultante;
    }

    public static Matriz matrizTranspuesta(Matriz matriz) {
        int filas = matriz.getDimension().width;
        int columnas = matriz.getDimension().height;
        
        Matriz resultante = new Matriz(columnas, filas, false);
        for(int i=0; i<filas; i++){
            for(int j=0; j<filas; j++)
                resultante.datos[i][j]=matriz.datos[j][i];
        }
        return resultante;
    }
    
    public static Matriz matrizAdjunta(Matriz matriz) throws DimensionesIncompatibles{
        return matrizTranspuesta(matrizCofactores(matriz));
    }
    
    public static Matriz multiplicarMatrizPorDeterminante(double n, Matriz matriz) {
        int filas = matriz.getDimension().width;
        Matriz resultante = new Matriz(filas, filas, false);
        for(int i=0; i<filas ;i++)
            for(int j=0; j<filas ;j++){
                resultante.datos[i][j] = matriz.datos[i][j] * n;
            }
       
        return resultante;
    }
    
    
    public static Matriz matrizInversa(Matriz matriz) throws DimensionesIncompatibles{
        if(matriz.getDimension().width != matriz.getDimension().height) throw new DimensionesIncompatibles("Requiere matrices de las mismas dimensiones");
        
        double det = 1/getDeterminante(matriz);
        Matriz resultante = matrizAdjunta(matriz);
        Matriz resulpordet = multiplicarMatrizPorDeterminante(det, resultante);
        return resulpordet;
    }

}
