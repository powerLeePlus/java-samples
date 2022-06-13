package lwq.java.study.dubbo.demo.consumer;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * @author lwq
 * @date 2019-09-11 下午 5:21
 */
public class Consumer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);

        ConsumerAction consumerAction = (ConsumerAction) context.getBean("consumerAction");
        String hello2 = consumerAction.doSayHello("consumerAction");
        System.out.println(hello2);

        DemoService demoService = (DemoService) context.getBean(DemoService.class);
        //DemoService demoService = (DemoService) context.getBean("demoService");
        String hello1 = demoService.sayHello("demoService");
        System.out.println(hello1);

        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context, DemoService.class);
        for (String beanName : beanNames) {
            System.out.println("demoService beanName:");
            System.out.println(beanName);
        }

    }
}
