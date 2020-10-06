package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;

public class HandlerChain implements Handler {


    RobotContext robotContext;
    ServiceContext serviceContext;

    AbstractHandler firstHander;

    public HandlerChain(RobotContext robotContext, ServiceContext serviceContext) {
        this.robotContext = robotContext;
        this.serviceContext = serviceContext;
        firstHander = new Headhandler(robotContext, serviceContext);
    }


    @Override
    public void handle(AbstractMessage abstractMessage) {
        firstHander.handle(abstractMessage);
    }


    public void buildHandlerChain(AbstractHandler... handlers) {

        AbstractHandler pre = firstHander;
        for (AbstractHandler handler : handlers) {
            pre.setSuccessor(handler);
            pre = handler;
        }
       pre.successor=new TailHander(robotContext,serviceContext);
    }


}
