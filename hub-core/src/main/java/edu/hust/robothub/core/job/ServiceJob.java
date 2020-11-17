package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.robot.RosRobotInvokerWithContext;

public class ServiceJob extends AbstractJob {
    public ServiceJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        super(jobName, robotContext, serviceContext);
        jobType = JOBTYPE_SERVICE;
    }

    @Override
    public void doRun() {
        RosRobotInvokerWithContext rosRobotInvokerWithContext = robotContext.getRosRobotInvokerWithContext();
        rosRobotInvokerWithContext.service();
    }
}
