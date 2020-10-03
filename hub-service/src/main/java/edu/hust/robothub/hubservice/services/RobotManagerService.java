package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.result.ResultKV;
import edu.hust.robothub.core.robot.Robot;
import edu.hust.robothub.core.robot.RobotManager;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import org.springframework.stereotype.Service;

/**
 * 管理机器人的服务
 */
@Service
public class RobotManagerService {
    RobotManager robotManager = RobotManager.getInstance();

    public ResultKV<String> add(String hostname, int port) {
        if(robotManager.addRobot(hostname, port)){
          return new ResultKV<>(true,"success add robot:"+hostname);
        }else{
            return new ResultKV<>(false,"fail add robot:"+hostname);
        }
    }
    
    public  ResultKV<String> remove(String hostname,int port) {
      if(robotManager.delRobot(hostname,port)){
          return new ResultKV<>(true,"success remove robot:"+hostname);
      }else {
          return new ResultKV<>(false,"fail remove robot:"+hostname);
      }
    }

    public ResultKV<RosRobotInvoker> get(String hostname, int port) {
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);
        if(robot==null){
            return new ResultKV<>(false,null);
        }

        return new ResultKV<>(true,robot);

    }

    public ResultKV<String> checkIsConnected(String hostname, int port){
      if(robotManager.checkIsConnected(hostname,port)){
          return new ResultKV<>(true," connecting ros :"+hostname);
      }else {
          return new ResultKV<>(false," not connecting ros :"+hostname);
      }
    }
}
