package edu.hust.robothub.core.robot;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.message.RosMessage;
import edu.wpi.rail.jrosbridge.Topic;

/**
 *    
 * 带有具体context的机器人调用器
 * @author BNer  
 * @date 2020/9/27 17:10
 * @param   
 * @return   
 */  
public class RosRobotInstance implements Robot {
    
    RobotContext context;
    private RosRobotInvoker rosRobotInvoker;

    public RosRobotInstance(RobotContext context) {
        this.context = context;
        this.rosRobotInvoker = new RosRobotInvoker(context.getRos());
    }

    @Override
    public boolean connect() {
        return rosRobotInvoker.connect();
    }

    @Override
    public boolean disconnect() {
        return rosRobotInvoker.disconnect();
    }

    @Override
    public String getRobotStatus() {
        return rosRobotInvoker.getRobotStatus();
    }
    public void subscribe(){
        Topic  topic = new Topic(context.getRos(),context.getSubscribeTopicName(),context.getSubscribeTopicType());
        context.setSubscribeTopic(topic);
        rosRobotInvoker.subscribe(topic,context.getHandler());
    }
    public void unSubscribe(){
        rosRobotInvoker.unSubscribe(context.getSubscribeTopic(),context.getHandler());
    }
    public void publish(RosMessage message){
       rosRobotInvoker.publish(context.getPublishTopicName(),context.getPublishTopicType(),message);
    }
    public void service(){
      rosRobotInvoker.service(context.getServiceName(),context.getServiceType(),context.getServiceRequest(),context.getHandler());
    }

}
