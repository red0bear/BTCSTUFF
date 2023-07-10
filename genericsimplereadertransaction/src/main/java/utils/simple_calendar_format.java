/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author felipe
 */
public class simple_calendar_format {
    
    private Calendar date;
    private TimeZone tz;
    private String timezone;
    
    private  SimpleDateFormat formatter_t = new SimpleDateFormat("hh:mm:ss:aa");
    private  SimpleDateFormat formatter_d = new SimpleDateFormat("dd/MM/yyyy");
    private  SimpleDateFormat formatter_ff = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:aa");
    
    
    public simple_calendar_format()
    {}
    
    public void set_timezone_calendar_UTC()
    {
        timezone ="UTC";
    }
    
    public void set_timezone_calendar_GMT()
    {
        timezone ="GMT";
    }    

    public void set_timezone_calendar_EST()
    {
        timezone ="EST";
    }    
    
    public int get_number_of_days(int year,int month)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.set(year, month, 0);
        
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }     

    public int get_hour(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000); 
        
        return date.get(Calendar.HOUR_OF_DAY);
    }
    
    public int get_minute(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000); 
        
        return date.get(Calendar.MINUTE);
    }    

    public int get_am(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        return date.get(Calendar.AM);
    }    
    
    public int get_year(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        return date.get(Calendar.YEAR);
    }
    
    public int get_month(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000); 
        
        return date.get(Calendar.MONTH);
    }    
    
    public int get_day(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        return date.get(Calendar.DAY_OF_MONTH);
    }  
    
    public int get_day_of_week(long value)
    {
        
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        while(date.get(Calendar.DAY_OF_MONTH) > 1)
        {
            date.add(Calendar.DATE, -1);
        }
        
        return (date.get(Calendar.DAY_OF_WEEK)-1);
    }  

    public int get_day_of_month(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    } 

    public int get_week_of_month(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        
        return date.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }         
        
    public String get_format_date(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        formatter_d.setTimeZone(TimeZone.getTimeZone(timezone));
        
        return formatter_d.format(date.getTime());
    }    
    
    public String get_format_time(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000);
        formatter_t.setTimeZone(TimeZone.getTimeZone(timezone));
        
        return formatter_t.format(date.getTime());
    }     

    public String get_format_time_ff(long value)
    {
        date = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        date.setTimeInMillis(value*1000); 
        formatter_ff.setTimeZone(TimeZone.getTimeZone(timezone));
        
        return formatter_ff.format(date.getTime());
    }      
}
