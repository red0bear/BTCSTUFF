/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blocksearch.blockstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author felipe
 */
public class simpledatabase {
    
    private ProcessSaveReadCommands handleclient = null;
    private Thread t_handleclient;
    
    private CopyOnWriteArrayList<blockstruct> value_txid = new CopyOnWriteArrayList();
    private JSONObject build_cfg;
    private RandomAccessFile writer;
    private RandomAccessFile reader;
    private RandomAccessFile writer_cfg;
   
    private boolean done = false;
    private long last_block_year = 0;
    private int last_year= 0;
    private long first_block_year= 0;
    
    public simpledatabase()
    {

    }
    
    public void start()
    {         
        handleclient = new ProcessSaveReadCommands();        
        t_handleclient = new Thread(handleclient);
        t_handleclient.setDaemon(true);
        t_handleclient.start();     
    }   
    
    public void stop()
    {
       handleclient.finish_execution();
    }       
    
    public long get_last_block()
    {
        return last_block_year;
    }
     
    public long get_first_block()
    {
        return first_block_year;
    }
    
    public boolean is_done()
    {
        return done;
    }
    
          
    public void load_cfg()
    {
        RandomAccessFile read_cfg;
        File my_cfg = new File("cfg/");
        ArrayList<JSONObject> list_json = new ArrayList();
        
        if(my_cfg.listFiles().length ==0)
            return;
        
        for(File aux : my_cfg.listFiles())
        {
            try {
                read_cfg =  new RandomAccessFile(aux.getAbsolutePath(), "rw");
                
                String result = read_cfg.readLine();
                
                result = result.trim().replace("\n", "");
                
                list_json.add(new JSONObject(result));
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        for(JSONObject aux : list_json)
        {
            if(aux.getInt("year") > last_year)
            {
                last_year = aux.getInt("year");
                first_block_year = aux.getLong("first_block_year");
                last_block_year = aux.getLong("last_block_year");
                done = aux.getBoolean("done");
            }
            
            
        }
        
        if(done)
        {
            last_block_year = last_block_year+1;
        }
        else
        {
            last_block_year = last_block_year - 2;
            check_data_consistence(last_year, last_block_year);
            
        }
                
    }

    public void set_new_db_by_year(int year)
    {    
        try {
            if(writer == null)
                writer = new RandomAccessFile("db/db"+year+".txt", "rw");
            else
            {
                writer.close();
                writer = new RandomAccessFile("db/db"+year+".txt", "rw");
            }
                            
            if(writer.length() > 0)
            {
                writer.seek(writer.length());
            }
                            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void set_cfg_actual(String walker)
    {
        build_cfg = new JSONObject(walker);
       
    }
    
    public void check_data_consistence(int year, long block_numer_reach)
    {

        String line;
        boolean reached = true;
        try {
            reader = new RandomAccessFile("db/db"+year+".txt", "rw");
            writer = new RandomAccessFile("db/db"+year+"aux.txt", "rw");
            
            while((line = reader.readLine()) != null && reached)
            {
                
                line = line.replaceAll("\n", "");
                if(new JSONObject(line).getLong("block_number") == block_numer_reach)
                {
                    reached = false;
                    writer.close();
                    
                    new File("db/db"+year+".txt").delete();
                    new File("db/db"+year+"aux.txt").renameTo(new File("db/db"+year+".txt"));
                }
                else
                {
                    writer.write((line+"\n").getBytes());
                }
            }
            
            reader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void wirte_data_cfg(String value ,int year)
    {
        
        try {
            writer_cfg = new RandomAccessFile("cfg/cfg"+year+".txt", "rw");
            writer_cfg.seek(0);
            writer_cfg.write((value+"\n").getBytes());
            writer_cfg.close();
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String read_data_cfg()
    {
        try {
            writer_cfg.seek(0);
            return writer_cfg.readLine();
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }    
    
    
    public void check_pointer_file()
    {
         try {
            
            if(writer.length() > 0)
            {
                writer.seek(writer.length());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void wirte_data(String value)
    {
        try {          
            writer.write((value+"\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String read_data(long index)
    {
        try {
            writer.seek(index);
            return writer.readLine();
        } catch (IOException ex) {
            Logger.getLogger(simpledatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    } 
    
    public boolean is_write()
    {
        if(value_txid.size() > 0)
        {
            return false;
        }
        
        return true;
    }
        
    public void push_data(blockstruct value_tx)
    {
        value_txid.add(value_tx);
    }    
    
    public class ProcessSaveReadCommands implements Runnable
    {

        private boolean running = true;
                
        public void finish_execution()
        {
           running = false; 
        }
        
        @Override
        public void run() {
            
            while(running)
            {
                while(value_txid.size() > 0)
                {
                    build_cfg.put("last_block_year", value_txid.get(0).block_number);
                    wirte_data_cfg(build_cfg.toString(),build_cfg.getInt("year"));
                    wirte_data(value_txid.remove(0).build_json().toString()); 
                }
                
                try { 
                    TimeUnit.NANOSECONDS.sleep(10);
                } catch (InterruptedException ex) {
                  //  Logger.getLogger(RSTRUNNING.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
            
        }
    
    }
}
