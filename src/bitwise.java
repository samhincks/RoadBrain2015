/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author samhincks
 */
public class bitwise {
    
    
    public static void main(String[] args) {
        //.. int test 1
        int x = 9;
        int y = 4;
                
        Integer trueE = (x | ~1); /// (x & (~2) &1) = good for 3
        System.out.println(Integer.toString(32768, 2));
        System.out.println("TrueE is " + trueE);
        
        System.out.println(x );
        //.. test 2
        
        ///.. test 3
        
        //.. test 4
    }
    
}
