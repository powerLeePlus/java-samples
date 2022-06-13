package lwq.java.study.dubbo.demo.provider;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * 服务接口提供
 *
 * @author lwq
 * @date 2019-09-10 下午 5:33
 */
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHello(String name) {
        return "hello "+ name;
    }
}
