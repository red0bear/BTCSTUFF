/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command_cli;

import blockinfo.block;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import utils.BubbleSortVout;
import utils.simple_calendar_format;

/**
 *
 * @author felipe
 */
public class commandexecute {
     
    private ProcessControlCommands handleclient = null;
    private Thread t_handleclient; 
    private String ROOT_PATH;
    private String line_panel = null;   
    private String line = null;
        
    private boolean debug = false;
    
    private boolean enable_remote = false; 
        
    private long increment_blockhash = 0;
    
    private ArrayList<block> block_info;    
    private Map<Integer,ArrayList<block>> blockchain_by_year;
    private int year_chck;
    
    private int year_start = 2009;
    private int year_end = 2009;
    private int month_start = 01;
    private int month_end = 01;
    
    private String result_hash;
    private String result;
    private block check;
            
    private String ip = "";
    private String user = "";
    private String pass = "";
            
    private simple_calendar_format my_calendar_simple;
            
    private String path_flo_cli = "bitcoin-cli" ;
    private String config_rpc   = "-rpcconnect=" + ip + " -rpcuser=" + user +" -rpcpassword="+ pass;            
    
    private String command;
    
    public commandexecute()
    {
        block_info = new ArrayList();
        blockchain_by_year = new HashMap<Integer,ArrayList<block>>();
        my_calendar_simple = new simple_calendar_format();
        my_calendar_simple.set_timezone_calendar_UTC();
    }
    
    public void set_root_path(String ROOT_PATH)
    {
        this.ROOT_PATH  = ROOT_PATH;
    }
    
    public void set_remote_user(String ip , String user , String pass)
    {
        this.ip = ip;
        this.user = user;
        this.pass = pass;
    }   
    
    public void en_dis_remote(boolean enable_remote)
    {
        this.enable_remote = enable_remote;
    }
    
    public void set_year(int year_start,int year_end)
    {
        this.year_start = year_start;
        this.year_end = year_end;
    }
    
    public void set_month(int month_start,int month_end)
    {
        this.month_start = month_start;
        this.month_end = month_end;
    }  
   
    public Map<Integer,ArrayList<block>> get_data_ready()
    {    
       return blockchain_by_year;
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
       handleclient.finish_thread();
    }
    
    public boolean is_running()
    {
        if(handleclient == null)
        {
            return true;
        }
        
        return handleclient.is_running();
    }
    
    public ArrayList<block> get_array_block()
    {
        return block_info;
    }
    /*
    public void build_text_pane(String aux)
    {
       StyledDocument doc = jTextPaneLog.getStyledDocument();

       //  Define a keyword attribute

       SimpleAttributeSet keyWord = new SimpleAttributeSet();
       StyleConstants.setForeground(keyWord, Color.RED);
       StyleConstants.setBackground(keyWord, Color.YELLOW);
       StyleConstants.setBold(keyWord, true);

       //  Add some text

       try
       {
        // doc.insertString(0, "Start of text\n", null );
         //doc.insertString(doc.getLength(), aux, null );
           doc.insertString(doc.getLength()>0?doc.getLength():0, aux, null);
       }
       catch(Exception e) { System.out.println(e); }    
    }
    */
    private block build_mapper(String result)
    {
            
        JSONParser parser = new JSONParser();  
        
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
    
    private void execute_getblockhash()
    {        
         if(enable_remote)
            command = ROOT_PATH + "bitcoin-cli" + " -rpcconnect=" + ip + " -rpcuser=" + user + " -rpcpassword=" + pass + " getblockhash " + Long.toString(increment_blockhash);
         else
            command = ROOT_PATH + "bitcoin-cli" + " -rpcuser=" + user + " -rpcpassword=" + pass  + " getblockhash " + Long.toString(increment_blockhash);
    }
    
    private void execute_getblock(String hash)
    {    
         if(enable_remote)
            command = ROOT_PATH + "bitcoin-cli" + " -rpcconnect=" + ip + " -rpcuser=" + user + " -rpcpassword=" + pass + " getblock "+ hash + " 2";
         else
            command = ROOT_PATH + "bitcoin-cli" + " -rpcuser=" + user + " -rpcpassword=" + pass  + " getblock "+ hash + " 2";
    }
    
    private void grab_info()
    {         
        //           
        execute_getblockhash();
        result_hash = execute_command();

        result_hash = result_hash.replaceAll("\n", "").trim();
                    
        execute_getblock(result_hash);
        result = execute_command(); 
        
        result = result.replaceAll("\n", "").trim();
        
        check = build_mapper(result);
        check.original_data = result;         
    }
     
    private String execute_command()
    {

        try {
            String line = null;
            line_panel = null;
             
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
    
    public class ProcessControlCommands implements Runnable
    {
    
        private boolean running = true;
        
        public boolean is_running()
        {
            return running;
        }
        
        public void finish_thread()
        {
            running = false;
        }

        @Override
        public void run() {
            
            System.out.println("Start");
                        
            grab_info();
            
            year_chck = my_calendar_simple.get_year(check.time);
             
            while(running)
            {
                   
                if( my_calendar_simple.get_year(check.time) >= year_start && my_calendar_simple.get_month(check.time) >= (month_start-1))
                {   
                       
                    System.out.println(result_hash + " --> " + my_calendar_simple.get_format_time_ff(check.time) + " --> " + increment_blockhash);
                    
                    //Hight value on vout
                     BubbleSortVout bubble_vout = new BubbleSortVout(check.tx);
                     bubble_vout.bubbleSort();
                     check.vout_ordered = bubble_vout.get_array_sorted();
                
                     check.block_number = increment_blockhash;
                
                    //Store Value
                
                    if(blockchain_by_year.containsKey(year_chck) == true)
                    {   
                        blockchain_by_year.get(year_chck).add(check);
                    }
                    else
                    {
                        block_info = new ArrayList();
                        block_info.add(check);
                        blockchain_by_year.put(year_chck, block_info);
                    }
                }
                
                increment_blockhash++;
                
                grab_info();
                
                year_chck = my_calendar_simple.get_year(check.time);
                 
                if(my_calendar_simple.get_month(check.time) >= (month_end-1) && my_calendar_simple.get_year(check.time) >= year_end)
                {
                    running = false;
                }
                
            }
            
            System.out.println("END");
            
        }
    
    }
}
