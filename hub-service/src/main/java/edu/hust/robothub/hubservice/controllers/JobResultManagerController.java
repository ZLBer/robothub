package edu.hust.robothub.hubservice.controllers;

import edu.hust.robothub.core.api.ObjListServiceResponseMessage;
import edu.hust.robothub.core.api.ObjServiceResponseMessage;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.result.JobResultList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/jobResultManager")
public class JobResultManagerController {

    JobManager jobManager = JobManager.getInstance();


    @GetMapping(value = "/getAll")
    public ObjListServiceResponseMessage<JobResultList> getAll() {
        BooleanResultKV<List<JobResultList>> allJobResults = jobManager.getAllJobResults();

       if(!allJobResults.getKey()){
         return new ObjListServiceResponseMessage<>(StatusCode.FAIL,"job resultList empty!",null,null);
       }else {
         return new ObjListServiceResponseMessage<>(StatusCode.SUCCESS,"success!",null,allJobResults.getValue());
       }
    }

    @GetMapping(value = "/getJobResult/{jobId}")
    public ObjServiceResponseMessage<JobResultList> getJobReuslt(@PathVariable("jobId")String jobId){
        BooleanResultKV<JobResultList> jobResults = jobManager.getJobResults(jobId);

        if(!jobResults.getKey()){
           return new ObjServiceResponseMessage<>(StatusCode.FAIL,"no job results!",null,null);
        }else {
            return new ObjServiceResponseMessage<>(StatusCode.SUCCESS,"success!",null,jobResults.getValue());
        }

    }
}
