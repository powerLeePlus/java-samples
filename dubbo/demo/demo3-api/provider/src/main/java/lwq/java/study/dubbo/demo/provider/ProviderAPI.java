package lwq.java.study.dubbo.demo.provider;

import java.io.IOException;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import lwq.java.study.dubbo.demo.DemoService;

/**
 * API方式配置dubbo
 *
 * @author lwq
 * @date 2019-09-11 下午 4:37
 */
public class ProviderAPI {
    private static ServiceConfig<DemoService> service;
    public static void export() throws IOException {

        // 服务实现
        DemoService demoService = new DemoServiceImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("helloworld-app");  //等同于xml中<dubbo:application name="helloworld-app"/>

        // 连接注册中西配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");  //等同于xml中<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
        //registry.setUsername("aaa");
        //registry.setPassword("bbb");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig(); //等同于xml中<dubbo:protocol name="dubbo" port="20880"/>
        protocol.setName("dubbo");
        protocol.setPort(20880);
        //protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置 // 等同于xml中<dubbo:service interface="lwq.java.study.dubbo.demo.DemoService" ref="demoService"/>
        ServiceConfig<DemoService> service = new ServiceConfig<>();// 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(DemoService.class);
        service.setRef(demoService);
        //service.setVersion("1.0.0");

        //暴露及注册服务
        service.export();


    }
}
