package edu.hust.robothub.hubservice.controllers;

import edu.hust.robothub.core.api.NullApiValue;
import edu.hust.robothub.core.api.ObjApiValue;
import edu.hust.robothub.core.api.StatusCode;
import edu.hust.robothub.core.result.ResultKV;
import edu.hust.robothub.core.robot.RosRobotInvoker;
import edu.hust.robothub.hubservice.services.RobotManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 机器人管理接口
 */
@RestController
@RequestMapping(value = "/RobotManager")
public class RobotManagerController {

    @Autowired
    RobotManagerService robotManagerService;

    @GetMapping("/add/{hostname}/{port}")
    public NullApiValue add(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.add(hostname, port);

        return NullApiValue.getReturnValue(resultKV);
    }

    @GetMapping("/del/{hostname}/{port}")
    public NullApiValue del(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.remove(hostname, port);

        return NullApiValue.getReturnValue(resultKV);
    }

    @GetMapping("/get/{hostname}/{port}")
    public ObjApiValue<RosRobotInvoker> get(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<RosRobotInvoker> resultKV = robotManagerService.get(hostname, port);

        if (resultKV.getKey() == false) {
            return new ObjApiValue<>(StatusCode.FAIL, "no this ros robot", null, null);
        }
        return new ObjApiValue<>(StatusCode.SUCCESS, "get a ros robot", null, resultKV.getValue());
    }


    @GetMapping("/checkIsConnected/{hostname}/{port}")
    public NullApiValue checkIsConnected(@PathVariable("hostname") String hostname, @PathVariable("port") int port) {

        ResultKV<String> resultKV = robotManagerService.checkIsConnected(hostname, port);

        return NullApiValue.getReturnValue(resultKV);
    }

}
