package edu.hust.robothub.core.robot;

import edu.hust.robothub.core.handler.Handler;
import edu.hust.robothub.core.message.RosMessage;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Service;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.callback.TopicCallback;
import edu.wpi.rail.jrosbridge.messages.Message;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import edu.wpi.rail.jrosbridge.services.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 *    
 * 不带有具体context的机器人调用器，可以用来表示连接上来的ros服务
 * @author BNer  
 * @date 2020/9/27 17:10
 * @param   
 * @return   
 */  
public class RosRobotInvoker implements Robot {

    private static final Logger LOGGER = LoggerFactory.getLogger(RosRobotInvoker.class);
    private Ros ros;
    public RosRobotInvoker(Ros ros) {
        this.ros = ros;
    }

    @Override
    public boolean connect() {
        try {
            return ros.connect();
        } catch (Exception e) {
            LOGGER.error("can't connnet to the ROS:" + ros.getURL() + ",because of " + e.toString());
        }
        return false;
    }

    @Override
    public boolean disconnect() {
        try {
            return ros.disconnect();
        } catch (Exception e) {
            LOGGER.error("can't connnet to the ROS:" + ros.getURL() + ",because of " + e.toString());
        }
        return false;
    }

    @Override
    public String getRobotStatus() {

        return "no status";
    }

    public void subscribe(Topic topic, final Handler handler) {
        try {
            topic.subscribe(new TopicCallback() {
                @Override
                public void handleMessage(Message message) {
                    LOGGER.info(message.toString());
                    handler.handle(new RosMessage(message));
                }
            });
        } catch (Exception e) {
            LOGGER.error("can't subscribe to the ROS topic:" + topic.getName() + ",because of " + e.toString());
        }
    }    public void unSubscribe( Topic topic , final Handler handler) {
        try {
            topic.unsubscribe();
        } catch (Exception e) {
            LOGGER.error("can't unSubscribe to the ROS topic:" + topic.getName() + ",because of " + e.toString());
        }
    }

    public void publish(String topicName, String topicType, RosMessage message) {
        try {
            Topic topic = new Topic(ros, topicName, topicType);
            topic.publish(message.getRosMessage());
        } catch (Exception e) {
            LOGGER.error("can't publish to the ROS topic:" + topicName + ",because of " + e.toString());
        }
    }

    public void service(String serviceName, String serviceType, ServiceRequest serviceRequest, Handler handler) {
        Service service = new Service(ros, serviceName, serviceType);
        try {
            ServiceResponse response = service.callServiceAndWait(serviceRequest);
            handler.handle(new RosMessage(response));
        }catch (Exception e){
            LOGGER.error("can't get the ROS service:" + serviceName+",because of " + e.toString());
        }

    }
}
