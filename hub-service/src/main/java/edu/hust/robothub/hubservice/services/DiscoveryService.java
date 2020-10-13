package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.result.BooleanResultKV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/** 用于发现第三方服务
 */  
@Service
public class DiscoveryService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public BooleanResultKV<List<String>> getEurekaServices(){
       List<String> services = new ArrayList<String>();

        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(instance->{
                services.add( String.format("%s:%s",serviceName,instance.getUri()));
            });
        });

        return new BooleanResultKV<>(true,services);
    }



}
