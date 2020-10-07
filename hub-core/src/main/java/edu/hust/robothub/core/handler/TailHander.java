package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.job.PublishJob;
import edu.hust.robothub.core.job.ServiceJob;
import edu.hust.robothub.core.message.AbstractMessage;

public class TailHander  extends AbstractHandler{
    public TailHander(RobotContext robotContext, ServiceContext serviceContext) {
        super(robotContext, serviceContext);
    }

    @Override
    public AbstractMessage handleInternal(AbstractMessage abstractMessage) {
        AbstractJob job = JobManager.getInstance().getJob(serviceContext.getJobId());
      if(job instanceof PublishJob||job instanceof ServiceJob){
         job.setStatus(AbstractJob.STATUS_FREE);//publish 和service 只会走一次
      }
      return null;
    }
}
