package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 *    
 *   仅仅起到头节点的作用
 * @author BNer  
 * @date 2020/10/4 20:17
 * @param   
 * @return   
 */  
public class Headhandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Headhandler.class);

    public Headhandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        LOGGER.info("The HeadHander is  null Handler," + abstractMessage.toString());
        return abstractMessage;
    }
}
