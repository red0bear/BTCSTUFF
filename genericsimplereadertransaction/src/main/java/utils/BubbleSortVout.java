/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blockinfo.tx;
import blockinfo.vout_base;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class BubbleSortVout {
    
    
    private vout_base[] A;
    private ArrayList<vout_base> aux_vout;
    
    public BubbleSortVout(ArrayList<tx> tx)
    {
         aux_vout = new ArrayList();
        
        for(tx aux:tx)
        {
            for(vout_base aux_b:aux.vout)
            {
                aux_vout.add(aux_b);
            }
        } 
        
        A = new vout_base[aux_vout.size()];
        aux_vout.toArray(A);
    }
     
    public vout_base[] get_array_sorted()
    {
        return A;
    }
    
    public void bubbleSort()
    {
        int n = A.length;
        vout_base temp;
         for(int i=0; i < n; i++)
         {
            for(int j=1; j < (n-i); j++)
            {               
                int result = CompareTxVout(A[j-1].value , A[j].value);
                
                 if(result == -1)
                 {
                    //swap elements
                    temp = A[j-1];
                    A[j-1] = A[j];
                    A[j] = temp;
                  }
                        
            }
         }
    }
    
    private int CompareTxVout(String a , String b)
    {
       BigDecimal value_a = new BigDecimal(a);
       BigDecimal value_b = new BigDecimal(b);
       
       return value_a.compareTo(value_b);
    }

}
