package edu.hust.robothub.core.robot;

import edu.hust.robothub.core.cache.Cache;
import edu.hust.robothub.core.cache.StandardCache;
import edu.hust.robothub.core.result.BooleanResultKV;
import edu.wpi.rail.jrosbridge.Ros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于机器人调用实例的添加、删除和暂存
 * 单例模式，系统内唯一存在
 *
 * @param
 * @author BNer
 * @date 2020/9/27 17:26
 * @return
 */
public enum RobotManager {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotManager.class);

    private final String COLON = ":";
   // private Map<String, RosRobotInvoker> robotSets = new HashMap<>();
    private Cache<String,RosRobotInvoker> robotCache = new StandardCache<>();

    static public RobotManager getInstance() {
        return INSTANCE;
    }

    ;

    public BooleanResultKV<String> addRobot(String hostname, int port) {
        if (checkHas(hostname, port)) {
            LOGGER.info("the robot already exit :" + hostname + COLON + port);
            return new BooleanResultKV<>(false, "the robot already exit :" + hostname + COLON + port);
        }

        Ros ros = new Ros(hostname, port);
        addRobot(ros);
        return new BooleanResultKV<>(true, "add a new robot " + ros.getHostname() + COLON + ros.getPort());
    }

    private boolean addRobot(Ros ros) {

        RosRobotInvoker rosRobotInvoker = new RosRobotInvoker(ros);

       // robotSets.put(ros.getHostname() + COLON + ros.getPort(), rosRobotInvoker);
        robotCache.cache(ros.getHostname() + COLON + ros.getPort(), rosRobotInvoker);

        LOGGER.info("add a new robot " + ros.getHostname() + COLON + ros.getPort());
        return true;
    }

    public BooleanResultKV<String> connect(String hostname, int port) {
        if (!checkHas(hostname, port))
            return new BooleanResultKV<>(false,"the robot not exit :" + hostname + COLON + port);
        RosRobotInvoker robot = getRobot(hostname, port);
        robot.connect();
        if (robot.isConnected()) {
            LOGGER.info("connect a new robot " + hostname + ":" + port);
            return new BooleanResultKV<>(true,"connect a new robot " + hostname + ":" + port);

        } else {
            LOGGER.info("connect fail of robot " + hostname + ":" + port);
            return new BooleanResultKV<>(false,"connect fail of robot " + hostname + ":" + port);
        }

    }

    public boolean checkHas(String hostName, int port) {
        String key = builtKey(hostName, port);
        //return robotSets.containsKey(key);
        return robotCache.containsKey(key);
    }

    public BooleanResultKV<String> delRobot(String hostName, int port) {
        String key = builtKey(hostName, port);
        if (!checkHas(hostName, port))
            return new BooleanResultKV<>(false,"the robot not exit :" + hostName + COLON + port);

        //Robot robot = robotSets.get(key);
        Robot robot = robotCache.getCache(key);

        Ros ros = ((RosRobotInvoker) robot).getRos();

        //robotSets.remove(key);

        LOGGER.info("success del a robot " + ros.getHostname() + COLON + ros.getPort());
        return new BooleanResultKV<>(true,"success del a robot " + ros.getHostname() + COLON + ros.getPort());

    }

    public RosRobotInvoker getRobot(String hostName, int port) {
        return getRobot(hostName + COLON + port);
    }

    public RosRobotInvoker getRobot(String rk) {
      //  return robotSets.get(rk);
     return robotCache.getCache(rk);
    }

    public List<RosRobotInvoker> getAllRobot() {
      //  return new ArrayList<>(robotSets.values());
      return robotCache.getAllValues();
    }

    public boolean checkIsConnected(String hostName, int port) {
        if (!checkHas(hostName, port)) return false;

        String key = builtKey(hostName, port);
       // RosRobotInvoker rosRobotInvoker = robotSets.get(key);
        RosRobotInvoker rosRobotInvoker = robotCache.getCache(key);

        if (rosRobotInvoker.getRos().isConnected()) {
            return true;
        } else {
           // robotSets.remove(key);
            return false;
        }

    }

    private String builtKey(String hostName, int port) {
        String key = hostName + COLON + port;
        return key;
    }

}
