package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;

/**
 * 暂时先不实现，貌似应该没有这种业务逻辑，即调用完service之后，又去订阅topic。
 *
 * @param
 * @author BNer
 * @date 2020/9/25 19:48
 * @return
 */
public class SubscribeServiceCallbackHandler extends ServiceCallbackHandler {


    public SubscribeServiceCallbackHandler(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    AbstractMessage doCallBack(AbstractMessage abstractMessage) {
        return null;
    }

}
