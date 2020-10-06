package edu.hust.robothub.core.api;

import edu.hust.robothub.core.result.ResultKV;

import java.util.Map;

public class NullServiceResponseMessage extends ServiceResponseMessage {

    public NullServiceResponseMessage(int code, String msg, Map<String, String> extra) {
        super(code, msg, extra);
    }

    static public NullServiceResponseMessage buildSuccessValue() {
        return new NullServiceResponseMessage(StatusCode.SUCCESS, "success", null);
    }

    static public NullServiceResponseMessage buildSuccessValue(String msg) {
        return new NullServiceResponseMessage(StatusCode.SUCCESS, msg, null);
    }

    static public NullServiceResponseMessage buildFailValue() {
        return new NullServiceResponseMessage(StatusCode.FAIL, "fail", null);
    }

    static public NullServiceResponseMessage buildFailValue(String msg) {
        return new NullServiceResponseMessage(StatusCode.FAIL, msg, null);
    }

    static public NullServiceResponseMessage getReturnValue(ResultKV<String> resultKV) {
        if (resultKV.getKey()) {
            return buildSuccessValue(resultKV.getValue());
        } else {
            return buildFailValue(resultKV.getValue());
        }
    }
}
