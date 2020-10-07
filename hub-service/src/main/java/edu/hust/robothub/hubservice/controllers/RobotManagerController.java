package edu.hust.robothub.hubservice.controllers;

import edu.hust.robothub.core.api.NullServiceResponseMessage;
import edu.hust.robothub.core.api.ObjListServiceResponseMessage;
import edu.hust.robothub.core.api.ObjServiceResponseMessage;
import edu.hust.robothub.core.api.StandardServiceResponseMessage;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.result.ResultKV;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import edu.hust.robothub.hubservice.services.RobotManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 机器人管理接口
 */
@RestController
@RequestMapping(value = "/RobotManager")
public class RobotManagerController {

    @Autowired
    RobotManagerService robotManagerService;

    @GetMapping("/add/{hostname}/{port}")
    public NullServiceResponseMessage add(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.add(hostname, port);

        return NullServiceResponseMessage.getReturnValue(resultKV);
    }

    @GetMapping("/connect/{hostname}/{port}")
    public NullServiceResponseMessage connect(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.connect(hostname, port);

        return NullServiceResponseMessage.getReturnValue(resultKV);
    }

    @GetMapping("/del/{hostname}/{port}")
    public NullServiceResponseMessage del(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.remove(hostname, port);

        return NullServiceResponseMessage.getReturnValue(resultKV);
    }

    @GetMapping("/get/{hostname}/{port}")
    public ObjServiceResponseMessage<RosRobotInvoker> get(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<RosRobotInvoker> resultKV = robotManagerService.get(hostname, port);

        if (resultKV.getKey() == false) {
            return new ObjServiceResponseMessage<>(StatusCode.FAIL, "no this ros robot", null, null);
        }
        return new ObjServiceResponseMessage<>(StatusCode.SUCCESS, "get a ros robot", null, resultKV.getValue());
    }

    @GetMapping("/getAll/")
    public ObjListServiceResponseMessage<RosRobotInvoker> getAll() {

        ResultKV<List<RosRobotInvoker>> resultKV = robotManagerService.getAll();

        if (resultKV.getKey() == false) {
            return new ObjListServiceResponseMessage<>(StatusCode.FAIL, "", null, null);
        }
        return new ObjListServiceResponseMessage<>(StatusCode.SUCCESS, "get all ros robot", null, resultKV.getValue());
    }

    @GetMapping("/check/{hostname}/{port}")
    public StandardServiceResponseMessage checkIsConnected(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.checkIsConnected(hostname, port);
        Map<String,String> data=new HashMap<>();
        int stats=0;
        if(resultKV.getKey()){
            stats=StatusCode.SUCCESS;
         data.put("isConncected","true");
        }else {
            stats=StatusCode.FAIL;
         data.put("isConncected","false");
        }
        return new StandardServiceResponseMessage(stats,resultKV.getValue(),null,data);
    }

}
