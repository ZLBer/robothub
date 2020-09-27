package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.RosMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service回调执行publish的handler
 *
 * @param
 * @author BNer
 * @date 2020/9/25 19:45
 * @return
 */
public class PublishServiceCallbackHandler extends ServiceCallbackHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishServiceCallbackHandler.class);


    public PublishServiceCallbackHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    AbstractMessage doCallBack(AbstractMessage abstractMessage) {
        LOGGER.info("PublishServiceCallbackHandler begin  handle");
        RosMessage rosMessage = (RosMessage) abstractMessage;
        robotContext.getRosRobotInstance().publish(rosMessage);
        return null;
    }

}
