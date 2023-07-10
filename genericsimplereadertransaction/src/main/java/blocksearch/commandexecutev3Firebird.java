/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksearch;

import blockinfo.block;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import command_cli.commandexecute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.simple_calendar_format;
import utils.simpledatabase;
import utils.firebirdpack;

/**
 *
 * @author felipe
 */
public class commandexecutev3Firebird {
    
    private ThreadGroup tgbuild = new ThreadGroup("Proccess INFO"); 
    private ThreadGroup tggrabinfo = new ThreadGroup("Grab INFO");  
    
    private CopyOnWriteArrayList<Thread> trgrabinfo = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<Thread> tr = new CopyOnWriteArrayList();
    
    private String ROOT_PATH;
    private boolean enable_remote = false; 
    
    private String ip = "";
    private String user = "";
    private String pass = "";
    
    private simple_calendar_format my_calendar_simple;
    private getblockchaininfo blockchaininfo;
    
    private ProcessControlCommands handleclient = null;
    private Thread t_handleclient;    
    
    private simpledatabase datahandler;
    
    private long increment_blockhash = 0;    
    private long counter_thread = 0;    
    private block aux;
    
    private ConcurrentHashMap<Integer,block> block_info = new ConcurrentHashMap();
    private CopyOnWriteArrayList<Long> indexl = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<Long> my_numbers = new CopyOnWriteArrayList();
    
    
        
    public commandexecutev3Firebird()
    {
        my_calendar_simple = new simple_calendar_format();
        my_calendar_simple.set_timezone_calendar_UTC();
    }
    
    public void start()
    {         
        handleclient = new ProcessControlCommands();        
        t_handleclient = new Thread(handleclient);
        t_handleclient.setDaemon(true);
        t_handleclient.start();     
    }   
    
    public void stop()
    {
       //handleclient.finish_thread();
    }        
    
    public void set_root_path(String ROOT_PATH)
    {
        this.ROOT_PATH  = ROOT_PATH;
    }    
    
    public void en_dis_remote(boolean enable_remote)
    {
        this.enable_remote = enable_remote;
    } 
    
    public void set_remote_user(String ip , String user , String pass)
    {
        this.ip = ip;
        this.user = user;
        this.pass = pass;
    }    
    
    private String execute_getblockhash(long increment_blockhash)
    {       
         String command;
         
         if(enable_remote)
            command = ROOT_PATH + "bitcoin-cli" + " -rpcconnect=" + ip + " -rpcuser=" + user + " -rpcpassword=" + pass + " getblockhash " + Long.toString(increment_blockhash);
         else
            command = ROOT_PATH + "bitcoin-cli" + " -rpcuser=" + user + " -rpcpassword=" + pass  + " getblockhash " + Long.toString(increment_blockhash);
         
         return command;
    }
    
    private String execute_getblockchaininfo()
    {        
        String command;
        
         if(enable_remote)
            command = ROOT_PATH + "bitcoin-cli" + " -rpcconnect=" + ip + " -rpcuser=" + user + " -rpcpassword=" + pass + " getblockchaininfo";
         else
            command = ROOT_PATH + "bitcoin-cli" + " -rpcuser=" + user + " -rpcpassword=" + pass  + " getblockchaininfo";
         
         return command;
    }
    
    private String execute_getblock(String hash)
    {    
        String command;
        
         if(enable_remote)
            command = ROOT_PATH + "bitcoin-cli" + " -rpcconnect=" + ip + " -rpcuser=" + user + " -rpcpassword=" + pass + " getblock "+ hash + " 2";
         else
            command = ROOT_PATH + "bitcoin-cli" + " -rpcuser=" + user + " -rpcpassword=" + pass  + " getblock "+ hash + " 2";
    
        return command; 
    }   
    
    
    private block build_mapper(String result)
    {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);   
        
