package edu.hust.robothub.core.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjListServiceResponseMessage<T>  extends ServiceResponseMessage {
    List<T> data=new ArrayList<>();

    public ObjListServiceResponseMessage(int code, String msg, Map<String, String> extra, List<T> data) {
        super(code, msg, extra);
        this.data = data;
    }

    public  boolean addData(T obj){
       return data.add(obj);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
