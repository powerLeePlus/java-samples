package lwq.java.web.ssm_swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lwq.java.web.ssm_swagger.entity.User;

/**
 * @author lwq
 * @create 2019-06-21 上午 11:49
 */
@RestController
@RequestMapping("/index")
@Api(value = "/index", tags = "接口测试")
public class IndexController {
    //@GetMapping(value = "index1")
    //@GetMapping(value = "index1", produces = "application/json; charset=utf-8")
    @GetMapping(value = "string", produces = "text/plain; charset=utf-8")
    @ApiOperation(value = "String", notes = "返回String")
    public String string(){
        System.out.println("进入index1");
        //return "欢迎来到SSM世界";
        return "你好，SSM集成Swagger2";
    }
    @GetMapping(value = "json")
    @ApiOperation(value = "Json", notes = "返回Json")
    public User json(){
        System.out.println("进入Index2");
        //return "欢迎来到SSM世界";
        User user = new User();
        user.setUsername("打不死的小强");
        return user;
    }
}
