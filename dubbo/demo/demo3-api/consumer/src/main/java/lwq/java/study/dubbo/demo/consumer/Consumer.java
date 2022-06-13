package lwq.java.study.dubbo.demo.consumer;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * @author lwq
 * @date 2019-09-11 下午 4:54
 */
public class Consumer {
    public static void main(String[] args) {
        DemoService demoService = ConsumerAPI.referenceDemoService();
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }
}
