package edu.hust.robothub.core.service;

import edu.hust.robothub.core.api.ServiceResponseMessage;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.ServiceMessage;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StandardServiceInvoker implements ServiceInvoker {
    ServiceContext serviceContext;
    AbstractClient<ServiceMessage, String> abstractClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardServiceInvoker.class);
    public StandardServiceInvoker(ServiceContext serviceContext, AbstractClient abstractClient) {
        this.serviceContext = serviceContext;
        this.abstractClient = abstractClient;
    }

    @Override
    public AbstractMessage invoke(AbstractMessage abstractMessage) {
        boolean isIntenal = serviceContext.getServiceUrl().contains("zuul")?true:false;

        String serviceResponseMessage = abstractClient.invokeRemote((ServiceMessage) abstractMessage, serviceContext.getHeaders(), serviceContext.getHttpMethod(), serviceContext.getServiceUrl(), String.class,isIntenal);

        LOGGER.info("invoker return:"+serviceResponseMessage);

        return new ServiceMessage(serviceResponseMessage);
    }
}
