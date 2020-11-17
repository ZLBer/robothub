package edu.hust.robothub.core.result;

public class ResultKV<K,V>{
    K key;
    V value;

    public ResultKV(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public ResultKV() {
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
