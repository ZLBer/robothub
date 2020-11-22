package edu.hust.robothub.hubservice.init;


import edu.hust.robothub.core.job.JobManager;
import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.core.robot.RobotManager;
import edu.hust.robothub.hubservice.model.JobModule;
import edu.hust.robothub.hubservice.model.JobResultModule;
import edu.hust.robothub.hubservice.model.RobotModule;
import edu.hust.robothub.hubservice.repository.JobRepository;
import edu.hust.robothub.hubservice.repository.JobResultRepository;
import edu.hust.robothub.hubservice.repository.RobotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;




/*  
 *    
 *  将数据库的数据加载到catch组件中
 * @author BNer  
 * @date 2020/11/22 21:06
 * @param   
 * @return   
 */  
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    RobotRepository robotRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobResultRepository jobResultRepository;

    RobotManager robotManager=RobotManager.getInstance();

    JobManager jobManager=JobManager.getInstance();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
      LOGGER.info("init data from db to cache");
      initRobot();
      initjobResult();
    }

    void initRobot(){
        Iterable<RobotModule> all = robotRepository.findAll();

        for (RobotModule robotModule : all) {
            robotManager.addRobot(robotModule.getHostname(),robotModule.getPort());
        }

    }

    void initjobResult(){
        Iterable<JobResultModule> all = jobResultRepository.findAll();

        for (JobResultModule jobResultModule : all) {
           jobManager.addJobResult(jobResultModule.getJobId(),new JobResult(jobResultModule.getJobTime(),jobResultModule.getJobRes()));
        }
    }

    void  initJob(){
        Iterable<JobModule> all = jobRepository.findAll();
        //TODO:

    }



}
