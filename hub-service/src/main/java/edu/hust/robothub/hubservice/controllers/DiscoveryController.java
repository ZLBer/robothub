package edu.hust.robothub.hubservice.controllers;

import edu.hust.robothub.core.api.ObjListServiceResponseMessage;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.hubservice.services.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ObjListServiceResponseMessage<String> getEurekaServices() {

        BooleanResultKV<List<String>> booleanResultKV = discoveryService.getEurekaServices();

        return new ObjListServiceResponseMessage<>(StatusCode.SUCCESS,"success",null, booleanResultKV.getValue());

    }
}
