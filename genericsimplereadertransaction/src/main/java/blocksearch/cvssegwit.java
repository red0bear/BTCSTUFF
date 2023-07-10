/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blocksearch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author felipe
 */
public class cvssegwit {
      
    @SuppressWarnings("unchecked")
    @JsonProperty("status") 
    public String status;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("startTime") 
    public long startTime;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("timeout") 
    public long timeout;

    @SuppressWarnings("unchecked")
    @JsonProperty("since") 
    public long since;    
}
