package edu.hust.robothub.hubservice.clients;

import edu.hust.robothub.core.service.AbstractClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;



//用来调用服务的

@Component
public class RestTemplateClient<T,R> extends AbstractClient<T,R> {
     /**用于调用hub系统内布的服务*/
    @Autowired
    @Qualifier("internalRestTemplate")
    RestTemplate internalRestTemplate;

    /**用于调用外部的服务，即各种http Api*/
    @Autowired
    @Qualifier("externalRestTemplate")
    RestTemplate externalRestTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateClient.class);


    public R invokeRemote(RequestEntity<T> requestEntity,boolean isInternal,Class<R> responseType){
        if(isInternal){
           return internalRestTemplate.exchange(requestEntity,responseType).getBody();
        }else {
            return externalRestTemplate.exchange(requestEntity,responseType).getBody();
        }
    }


   HttpMethod getSringHttpMethod(int httpMethod){
     if(httpMethod==0){
         return HttpMethod.GET;
     }else {
       return HttpMethod.POST;
     }
         }

    @Override
    public R invokeRemote(T body, Map<String, String> headers, int httpMethod, String serviceUrl,Class<R> responseType,boolean isInternal) {
        HttpHeaders hs=new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
            hs.add(stringStringEntry.getKey(),stringStringEntry.getValue());
        }
        URI url=null;
        try {
            url=new URI(serviceUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        RequestEntity<T> requestEntity=new RequestEntity<>(body,hs,getSringHttpMethod(httpMethod),url);

       return invokeRemote(requestEntity,false, responseType);
    }
}
