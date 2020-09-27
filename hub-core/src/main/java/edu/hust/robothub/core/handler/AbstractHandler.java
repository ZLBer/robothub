package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;

abstract public class AbstractHandler implements Handler {

    RobotContext robotContext;
    ServiceContext serviceContext;
    Handler successor;

    public AbstractHandler(RobotContext robotContext, ServiceContext serviceContext) {
        this.robotContext = robotContext;
        this.serviceContext = serviceContext;
    }

    @Override
    public void handle(AbstractMessage abstractMessage) {

        AbstractMessage message = handleInternal(abstractMessage);

        if (successor != null) {
            successor.handle(message);
        }

    }


    abstract public AbstractMessage handleInternal(AbstractMessage abstractMessage);

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
