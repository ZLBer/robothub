package edu.hust.robothub.core.handler;

import edu.hust.robothub.core.message.AbstractMessage;

/**
 *    
 * 消息处理链，假定消息传递的方向是单一的，即从robot->service
 * @author BNer  
 * @date 2020/9/25 20:39
 * @param   
 * @return   
 */  
public interface Handler {

    void handle(AbstractMessage abstractMessage);

}
