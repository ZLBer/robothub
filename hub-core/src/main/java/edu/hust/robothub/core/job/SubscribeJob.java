package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.robot.RosRobotInstance;


/**订阅topic消息，有回调
 */  
public class SubscribeJob extends AbstractJob {
    public SubscribeJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        super(jobName, robotContext, serviceContext);
        jobType = JOBTYPE_SUBSCRIBE;
    }

    @Override
    public void doRun() {
        RosRobotInstance rosRobotInstance = robotContext.getRosRobotInstance();
        //rosRobotInstance.connect();
        rosRobotInstance.subscribe();
    }

}
