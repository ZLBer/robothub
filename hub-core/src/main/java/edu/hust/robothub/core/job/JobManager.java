package edu.hust.robothub.core.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 用于job的添加删除和暂存
 * 单例，系统内唯一存在
 *
 * @param
 * @author BNer
 * @date 2020/9/27 17:27
 * @return
 */
public enum JobManager {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobManager.class);

    static ExecutorService executor = new ThreadPoolExecutor(20, 200,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "JOB-EXECUTOR-" + r.hashCode());
        }
    });
    Map<String, AbstractJob> jobSets = new HashMap<>();

    public static JobManager getInstance() {
        return INSTANCE;
    }

    public boolean addJob(AbstractJob job) {
        if (jobSets.containsKey(job.getJobId())) {
            return false;
        }
        jobSets.put(job.getJobId(),job);
        return true;
    }

    public boolean delJob(String jobId) {
        if (!jobSets.containsKey(jobId)) {
            return false;
        }
        jobSets.remove(jobId);
        return true;
    }

    public boolean stopJob(String jobId) {
        if (!jobSets.containsKey(jobId)) {
            return false;
        }
        //中断执行线程
        Thread jobExecuteThread = jobSets.get(jobId).getJobExecuteThread();
        if(jobExecuteThread==null) return false;

        jobExecuteThread.interrupt();

        return true;
    }

    public void execute(AbstractJob job) {
        LOGGER.info("JobManager start a new job：" + job.getJobName());
        executor.execute(job);
        Thread.currentThread().interrupt();
    }

    public List<AbstractJob> getAllJob() {
        return new ArrayList<>(jobSets.values());
    }
}
