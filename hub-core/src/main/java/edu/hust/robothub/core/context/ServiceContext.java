package edu.hust.robothub.core.context;

import edu.hust.robothub.core.service.ServiceInvoker;

public class ServiceContext implements Context {
    ServiceInvoker serviceInvoker;

    public ServiceInvoker getServiceInvoker() {
        return serviceInvoker;
    }

    public void setServiceInvoker(ServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }
}
