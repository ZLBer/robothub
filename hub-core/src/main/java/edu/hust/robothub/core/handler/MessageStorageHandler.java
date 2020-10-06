package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageStorageHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStorageHandler.class);


    public MessageStorageHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {

      LOGGER.info("storage the message:"+serviceContext.getJobId()+","+abstractMessage.getMessage());

      return abstractMessage;
    }
}
