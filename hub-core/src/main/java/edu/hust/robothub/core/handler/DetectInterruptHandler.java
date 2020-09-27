package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetectInterruptHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectInterruptHandler.class);


    public DetectInterruptHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {

        //线程中断，标识发出job终止信号
        if (Thread.interrupted()) {
            LOGGER.info("DetectInterruptHandler  detected Interrupt!!");
            robotContext.getSubscribeTopic().unsubscribe();
            return null;
        }
        LOGGER.info("DetectInterruptHandler  detected  no  Interrupt.");
        return abstractMessage;
    }
}
