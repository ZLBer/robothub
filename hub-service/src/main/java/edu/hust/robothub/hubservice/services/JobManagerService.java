package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.job.JobFactory;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.BooleanResultKV;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class JobManagerService {

    JobManager jobManager=JobManager.getInstance();

    public BooleanResultKV<List<AbstractJob>> getAllJob(){
       return new BooleanResultKV<>(true,jobManager.getAllJob());
    }

    public BooleanResultKV<AbstractJob> add(int jobType, String jobName, String hostname, int port, Map<String,Object> args){
        BooleanResultKV<AbstractJob> abstractJobBooleanResultKV = JobFactory.getInstance().create(jobType,jobName, hostname, port, args);

        if(abstractJobBooleanResultKV.getKey()==false||!jobManager.addJob(abstractJobBooleanResultKV.getValue())){
            return new BooleanResultKV<>(false,null);
        }else {
            return new BooleanResultKV<>(true, abstractJobBooleanResultKV.getValue());
        }
    }



    public BooleanResultKV<String> stop(String jobId){
       if(jobManager.interupteJob(jobId)){
          return new BooleanResultKV<>(true,"success  stop job:"+jobId);
       }else {
           return new BooleanResultKV<>(false,"fail stop job: "+jobId);
       }
    }

    public BooleanResultKV<String> del(String jobId){
        return jobManager.delJob(jobId);
    }
    public BooleanResultKV<String> execute(String jobId){
        BooleanResultKV<String> resultKV = jobManager.execute(jobId);
        if(resultKV.getKey()) {
            return  new BooleanResultKV<>(true,"success execute job <"+jobId+">");
        }else {
            return new BooleanResultKV<>(false,resultKV.getValue());
        }
    }

    public BooleanResultKV<AbstractJob> get(String jobId){
        AbstractJob job = jobManager.getJob(jobId);
       if(job!=null){
        return  new BooleanResultKV<>(true,job);
        }else {
            return new BooleanResultKV<>(false,null);
        }
    }

    public BooleanResultKV<List<AbstractJob>> getAll(){
        List<AbstractJob> allJob = jobManager.getAllJob();
        return  new BooleanResultKV<>(true,allJob);
    }


}
