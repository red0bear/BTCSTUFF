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
public class scriptPubKey {
        
    @SuppressWarnings("unchecked")
    @JsonProperty("asm")
    public String asm;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hex")
    public String hex;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("reqSigs")
    public int reqSigs;

    @SuppressWarnings("unchecked")
    @JsonProperty("type")
    public String type;

    @SuppressWarnings("unchecked")
    @JsonProperty("addresses")
    public ArrayList<String> addresses = new ArrayList();    
    
}
