package hust.robothub.core.robot;

import edu.hust.robothub.core.robot.RosRobotInvokerWithContext;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.handler.AbstractHandler;
import edu.hust.robothub.core.handler.HandlerChain;
import edu.hust.robothub.core.handler.MessageConvertHandler;
import edu.hust.robothub.core.handler.PublishServiceCallbackHandler;
import edu.hust.robothub.core.handler.ServiceInvokerHander;
import edu.hust.robothub.core.message.RosMessage;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* RosRobotInstance Tester. 
* 
* @author <Authors name> 
* @since <pre>9月 26, 2020</pre> 
* @version 1.0 
*/ 
public class RosRobotInvokerWithContextTest {

RosRobotInvokerWithContext rosRobotInvokerWithContext;
@Before
public void before() throws Exception {
  //build robotcontext
  RobotContext robotContext=new RobotContext();
  Ros ros = new Ros("192.168.232.100",9090);
  robotContext.setRos(ros);
  robotContext.setSubscribeTopicName("/connected_clients");
  robotContext.setSubscribeTopicType("rosbridge_msgs/ConnectedClients");
  robotContext.setPublishTopicName("/connected_clients");
  robotContext.setPublishTopicType("rosbridge_msgs/ConnectedClients");
  robotContext.setServiceName("");
  robotContext.setServiceType("");
  robotContext.setServiceRequest(new ServiceRequest("{}"));
  rosRobotInvokerWithContext =new RosRobotInvokerWithContext(robotContext);

  //build servicecontext

  ServiceContext serviceContext=new ServiceContext();
  serviceContext.setServiceInvoker(new ServiceInvokerMock());

//build handlerChain
  HandlerChain handlerChain=new HandlerChain(robotContext,serviceContext);
  AbstractHandler handler1=new ServiceInvokerHander(robotContext,serviceContext);
  AbstractHandler handler2=new PublishServiceCallbackHandler(robotContext,serviceContext);
  AbstractHandler handler3=new MessageConvertHandler(robotContext,serviceContext);
  handlerChain.buildHandlerChain(handler3,handler1,handler3,handler2);

  robotContext.setHandler(handlerChain);


} 

@After
public void after() throws Exception {

} 

/** 
* 
* Method: connect() 
* 
*/ 
@Test
public void testConnect() throws Exception { 
 rosRobotInvokerWithContext.connect();
 rosRobotInvokerWithContext.disconnect();
} 

/** 
* 
* Method: disconnect() 
* 
*/ 
@Test
public void testDisconnect() throws Exception { 
}

/** 
* 
* Method: getRobotStatus() 
* 
*/ 
@Test
public void testGetRobotStatus() throws Exception { 
}

/** 
* 
* Method: subscribe() 
* 
*/ 
@Test
public void testSubscribe() throws Exception {
  rosRobotInvokerWithContext.connect();
  rosRobotInvokerWithContext.subscribe();

  System.in.read();
} 

/** 
* 
* Method: publish(RosMessage message) 
* 
*/ 
@Test
public void testPublish() throws Exception {
 rosRobotInvokerWithContext.connect();
rosRobotInvokerWithContext.publish(new RosMessage("test"));
  System.in.read();
} 

/** 
* 
* Method: service() 
* 
*/ 
@Test
public void testService() throws Exception {
  rosRobotInvokerWithContext.connect();
  rosRobotInvokerWithContext.subscribe();
}


} 
