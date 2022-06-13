package lwq.java.study.dubbo.demo.provider;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lwq
 * @date 2019-09-11 下午 5:29
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.out.println("按任意键退出");
        System.in.read();
    }
}
