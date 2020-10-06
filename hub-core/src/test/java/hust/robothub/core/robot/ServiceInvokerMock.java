package hust.robothub.core.robot;

import edu.hust.robothub.core.message.AbstractMessage;
import edu.hust.robothub.core.message.ServiceMessage;
import edu.hust.robothub.core.service.ServiceInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceInvokerMock implements ServiceInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInvokerMock.class);
    public AbstractMessage invoke(AbstractMessage abstractMessage) {
        LOGGER.info("ServiceInvokerMock invoker the services"+abstractMessage.getClass()+"  ");
        return new ServiceMessage(abstractMessage.getMessage());
    }
}
