package edu.hust.robothub.core.message;

import edu.wpi.rail.jrosbridge.messages.Message;
import edu.wpi.rail.jrosbridge.services.ServiceResponse;

import javax.json.stream.JsonParsingException;

/**  
 *    
 * 对rosmessage和rosServiceResponse进行封装 ,以上两个是jrosbridge里的类 ，所以我们要转换成自己的
 */  
public class RosMessage extends AbstractMessage {
    Message rosMessage;
    ServiceResponse rosServiceResponse;
    public RosMessage(Message rosMessage) {
        this.rosMessage = rosMessage;
      //进行message的转移
        this.message = rosMessage.toString();
    }

    public RosMessage(String data){
        this.message =data;
        try {
            this.rosMessage= new Message(data);
        }catch (JsonParsingException e){
            this.rosMessage= new Message("{\"data\":\""+data+"\"}");
        }

    }


    public RosMessage(ServiceResponse serviceResponse) {
        this.rosServiceResponse = serviceResponse;
        //进行message的转移
        this.message = serviceResponse.toString();
    }

    public  RosMessage(ServiceMessage serviceMessage){

        try {
            rosMessage=new Message("{\"data\":"+serviceMessage.message+"}");
        }catch (JsonParsingException e){
            e.printStackTrace();
        }
    }

    public Message getRosMessage() {
        return rosMessage;
    }

    public void setRosMessage(Message rosMessage) {
        this.rosMessage = rosMessage;
    }

    public ServiceResponse getRosServiceResponse() {
        return rosServiceResponse;
    }

    public void setRosServiceResponse(ServiceResponse rosServiceResponse) {
        this.rosServiceResponse = rosServiceResponse;
    }


}
