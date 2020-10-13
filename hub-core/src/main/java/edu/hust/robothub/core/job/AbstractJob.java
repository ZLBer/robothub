package edu.hust.robothub.core.job;

import edu.hust.robothub.core.context.RobotContext;
import edu.hust.robothub.core.context.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

abstract public class AbstractJob implements Job, Runnable {
    public static final int STATUS_NEW = 0;
    public static final int STATUS_RUNING = 1;
    public static final int STATUS_END = 2;
    public static final int STATUS_FREE = 3;
    public static final int STATUS_INTERUPTEED = 4;

    public static final int JOBTYPE_PUBLISH = 0;
    public static final int JOBTYPE_SERVICE = 1;
    public static final int JOBTYPE_SUBSCRIBE = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);
    RobotContext robotContext;
    ServiceContext serviceContext;
    int jobType;
    Thread jobExecuteThread;
    private String jobName;
    private String jobId;
    private long createTime;
    private volatile int status;

    public AbstractJob(String jobName, RobotContext robotContext, ServiceContext serviceContext) {
        this.jobName = jobName;
        this.robotContext = robotContext;
        createTime = System.nanoTime();
        this.serviceContext = serviceContext;
        status = STATUS_NEW;
        jobId = UUID.randomUUID().toString();
    }

    public void run() {

        this.sJobExecuteThread(Thread.currentThread()); //设置执行线程


        this.status = STATUS_RUNING;

        doRun();

    }

   public  boolean checkRosConnect(){
     return   robotContext.getRos().isConnected();
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

    public Thread gJobExecuteThread() {
        return jobExecuteThread;
    }

    public void sJobExecuteThread(Thread jobExecuteThread) {
        this.jobExecuteThread = jobExecuteThread;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
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
