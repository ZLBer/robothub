package edu.hust.robothub.hubservice.services;
import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.result.JobResultList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class jobResultService {
   JobManager jobManager = JobManager.getInstance();

   public BooleanResultKV<List<JobResultList>> getAllJobRsults(){
      BooleanResultKV<List<JobResultList>> allJobResults = jobManager.getAllJobResults();

      if(allJobResults.getKey()){
         return new BooleanResultKV<>(false,null);
      }else {
         return  new BooleanResultKV<>(true,allJobResults.getValue());
      }
   }

   public BooleanResultKV<JobResultList> getjobReults(String jobId){
      return jobManager.getJobResults(jobId);
   }
}
