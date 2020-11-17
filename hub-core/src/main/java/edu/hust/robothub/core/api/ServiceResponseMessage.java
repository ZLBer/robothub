package edu.hust.robothub.core.api;

import edu.hust.robothub.core.message.ServiceMessage;

import java.util.Map;


/*  
 *    
 * 用来封装RestAPI返回的数据,
 * such as: null 不返回数据
 *         obj 返回对象
 *         objList 对象列表
 *         map  任意设置的k-v对
 * @author BNer  
 * @date 2020/11/3 17:09
 * @param   
 * @return   
 */  
abstract public class ServiceResponseMessage extends ServiceMessage {
    int code;
    String msg;
    Map<String,String> extra;

    public ServiceResponseMessage(int code, String msg, Map<String, String> extra) {
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
