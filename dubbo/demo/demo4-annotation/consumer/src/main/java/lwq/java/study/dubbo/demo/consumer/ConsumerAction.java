package lwq.java.study.dubbo.demo.consumer;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * @author lwq
 * @date 2019-09-11 下午 5:15
 */
@Component
public class ConsumerAction {

    @Reference
    //@Reference(id = "demoService")
    private DemoService demoService;

    public String doSayHello(String name){
        return demoService.sayHello(name);
    }
}
