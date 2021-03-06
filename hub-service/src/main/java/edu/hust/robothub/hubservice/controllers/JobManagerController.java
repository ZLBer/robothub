package edu.hust.robothub.hubservice.controllers;


import edu.hust.robothub.core.api.NullServiceResponseMessage;
import edu.hust.robothub.core.api.ObjListServiceResponseMessage;
import edu.hust.robothub.core.api.ObjServiceResponseMessage;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.hubservice.clients.RestTemplateClient;
import edu.hust.robothub.hubservice.services.JobManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/jobManager")
public class JobManagerController {
    @Autowired
    RestTemplateClient restTemplateClient;

    @Autowired
    JobManagerService jobManagerService;

    @PostMapping("/add/{jobType}/{jobName}/{hostname}/{port}")
    public ObjServiceResponseMessage<AbstractJob> add(@PathVariable("jobType") int jobType,
                                          @PathVariable("jobName") String jobName,
                                          @PathVariable("hostname") String hostName,
                                          @PathVariable("port") int port,
                                          @RequestBody Map<String, String> args) {

        Map<String, Object> cp = new HashMap<>(args);


        //判断需不需要加入service，不需要就是纯subscribe
        if(args.get("serviceUrl")!=null){
            cp.put("abstractClient", restTemplateClient);
            cp.put("headers", new HashMap<>());
        }

        BooleanResultKV<AbstractJob> booleanResultKV = jobManagerService.add(jobType, jobName, hostName, port, cp);

        if(booleanResultKV.getKey()){
        return new ObjServiceResponseMessage<>(StatusCode.SUCCESS,"success to add a job",null, booleanResultKV.getValue());
        }else {
            return new ObjServiceResponseMessage<>(StatusCode.FAIL,"fail to add a job",null,null);
        }

    }

    @GetMapping("/get/{jobId}")
    public ObjServiceResponseMessage<AbstractJob> get(@PathVariable("jobId") String jobId) {

        BooleanResultKV<AbstractJob> booleanResultKV = jobManagerService.get(jobId);


        if(booleanResultKV.getKey()){
         return new ObjServiceResponseMessage<>(StatusCode.SUCCESS,"success",null, booleanResultKV.getValue());
        }else {
            return new ObjServiceResponseMessage<>(StatusCode.FAIL,"fail to get this job:"+jobId,null,null);
        }

    }


    @GetMapping("/getAll")
    public ObjListServiceResponseMessage<AbstractJob> getAll() {

        BooleanResultKV<List<AbstractJob>> booleanResultKV = jobManagerService.getAll();


        if(booleanResultKV.getKey()){
            return new ObjListServiceResponseMessage<>(StatusCode.SUCCESS,"success",null, booleanResultKV.getValue());
        }else {
            return new ObjListServiceResponseMessage<>(StatusCode.FAIL,"fail to get  job",null,null);
        }

    }



    @GetMapping("/del/{jobId}")
    public NullServiceResponseMessage add(@PathVariable("jobId") String jobId) {

        BooleanResultKV<String> booleanResultKV = jobManagerService.del(jobId);

        return NullServiceResponseMessage.getReturnValue(booleanResultKV);
    }

    @GetMapping("/stop/{jobId}")
    public NullServiceResponseMessage stop(@PathVariable("jobId") String jobId) {

        BooleanResultKV<String> booleanResultKV = jobManagerService.stop(jobId);

        return NullServiceResponseMessage.getReturnValue(booleanResultKV);
    }

    @GetMapping("/execute/{jobId}")
    public NullServiceResponseMessage execute(@PathVariable("jobId") String jobId) {

        BooleanResultKV<String> booleanResultKV = jobManagerService.execute(jobId);

        return NullServiceResponseMessage.getReturnValue(booleanResultKV);
    }


}
