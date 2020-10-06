package edu.hust.robothub.core.context;

import edu.hust.robothub.core.service.ServiceInvoker;

import java.util.HashMap;
import java.util.Map;

public class ServiceContext implements Context {
    Object body;
    Map<String, String> headers = new HashMap<>();
    int httpMethod = 0;
    String serviceUrl;
    String jobId;
    ServiceInvoker serviceInvoker;

    public ServiceInvoker getServiceInvoker() {
        return serviceInvoker;
    }

    public void setServiceInvoker(ServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }


    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(int httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
