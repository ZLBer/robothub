package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

abstract public class AbstractJob implements Job, Runnable {
    public static final int STATUS_RUNING = 1;
    public static final int STATUS_NEW = 1;
    public static final int STATUS_END = 2;
    public static final  int JOBTYPE_PUBLISH = 0;
    public static final  int JOBTYPE_SERVICE = 1;
    public static final  int JOBTYPE_SUBSCRIBE = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);
    RobotContext robotContext;
    ServiceContext serviceContext;
    private String jobName;
    private String jobId;
    private long startTime;
    private volatile int status;
    int jobType;
    Thread jobExecuteThread;

    public AbstractJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        this.jobName = jobName;
        this.robotContext = robotContext;
        startTime = System.nanoTime();
        this.serviceContext = serviceContext;
        status = STATUS_NEW;
        jobId=UUID.randomUUID().toString();
    }

    public void run() {

        this.setJobExecuteThread(Thread.currentThread()); //设置执行线程

        this.status = STATUS_RUNING;

        doRun();

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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Thread getJobExecuteThread() {
        return jobExecuteThread;
    }

    public void setJobExecuteThread(Thread jobExecuteThread) {
        this.jobExecuteThread = jobExecuteThread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractJob that = (AbstractJob) o;
        return jobId.equals(that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId);
    }
}
