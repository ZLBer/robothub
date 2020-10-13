package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.job.AbstractJob;
import edu.hust.robothub.core.job.JobFactory;
import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.result.JobResultList;
import edu.hust.robothub.core.result.ResultCatchManager;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class RosService {

    JobFactory instance = JobFactory.getInstance();

    JobManager jobManager = JobManager.getInstance();

    ResultCatchManager resultCatchManager = ResultCatchManager.getInstance();


    private BooleanResultKV<String> runJob(AbstractJob job) {
        BooleanResultKV<String> execute = jobManager.execute(job);
        if (!execute.getKey()) {
            return execute;
        }
        return new BooleanResultKV<>(true, "the job <" + job.getJobId() + "> is running ,later you can get result");
    }

    public BooleanResultKV<JobResultList> getRosServices(String jobId) {
        JobResultList jobResultList = resultCatchManager.get(jobId);
        if (jobResultList == null) return new BooleanResultKV<>(false, null);
        return new BooleanResultKV<>(true, jobResultList);
    }


    //获取services列表
    public BooleanResultKV<String> callRosServices(String hostname, int port) {
        BooleanResultKV<AbstractJob> resultKV = instance.createServiceJob("getRosServices", hostname, port, "/rosapi/services", "rosapi/Services", new ServiceRequest("{}"));
        return runJob(resultKV.getValue());
    }
    //获取service类型
    public BooleanResultKV<String> callServiceType(String hostname, int port,String service) {
        BooleanResultKV<AbstractJob> resultKV = instance.createServiceJob("getServiceType", hostname, port, "/rosapi/service_type", "rosapi/ServiceType", new ServiceRequest("{\"service\": \""+service+ "\"}"));
        return runJob(resultKV.getValue());
    }
    //获取topics列表
    public BooleanResultKV<String> callRosTopics(String hostname, int port) {
        BooleanResultKV<AbstractJob> resultKV = instance.createServiceJob("getRosTopics", hostname, port, "/rosapi/topics", "rosapi/Topics", new ServiceRequest("{}"));
        return runJob(resultKV.getValue());
    }
    //获取topicl类型
    public BooleanResultKV<String> callTopicType(String hostname, int port,String topic) {
        BooleanResultKV<AbstractJob> resultKV = instance.createServiceJob("getTopicType", hostname, port, "/rosapi/topic_type", "rosapi/TopicType", new ServiceRequest("{\"topic\": \""+topic+ "\"}"));
        return runJob(resultKV.getValue());
    }
    //获取loggers日志
    public BooleanResultKV<String> callRosloggers(String hostname, int port) {
        BooleanResultKV<AbstractJob> resultKV = instance.createServiceJob("getRosloggers", hostname, port, "/rosapi/get_loggers", "roscpp/GetLoggers", new ServiceRequest("{}"));
        return runJob(resultKV.getValue());
    }
}
