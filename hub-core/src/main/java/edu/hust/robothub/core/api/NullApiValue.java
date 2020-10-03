package edu.hust.robothub.core.api;

import edu.hust.robothub.core.result.ResultKV;

import java.util.Map;

public class NullApiValue extends ApiValue {

    public NullApiValue(int code, String msg, Map<String, String> extra) {
        super(code, msg, extra);
    }

    static public NullApiValue buildSuccessValue(){
        return new NullApiValue(StatusCode.SUCCESS,"success",null);
    }

    static public NullApiValue buildSuccessValue(String msg){
        return new NullApiValue(StatusCode.SUCCESS,msg,null);
    }

    static  public  NullApiValue buildFailValue(){
        return new NullApiValue(StatusCode.FAIL,"fail",null);
    }
    static  public  NullApiValue buildFailValue(String msg){
        return new NullApiValue(StatusCode.FAIL,msg,null);
    }

    static  public NullApiValue getReturnValue(ResultKV<String> resultKV){
         if(resultKV.getKey()){
            return buildSuccessValue(resultKV.getValue());
         }else {
            return buildFailValue(resultKV.getValue());
         }
    }
}
