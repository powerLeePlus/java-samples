package lwq.java.web.ssm_swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lwq.java.web.ssm_swagger.config.Result;
import lwq.java.web.ssm_swagger.entity.User;
import lwq.java.web.ssm_swagger.service.UserService;

/**
 *
 * @author lwq
 * @create 2019-06-28 下午 3:01
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "用户信息")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "list")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public List<User> list(){
        return userService.list();
    }

    @PostMapping()
    @ApiOperation(value = "创建用户", notes = "创建用户")
    public Result save(@ApiParam(name = "用户对象") User user){
        userService.save(user);
        return new Result(1, "创建成功");
    }

}
