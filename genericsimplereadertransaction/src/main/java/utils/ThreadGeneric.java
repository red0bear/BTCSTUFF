/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blockinfo.block;
import blockinfo.tx;
import blockinfo.vout_base;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class ThreadGeneric {
 
    private ProcessPostDataInfo handleclient = null;
    private Thread t_handleclient;
    private boolean enable_top_50;
    //private boolean finish_load;
    private boolean ena_month = false;
    private boolean ena_day = false;
    
    private String option_selected;
    private orderinggeneric top_wallets;
    private ArrayList<block> listtransactions;
    
    private ArrayList<block> listtransactions_local_filter;
    
    private String addr;

    private int year = 2009;
    private int month = 01;
    private int day = 01;    
    
    private simple_calendar_format my_calendar_simple;
    
    public ThreadGeneric()
    {
        listtransactions_local_filter = new ArrayList();
        my_calendar_simple = new simple_calendar_format();
        my_calendar_simple.set_timezone_calendar_UTC();   
    }
    
    public void start()
    {
       // finish_load = false;
        handleclient = new ProcessPostDataInfo();        
        t_handleclient = new Thread(handleclient);
        t_handleclient.setDaemon(true);
        t_handleclient.start();      
    }
    
    public void stop()
    {
    
    }  

    public void set_year(int year)
    {
        this.year = year;
    }
    
    public void set_month(int month)
    {
        this.month = month;
    }    
    public void set_day(int day)
    {
        this.day = day;
    } 
    
    public void ena_month(boolean ena_month)
    {
        this.ena_month = ena_month;
    }
    
    public void ena_day(boolean ena_day)
    {
        this.ena_day = ena_day;
    }    
        
    public void put_option(String option_selected)
    {
        this.option_selected = option_selected;
    }
    
    public void put_transaction_list(ArrayList<block> listtransactions)
    {
        this.listtransactions = listtransactions;
    }
    
    public void top_50_en(boolean enable_top_50)
    {
        this.enable_top_50 = enable_top_50;
    }
    
    public void set_string(String addr)
    {
        this.addr = addr;
    }
    
    public class ProcessPostDataInfo implements Runnable
    {

        @Override
        public void run() {

            System.out.println("Start : ProcessPostDataInfo");
            
            switch(option_selected)
            {
                case "search":
                    
                    listtransactions_local_filter.clear();
                    
                    if(ena_month && ena_day)
                    {
                        for(block aux : listtransactions)
                        {                            
                            if(my_calendar_simple.get_month(aux.time) == (month-1) && my_calendar_simple.get_year(aux.time) == year && (my_calendar_simple.get_day(aux.time) > day-1 && my_calendar_simple.get_day(aux.time) < day+1))
                            {
                                listtransactions_local_filter.add(aux);
                            }
                        }
                        
                        top_wallets = new orderinggeneric(listtransactions_local_filter);                    
                    }
                    else if(ena_month)
                    {
                        for(block aux : listtransactions)
                        {                            
                            if(my_calendar_simple.get_month(aux.time) == (month-1) && my_calendar_simple.get_year(aux.time) == year)
                            {
                                listtransactions_local_filter.add(aux);
                            }
                        }
                        
                        top_wallets = new orderinggeneric(listtransactions_local_filter);
                    }
                    else
                    {
                        top_wallets = new orderinggeneric(listtransactions);
                    }
                    
                    top_wallets.list_top_50(enable_top_50);
                    top_wallets.start_ordering();                    
                break;
                case "search_by_address":

                    for(block aux : listtransactions)
                    {
                            
                        for(tx search_addr: aux.tx)
                        {
                            for(vout_base vout :search_addr.vout)
                            {    
                                
                                for(String addr_chk:vout.scriptPubKey.addresses)
                                {
                                    if(addr_chk.compareTo(addr) == 0)
                                        listtransactions_local_filter.add(aux);   
                                }
                            }
                            
                        }
                    }                    

                break;
            }
            
            System.out.println("End : ProcessPostDataInfo");
            
        }
    
    }
}
