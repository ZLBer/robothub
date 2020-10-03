package edu.hust.robothub.core.service;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractClient<T,R> implements Client {

    abstract public R invokeRemote(T body,Map<String,String> headers,int httpMethod,String serviceUrl,Class<R> responseType);

}
