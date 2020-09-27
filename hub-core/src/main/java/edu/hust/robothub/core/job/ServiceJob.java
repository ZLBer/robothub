package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.robot.RosRobotInstance;

public class ServiceJob extends AbstractJob {
    public ServiceJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        super(jobName, robotContext, serviceContext);
    }

    @Override
    public void doRun() {
        RosRobotInstance rosRobotInstance = robotContext.getRosRobotInstance();
        rosRobotInstance.connect();
        rosRobotInstance.service();
    }
}
