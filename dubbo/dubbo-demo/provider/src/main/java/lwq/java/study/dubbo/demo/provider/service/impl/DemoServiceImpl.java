package lwq.java.study.dubbo.demo.provider.service.impl;


import lwq.java.study.dubbo.demo.DemoService;

/**
 * 服务提供方接口实现
 *
 * @author lwq
 * @date 2019-09-06 上午 11:59
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
