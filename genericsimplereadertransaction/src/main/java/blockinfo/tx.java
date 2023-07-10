/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class tx {
 
    @SuppressWarnings("unchecked")
    @JsonProperty("txid")
    public String txid;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hash")
    public String hash;

    @SuppressWarnings("unchecked")
    @JsonProperty("version")
    public int version;

    @SuppressWarnings("unchecked")
    @JsonProperty("size")
    public int size;

    @SuppressWarnings("unchecked")
    @JsonProperty("vsize")
    public int vsize; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("weight")
    public int weight; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("locktime")
    public long locktime; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("vin")
    public ArrayList<vin_base> vin = new ArrayList();   
    
    @SuppressWarnings("unchecked")
    @JsonProperty("vout")
    public ArrayList<vout_base> vout = new ArrayList();
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hex")
    public String hex;    
    
}
