package edu.hust.robothub.core.api;

import java.util.HashMap;
import java.util.Map;

public class StandardApiValue extends ApiValue {
    Map<String,String> data=new HashMap<>();

    public StandardApiValue(int code, String msg, Map<String, String> extra,Map<String,String> data) {
        super(code, msg, extra);
        this.data = data;
    }

    public boolean addData(String key,String val){
       data.put(key,val);
       return true;
    }
}
