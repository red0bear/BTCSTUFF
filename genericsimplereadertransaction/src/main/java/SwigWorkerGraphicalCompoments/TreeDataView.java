/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwigWorkerGraphicalCompoments;

import blockinfo.block;
import blockinfo.tx;
import blockinfo.vin_base;
import blockinfo.vout_base;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author felipe
 */
public class TreeDataView {
   
    private DefaultMutableTreeNode data_top;
    private DefaultMutableTreeNode data_tx;
    private DefaultMutableTreeNode data_info;
    
    private DefaultMutableTreeNode data_tx_array;
    private DefaultMutableTreeNode data_tx_vin;
    private DefaultMutableTreeNode data_tx_vin_scriptSig;
    private DefaultMutableTreeNode data_tx_vin_counter;
    
    private DefaultMutableTreeNode data_tx_vout;
    private DefaultMutableTreeNode data_tx_vout_counter;
    private DefaultMutableTreeNode data_tx_vout_scriptPubkey;
    private DefaultMutableTreeNode data_tx_vout_address;
    
    private JTree jTreeBlockInfo;
    private block info;        
    
    private boolean first_time = true;
    
    private ProcessPostDataView handleclient = null;
    private Thread t_handleclient;
    
    public TreeDataView(JTree jTreeBlockInfo)
    {
       this.jTreeBlockInfo = jTreeBlockInfo;
    }
    
    public void start()
    {
        handleclient = new ProcessPostDataView();        
        t_handleclient = new Thread(handleclient);
        t_handleclient.setDaemon(true);
        t_handleclient.start();      
    }
    
    public void stop()
    {
    
    }
    
    public void set_selected_block_info(block info)
    {
        this.info = info;
    }
    
    
    private DefaultMutableTreeNode build_tx(tx array_info, int counter)
    {
         DefaultMutableTreeNode data_tx_array_l;
        //tx array x
        data_tx_array_l = new DefaultMutableTreeNode(counter);
                      
        data_tx_array_l.add(new DefaultMutableTreeNode("txid:"+array_info.txid));
                
        data_tx_array_l.add(new DefaultMutableTreeNode("hash:"+array_info.hash));

        data_tx_array_l.add(new DefaultMutableTreeNode("version:"+array_info.version));                
            
        data_tx_array_l.add(new DefaultMutableTreeNode("size:"+array_info.size));

        data_tx_array_l.add(new DefaultMutableTreeNode("vsize:"+array_info.vsize));

        data_tx_array_l.add(new DefaultMutableTreeNode("weight:"+array_info.weight));       

        data_tx_array_l.add(new DefaultMutableTreeNode("locktime:"+array_info.locktime));  
        
        data_tx_array_l.add(build_tx_vin(array_info.vin));
        data_tx_array_l.add(build_tx_vout(array_info.vout));
        
        data_tx_array_l.add(new DefaultMutableTreeNode("hex:"+array_info.hex));
        
        return data_tx_array_l;
    
    }
    
    private DefaultMutableTreeNode build_tx_vin(ArrayList<vin_base> array_info)
    {
        DefaultMutableTreeNode data_tx_vin_l;
        
        data_tx_vin_l = new DefaultMutableTreeNode("vin");
                
        int counter_v = 0;
                
        for(vin_base in_vin : array_info)
        {
            if(in_vin.coinbase == null)
            {
                data_tx_vin_l.add(build_tx_vin_format_2(in_vin,counter_v));
            }
            else
            {
                data_tx_vin_l.add(build_tx_vin_format_1(in_vin,counter_v));
            }
            
            counter_v++;
        }
         
        return data_tx_vin_l;
    }
    
    private DefaultMutableTreeNode build_tx_vin_format_1(vin_base in_vin, int counter_v)
    {
        
        DefaultMutableTreeNode data_tx_vin_counter_l;
        
        data_tx_vin_counter_l = new DefaultMutableTreeNode(counter_v);

        data_tx_vin_counter_l.add(new DefaultMutableTreeNode("coinbase:"+in_vin.coinbase));
        data_tx_vin_counter_l.add(new DefaultMutableTreeNode("sequence:"+in_vin.sequence));  
        
        return data_tx_vin_counter_l;
    }
    
    private DefaultMutableTreeNode build_tx_vin_format_2(vin_base in_vin, int counter_v)
    {
        DefaultMutableTreeNode data_tx_vin_counter_l;
        
        data_tx_vin_counter_l = new DefaultMutableTreeNode(counter_v);
                    
        data_tx_vin_counter_l.add(new DefaultMutableTreeNode("txid:"+in_vin.txid));
        data_tx_vin_counter_l.add(new DefaultMutableTreeNode("vout:"+in_vin.vout)); 
        
        DefaultMutableTreeNode data_tx_vin_scriptSig_l;
        
        data_tx_vin_scriptSig_l = new DefaultMutableTreeNode("scriptSig");
        
        data_tx_vin_scriptSig_l.add(new DefaultMutableTreeNode("asm:"+in_vin.scriptSig.asm));
        data_tx_vin_scriptSig_l.add(new DefaultMutableTreeNode("hex:"+in_vin.scriptSig.hex));
        
        data_tx_vin_counter_l.add(data_tx_vin_scriptSig_l);
        
        data_tx_vin_counter_l.add(new DefaultMutableTreeNode(in_vin.sequence));
        
        return data_tx_vin_counter_l;    
    }
    
