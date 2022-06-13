package lwq.java.study.dubbo.demo.consumer;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * @author lwq
 * @date 2019-09-06 下午 2:31
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        // 获取远程服务代理
        DemoService demoService = (DemoService) context.getBean("demoService");
        // 执行远程方法
        String hello = demoService.sayHello("world");
        // 显示调用结果
        System.out.println(hello);
        System.in.read();
    }
}
