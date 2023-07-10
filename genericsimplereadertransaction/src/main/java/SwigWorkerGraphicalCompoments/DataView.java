/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwigWorkerGraphicalCompoments;

import blockinfo.block;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import command_cli.commandexecute;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.simple_calendar_format;

/**
 *
 * @author felipe
 */
public class DataView {
    
    private JTextPane jTxtPanelFormmatedJsonData;
    private JTextPane jTextPaneHexDecode;                
    private JButton jBtnLoadData;
    private JButton jBtnLoadByDayData;
    
    private JList jListBlockNumberData;
    private JTextField jTFDayData;
    private JComboBox jCmBoxDayData;
    private JComboBox jCmBoxMonthData;
    private JComboBox jCmBoxYearData;
    
    private JLabel jLabelHash;
    private JLabel jLabelTime;
    private JLabel jLabelMendianTime;
    private JLabel jLabelTxid;
    private JLabel jLabelPreviousBlockHash;
    private JLabel jLabelNextBlockHash;
    
    private JTable jTableMonth;
     
    private DefaultListModel model = new DefaultListModel();
    private DefaultComboBoxModel modelcbx;
    private DefaultTableModel modelt;
     
    private Map<Integer,ArrayList<block>> data;
    private Map<Long,block> data_on_combobox;
    private commandexecute grab_data;
    
    private boolean first_time = true;
    
    private ProcessPostDataView handleclient = null;
    private Thread t_handleclient;
            
    private final simple_calendar_format my_calendar_simple;

    private int row_selected = 0,columns_selected = 0; 
    private int day_selected;
     
    private String command_button;
    
    public DataView(
                    JTextPane jTxtPanelFormmatedJsonData,
                    JList jListBlockNumberData,
                    JTable jTableMonth,
                    JTextPane jTextPaneHexDecode,
                    JButton jBtnLoadData,
                    JButton jBtnLoadByDayData,
                    JComboBox jCmBoxDayData,
                    JComboBox jCmBoxMonthData,
                    JComboBox jCmBoxYearData,
                    JLabel jLabelHash,
                    JLabel jLabelTime,
                    JLabel jLabelMendianTime,
                    JLabel jLabelTxid,
                    JLabel jLabelPreviousBlockHash,
                    JLabel jLabelNextBlockHash
                   )
    {
        data_on_combobox = new HashMap<Long,block>();
        
        
        this.jTxtPanelFormmatedJsonData = jTxtPanelFormmatedJsonData;
        this.jTextPaneHexDecode = jTextPaneHexDecode;
        this.jListBlockNumberData = jListBlockNumberData;
        this.jCmBoxDayData = jCmBoxDayData;
        this.jCmBoxMonthData = jCmBoxMonthData;
        this.jCmBoxYearData = jCmBoxYearData;
        
        this.jBtnLoadData = jBtnLoadData;
        this.jBtnLoadByDayData = jBtnLoadByDayData;
        
        this.jLabelHash = jLabelHash;
        this.jLabelTime = jLabelTime;
        this.jLabelMendianTime = jLabelMendianTime;
        this.jLabelTxid = jLabelTxid;
        this.jLabelPreviousBlockHash = jLabelPreviousBlockHash;
        this.jLabelNextBlockHash     = jLabelNextBlockHash;
        
        my_calendar_simple = new simple_calendar_format();
        my_calendar_simple.set_timezone_calendar_UTC();
        
        this.jTableMonth = jTableMonth;
        String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        modelt = new DefaultTableModel(null,columns);
        this.jTableMonth.setModel(modelt);
        this.jTableMonth.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jTableMonth.setColumnSelectionAllowed(true);
        this.jTableMonth.setRowSelectionAllowed(false);
    }
 
    public void start()
    {        
        handleclient = new ProcessPostDataView();        
        t_handleclient = new Thread(handleclient);
        t_handleclient.setDaemon(true);
        t_handleclient.start();      
    }
    
    public void stop()
    {
    
    }
    
    public void set_command(String command_button)
    {
        this.command_button = command_button;
    }
    
    public void set_grab_data(commandexecute grab_data)
    {
        this.grab_data = grab_data;
    }
    
    public block get_data_selected()
    {
        long test =  (Long) jListBlockNumberData.getModel().getElementAt(jListBlockNumberData.getSelectedIndex());
        return data_on_combobox.get(test);    
    }
    
