/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.util.ArrayList;
import java.util.regex.*;



/**
 *
 * @author Shipus
 */
public class ExpresionRegular {
    public static void main(String[] args) {
        /***
         * Codigo para comprobar lo que se ingrese en una figura de entrada,
         * 
         * Se seguira usando esto como guia
         * 
         * Se invoca la clase regex que se encargara de las expresiones regulares
         * para comprobar que se ingrese algo acorde
         * 
         * 
         * Se deja previsto que solo se ocuparan caracteres desde A hasta z respetando
         * mayusculas y minusculas o numeros
         * 
         */
        String cad1="",cad2 ="";
        
        ArrayList<String> variables=new ArrayList<>();
        ArrayList<String> valorVar=new ArrayList<>();
        String cadena1="a=abc";
        String cadena2="a=abc+23";
        int ks=0;
        Pattern patron = Pattern.compile("^[A-z]+(=)([A-Za-z1-9])+$");
        Pattern patron2 = Pattern.compile("^[A-z]+(=)[A-z1-9]+[+|-|*|/]{1}[A-z1-9]+$");
        /***
         *La linea anterior comprueba una operacion normal tipo
         * a=b+c
         * 
         * Debemos resolver ciertos problemas
         * 1.- si ya hay una variable creada con el mismo nombre se reemplaza el valor por el nuevo
         * 2.- si se ocupa una variable se debe comprobar su tipo
         *        - si es un String solo se ocuparia la concatenacion, creo...
         *        - si es un numero y se le suma una letra se debe tener en cuenta que no es posible a menos que
         *          sea una variable que sea del tipo int
         * 3.- se dejara como un supuesto que las desiciones o ciclos sera del tipo
         *                  a>b     b<a     a=b 
         *      y no del tipo 
         *                  a>b && b<a
         *      que conlleve a comprobar mas de un tipo de comprobacion a menos que sea dicho por los profes
         * 4.- la comprobacion de lo ingresado en la figura salida (Que es la figura entrada en si) solo necesitaria
         * que se escriba el valor de la variable a mostrar, aun no sé si es necesario que tenga alguna operacion 
         * o que tenga algo especial
         * 5.- la figura documentacion no se si tenga que mostrar algo 
         */
        Matcher match2 = patron2.matcher(cadena2);
        Matcher match= patron.matcher(cadena1);

        if(match.find()){
            System.out.println("wena");
            String cadenaSe=cadena1.split("=")[0];
            String cadenaSf=cadena1.split("=")[1];
            System.out.println(cadenaSe+" "+cadenaSf);
            variables.add(cadenaSe);
            valorVar.add(cadenaSf);
        }
        else{
            System.out.println("Lo ingresado es incorrecto debe seguir la regla: variable = variable(numero o variable)");
        }
        
        if(match2.find()){
            String cadenaV=cadena2.split("=")[0];
            for (int i = 0; i < variables.size(); i++) {
                if(cadenaV==variables.get(i)){
                    /***
                     * Se comprueba que la variable ingresada es igual a una existente por tanto 
                     * el resultado que se obtenga de la operacion obtenida cambie su valor original
                     * obteniendo su index 
                     * 
                     * Cada variable nueva que sea ingresada tendra un valor asociado a su index en 
                     * el arreglo de valorVar (valores de variables), y se asociara con su contraparte
                     * del arreglo variables (arreglo de variables).
                     */
                }
            }
            System.out.println("Match");
        }
        else{
            System.out.println("Lo ingresado es incorrecto, debe seguir la regla variable = variable (operacion) variable");
        }
        
    }
    
}
