package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

abstract public class AbstractJob implements Job, Runnable {
    public static final int STATUS_RUNING = 1;
    public static final int STATUS_END = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);
    RobotContext robotContext;
    ServiceContext serviceContext;
    private String jobName;
    private long startTime;
    private volatile int status;
    private int jobType;

    public AbstractJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        this.jobName = jobName;
        this.robotContext = robotContext;
        startTime = System.nanoTime();
        this.serviceContext = serviceContext;
        status = STATUS_RUNING;
    }

    public void run() {

        JobManager.getInstance().addJob(this);

        doRun();

        while (!Thread.interrupted()) {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //JobManager.getInstance().cleanJon(this);
    }

    abstract public void doRun();

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
