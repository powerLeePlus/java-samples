package lwq.java.study.dubbo.demo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lwq
 * @date 2019-09-06 下午 1:50
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
