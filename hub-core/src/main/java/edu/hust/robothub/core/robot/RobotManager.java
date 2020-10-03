package edu.hust.robothub.core.robot;

import edu.wpi.rail.jrosbridge.Ros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于机器人调用实例的添加、删除和暂存
 * 单例，系统内唯一存在
 *
 * @param
 * @author BNer
 * @date 2020/9/27 17:26
 * @return
 */
public enum RobotManager {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(RosRobotInvoker.class);

    private final String COLON = ":";
    private Map<String, RosRobotInvoker> robotSets = new HashMap<>();

    static public RobotManager getInstance() {
        return INSTANCE;
    }

    ;

    public boolean addRobot(String hostname, int port) {
        if(checkHas(hostname,port) ) return false;
        Ros ros = new Ros(hostname, port);
        return addRobot(ros);
    }

    private boolean addRobot(Ros ros) {

        RosRobotInvoker rosRobotInvoker = new RosRobotInvoker(ros);

        if (ros.connect()) {
            robotSets.put(ros.getHostname() + COLON + ros.getPort(), rosRobotInvoker);
            LOGGER.info("add a new robot " + ros.getHostname() + COLON + ros.getPort());
            return true;

        } else {
            LOGGER.info("connect fail  of  robot " + ros.getHostname() + COLON + ros.getPort());
            return false;
        }


    }

    public boolean checkHas(String hostName, int port) {
        String key =  builtKey(hostName, port);
        return robotSets.containsKey(key);
    }

    public boolean delRobot(String hostName, int port) {
        String key = builtKey(hostName, port);
        if(!checkHas(hostName,port)) return false;

        Robot robot = robotSets.get(key);

        if (robot == null) return false;

        Ros ros = ((RosRobotInvoker) robot).getRos();

        if (ros.disconnect()) {
            robotSets.remove(key);
            LOGGER.info("del a robot " + ros.getHostname() + COLON + ros.getPort());
            return true;
        } else {
            LOGGER.info("del fail of robot " + ros.getHostname() + COLON + ros.getPort());
            return false;
        }
    }

    public RosRobotInvoker getRobot(String hostName, int port) {
        return getRobot(hostName + COLON + port);
    }

    public RosRobotInvoker getRobot(String rk) {
        return robotSets.get(rk);
    }

    public boolean checkIsConnected(String hostName, int port) {
        if(!checkHas(hostName,port)) return false;

        String key = builtKey(hostName, port);
        RosRobotInvoker rosRobotInvoker = robotSets.get(key);

       if(rosRobotInvoker.getRos().isConnected()){
          return true;
       }else {
          robotSets.remove(key);
          return false;
       }

    }

    private  String builtKey(String hostName,int port){
        String key = hostName + COLON + port;
        return key;
    }

}
