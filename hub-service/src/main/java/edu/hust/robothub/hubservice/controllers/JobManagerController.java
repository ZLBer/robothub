package edu.hust.robothub.hubservice.controllers;


import edu.hust.robothub.core.api.NullApiValue;
import edu.hust.robothub.core.result.ResultKV;
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
import java.util.Map;

@RestController
@RequestMapping(value = "/jobManager")
public class JobManagerController {
    @Autowired
    RestTemplateClient restTemplateClient;

    @Autowired
    JobManagerService jobManagerService;

    @PostMapping("/add/{jobType}/{jobName}/{hostname}/{port}/")
    public NullApiValue add(@PathVariable("jobType") int jobType,
                            @PathVariable("jobName") String jobName,
                            @PathVariable("hostname") String hostName,
                            @PathVariable("port") int port,
                            @RequestBody Map<String, String> args) {

        Map<String, Object> cp = new HashMap<>(args);
        cp.put("abstractClient", restTemplateClient);
        ResultKV<String> resultKV = jobManagerService.add(jobType, jobName, hostName, port, cp);

        return NullApiValue.getReturnValue(resultKV);
    }

    @GetMapping("/del/{jobId}")
    public NullApiValue add(@PathVariable("jobId") String jobId) {

        ResultKV<String> resultKV = jobManagerService.del(jobId);

        return NullApiValue.getReturnValue(resultKV);
    }

    @GetMapping("/stop/{jobId}")
    public NullApiValue stop(@PathVariable("jobId") String jobId) {

        ResultKV<String> resultKV = jobManagerService.stop(jobId);

        return NullApiValue.getReturnValue(resultKV);
    }


}
