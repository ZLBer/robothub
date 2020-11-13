package edu.hust.robothub.core.until;

import java.util.HashMap;
import java.util.Map;


//配置读取之后的存储
public  abstract class  Config {


    Map<String,String> configMap = new HashMap<>();

    public  String  getConfigValue(String key){
       return configMap.get(key);
    }

    public void setConfigKV(String key,String value){
        configMap.put(key,value);
    }


}
