/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class getblockchaininfo {

    
    @SuppressWarnings("unchecked")
    @JsonProperty("chain") 
    public String chain;

    @SuppressWarnings("unchecked")
    @JsonProperty("blocks") 
    public long blocks;

    @SuppressWarnings("unchecked")
    @JsonProperty("headers") 
    public long headers;

    @SuppressWarnings("unchecked")
    @JsonProperty("bestblockhash") 
    public String bestblockhash; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("difficulty") 
    public String difficulty; 

    @SuppressWarnings("unchecked")
    @JsonProperty("mediantime") 
    public long mediantime;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("verificationprogress") 
    public String verificationprogress;

    @SuppressWarnings("unchecked")
    @JsonProperty("initialblockdownload") 
    public boolean initialblockdownload;

    @SuppressWarnings("unchecked")
    @JsonProperty("chainwork") 
    public String chainwork;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("size_on_disk") 
    public String size_on_disk;

    @SuppressWarnings("unchecked")
    @JsonProperty("pruned") 
    public boolean pruned;

    @SuppressWarnings("unchecked")
    @JsonProperty("softforks") 
    public ArrayList<softworksc> softforks;
 
    @SuppressWarnings("unchecked")
    @JsonProperty("bip9_softforks") 
    public bip9_softforksc bip9_softforks;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("warnings") 
    public String warnings;
    
}
