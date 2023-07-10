/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import blockinfo.block;
import blockinfo.tx;
import blockinfo.vin_base;
import blockinfo.vout_base;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class firebirdpack {
        
    private Connection connection;
    
    private int year_got;
    private long start_block;
    private long end_block;
    
    private String transaction;
    private String tx_block;
    
    private String tx_vin;
    private String tx_scriptsig;
    
    private String tx_vout;
    private String tx_vout_scriptubkey;
    private String tx_vout_addr;
    
    
    private String generic_insert = "insert or update into ";
    
    private simple_calendar_format calendarf;
    
    public firebirdpack()
    {
        calendarf = new simple_calendar_format();
        calendarf.set_timezone_calendar_UTC();
    }
    
    
    public void local_pak()
    {
        try {
            
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            
            connection = DriverManager.getConnection(
                    "jdbc:firebirdsql:localhost:data.fdb",
                    "ASDF", "000000");
            
           // boolean test = connection.isClosed();   
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void insert_block(block blockv)
    {
         
        try {
            
            java.sql.Statement STMNT = connection.createStatement();      

          //  transaction = "insert into BLOCK_TRANSACTION values ("+blockv.block_number+","++",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
          /*  INSERT INTO BLOCK_TRANSACTION (BLOCK_NUMBER, 
                                             DATA_GOT, 
                                             HASHB, 
                                             CONFIRMATIONS,
                                             STRIPPEDSIZE, 
                                             SIZEB, 
                                             WEIGHT,
                                             HEIGHT, 
                                             VERSIONB, 
                                             VERSIONHEX, 
                                             MEKLEROOT, 
                                             TIMEB,
                                             MEDIANTIME, 
                                             NONCE, 
                                             BITS, 
                                             DIFFICULTY, 
                                             CHAINWORK, 
                                             NTX, 
                                             PREVIOUSBLOCKHASH,
                                             NEXTBLOCKHASH
                                            )
                                    VALUES (
                                        'BLOCK_NUMBER*', 
                                        'DATA_GOT*', 
                                        'HASHB*', 
                                        'CONFIRMATIONS*', 
                                        'STRIPPEDSIZE*', 
                                        'SIZEB*', 
                                        'WEIGHT*', 
                                        'HEIGHT*', 
                                        'VERSIONB*', 
                                        'VERSIONHEX*', 
                                        'MEKLEROOT*', 
                                        'TIMEB*', 
                                        'MEDIANTIME*', 
                                        'NONCE*', 
                                        'BITS*', 
                                        'DIFFICULTY*', 
                                        'CHAINWORK*', 
                                        'NTX*', 
                                        'PREVIOUSBLOCKHASH*', 
                                        'NEXTBLOCKHASH*'
                                    );
            */
            transaction = "update or insert into BLOCK_TRANSACTION ("
                    + "BLOCK_NUMBER,"
                    //+ "DATA_GOT,"
                    + "HASHB,"
                    + "CONFIRMATIONS,"
                    + "STRIPPEDSIZE,"
                    + "SIZEB,"
                    + "WEIGHT,"
                    + "HEIGHT,"
                    + "VERSIONB,"
                    + "VERSIONHEX,"
                    + "MEKLEROOT,"
                    + "TIMEB,"
                    + "MEDIANTIME,"
                    + "NONCE,"
                    + "BITS,"
                    + "DIFFICULTY,"
                    + "CHAINWORK,"
                    + "NTX,"
                    + "PREVIOUSBLOCKHASH,"
                    + "NEXTBLOCKHASH"
                    + ") values (" + blockv.block_number  + "," 
                                  // + "'" + Base64.getEncoder().encodeToString(blockv.original_data.getBytes())+ "'" + ","
                                   +  "'" + blockv.hash + "'" + ","                    
                                   + blockv.confirmations + ","
                                   + blockv.strippedsize + ","
                                   + blockv.size + ","
                                   + blockv.weight + ","
                                   + blockv.height + ","
                                   + blockv.version + ","                    
                                   + "'" + blockv.versionHex + "'" + ","
                                   + "'" + blockv.merkleroot + "'" + ","
                                   + "'" + blockv.time + "'" + ","
                                   + "'" + blockv.mediantime + "'" + ","
                                   + blockv.nonce + ","
                                   + "'" + blockv.bits + "'" + ","
                                   + blockv.difficulty + ","
                                   + "'" + blockv.chainwork + "'" + ","
                                   + blockv.nTx + ","
                                   + "'" + blockv.previousblockhash + "'" + ","
                                   + "'" + blockv.nextblockhash + "'" +");";

             
            STMNT.execute(transaction);

            for(tx aux : blockv.tx)
            {

                tx_block = "insert or update into TX_TRANSACTION (BLOCK_BLOCK_NUMBER,TXID,HASHTX,VERSIONTX,SIZETX,VSIZETX,WEIGHTTX,LOCKTIMETX) values (" 
                                                                + blockv.block_number  + "," 
                                                                + "'" + aux.txid  + "'" + ","
                                                                + "'" + aux.hash  + "'" + ","
                                                                + aux.version + ","
                                                                + aux.size + ","
                                                                + aux.vsize + ","
                                                                + aux.weight + ","
                                                                + aux.locktime + ")"; // + ","
                                                                 //+ "'" + aux.hex  + "'" + ")";    
                
                STMNT.execute(tx_block);
                
                
                boolean boolean_vin_txid_empty;
                
                for(vin_base vin_aux:aux.vin)
                {
                        
                   String aux_txid = (vin_aux.txid == null)?"empty"+blockv.block_number:vin_aux.txid;
                        
                  boolean_vin_txid_empty = (vin_aux.txid == null);
                   
                   if(boolean_vin_txid_empty)
                   {
                                       
                       tx_vin = "insert or upadte into TX_VIN_TYPE_0 (BLOCK_NUMBER_TX_VINT0,TX_TXID,COINBASEVIN,SEQUENCEVIN) values (" + blockv.block_number  + "," 
                                                            + "'" + aux.txid  + "'" + ","
                                                            + "'" + vin_aux.coinbase  + "'" + ","
                                                           + vin_aux.sequence +")"; 
                   }
                   else
                   {
                       tx_vin = "insert into TX_VIN (BLOCK_NUMBER_TX_VIN,TX_TXID_VIN,TXID,VOUT,COINBASEVIN,SEQUENCEVIN) values (" + blockv.block_number  + "," 
                                                            + "'" + aux.txid  + "'" + ","
                                                            + "'" + vin_aux.txid  + "'" + ","
                                                            + "'" + vin_aux.vout  + "'" + ","
                                                            + "'" + vin_aux.coinbase  + "'" + ","
                                                           + vin_aux.sequence +")"; 
                   } 
                    STMNT.execute(tx_vin);
                                       
                    
                    if(vin_aux.scriptSig == null)
                    {}
                    else
                    {
                        
                        String aux_scriptSig_asm = (vin_aux.scriptSig.asm == null)?"empty":vin_aux.scriptSig.asm;
                        String aux_scriptSig_hex = (vin_aux.scriptSig.hex == null)?"empty":vin_aux.scriptSig.hex;

                        tx_scriptsig = "insert into TX_VIN_SCRIPTSIG (BLOCK_NUMBER_TX_VINSIG,TXID_TRANSACTION,TXID_TX,ASM,HEX) values ("  
                                                                                   + blockv.block_number  + "," 
                                                                                   + "'" + aux.txid  + "'" + ","
                                                                                   + "'" + aux_txid  + "'" + ","
                                                                                   + "'" + aux_scriptSig_asm  + "'" + ","
                                                                                   + "'" + aux_scriptSig_hex  + "'" + ")"; 

                        STMNT.execute(tx_scriptsig);
                    }
                    
                }
                
                for(vout_base vout_aux:aux.vout)
                {
                        
                    tx_vout = "insert or update into TX_VOUT (BLOCK_NUMBER_TXVOUT,TX_TXID,VALUEVOUT,N) values (" + blockv.block_number  + "," 
                                                             + "'" + aux.txid  + "'" +","
                                                             + "'" + vout_aux.value  + "'" + ","
                                                             + "'" + vout_aux.n  + "'" + ")"; 
                        
                    STMNT.execute(tx_vout);
                        
                    tx_vout_scriptubkey = "insert or update into TX_VOUT_SCRIPTPUBKEY (BLOCK_NUMBER_TXVOUT_SCP_PUBKEY,TXID_TRANSACTION_TX_VOUTKEY,ASM,HEX,REQSIGS,\"TYPE\") values (" + blockv.block_number  + "," 
                                                                 + "'" + aux.txid + "'" + ","
                                                                 + "'" + vout_aux.scriptPubKey.asm  + "'" + ","
                                                                 + "'" + vout_aux.scriptPubKey.hex  + "'" + ","
                                                                 + vout_aux.scriptPubKey.reqSigs + ","
                                                                 + "'" + vout_aux.scriptPubKey.type  + "'" + ")"; 
                    STMNT.execute(tx_vout_scriptubkey);
                        
                    for(String addr:vout_aux.scriptPubKey.addresses)
                    {
                        tx_vout_addr = "insert or update  into TX_VOUT_ADDRESS (BLOCK_NUMBER_TX_VOUTADDR,TX_TXID_VOUT_ADDR,ADDRVALUEVOUT) values (" + blockv.block_number  + "," 
                                                                  + "'" + aux.txid  + "'" + ","
                                                                  + "'" + addr  + "'" + ")";  
                        STMNT.execute(tx_vout_addr);
                        
                                                
                        tx_vout_addr = "insert into TX_HISTORY_VALUES (BLOCK_NUMBER_,TX_TXID,TIMEGEN,VALUEVOUT,ADDRVOUT,TIMERH) values (" + blockv.block_number  + "," 
                                                                  + "'" + aux.txid       + "'" + ","
                                                                  + "'" + blockv.time    + "'" + ","
                                                                  + "'" + vout_aux.value + "'" + "," 
                                                                  + "'" + addr  + "'" + ","
                                                                  + "'" + calendarf.get_format_time_ff(blockv.time) + "'" +")";  
                        STMNT.execute(tx_vout_addr);
                    }
                        
                }                
                
            }
            
            STMNT.close();
           
        } catch (SQLException ex) {
            
            System.out.println("ERROR --> " + tx_block);
            
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void get_block()
    {
    
    }    
    
    public void update_block()
    {
    
    }
    
    public void delete_block()
    {
    
    }
    
    
    public void insert_date(int year ,long last_block)
    {
        
        String put_info = "insert into DATE_CONTROL (DATECONTROL, BLOCK_TRANSACTION_NUMBER) values (" + year  +","+ last_block +")";
        
        try {
            java.sql.Statement STMNT = connection.createStatement();
            
            STMNT.execute(put_info);
            STMNT.close();
        } catch (SQLException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean get_date()
    {
        //ODDER BY 
        //select * from DATE_CONTROL ORDER BY BLOCK_TRANSACTION_NUMBER ASC
        
        //
        //select * from TX_VOUT where cast (VALUEVOUT as BIGINT)  >= 1000.000
        
        //
        //select * from TX_VOUT WHERE BLOCK_NUMBER between 0 and 32489
        
        //
        //select * from TX_VOUT where cast (VALUEVOUT as BIGINT)  >= 1000.000 and block_number <= 32489 
        
        //
        //select distinct (block_number),TX_TXID from TX_VOUT where cast (VALUEVOUT as BIGINT)  >= 1000.000 and block_number <= 32489 
        
        //select distinct (TX_VOUT.BLOCK_NUMBER_TXVOUT),TX_VOUT.TX_TXID,BLOCK_TRANSACTION.TIMEB from TX_VOUT,BLOCK_TRANSACTION where cast (VALUEVOUT as BIGINT)  >= 1000.000 and block_number <= 32489 and BLOCK_TRANSACTION.BLOCK_NUMBER = TX_VOUT.BLOCK_NUMBER_TXVOUT 
        
        String put_info = "select count(*) from DATE_CONTROL"; 
        
        try {
            
            java.sql.Statement STMNT = connection.createStatement();
            
            ResultSet results = STMNT.executeQuery(put_info);
            //STMNT.getResultSet().next();
            
            if(results.next())
            {
                        
                int result = results.getInt(1);

                if(result > 0)
                {
                    put_info = "select * from DATE_CONTROL ORDER BY DATECONTROL desc";
                    results = STMNT.executeQuery(put_info);

                    if(results.next())
                    {
                        year_got = results.getInt("DATECONTROL");
                        //start_block = results.getInt("START_BLOCK");
                        end_block = results.getInt("BLOCK_TRANSACTION_NUMBER");
                    }
                    
                    STMNT.close();
                    return true;
                }
                else
                {
                    year_got = 0;
                    start_block = 0;
                    end_block = 0;
                }
            
            }
            STMNT.close();
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return false;
    
    }    
    
    public void update_date(int year,long first_block ,long last_block)
    {
             
        String info = "update DATE_CONTROL set START_BLOCK = " + first_block + ", END_BLOCK =" + last_block + " where DATE_CONTROL.DATECONTROL =" + year;
            
        try {
            java.sql.Statement STMNT = connection.createStatement();
            STMNT.execute(info);
        } catch (SQLException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void delete_date(int year)
    {
         String info = "delete from DATE_CONTROL where DATE_CONTROL.DATECONTROL starting '" + 2010 + "'";

         try {
            java.sql.Statement STMNT = connection.createStatement();
            STMNT.execute(info);
            STMNT.close();
         } catch (SQLException ex) {
            Logger.getLogger(firebirdpack.class.getName()).log(Level.SEVERE, null, ex);
         }
    }    
    
    
    public int get_year()
    {
        return year_got;
    }
     
    public long get_start_block()
    {
        return start_block;
    }
    
    public long get_end_block()
    {
        return end_block;
    }    
    
    
}
