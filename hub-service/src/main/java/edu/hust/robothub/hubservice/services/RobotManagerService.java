package edu.hust.robothub.hubservice.services;


import edu.hust.robothub.core.result.BooleanResultKV;
import edu.hust.robothub.core.robot.RobotManager;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理机器人的服务
 */
@Service
public class RobotManagerService {
    RobotManager robotManager = RobotManager.getInstance();

    public BooleanResultKV<String> add(String hostname, int port) {
        if(robotManager.addRobot(hostname, port)){
          return new BooleanResultKV<>(true,"success add robot:"+hostname);
        }else{
            return new BooleanResultKV<>(false,"fail add robot:"+hostname);
        }
    }


    public BooleanResultKV<String> connect(String hostname, int port) {
        if(robotManager.connect(hostname, port)){
            return new BooleanResultKV<>(true,"success connect robot:"+hostname);
        }else{
            return new BooleanResultKV<>(false,"fail connect robot:"+hostname);
        }
    }
    
    public BooleanResultKV<String> remove(String hostname, int port) {
      if(robotManager.delRobot(hostname,port)){
          return new BooleanResultKV<>(true,"success remove robot:"+hostname);
      }else {
          return new BooleanResultKV<>(false,"fail remove robot:"+hostname);
      }
    }

    public BooleanResultKV<RosRobotInvoker> get(String hostname, int port) {
        RosRobotInvoker robot = robotManager.getRobot(hostname, port);
        if(robot==null){
            return new BooleanResultKV<>(false,null);
        }

        return new BooleanResultKV<>(true,robot);

    }

    public BooleanResultKV<List<RosRobotInvoker>> getAll() {
        List<RosRobotInvoker> allRobot = robotManager.getAllRobot();

        return new BooleanResultKV<>(true,allRobot);

    }

    public BooleanResultKV<String> checkIsConnected(String hostname, int port){
      if(robotManager.checkIsConnected(hostname,port)){
          return new BooleanResultKV<>(true," connecting ros :"+hostname);
      }else {
          return new BooleanResultKV<>(false," not connecting ros :"+hostname);
      }
    }
}