    private DefaultMutableTreeNode build_tx_vout(ArrayList<vout_base> array_info)
    {
        DefaultMutableTreeNode data_tx_vout_l;
        
        data_tx_vout_l = new DefaultMutableTreeNode("vout");
        
        int counter_v = 0;
                
        for(vout_base in_vout : array_info)
        {
            data_tx_vout_l.add(build_tx_vout_format(in_vout,counter_v));
            counter_v++;
        }
        
        return data_tx_vout_l;
    }
    
    private DefaultMutableTreeNode build_tx_vout_format(vout_base in_vout, int counter_v)
    {
         DefaultMutableTreeNode data_tx_vout_counter_l;
         
        data_tx_vout_counter_l = new DefaultMutableTreeNode(counter_v);
        
        data_tx_vout_counter_l.add(new DefaultMutableTreeNode("value:"+in_vout.value));
        data_tx_vout_counter_l.add(new DefaultMutableTreeNode("n:"+in_vout.n));
        
        DefaultMutableTreeNode data_tx_vout_scriptPubkey_l;
        
        data_tx_vout_scriptPubkey_l = new DefaultMutableTreeNode("scriptPubKey");
        
        data_tx_vout_scriptPubkey_l.add(new DefaultMutableTreeNode("asm:"+in_vout.scriptPubKey.asm));
        data_tx_vout_scriptPubkey_l.add(new DefaultMutableTreeNode("hex:"+in_vout.scriptPubKey.hex));
        data_tx_vout_scriptPubkey_l.add(new DefaultMutableTreeNode("reqSigs:"+in_vout.scriptPubKey.reqSigs));
        data_tx_vout_scriptPubkey_l.add(new DefaultMutableTreeNode("type:"+in_vout.scriptPubKey.type)); 
        
        DefaultMutableTreeNode data_tx_vout_address_l;
        
        data_tx_vout_address_l = new DefaultMutableTreeNode("addresses");
        
        for(String aux : in_vout.scriptPubKey.addresses)
        {
            data_tx_vout_address_l.add(new DefaultMutableTreeNode(aux));
        }
        
        data_tx_vout_scriptPubkey_l.add(data_tx_vout_address_l);
        
        data_tx_vout_counter_l.add(data_tx_vout_scriptPubkey_l);
        
        return data_tx_vout_counter_l;
    }
    
    public class ProcessPostDataView implements Runnable
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
        public void run() 
        {
            data_top = new DefaultMutableTreeNode("");
            
            data_top.add(new DefaultMutableTreeNode("hash:"+info.hash));
            
            data_top.add(new DefaultMutableTreeNode("confirmations:"+info.confirmations));
            
            data_top.add(new DefaultMutableTreeNode("strippedsize:"+info.strippedsize));  
            
            data_top.add(new DefaultMutableTreeNode("size:"+info.size)); 
            
            data_top.add(new DefaultMutableTreeNode("weight:"+info.weight));   
            
            data_top.add(new DefaultMutableTreeNode("height:"+info.height)); 

            data_top.add(new DefaultMutableTreeNode("version:"+info.version)); 
            
            data_top.add(new DefaultMutableTreeNode("versionHex:"+info.versionHex)); 

            data_top.add(new DefaultMutableTreeNode("merkleroot:"+info.merkleroot));
            
            data_tx = new DefaultMutableTreeNode("tx");
            data_top.add(data_tx);            
           
            int counter = 0;
           
            for(tx infos: info.tx)
            {
                data_tx.add(build_tx(infos,counter));
                counter++;
            }
            
            data_top.add( new DefaultMutableTreeNode("time:"+info.time));
            data_top.add( new DefaultMutableTreeNode("mediantime:"+info.mediantime));
            data_top.add( new DefaultMutableTreeNode("nonce:"+info.nonce));
            data_top.add( new DefaultMutableTreeNode("bits:"+info.bits));
            data_top.add( new DefaultMutableTreeNode("difficulty:"+info.difficulty));
            data_top.add( new DefaultMutableTreeNode("chainwork:"+info.chainwork));
            data_top.add( new DefaultMutableTreeNode("nTx:"+info.nTx));
            data_top.add( new DefaultMutableTreeNode("previousblockhash:"+info.previousblockhash));
            data_top.add( new DefaultMutableTreeNode("nextblockhash:"+info.nextblockhash));
            
            jTreeBlockInfo.setModel(new DefaultTreeModel(data_top));
        }
        
    }    
}
