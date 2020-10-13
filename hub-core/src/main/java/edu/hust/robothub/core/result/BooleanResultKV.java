package edu.hust.robothub.core.result;

public class BooleanResultKV<V>  extends ResultKV<Boolean,V>{


    public BooleanResultKV(boolean key, V value) {
        this.key = key;
        this.value = value;
    }


}
