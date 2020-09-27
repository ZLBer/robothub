package edu.hust.robothub.core.message;

import edu.wpi.rail.jrosbridge.messages.Message;
import edu.wpi.rail.jrosbridge.services.ServiceResponse;

/**  
 *    
 * 对rosmessage和rosServiceResponse进行封装
 * @author BNer  
 * @date 2020/9/25 16:58
 * @param   
 * @return   
 */  
public class RosMessage extends AbstractMessage {
    Message rosMessage;
    ServiceResponse rosServiceResponse;
    public RosMessage(Message rosMessage) {
        this.rosMessage = rosMessage;
        this.data = rosMessage.toString();
    }

    public RosMessage(String data){
        this.data=data;
        this.rosMessage= new Message(data);
    }


    public RosMessage(ServiceResponse serviceResponse) {
        this.rosServiceResponse = serviceResponse;
    }

    public  RosMessage(ServiceMessage serviceMessage){
       rosMessage=new Message(serviceMessage.data);
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
