package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import edu.hust.robothub.core.message.RosMessage;
import edu.hust.robothub.core.robot.RosRobotInstance;

/** publish 消息到topic，无回调
 */  
public class PublishJob extends AbstractJob {
    private RosMessage rosMessage;

    public PublishJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        super(jobName, robotContext, serviceContext);
        jobType = JOBTYPE_PUBLISH;
    }

    @Override
    public void doRun() {
        RosRobotInstance rosRobotInstance = robotContext.getRosRobotInstance();
       // rosRobotInstance.connect();
        rosRobotInstance.publish(rosMessage);
    }

    public RosMessage getRosMessage() {
        return rosMessage;
    }

    public void setRosMessage(RosMessage rosMessage) {
        this.rosMessage = rosMessage;
    }
}
