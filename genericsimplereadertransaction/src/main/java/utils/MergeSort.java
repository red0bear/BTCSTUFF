/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blockinfo.block;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author felipe
 */
public class MergeSort {
    
    private block[] A,B;
    
    public MergeSort(ArrayList<block> data)
    {
       A = new block[data.size()];
       B = new block[data.size()];
       
       data.toArray(A);
       data.toArray(B);
    }
    
    public long get_total_lenght()
    {
       return (long) B.length;
    }
    
    public List<block> get_array_sorted()
    {
        return Arrays.asList(A);
    }
    
    public boolean start_merge_sort()
    {
        TopDownMergeSort(A,B, (long) A.length-1);
        return true;
    }
    
    private void TopDownMergeSort(block[] A, block[] B, long n)
    {
        TopDownSplitMerge(B, 0, n ,A);
    }
    
    private void TopDownSplitMerge(block[] B, long iBegin, long iEnd , block[] A)
    {
        if(iEnd - iBegin <= 1)                      // if run size == 1
            return;                                 //   consider it sorted
        // split the run longer than 1 item into halves
        long iMiddle = (iEnd + iBegin) / 2;              // iMiddle = mid point
        // recursively sort both runs from array A[] into B[]
        TopDownSplitMerge(A, iBegin,  iMiddle, B);  // sort the left  run
        TopDownSplitMerge(A, iMiddle,    iEnd, B);  // sort the right run
        // merge the resulting runs from array B[] into A[]
        TopDownMerge(B, iBegin, iMiddle, iEnd, A);
    }
    
    private void TopDownMerge(block[] A,long iBegin,long iMiddle,long iEnd,block[] B)
    {
       long i = iBegin, j = iMiddle;

        // While there are elements in the left or right runs...
        for (long k = iBegin; k < iEnd; k++) 
        {
            int result = CompareTxVout(A[(int)i].vout_ordered[0].value , A[(int)j].vout_ordered[0].value);
            
            // If left run head exists and is <= existing right run head.
            if (i < iMiddle && (j >= iEnd || result  == 1 )) {
                B[(int)k] = A[(int)i];
                i = i + 1;
            } else {
                B[(int)k] = A[(int)j];
                j = j + 1;
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
