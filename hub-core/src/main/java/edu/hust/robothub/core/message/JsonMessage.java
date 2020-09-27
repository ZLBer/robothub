package edu.hust.robothub.core.message;

public class JsonMessage extends AbstractMessage {



   static public AbstractMessage convertMessage(String originMessage){

       return new JsonMessage();

    }
}
