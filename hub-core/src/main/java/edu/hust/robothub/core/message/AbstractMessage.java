package edu.hust.robothub.core.message;

public abstract class AbstractMessage {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
