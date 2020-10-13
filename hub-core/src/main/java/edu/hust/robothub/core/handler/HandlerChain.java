package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.AbstractMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerChain implements Handler {
    private static ExecutorService executor = new ThreadPoolExecutor(20, 200,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "JOB-EXECUTOR-" + r.hashCode());
        }
    });

    RobotContext robotContext;
    ServiceContext serviceContext;

    AbstractHandler firstHander;

    public HandlerChain(RobotContext robotContext, ServiceContext serviceContext) {
        this.robotContext = robotContext;
        this.serviceContext = serviceContext;
        firstHander = new Headhandler(robotContext, serviceContext);
    }


    @Override
    public void handle(AbstractMessage abstractMessage) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
         firstHander.handle(abstractMessage);
            }
        });

    }


    public void buildHandlerChain(AbstractHandler... handlers) {

        AbstractHandler pre = firstHander;
        for (AbstractHandler handler : handlers) {
            pre.setSuccessor(handler);
            pre = handler;
        }
       pre.successor=new TailHander(robotContext,serviceContext);
    }


}
