package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import matrices.DimensionesIncompatibles;
import matrices.Matriz;

public class Matrices {

    public static void main(String[] args) {
        Matriz m1 = new Matriz(3, 4, true);
        System.out.println(m1);
        Matriz m2 = new Matriz(3, 4, true);
        System.out.println(m2);
        try {
            System.out.println(Matriz.sumarDosMatrices(m1, m2));
        } catch (DimensionesIncompatibles ex) {
            ex.printStackTrace();
        }
        
        Matriz cuadrada = new Matriz(3,3,true);
        
        System.out.println(cuadrada);
        
        try{
            System.out.println(Matriz.getDeterminante(cuadrada));
            System.out.println(Matriz.matrizCofactores(cuadrada));
        } catch(DimensionesIncompatibles ex){
            ex.printStackTrace();
        }
        
        System.out.println(Matriz.matrizTranspuesta(cuadrada));
        
        try{
            System.out.println(Matriz.matrizInversa(cuadrada));
        } catch(DimensionesIncompatibles ex){
            ex.printStackTrace();
        }
    }
        
}
