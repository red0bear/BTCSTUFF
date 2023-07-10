/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author felipe
 */
public class vin_base {

    @SuppressWarnings("unchecked")
    @JsonProperty("txid")
    public String txid;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("vout")
    public String vout;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("scriptSig")
    public scriptSig scriptSig;    
    
    @SuppressWarnings("unchecked")
    @JsonProperty("coinbase")
    public String coinbase;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("sequence")
    public long sequence;   
    
}
