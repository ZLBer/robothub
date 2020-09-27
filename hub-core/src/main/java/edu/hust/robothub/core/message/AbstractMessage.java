package edu.hust.robothub.core.message;

public abstract class AbstractMessage {
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
