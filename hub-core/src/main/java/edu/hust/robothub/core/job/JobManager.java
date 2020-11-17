package edu.hust.robothub.core.job;

import edu.hust.robothub.core.cache.Cache;
import edu.hust.robothub.core.cache.StandardCache;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.result.JobResultList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 用于job的添加删除和暂存
 * 单例模式，系统内唯一存在
 *
 * @param
 * @author BNer
 * @date 2020/9/27 17:27
 * @return
 */
public enum JobManager  {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobManager.class);

    private  ExecutorService executor = new ThreadPoolExecutor(20, 200,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "JOB-EXECUTOR-" + r.hashCode());
        }
    });

   // Map<String, AbstractJob> jobSets = new HashMap<>();
   // Map<String, JobResultList> jobResultSets = new HashMap<>();

    Cache<String,AbstractJob> jobCache = new StandardCache<>();
    Cache<String,JobResultList> jonResultCatch = new StandardCache<>();




    public static JobManager getInstance() {
        return INSTANCE;
    }

    public boolean addJobResult(String jobId, JobResult jobResult) {
        //if (jobResultSets.containsKey(jobId)) jobResultSets.put(jobId, new JobResultList(jobId));
        if (jonResultCatch.containsKey(jobId)) jonResultCatch.cache(jobId, new JobResultList(jobId));
       // jobResultSets.get(jobId).addJobResult(jobResult);
        jonResultCatch.getCache(jobId).addJobResult(jobResult);

        return true;
    }

    public boolean addJob(AbstractJob job) {
       // if (jobSets.containsKey(job.getJobId())) {
        if (jobCache.containsKey(job.getJobId())) {
            return false;
        }
      //  jobSets.put(job.getJobId(), job);
         jobCache.cache(job.getJobId(), job);
        LOGGER.info("add a new job:" + job.getJobId() + "  " + job.getJobName());
        return true;
    }

    public AbstractJob getJob(String jobId) {
      //  return jobSets.get(jobId);
        return jobCache.getCache(jobId);

    }

    public BooleanResultKV<String> delJob(String jobId) {
      //  if (!jobSets.containsKey(jobId)) {
        if (!jobCache.containsKey(jobId)) {
            return new BooleanResultKV<>(false, "the job <" + jobId + ">  not exit");
        }
      //  jobSets.remove(jobId);
        return new BooleanResultKV<>(true, "sucess del a job <" + jobId + ">");
    }

    public boolean isInterupted(String jobId) {
        //AbstractJob job = jobSets.get(jobId);
        AbstractJob job =jobCache.getCache(jobId);

        if (job.getStatus() == AbstractJob.STATUS_INTERUPTED) {
            job.setStatus(AbstractJob.STATUS_FREE);
            return true;
        }
        return false;
    }

    //强行中断
    public boolean interupteJob(String jobId) {
      //  if (!jobSets.containsKey(jobId)) {
        if (!jobCache.containsKey(jobId)) {
            return false;
        }

       // AbstractJob job = jobSets.get(jobId);
        AbstractJob job = jobCache.getCache(jobId);
        job.setStatus(AbstractJob.STATUS_INTERUPTED);
        //中断执行线程
        Thread jobExecuteThread = job.gJobExecuteThread();
        if (jobExecuteThread == null) return false;

        jobExecuteThread.interrupt();

        job.sJobExecuteThread(null);
        return true;
    }


    public  BooleanResultKV<String> execute(String jobId) {
       // if (!jobSets.containsKey(jobId))
        if (!jobCache.containsKey(jobId))
            return new BooleanResultKV<>(false,"the job <"+jobId+"> not exit");


       // AbstractJob abstractJob = jobSets.get(jobId);

        AbstractJob abstractJob = jobCache.getCache(jobId);

        return execute(abstractJob);
    }

    public BooleanResultKV<String> execute(AbstractJob job) {


        if(!job.checkRosConnect()){
            return new BooleanResultKV<>(false,"the job of ros is not connect");
        }
        LOGGER.info("JobManager start a new job：" + job.getJobName());


        //当job处于执行或者中断状态时禁止再次执行
        if (!checkStatus(job.getStatus()))
            return new BooleanResultKV<>(false, "the job status is not right:" + job.getStatus());
        executor.execute(job);
        return new BooleanResultKV<>(true, "");
    }

    private boolean checkStatus(int status) {
        if (status == AbstractJob.STATUS_RUNING || status == AbstractJob.STATUS_RUNING) return false;

        return true;
    }

    public List<AbstractJob> getAllJob() {
       // return new ArrayList<>(jobSets.values());
       return jobCache.getAllValues();
    }


}
