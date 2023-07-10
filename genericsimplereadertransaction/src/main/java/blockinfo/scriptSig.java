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
public class scriptSig {
    
    @SuppressWarnings("unchecked")
    @JsonProperty("asm")
    public String asm; 
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hex")
    public String hex; 
    
}
