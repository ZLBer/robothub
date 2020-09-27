package edu.hust.robothub.core.message;

public class ServiceMessage extends AbstractMessage {
    public ServiceMessage(RosMessage rosMessage) {
       this.data=rosMessage.rosMessage.toString();
    }

    public ServiceMessage(String ms) {
        this.data=ms;
    }
}
