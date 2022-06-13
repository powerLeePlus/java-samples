package lwq.java.study.dubbo.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * 服务消费者
 *
 * @author lwq
 * @date 2019-09-11 下午 3:05
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        //context.start(); //启动Spring容器,如果调用了getBean方法,容器默认就会随之启动,可以不显式的调用start方法
        DemoService demoService = (DemoService)context.getBean("demoService");
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }
}
