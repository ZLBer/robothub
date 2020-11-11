package edu.hust.robothub.core;

import edu.hust.robothub.core.robot.RosRobotInvokerWithContext;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.handler.AbstractHandler;
import edu.hust.robothub.core.handler.DetectInterruptHandler;
import edu.hust.robothub.core.handler.HandlerChain;
import edu.hust.robothub.core.handler.MessageConvertHandler;
import edu.hust.robothub.core.handler.PublishServiceCallbackHandler;
import edu.hust.robothub.core.handler.ServiceInvokerHander;
import edu.hust.robothub.core.job.SubscribeJob;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* JobManager Tester. 
* 
* @author <Authors name> 
* @since <pre>9月 27, 2020</pre> 
* @version 1.0 
*/ 
public class JobManagerTest {
   static SubscribeJob subscribeJob;
@Before
public  void before() throws Exception {
    //build robotcontext
    RobotContext robotContext=new RobotContext();
    Ros ros = new Ros("192.168.232.100",9090);
    robotContext.setRos(ros);
    robotContext.setSubscribeTopicName("/listener");
    robotContext.setSubscribeTopicType("std_msgs/String");
    robotContext.setPublishTopicName("/listener");
    robotContext.setPublishTopicType("std_msgs/String");
    robotContext.setServiceName("");
    robotContext.setServiceType("");
    robotContext.setServiceRequest(new ServiceRequest("{}"));
    RosRobotInvokerWithContext rosRobotInvokerWithContext =new RosRobotInvokerWithContext(robotContext);

    //build servicecontext

    ServiceContext serviceContext=new ServiceContext();
    serviceContext.setServiceInvoker(new ServiceInvokerMock());

//build handlerChain
    HandlerChain handlerChain=new HandlerChain(robotContext,serviceContext);
    AbstractHandler handler1=new ServiceInvokerHander(robotContext,serviceContext);
    AbstractHandler handler2=new PublishServiceCallbackHandler(robotContext,serviceContext);
    AbstractHandler handler3=new MessageConvertHandler(robotContext,serviceContext);
    AbstractHandler handler5=new MessageConvertHandler(robotContext,serviceContext);
    AbstractHandler handler4=new DetectInterruptHandler(robotContext,serviceContext);
    handlerChain.buildHandlerChain(handler4,handler3,handler1,handler5,handler2);

    robotContext.setHandler(handlerChain);

    robotContext.setRosRobotInvokerWithContext(rosRobotInvokerWithContext);


    subscribeJob=new SubscribeJob("test-job",robotContext,serviceContext);



} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getInstance() 
* 
*/ 
@Test
public void testGetInstance() throws Exception {
    //JobManager.getInstance().execute(subscribeJob);

    subscribeJob.doRun();
    System.in.read();

} 

/** 
* 
* Method: catchJob(AbstractJob job) 
* 
*/ 
@Test
public void testCatchJob() throws Exception { 
}

/** 
* 
* Method: cleanJon(AbstractJob job) 
* 
*/ 
@Test
public void testCleanJon() throws Exception { 
}

/** 
* 
* Method: execute(AbstractJob job) 
* 
*/ 
@Test
public void testExecute() throws Exception { 
}

} 
