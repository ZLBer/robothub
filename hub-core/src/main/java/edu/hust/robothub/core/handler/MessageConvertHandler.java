package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.RosMessage;
import edu.hust.robothub.core.message.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConvertHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConvertHandler.class);


    public MessageConvertHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        //如果是机器消息，转成 service 消息
        if (abstractMessage instanceof RosMessage) {
            LOGGER.info("MessageConvertHandler  convert rosMessage to serviceMessage, " + abstractMessage.getClass() + "  " + abstractMessage.toString());
            ServiceMessage serviceMessage = new ServiceMessage((RosMessage) abstractMessage);
            return serviceMessage;
            //如果是服务消息，转成 机器消息
        } else if (abstractMessage instanceof ServiceMessage) {
            LOGGER.info("MessageConvertHandler  convert serviceMessage to rosMessage," + abstractMessage.getClass() + "  " + abstractMessage.toString());
            RosMessage rosMessage = new RosMessage((ServiceMessage) abstractMessage);
            return rosMessage;
        }

        return abstractMessage;
    }
}
