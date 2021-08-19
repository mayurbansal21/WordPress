package com.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
 
public class PropHandler {
 
    private Properties prop = null;
     
    public PropHandler(){
         
        InputStream is = null;
        try {
            prop = new Properties();
            is = this.getClass().getResourceAsStream("/config.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
     
    public Set<Object> getAllKeys(){
        Set<Object> keys = prop.keySet();
        return keys;
    }
     
    public String getPropertyValue(String key){
        return this.prop.getProperty(key);
    }
     
/*    public static void main(String a[]){
         
        PropHandler mpc = new PropHandler();
        Set<Object> keys = mpc.getAllKeys();
        for(Object k:keys){
            String key = (String)k;
            System.out.println(key+": "+mpc.getPropertyValue(key));
        }
    }*/
    
   
}
