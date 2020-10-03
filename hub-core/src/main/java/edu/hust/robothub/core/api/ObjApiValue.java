package edu.hust.robothub.core.api;

import java.util.Map;

public class ObjApiValue<T> extends ApiValue {

    T data;

    public ObjApiValue(int code, String msg, Map<String, String> extra,T data) {
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
