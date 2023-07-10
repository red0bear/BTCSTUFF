/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blockinfo.block;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felipe
 */
public class orderinggeneric {
 
    private ArrayList<block> listtransactions;
    private boolean enable_top_50;
    private boolean custom_value_transaction =true;
    private int value;
    
    private MergeSort ms;
    private BigDecimal value_a;
    private BigDecimal value_b;
    
    private final simple_calendar_format my_calendar_simple;
    
    public orderinggeneric(ArrayList<block> listtransactions)
    {             
        this.listtransactions = listtransactions;
        my_calendar_simple = new simple_calendar_format();
        my_calendar_simple.set_timezone_calendar_UTC();
    }
    
    public void list_top_50(boolean enable)
    {
        this.enable_top_50 = enable;
    }
    
    public void list_top_custom(int value)
    {
        this.value = value;
    }    
    
    public void start_ordering()
    {                
        ms = new MergeSort(listtransactions);
        List<block> result =  ms.get_array_sorted();
        
        System.out.println("Starting Merge Sort :" + ms.start_merge_sort());
        
        if(enable_top_50)
        {
           
            ArrayList<block> values_filter_r = filter_data(result);
            
            for(int counter = 0; counter < 50; counter++)
            {   
                printer_50(values_filter_r,counter);
            }
        }
        else
        {
            
            
            for(block aux :result)
            {
               
            //    if( my_calendar_simple.get_month(aux.time) == (9-1) &&  my_calendar_simple.get_year(aux.time) == 2009)
            //  {
                // if(my_calendar_simple.get_day(aux.time) >= 1 && my_calendar_simple.get_day(aux.time)  <= 30)
                // {
                        printer_by_value(aux, "1000");
              //   }
            //  }
                
            }
            
            for(block aux :result)
            {
               
             //   if( my_calendar_simple.get_month(aux.time) == (9-1) &&  my_calendar_simple.get_year(aux.time) == 2009)
           //   {
             //    if(my_calendar_simple.get_day(aux.time) >= 1 && my_calendar_simple.get_day(aux.time)  <= 30)
             //    {
                        printer_by_value_less_than(aux, "100");
              //   }
           //   }
                
            }
            

            /*
            for(block aux :result)
            {   

              if( my_calendar_simple.get_month(aux.time) == (9-1) &&  my_calendar_simple.get_year(aux.time) == 2009)
              {
                 if(my_calendar_simple.get_day(aux.time) > 25 && my_calendar_simple.get_day(aux.time)  < 29)
                 {
                        printer(aux);
                 }
              }
                          
            }    
           
            for(block aux :result)
            {   

              if(my_calendar_simple.get_month(aux.time)  == (10-1) && my_calendar_simple.get_year(aux.time) == 2009)
              {
                 if(my_calendar_simple.get_day(aux.time)  > 25 && my_calendar_simple.get_day(aux.time)  < 29)
                    printer(aux);
              }              
            } 
            */
        }
    }
    
    private  ArrayList<block> filter_data(List<block> result)
    {
    
        ArrayList<block> filter_repeated = new ArrayList();
        
        for(block filter : result)
        {
                if(filter_repeated.isEmpty())
                {
                    filter_repeated.add(filter);
                }
                else
                {
                    boolean found_r = false;
                    for(block re_check : filter_repeated)
                    {
                      if(filter.vout_ordered[0].scriptPubKey.addresses.get(0).compareTo(re_check.vout_ordered[0].scriptPubKey.addresses.get(0)) == 0)
                      {
                          found_r = true;
                      } 
                    }
                    
                    if(found_r)
                    {}
                    else
                    {
                        filter_repeated.add(filter);
                    }
                }
        }
            
        return filter_repeated;
    }

    private void printer_by_value(block result, String valuegot)
    {
              value_a = new BigDecimal(result.vout_ordered[0].value);
              value_b = new BigDecimal(valuegot);
              
              if(value_a.compareTo(value_b) == 1 || value_a.compareTo(value_b) == 0)
              {
                System.out.println( " Block Number :" + result.block_number +
                                    " Hash :"   + result.hash + 
                                    " Value:"   +value_a.toEngineeringString() + 
                                    " Address:" +result.vout_ordered[0].scriptPubKey.addresses.get(0)+ 
                                    " Time:"    + my_calendar_simple.get_format_time(result.time) +
                                    " Date:"    + my_calendar_simple.get_format_date(result.time));    
              }
              
    }  
    
       
    private void printer_by_value_less_than(block result, String valuegot)
    {
              value_a = new BigDecimal(result.vout_ordered[0].value);
              value_b = new BigDecimal(valuegot);
              
              if(value_a.compareTo(value_b) == -1)
              {
                System.out.println( " Block Number :" + result.block_number +
                                    " Hash :"   + result.hash + 
                                    " Value:"   +value_a.toEngineeringString() + 
                                    " Address:" +result.vout_ordered[0].scriptPubKey.addresses.get(0)+ 
                                    " Time:"    + my_calendar_simple.get_format_time(result.time) +
                                    " Date:"    + my_calendar_simple.get_format_date(result.time));    
              }
              
    }  
    
    
    private void printer(block result)
    {
              value_a = new BigDecimal(result.vout_ordered[0].value);
              
              System.out.println( " Block Number :" + result.block_number +
                                  " Hash :"   + result.hash + 
                                  " Value:"   +value_a.toEngineeringString() + 
                                  " Address:" +result.vout_ordered[0].scriptPubKey.addresses.get(0)+ 
                                  " Time:"    + my_calendar_simple.get_format_time(result.time) +
                                  " Date:"    + my_calendar_simple.get_format_date(result.time));    
    }
    
    private void printer_50(List<block> result, int counter)
    {
              value_a = new BigDecimal(result.get(counter).vout_ordered[0].value);

              System.out.println( "Number: " + (counter+1) +
                                  " Block Number :" + result.get(counter).block_number +
                                  " Hash :"   + result.get(counter).hash + 
                                  " Value:"   +value_a.toEngineeringString() + 
                                  " Address:" +result.get(counter).vout_ordered[0].scriptPubKey.addresses.get(0)+ 
                                  " Time:"    + my_calendar_simple.get_format_time(result.get(counter).time) +
                                  " Date:"    + my_calendar_simple.get_format_date(result.get(counter).time));    
    }
    
    private void printer_50(ArrayList<block> values_filter_r, int counter)
    {
              value_a = new BigDecimal(values_filter_r.get(counter).vout_ordered[0].value);
              
              System.out.println( "Number: " + (counter+1) +
                                  " Block Number :" + values_filter_r.get(counter).block_number +
                                  " Hash :"   + values_filter_r.get(counter).hash + 
                                  " Value:"   +value_a.toEngineeringString() + 
                                  " Address:" +values_filter_r.get(counter).vout_ordered[0].scriptPubKey.addresses.get(0)+ 
                                  " Time:"    + my_calendar_simple.get_format_time(values_filter_r.get(counter).time)+
                                  " Date:"    + my_calendar_simple.get_format_date(values_filter_r.get(counter).time));    
    }    
    
}
