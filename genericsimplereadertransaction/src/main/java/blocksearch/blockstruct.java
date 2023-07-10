/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksearch;

import blockinfo.tx;
import blockinfo.vin_base;
import blockinfo.vout_base;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author felipe
 */
public class blockstruct {
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hash")
    public String hash;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("is_spent")
    public boolean is_spent;

    @SuppressWarnings("unchecked")
    @JsonProperty("block_number")    
    public long block_number;

    @SuppressWarnings("unchecked")
    @JsonProperty("time")    
    public long time;

    @SuppressWarnings("unchecked")
    @JsonProperty("hashhistory")    
    public ArrayList<String> hashhistory = new ArrayList();
    
    @SuppressWarnings("unchecked")
    @JsonProperty("block_infos")    
    public tx block_infos;
    
    public int year;
    public long block_number_first;
    public long block_number_last;
    public boolean done;

    public blockstruct()
    {
        block_number_first =0;
        block_number_last = 0;
        done = false;
    }
    
    public JSONObject build_json_cfg()
    {
        JSONObject build = new JSONObject();
        
        build.put("year", year);
        build.put("db", "db"+year+".txt");
        build.put("first_block_year", block_number_first);
        build.put("last_block_year", block_number_last);
        build.put("done", done);
        
        return build;
    }
    
    public JSONObject build_json()
    {
        JSONObject build = new JSONObject();
        
        build.put("hash", hash);
        build.put("block_number", block_number);
        build.put("time", time);
        build.put("is_spent", is_spent);
        
        JSONArray my_history_hash = new  JSONArray();
        
        for(String aux : hashhistory)
        {
            my_history_hash.put(aux);
        }
        
        build.put("hashhistory", my_history_hash);
        
        JSONObject block_tx = new JSONObject();
        
        block_tx.put("txid", block_infos.txid);
        block_tx.put("hash", block_infos.hash);         
        block_tx.put("version", block_infos.version);
        block_tx.put("size", block_infos.size);
        block_tx.put("vsize", block_infos.vsize);
        block_tx.put("weight", block_infos.weight);
        block_tx.put("locktime", block_infos.locktime);
         
        JSONArray my_vin = new  JSONArray();
        
        for(vin_base vin : block_infos.vin)
        {
            JSONObject build_vin = new JSONObject();
            
            if(vin.txid == null)
            {}
            else
            {
                build_vin.put("txid",vin.txid);
                build_vin.put("vout",vin.vout);

                JSONObject scriptsig = new JSONObject();

                scriptsig.put("asm", vin.scriptSig.asm);
                scriptsig.put("hex", vin.scriptSig.hex);

                build_vin.put("scriptSig", scriptsig);
            }
            
            build_vin.put("coinbase",vin.coinbase);
            build_vin.put("sequence",vin.sequence);
            
            my_vin.put(build_vin);
        }
        
        block_tx.put("vin", my_vin);
         
        JSONArray my_vout = new  JSONArray();
        JSONObject build_vout = new JSONObject();
        
        for(vout_base vout : block_infos.vout)
        {   
           build_vout.put("value", vout.value);
           build_vout.put("n", vout.n);
           
           JSONObject scriptPubKey = new JSONObject();
           
           scriptPubKey.put("asm", vout.scriptPubKey.asm);
           scriptPubKey.put("hex", vout.scriptPubKey.hex);
           scriptPubKey.put("reqSigs", vout.scriptPubKey.reqSigs);
           scriptPubKey.put("type", vout.scriptPubKey.type);    
           
           JSONArray my_addrs = new  JSONArray();
           
           for(String aux : vout.scriptPubKey.addresses)
           {
               my_addrs.put(aux);
           }
           
           scriptPubKey.put("addresses", my_addrs);
           
           build_vout.put("scriptPubKey", scriptPubKey);
           
           my_vout.put(build_vout);
        }   
        
        block_tx.put("vout", my_vout);
        
        build.put("tx",block_tx);
        
        build.put("hex", block_infos.hex);
        
        return build;
    }
    
    
}