    public void get_selected_block()
    {
        
        if(data_on_combobox == null)
        {
        
        }
        else
        {
        
           ObjectMapper mapper = new ObjectMapper()
                  // .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                   .configure(SerializationFeature.INDENT_OUTPUT, true);
            
           long test =  (Long) jListBlockNumberData.getModel().getElementAt(jListBlockNumberData.getSelectedIndex());
           String result =  data_on_combobox.get(test).original_data;

           updateMonth(data_on_combobox.get(test).time);
          
            try {
                 
                 
                jLabelHash.setText(data_on_combobox.get(test).hash);
               
                jLabelTime.setText(my_calendar_simple.get_format_time_ff(data_on_combobox.get(test).time));
                 
                jLabelMendianTime.setText(my_calendar_simple.get_format_time_ff(data_on_combobox.get(test).mediantime));
                
                jLabelTxid.setText(data_on_combobox.get(test).tx.get(0).txid);
                
                if(data_on_combobox.get(test).previousblockhash == null)
                {
                    jLabelPreviousBlockHash.setText("NONE");
                    jTextPaneHexDecode.setText(unHex(data_on_combobox.get(test).tx.get(0).hex));
                }
                else
                {
                    jTextPaneHexDecode.setText("");
                    jLabelPreviousBlockHash.setText(data_on_combobox.get(test).previousblockhash);
                }
                
                jLabelNextBlockHash.setText(data_on_combobox.get(test).nextblockhash);
                
                Object pojo = mapper.readValue(result, Object.class);
                jTxtPanelFormmatedJsonData.setText(mapper.writeValueAsString(pojo));
                
            } catch (JsonProcessingException ex) {
                Logger.getLogger(DataView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DataView.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    private void updateMonth(long value) 
    {        
        my_calendar_simple.set_timezone_calendar_UTC();

        int startDay = my_calendar_simple.get_day_of_week(value);
        int numberOfDays = my_calendar_simple.get_day_of_month(value);
        int weeks = my_calendar_simple.get_week_of_month(value);

        day_selected = my_calendar_simple.get_day(value);
        
        
        modelt.setRowCount(0);
        modelt.setRowCount(weeks+1);

        int i = startDay;
        
        for(int day=1;day<=numberOfDays;day++)
        {
           modelt.setValueAt(day, i/7 , i%7 );  
            
            if(day_selected  == day) 
            {    
                row_selected = i/7;
                columns_selected = i%7;
            }
            
            i = i + 1;
        }
        
        jTableMonth.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
          @Override
          public Component getTableCellRendererComponent(JTable table,
              Object value, boolean isSelected, boolean hasFocus, int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            
            if (row_selected == row && col == columns_selected) {
              setBackground(Color.CYAN);
              setForeground(Color.WHITE);
            } else {
              setBackground(table.getBackground());
              setForeground(table.getForeground());
            }       
            return this;
          }   
        });
        
    }

    public String unHex(String arg) {        

        String str = "";
        for(int i=0;i<arg.length();i+=2)
        {
            String s = arg.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            str = str + (char) decimal;
        }       
        return str;
    }    
    
    
    public class ProcessPostDataView implements Runnable
    {
        private boolean running = true;
        
        public boolean is_running()
        {
            return running;
        }
        
        public void finish_thread()
        {
            running = false;
        }

        @Override
        public void run() {
       
        jCmBoxYearData.setEnabled(false);
        jCmBoxMonthData.setEnabled(false);
        jCmBoxDayData.setEnabled(false);
        jBtnLoadData.setEnabled(false);
        jBtnLoadByDayData.setEnabled(false);
            
        jListBlockNumberData.clearSelection();

        data = grab_data.get_data_ready();                    
        my_calendar_simple.set_timezone_calendar_UTC();

        String value = (String) jCmBoxYearData.getModel().getSelectedItem();            
        boolean test = data.containsKey(Integer.valueOf(value));
         
        data_on_combobox.clear();
        
        switch(command_button)   
        {
                
            case "load_data":


                modelcbx = new DefaultComboBoxModel();

                int num_of_days = my_calendar_simple.get_number_of_days(Integer.valueOf((String) jCmBoxYearData.getModel().getSelectedItem()), Integer.valueOf((String) jCmBoxMonthData.getModel().getSelectedItem()));

                for(int counter = 0 ;counter < num_of_days; counter++)        
                {
                    modelcbx.addElement(counter+1);        
                }
                    
                jCmBoxDayData.setModel(modelcbx);            

                if(test == true)
                {  
                
                    model = new DefaultListModel();                

                    for(block transaction : data.get(Integer.valueOf( (String) jCmBoxYearData.getModel().getSelectedItem())))
                    {
                        if(Integer.valueOf( (String) jCmBoxYearData.getModel().getSelectedItem()) == my_calendar_simple.get_year(transaction.time))
                        {
                            model.addElement(transaction.block_number);
                            data_on_combobox.put(transaction.block_number, transaction);                       
                        }
                    }
                
                    jListBlockNumberData.setModel(model);

                }
                    
            break;
            case "load_by_day":

                if(test == true)
                {  
            
                    model = new DefaultListModel();                

                    for(block transaction : data.get(Integer.valueOf( (String) jCmBoxYearData.getModel().getSelectedItem())))
                    {

                        if(Integer.valueOf( (String) jCmBoxYearData.getModel().getSelectedItem()) == my_calendar_simple.get_year(transaction.time))
                        {
                            int month_selected = Integer.valueOf((String)jCmBoxMonthData.getModel().getSelectedItem())-1;
                            int transaction_month = my_calendar_simple.get_month(transaction.time);
                            
                            int transaction_day = my_calendar_simple.get_day(transaction.time);
                            int day_selected   = (int) jCmBoxDayData.getModel().getSelectedItem();
                            
                            if(transaction_month == month_selected && transaction_day == day_selected)
                            {    
                                model.addElement(transaction.block_number);
                                data_on_combobox.put(transaction.block_number, transaction);
                            }

                        }
                    }

                        jListBlockNumberData.setModel(model);

                }                    
                    
                break;
        }
            jBtnLoadByDayData.setEnabled(true);
            jBtnLoadData.setEnabled(true);
            jCmBoxYearData.setEnabled(true);
            jCmBoxMonthData.setEnabled(true);
            jCmBoxDayData.setEnabled(true);
      }
     
    }
    
}
