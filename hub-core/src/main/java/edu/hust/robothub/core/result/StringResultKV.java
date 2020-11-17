package edu.hust.robothub.core.result;

public class StringResultKV<V> extends  ResultKV<String,V>{

    public StringResultKV(String key,V value) {
       this.key=key;
       this.value=value;
    }
}
