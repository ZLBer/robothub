package edu.hust.robothub.core.api;

import java.util.HashMap;
import java.util.Map;

public class StandardServiceResponseMessage extends ServiceResponseMessage {
    Map<String,String> data=new HashMap<>();

    public StandardServiceResponseMessage(int code, String msg, Map<String, String> extra, Map<String,String> data) {
        super(code, msg, extra);
        this.data = data;
    }

    public boolean addData(String key,String val){
       data.put(key,val);
       return true;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
