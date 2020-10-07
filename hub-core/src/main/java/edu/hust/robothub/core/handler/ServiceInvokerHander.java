package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.service.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用service服务的Handler
 *
 * @param
 * @author BNer
 * @date 2020/9/25 19:28
 * @return
 */
public class ServiceInvokerHander extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInvokerHander.class);

    ServiceInvoker remoteInvoker;

    public ServiceInvokerHander(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
        this.remoteInvoker = serviceContext.getServiceInvoker();
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        LOGGER.info("ServiceInvokerHander begin  handle," + abstractMessage.getClass() + "  " + abstractMessage.toString());
        AbstractMessage rm = null;

        if(remoteInvoker==null) {

            LOGGER.info("no remoteInvoker so return:"+abstractMessage.getMessage());
            return abstractMessage;
        }
        try {
            rm = remoteInvoker.invoke(abstractMessage);
        } catch (Exception e) {
            LOGGER.error("serviceInvoker broken because  of" );
            e.printStackTrace();
        }
        return rm;
    }
}
