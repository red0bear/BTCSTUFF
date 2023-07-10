package blockinfo;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author felipe
 */
public class block {
    
    public long block_number;
    
    public vout_base[] vout_ordered;
    
    public String original_data;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hash") 
    public String hash;
 
    @SuppressWarnings("unchecked")
    @JsonProperty("confirmations")     
    public long confirmations;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("strippedsize")     
    public int strippedsize;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("size")  
    public int size;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("weight")  
    public int weight;    
    
    @SuppressWarnings("unchecked")
    @JsonProperty("height")  
    public int height;
 
    @SuppressWarnings("unchecked")
    @JsonProperty("version")  
    public int version;    

    @SuppressWarnings("unchecked")
    @JsonProperty("versionHex")  
    public String versionHex; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("merkleroot")  
    public String merkleroot;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("tx")  
    public ArrayList<tx> tx = new ArrayList();    
   
    @SuppressWarnings("unchecked")
    @JsonProperty("time")     
    public long time;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("mediantime")     
    public long mediantime;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("nonce")     
    public long nonce;

    @SuppressWarnings("unchecked")
    @JsonProperty("bits")     
    public String bits;

    @SuppressWarnings("unchecked")
    @JsonProperty("difficulty")     
    public long difficulty;    

    @SuppressWarnings("unchecked")
    @JsonProperty("chainwork")     
    public String chainwork;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("nTx")     
    public long nTx;    

    @SuppressWarnings("unchecked")
    @JsonProperty("previousblockhash")     
    public String previousblockhash;    
    
    @SuppressWarnings("unchecked")
    @JsonProperty("nextblockhash")     
    public String nextblockhash;
}
