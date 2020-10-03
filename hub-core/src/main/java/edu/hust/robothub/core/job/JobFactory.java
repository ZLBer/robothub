package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.handler.AbstractHandler;
import edu.hust.robothub.core.handler.DetectInterruptHandler;
import edu.hust.robothub.core.handler.HandlerChain;
import edu.hust.robothub.core.handler.MessageConvertHandler;
import edu.hust.robothub.core.handler.PublishServiceCallbackHandler;
import edu.hust.robothub.core.handler.ServiceInvokerHander;
import edu.hust.robothub.core.message.RosMessage;
import edu.hust.robothub.core.result.ResultKV;
import edu.hust.robothub.core.robot.Robot;
import edu.hust.robothub.core.robot.RobotManager;
import edu.hust.robothub.core.robot.RosRobotInstance;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import edu.hust.robothub.core.service.AbstractClient;
import edu.hust.robothub.core.service.ServiceInvoker;
import edu.hust.robothub.core.service.StandardServiceInvoker;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


/** 用于构建job
*/
 public enum  JobFactory {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFactory.class);

    RobotManager robotManager = RobotManager.getInstance();

    public  static JobFactory getInstance(){
        return INSTANCE;
    }

    public  ResultKV<AbstractJob> create(int jobType, String jobName,String hostname, int port, Map<String,Object> args){
        AbstractJob job=null;
       if(AbstractJob.JOBTYPE_PUBLISH==jobType){
           createPublishJob(jobName,hostname,port,(String)args.get("publishTopicName"),(String)args.get("publicTopicType"),new RosMessage((String)args.get("rosMessage")));
       }else if(AbstractJob.JOBTYPE_SERVICE==jobType){
           createServiceJob(jobName,hostname,port,(String)args.get("serviceName"),(String)args.get("serviceType"),new ServiceRequest((String)args.get("serviceRequest")));
       }else if(AbstractJob.JOBTYPE_SUBSCRIBE==jobType){
           createSubscribeJob(jobName,hostname,port,(AbstractClient)args.get("abstractClient"),(int)args.get("httpMethod"),(Map<String, String>) args.get("headers"),(String) args.get("subscribeTopicName"),(String)args.get("subscribeTopicType"),(String)args.get("publishTopicName"),(String)args.get("publicTopicType"));
       }else {
           LOGGER.error("no this type job");
           return new ResultKV<>(false,null);
       }
     return new ResultKV<>(true,job);
    }


    public ResultKV<AbstractJob> createPublishJob(String jobName,String hostname, int port, String publishTopicName, String publicTopicType, RosMessage rosMessage) {
        //build robotcontext
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

        if (!check(robot, publicTopicType, publishTopicName)) return new ResultKV<>(false, null);

        robotContext.setRos(robot.getRos());
        robotContext.setPublishTopicName(publishTopicName);
        robotContext.setPublishTopicType(publicTopicType);
        RosRobotInstance rosRobotInstance = new RosRobotInstance(robotContext);

        //build servicecontext
        ServiceContext serviceContext = new ServiceContext();

        robotContext.setHandler(new HandlerChain(robotContext, serviceContext));
        robotContext.setRosRobotInstance(rosRobotInstance);


        PublishJob publishJob = new PublishJob(jobName, robotContext, serviceContext);
        publishJob.setRosMessage(rosMessage);

        return new ResultKV<>(true, publishJob);
    }

    boolean check(Robot robot, String... stringArgs) {
        if (robot == null) return false;
        for (String arg : stringArgs) {
            if (arg == null || "".equals(arg)) return false;
        }
        return true;
    }

    public ResultKV<AbstractJob> createSubscribeJob(String jobName,String hostname, int port,AbstractClient abstractClient,int httpMethod,Map<String,String> headers,String subscribeTopicName, String subscribeTopicType,String publishTopicName, String publicTopicType){
        //build robotcontext
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

        if (!check(robot, subscribeTopicName,subscribeTopicType,publicTopicType, publishTopicName)) return new ResultKV<>(false, null);

        robotContext.setRos(robot.getRos());
        robotContext.setSubscribeTopicName(subscribeTopicName);
        robotContext.setSubscribeTopicType(subscribeTopicType);
        robotContext.setPublishTopicName(publishTopicName);
        robotContext.setPublishTopicType(publicTopicType);
        RosRobotInstance rosRobotInstance=new RosRobotInstance(robotContext);

        //build servicecontext

        ServiceContext serviceContext=new ServiceContext();

        ServiceInvoker serviceInvoker = new StandardServiceInvoker(serviceContext,abstractClient);
        serviceContext.setHeaders(headers);
        serviceContext.setHttpMethod(httpMethod);
        serviceContext.setServiceInvoker(serviceInvoker);

        //build handlerChain
        HandlerChain handlerChain=new HandlerChain(robotContext,serviceContext);
        AbstractHandler handler1=new ServiceInvokerHander(robotContext,serviceContext);
        AbstractHandler handler2=new PublishServiceCallbackHandler(robotContext,serviceContext);
        AbstractHandler handler3=new MessageConvertHandler(robotContext,serviceContext);
        AbstractHandler handler5=new MessageConvertHandler(robotContext,serviceContext);
        AbstractHandler handler4=new DetectInterruptHandler(robotContext,serviceContext);
        handlerChain.buildHandlerChain(handler4,handler3,handler1,handler5,handler2);

        robotContext.setHandler(handlerChain);

        robotContext.setRosRobotInstance(rosRobotInstance);


        SubscribeJob subscribeJob=new SubscribeJob(jobName,robotContext,serviceContext);

        return new ResultKV<>(true, subscribeJob);
    }


    public ResultKV<AbstractJob> createServiceJob(String jobName,String hostname, int port, String serviceName, String serviceType,ServiceRequest serviceRequest) {
        //build robotcontext
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

        if (!check(robot, serviceName, serviceType)) return new ResultKV<>(false, null);
        robotContext.setRos(robot.getRos());
        robotContext.setServiceName(serviceName);
        robotContext.setServiceType(serviceType);
        RosRobotInstance rosRobotInstance = new RosRobotInstance(robotContext);
        robotContext.setServiceRequest(serviceRequest);
        //build servicecontext

        ServiceContext serviceContext = new ServiceContext();

        //build handlerChain
        HandlerChain handlerChain = new HandlerChain(robotContext, serviceContext);
        AbstractHandler handler1 = new ServiceInvokerHander(robotContext, serviceContext);
        AbstractHandler handler3 = new MessageConvertHandler(robotContext, serviceContext);
        AbstractHandler handler5 = new MessageConvertHandler(robotContext, serviceContext);
        AbstractHandler handler4 = new DetectInterruptHandler(robotContext, serviceContext);
        handlerChain.buildHandlerChain(handler4, handler3, handler1);

        robotContext.setHandler(handlerChain);

        robotContext.setRosRobotInstance(rosRobotInstance);


        ServiceJob serviceJob = new ServiceJob(jobName, robotContext, serviceContext);

        return new ResultKV<>(true, serviceJob);
    }
}
