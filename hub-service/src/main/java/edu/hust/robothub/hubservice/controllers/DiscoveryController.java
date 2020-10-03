package edu.hust.robothub.hubservice.controllers;

import edu.hust.robothub.core.api.ObjListApiValue;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.result.ResultKV;
import edu.hust.robothub.hubservice.services.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;





/** 用于发现第三方服务
 */  
@RestController
@RequestMapping(value="/eureka")
public class DiscoveryController {
    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping(value="/services")
    public ObjListApiValue<String> getEurekaServices() {

        ResultKV<List<String>> resultKV = discoveryService.getEurekaServices();

        return new ObjListApiValue<>(StatusCode.SUCCESS,"success",null,resultKV.getValue());
    }
}
