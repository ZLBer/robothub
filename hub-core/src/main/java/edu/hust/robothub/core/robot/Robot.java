package edu.hust.robothub.core.robot;

/**
 *机器人接口
 */  
public interface Robot {

    /**
     *    
     * 开启与机器人的连接
     * @author BNer  
     * @date 2020/9/25 15:47
     * @param []  
     * @return boolean  
     */
    boolean connect();
    
    /**
     * 断开与机器人的连接   
     *   
     * @author BNer  
     * @date 2020/9/25 15:49  
     * @param []  
     * @return boolean  
     */  
    boolean disconnect();
    
    /**
     *    
     *   
     * @author BNer  
     * @date 2020/9/25 15:49  
     * @param []  
     * @return boolean  
     */  
    String getRobotStatus();
}
