package edu.hust.robothub.example;

import edu.hust.robothub.example.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import javax.servlet.Filter;


/** 第三方服务示例
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ExampleServiceApplication {

    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleServiceApplication.class, args);
    }
}
