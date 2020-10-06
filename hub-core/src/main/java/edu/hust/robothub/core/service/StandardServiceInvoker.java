package edu.hust.robothub.core.service;

import edu.hust.robothub.core.api.ServiceResponseMessage;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.ServiceMessage;


public class StandardServiceInvoker implements ServiceInvoker {
    ServiceContext serviceContext;
    AbstractClient<ServiceMessage, ServiceResponseMessage> abstractClient;

    public StandardServiceInvoker(ServiceContext serviceContext, AbstractClient abstractClient) {
        this.serviceContext = serviceContext;
        this.abstractClient = abstractClient;
    }

    @Override
    public AbstractMessage invoke(AbstractMessage abstractMessage) {

          ServiceResponseMessage serviceResponseMessage = abstractClient.invokeRemote((ServiceMessage) abstractMessage, serviceContext.getHeaders(), serviceContext.getHttpMethod(), serviceContext.getServiceUrl(), ServiceResponseMessage.class);

          return serviceResponseMessage;
    }
}
