package edu.hust.robothub.core.api;

import java.util.Map;

public class ObjServiceResponseMessage<T> extends ServiceResponseMessage {

    T data;

    public ObjServiceResponseMessage(int code, String msg, Map<String, String> extra, T data) {
        super(code, msg, extra);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
