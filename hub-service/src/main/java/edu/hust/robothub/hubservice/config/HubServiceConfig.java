package edu.hust.robothub.hubservice.config;

import edu.hust.robothub.hubservice.utils.UserContextInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;



//进行服务的一些基础配置

@Component
public class HubServiceConfig {

  @Value("${example.property}")
  private String exampleProperty="";

  public String getExampleProperty(){
    return exampleProperty;
  }

  @LoadBalanced
  @Bean("internalRestTemplate")
  @Primary
  public RestTemplate getRestTemplate(){
    RestTemplate template = new RestTemplate();
    List interceptors = template.getInterceptors();
    if (interceptors==null){
      template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
    }
    else{
      interceptors.add(new UserContextInterceptor());
      template.setInterceptors(interceptors);
    }

    return template;
  }

  @Bean(name = "externalRestTemplate")
  public RestTemplate restTemplate(@Qualifier("externalClientHttpRequestFactory") ClientHttpRequestFactory factory){
    return new RestTemplate(factory);
  }

  @Bean(name = "externalClientHttpRequestFactory")
  public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(15000);
    factory.setReadTimeout(5000);
    return factory;
  }
}
