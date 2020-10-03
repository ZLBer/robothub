package edu.hust.robothub.core.api;

import edu.hust.robothub.core.message.AbstractMessage;

import java.util.Map;

abstract public class ApiValue extends AbstractMessage {
    int code;
    String msg;
    Map<String,String> extra;

    public ApiValue(int code, String msg, Map<String, String> extra) {
        this.code = code;
        this.msg = msg;
        this.extra = extra;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
