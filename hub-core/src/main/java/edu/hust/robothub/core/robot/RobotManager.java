package edu.hust.robothub.core.robot;

import edu.wpi.rail.jrosbridge.Ros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
/**  
 *用于机器人调用实例的添加、删除和暂存
 * 单例，系统内唯一存在
 * @author BNer  
 * @date 2020/9/27 17:26
 * @param   
 * @return   
 */
public enum RobotManager {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(RosRobotInvoker.class);

    private final String COLON = ":";
    private Map<String, Robot> robotSets = new HashMap<>();

    public boolean addRobot(String hostname, int port) {
        Ros ros = new Ros(hostname, port);
        return addRobot(ros);
    }

    ;

    public boolean addRobot(Ros ros) {

        RosRobotInvoker rosRobotInvoker = new RosRobotInvoker(ros);
        robotSets.put(ros.getHostname() + COLON + ros.getPort(), rosRobotInvoker);

        LOGGER.info("add a new robot "+ros.getHostname()+COLON+ros.getPort());
        return true;
    }


    public boolean delRobot(String hostName, String port) {
        String key = hostName + COLON + port;
        if (!robotSets.containsKey(key)) {
            return false;
        }
        robotSets.remove(key);
        return true;
    }

    public Robot getRobot(String hostName, String port){
      return getRobot(hostName+COLON+port);
    }

    public Robot getRobot(String rk){
     return robotSets.get(rk);
    }

    static public RobotManager getInstance(){
       return INSTANCE;
    }

}
