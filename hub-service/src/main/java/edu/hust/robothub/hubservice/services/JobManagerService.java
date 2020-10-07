package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.job.JobFactory;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.ResultKV;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class JobManagerService {

    JobManager jobManager=JobManager.getInstance();

    public ResultKV<List<AbstractJob>> getAllJob(){
       return new ResultKV<>(true,jobManager.getAllJob());
    }

    public ResultKV<AbstractJob> add(int jobType, String jobName,String hostname, int port, Map<String,Object> args){
        ResultKV<AbstractJob> abstractJobResultKV = JobFactory.getInstance().create(jobType,jobName, hostname, port, args);

        if(abstractJobResultKV.getKey()==false||!jobManager.addJob(abstractJobResultKV.getValue())){
            return new ResultKV<>(false,null);
        }else {
            return new ResultKV<>(true,abstractJobResultKV.getValue());
        }
    }



    public  ResultKV<String> stop(String jobId){
       if(jobManager.interupteJob(jobId)){
          return new ResultKV<>(true,"success  stop job:"+jobId);
       }else {
           return new ResultKV<>(false,"fail stop job: "+jobId);
       }
    }

    public ResultKV<String> del(String jobId){
          if(jobManager.delJob(jobId)){
           return  new ResultKV<>(true,"success del job:"+jobId);
          }else {
            return new ResultKV<>(false,"fail del job"+jobId);
          }
    }
    public ResultKV<String> execute(String jobId){
        if(jobManager.execute(jobId)){
            return  new ResultKV<>(true,"success execute job:"+jobId);
        }else {
            return new ResultKV<>(false,"fail execute job:"+jobId);
        }
    }

    public ResultKV<AbstractJob> get(String jobId){
        AbstractJob job = jobManager.getJob(jobId);
       if(job!=null){
        return  new ResultKV<>(true,job);
        }else {
            return new ResultKV<>(false,null);
        }
    }

    public ResultKV<List<AbstractJob>> getAll(){
        List<AbstractJob> allJob = jobManager.getAllJob();
        return  new ResultKV<>(true,allJob);
    }
}
