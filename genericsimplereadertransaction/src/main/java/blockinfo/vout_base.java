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
public class vout_base {
  
        
    @SuppressWarnings("unchecked")
    @JsonProperty("value")
    public String value;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("n")
    public String n;

    @SuppressWarnings("unchecked")
    @JsonProperty("scriptPubKey")
    public scriptPubKey scriptPubKey;

}