        try {
           
            return mapper.readValue(result, block.class);
           
        } catch (IOException ex) {
            Logger.getLogger(commandexecute.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private getblockchaininfo build_mapper_getblockchaininfo(String result)
    {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);   
        
        try {
            return  mapper.readValue(result, getblockchaininfo.class);
        } catch (IOException ex) {
            Logger.getLogger(commandexecute.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private String execute_command(String command)
    {

        try {
            String line = null;
            String line_panel = null;
             
            Runtime runtime = Runtime.getRuntime();
            
            Process process = runtime.exec(command+"\n"); 
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          
            while ((line = lineReader.readLine()) != null) {
                    
                if(line_panel == null)
                    line_panel = line;
                else
                    line_panel = line_panel + line;
                
            }
            
            return line_panel;
        
        } catch (IOException ex) {      
            Logger.getLogger(commandexecute.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    

    private String grab_getblockchaininfo()
    {
        
        String result_hash = execute_command(execute_getblockchaininfo());
        
        result_hash = result_hash.replaceAll("\n", "").trim();
        
       // blockchaininfo = build_mapper_getblockchaininfo(result_hash);
        
        return result_hash;
    }
    
    private block grab_info(long increment_blockhash)
    {         
        //           
        String result_hashl = execute_command(execute_getblockhash(increment_blockhash));

        result_hashl = result_hashl.replaceAll("\n", "").trim();
                    
        String resultl = execute_command(execute_getblock(result_hashl)); 
        
        resultl = resultl.replaceAll("\n", "").trim();
        
        block checkl = build_mapper(resultl);
        checkl.original_data = resultl;
        checkl.block_number = increment_blockhash;
        
        return checkl;
    }  
    
    public void build_start_ProcessGrabInfo(long thread)
    {         
        Thread t_handleclientl = new Thread(tggrabinfo, new ProcessGrabInfo(),"ProcessGrabInfo"+thread);
        t_handleclientl.setDaemon(true);
        t_handleclientl.setPriority(10);
        t_handleclientl.start();  
        
        trgrabinfo.add(t_handleclientl);    
    }   
    
    public void build_start_ProcessBuildInfo()
    {         
        Thread t_handleclientl = new Thread(tgbuild, new ProcessBuildInfo(),"ProcessBuildInfo_"+counter_thread);
        t_handleclientl.setDaemon(true);
        t_handleclientl.setPriority(10);
        t_handleclientl.start();  
                      
        counter_thread++;
        tr.add(t_handleclientl);
    }
    
    
    public class ProcessControlCommands implements Runnable
    {
        @Override
        public void run() {

            System.out.println("Get blockchaininfo");
            
            blockchaininfo = build_mapper_getblockchaininfo(grab_getblockchaininfo());

            System.out.println("Start");
                       
            //datahandler = new simpledatabase();
            //datahandler.start();
            firebirdpack fdb_pack_l = new firebirdpack();
            
            fdb_pack_l.local_pak();
            fdb_pack_l.get_date();

            //datahandler.load_cfg();
            increment_blockhash = (fdb_pack_l.get_end_block() > 0 )?fdb_pack_l.get_end_block()-1:0;            
            
          //  datahandler.check_data_consistence(datahandler., increment_blockhash);
            
            
            build_start_ProcessGrabInfo(0);
            build_start_ProcessGrabInfo(1);
            build_start_ProcessGrabInfo(2);
            build_start_ProcessGrabInfo(3);
            build_start_ProcessGrabInfo(4);
            build_start_ProcessGrabInfo(5);
            build_start_ProcessGrabInfo(6);
            build_start_ProcessGrabInfo(7);
            build_start_ProcessGrabInfo(8);
          //  build_start_ProcessGrabInfo(9);

             
            //build_start_ProcessBuildInfo();
            
            for(;increment_blockhash < blockchaininfo.blocks;increment_blockhash++)
            {
                //block_info.add(grab_info(increment_blockhash));
                indexl.add(increment_blockhash);
            }
            
            while( tggrabinfo.activeCount() > 0)
            {
                try { 
                    TimeUnit.NANOSECONDS.sleep(10);
                } catch (InterruptedException ex) {
                  //  Logger.getLogger(RSTRUNNING.class.getName()).log(Level.SEVERE, null, ex);
                }             
            }            
            
            datahandler.stop();
            
            System.out.println("END");
        }
    }
    
    public class ProcessBuildInfo implements Runnable
    {    
        
        private blockstruct walker;
        private int actual_year = 0;
        private int index = (int)increment_blockhash;
       /// private long l_b_year, f_b_year;
                
        
        @Override
        public void run() {
            
           // fdb_pack.local_pak();
           // fdb_pack.get_date();
            
            while(true)
            {
                if(block_info.containsKey(index))
                {
                 /*   
                    aux = block_info.remove(index);
                    int year_got = my_calendar_simple.get_year(aux.time);
                    int actual_year = fdb_pack.get_year();
                    
                    if( actual_year == year_got)
                    {
                        fdb_pack.insert_block(aux);
                        fdb_pack.update_date(fdb_pack.get_year(), fdb_pack.get_start_block(), aux.block_number);
                        
                    }else
                    {
                        fdb_pack.insert_block(aux);
                        fdb_pack.insert_date(year_got, aux.block_number, aux.block_number);
                    }
                    
                    fdb_pack.get_date();
                    index=index+1;
                   */ 
                }
                
                // TimeUnit.NANOSECONDS.sleep(30);
               
            }
        }
    }  
    
    public class ProcessGrabInfo implements Runnable
    {
        private firebirdpack fdb_pack = new firebirdpack();
        
        @Override
        public void run() {
            
            fdb_pack.local_pak();
            
            while(true)
            {
                while(indexl.size() > 0)
                {
                    long number = indexl.remove(0);
                    block aux = grab_info(number);

                    int year_got = my_calendar_simple.get_year(aux.time);

                    fdb_pack.insert_block(aux);
                    fdb_pack.insert_date(year_got, aux.block_number);
        
              }
                
            }

        }
    }

}
