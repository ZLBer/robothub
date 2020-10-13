package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.result.ResultCatchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageStorageHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStorageHandler.class);

    ResultCatchManager resultCatchManager = ResultCatchManager.getInstance();

    public MessageStorageHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {


      LOGGER.info("storage the message:"+serviceContext.getJobId()+","+abstractMessage.getMessage());
      resultCatchManager.add(serviceContext.getJobId(),new JobResult(System.currentTimeMillis(),abstractMessage.getMessage()));
      return abstractMessage;
    }
}
