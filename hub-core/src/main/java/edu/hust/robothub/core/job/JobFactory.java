package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.handler.DetectInterruptHandler;
import edu.hust.robothub.core.handler.HandlerChain;
import edu.hust.robothub.core.handler.MessageConvertHandler;
import edu.hust.robothub.core.handler.MessageStorageHandler;
import edu.hust.robothub.core.handler.PublishServiceCallbackHandler;
import edu.hust.robothub.core.handler.ServiceInvokerHander;
import edu.hust.robothub.core.handler.TailHander;
import edu.hust.robothub.core.message.RosMessage;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.result.ResultCatchManager;
import edu.hust.robothub.core.robot.Robot;
import edu.hust.robothub.core.robot.RobotManager;
import edu.hust.robothub.core.robot.RosRobotInvokerWithContext;
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

//工厂模式
 public enum  JobFactory {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFactory.class);

    RobotManager robotManager = RobotManager.getInstance();

    public  static JobFactory getInstance(){
        return INSTANCE;
    }

    public BooleanResultKV<AbstractJob> create(int jobType, String jobName, String hostname, int port, Map<String,Object> args){
        BooleanResultKV<AbstractJob> res=null;
       if(AbstractJob.JOBTYPE_PUBLISH==jobType){
          res= createPublishJob(jobName,hostname,port,(String)args.get("publishTopicName"),(String)args.get("publicTopicType"),new RosMessage((String)args.get("rosMessage")));
       }else if(AbstractJob.JOBTYPE_SERVICE==jobType){
           res= createServiceJob(jobName,hostname,port,(String)args.get("serviceName"),(String)args.get("serviceType"),new ServiceRequest((String)args.get("serviceRequest")));
       }else if(AbstractJob.JOBTYPE_SUBSCRIBE==jobType){
           res=  createSubscribeJob(jobName,hostname,port,(AbstractClient)args.get("abstractClient"),Integer.parseInt((String) args.get("httpMethod")),(String) args.get("serviceUrl"),(Map<String, String>) args.get("headers"),(String) args.get("subscribeTopicName"),(String)args.get("subscribeTopicType"),(String)args.get("publishTopicName"),(String)args.get("publicTopicType"));
       }else {
           LOGGER.error("no this type job");
           return new BooleanResultKV<>(false,null);
       }
     return res==null?new BooleanResultKV<>(false,null):res;
    }



    //publish类型的调用链 HeadHandler -> TailHandler
    public BooleanResultKV<AbstractJob> createPublishJob(String jobName, String hostname, int port, String publishTopicName, String publicTopicType, RosMessage rosMessage) {
        //build robotcontext
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

        if (!check(robot, publicTopicType, publishTopicName)) return new BooleanResultKV<>(false, null);

        robotContext.setRos(robot.getRos());
        robotContext.setPublishTopicName(publishTopicName);
        robotContext.setPublishTopicType(publicTopicType);
        RosRobotInvokerWithContext rosRobotInvokerWithContext = new RosRobotInvokerWithContext(robotContext);

        //build servicecontext
        ServiceContext serviceContext = new ServiceContext();
        HandlerChain handlerChain = new HandlerChain(robotContext, serviceContext);
        TailHander tailHander = new TailHander(robotContext, serviceContext);
        handlerChain.buildHandlerChain(tailHander);

        robotContext.setHandler(handlerChain);
        robotContext.setRosRobotInvokerWithContext(rosRobotInvokerWithContext);


        PublishJob publishJob = new PublishJob(jobName, robotContext, serviceContext);
        publishJob.setRosMessage(rosMessage);
        serviceContext.setJobId(publishJob.getJobId());
        //存储发送的消息
       storageMessage(serviceContext.getJobId(),rosMessage.getMessage());
        return new BooleanResultKV<>(true, publishJob);
    }

    boolean check(Robot robot, String... stringArgs) {
        if (robot == null) return false;
        for (String arg : stringArgs) {
            if (arg == null || "".equals(arg)) return false;
        }
        return true;
    }
     //subscribe类型的调用链
     // HeadHandler -> DetectInterruptHandler -> MessageStorageHandler->MessageConvertHandler -> ServiceInvokerHander
     //  -> MessageStorageHandler -> MessageConvertHandler ->PublishServiceCallbackHandler
    public BooleanResultKV<AbstractJob> createSubscribeJob(String jobName, String hostname, int port, AbstractClient abstractClient, int httpMethod, String serviceUrl, Map<String,String> headers, String subscribeTopicName, String subscribeTopicType, String publishTopicName, String publicTopicType){
        //构建robot环境变量
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

      //  if (!check(robot, subscribeTopicName,subscribeTopicType,publicTopicType, publishTopicName)) return new ResultKV<>(false, null);

        robotContext.setRos(robot.getRos());
        robotContext.setSubscribeTopicName(subscribeTopicName);
        robotContext.setSubscribeTopicType(subscribeTopicType);
        robotContext.setPublishTopicName(publishTopicName);
        robotContext.setPublishTopicType(publicTopicType);
        RosRobotInvokerWithContext rosRobotInvokerWithContext =new RosRobotInvokerWithContext(robotContext);

        //构建service环境变量

        ServiceContext serviceContext=new ServiceContext();

        ServiceInvoker serviceInvoker = null;
        if(abstractClient!=null) serviceInvoker = new StandardServiceInvoker(serviceContext,abstractClient);

        serviceContext.setHeaders(headers);
        serviceContext.setHttpMethod(httpMethod);
        serviceContext.setServiceInvoker(serviceInvoker);
        serviceContext.setServiceUrl(serviceUrl);



        //构建处理链
        HandlerChain handlerChain=new HandlerChain(robotContext,serviceContext);
        ServiceInvokerHander serviceInvokerHander = new ServiceInvokerHander(robotContext, serviceContext);
        PublishServiceCallbackHandler publishServiceCallbackHandler = new PublishServiceCallbackHandler(robotContext, serviceContext);
        MessageConvertHandler messageConvertHandler = new MessageConvertHandler(robotContext, serviceContext);
        MessageConvertHandler messageConvertHandler1 = new MessageConvertHandler(robotContext, serviceContext);
        DetectInterruptHandler detectInterruptHandler = new DetectInterruptHandler(robotContext, serviceContext);
        MessageStorageHandler messageStorageHandler = new MessageStorageHandler(robotContext,serviceContext);
        MessageStorageHandler messageStorageHandler1 = new MessageStorageHandler(robotContext,serviceContext);

        handlerChain.buildHandlerChain(detectInterruptHandler,messageStorageHandler,messageConvertHandler,serviceInvokerHander,messageStorageHandler1,messageConvertHandler1,publishServiceCallbackHandler);

        robotContext.setHandler(handlerChain);

        robotContext.setRosRobotInvokerWithContext(rosRobotInvokerWithContext);


        SubscribeJob subscribeJob=new SubscribeJob(jobName,robotContext,serviceContext);
        serviceContext.setJobId(subscribeJob.getJobId());


        return new BooleanResultKV<>(true, subscribeJob);
    }


    //service类型的调用链 Headhandler -> MessageStorageHandler ->TailHandler
    public BooleanResultKV<AbstractJob> createServiceJob(String jobName, String hostname, int port, String serviceName, String serviceType, ServiceRequest serviceRequest) {
        //构建robot环境变量
        RobotContext robotContext = new RobotContext();
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);

        if (!check(robot, serviceName, serviceType)) return new BooleanResultKV<>(false, null);
        robotContext.setRos(robot.getRos());
        robotContext.setServiceName(serviceName);
        robotContext.setServiceType(serviceType);
        RosRobotInvokerWithContext rosRobotInvokerWithContext = new RosRobotInvokerWithContext(robotContext);
        robotContext.setServiceRequest(serviceRequest);


        //构建service环境变量
        ServiceContext serviceContext = new ServiceContext();


        //构建处理链
        HandlerChain handlerChain = new HandlerChain(robotContext, serviceContext);
        TailHander tailHander = new TailHander(robotContext, serviceContext);
        MessageStorageHandler messageStorageHandler = new MessageStorageHandler(robotContext, serviceContext);
        handlerChain.buildHandlerChain(messageStorageHandler,tailHander);
        robotContext.setHandler(handlerChain);


        robotContext.setRosRobotInvokerWithContext(rosRobotInvokerWithContext);


        ServiceJob serviceJob = new ServiceJob(jobName, robotContext, serviceContext);
        serviceContext.setJobId(serviceJob.getJobId());

        //存储发送的消息
        storageMessage(serviceJob.getJobId(),serviceRequest.toString());

        return new BooleanResultKV<>(true, serviceJob);
    }

    ResultCatchManager resultCatchManager = ResultCatchManager.getInstance();
    boolean  storageMessage(String jobId,String message){
      return   resultCatchManager.add(jobId,new JobResult(System.currentTimeMillis(),message));
    }
}
