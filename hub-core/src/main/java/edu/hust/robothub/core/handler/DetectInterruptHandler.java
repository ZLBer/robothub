package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.wpi.rail.jrosbridge.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DetectInterruptHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectInterruptHandler.class);


    public DetectInterruptHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        if (Thread.interrupted()|| JobManager.getInstance().isInterupted(serviceContext.getJobId())) {
            LOGGER.info("DetectInterruptHandler  detected Interrupt!!");
            Topic subscribeTopic = robotContext.getSubscribeTopic();
            if(subscribeTopic!=null) subscribeTopic.unsubscribe();
            return null;
        }
        LOGGER.info("DetectInterruptHandler  detected  no  Interrupt.");
        return abstractMessage;
    }
}
