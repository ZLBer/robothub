package edu.hust.robothub.core.result;

public class ResultKV<V> {
    boolean key;
    V value;

    public ResultKV(boolean key, V value) {
        this.key = key;
        this.value = value;
    }

    public boolean getKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
