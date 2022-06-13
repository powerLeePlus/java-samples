package lwq.java.study.dubbo.demo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import lwq.java.study.dubbo.demo.DemoService;

/** 开启服务
 * @author lwq
 * @date 2019-09-10 下午 5:32
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("provider");
        System.out.println(hello);
        System.in.read(); // 按任意键退出，读完一帧数据不再阻塞则退出该方法，所以服务也关闭了。
    }
}
