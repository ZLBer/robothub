package edu.hust.robothub.core.service;

import edu.hust.robothub.core.api.ApiValue;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.ServiceMessage;


public class StandardServiceInvoker implements ServiceInvoker {
    ServiceContext serviceContext;
    AbstractClient<ServiceMessage, ApiValue> abstractClient;

    public StandardServiceInvoker(ServiceContext serviceContext, AbstractClient abstractClient) {
        this.serviceContext = serviceContext;
        this.abstractClient = abstractClient;
    }

    @Override
    public AbstractMessage invoke(AbstractMessage abstractMessage) {

          ApiValue apiValue = abstractClient.invokeRemote((ServiceMessage) abstractMessage, serviceContext.getHeaders(), serviceContext.getHttpMethod(), serviceContext.getServiceUrl(), ApiValue.class);

          return apiValue;
    }
}
