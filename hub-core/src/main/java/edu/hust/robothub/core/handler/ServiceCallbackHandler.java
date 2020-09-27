package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;

abstract public class ServiceCallbackHandler extends AbstractHandler {


    public ServiceCallbackHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        return doCallBack(abstractMessage);
    }

    abstract AbstractMessage doCallBack(AbstractMessage abstractMessage);

}
