package edu.hust.robothub.core.context;

import edu.hust.robothub.core.handler.Handler;
import edu.hust.robothub.core.robot.RosRobotInstance;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import edu.wpi.rail.jrosbridge.services.ServiceResponse;

public class RobotContext implements Context {
    Ros ros;
    String subscribeTopicName;
    String subscribeTopicType;
    Topic subscribeTopic;
    String publishTopicName;
    String publishTopicType;
    String serviceName;
    String serviceType;
    ServiceRequest serviceRequest;
    ServiceResponse serviceResponse;
    RosRobotInstance rosRobotInstance;
    Handler handler;

    public Topic getSubscribeTopic() {
        return subscribeTopic;
    }

    public void setSubscribeTopic(Topic subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public RosRobotInstance getRosRobotInstance() {
        return rosRobotInstance;
    }

    public void setRosRobotInstance(RosRobotInstance rosRobotInstance) {
        this.rosRobotInstance = rosRobotInstance;
    }

    public String getPublishTopicName() {
        return publishTopicName;
    }

    public void setPublishTopicName(String publishTopicName) {
        this.publishTopicName = publishTopicName;
    }

    public String getPublishTopicType() {
        return publishTopicType;
    }

    public void setPublishTopicType(String publishTopicType) {
        this.publishTopicType = publishTopicType;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    public Ros getRos() {
        return ros;
    }

    public void setRos(Ros ros) {
        this.ros = ros;
    }

    public String getSubscribeTopicName() {
        return subscribeTopicName;
    }

    public void setSubscribeTopicName(String subscribeTopicName) {
        this.subscribeTopicName = subscribeTopicName;
    }

    public String getSubscribeTopicType() {
        return subscribeTopicType;
    }

    public void setSubscribeTopicType(String subscribeTopicType) {
        this.subscribeTopicType = subscribeTopicType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }
}
