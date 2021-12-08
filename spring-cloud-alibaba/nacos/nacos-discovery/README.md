演示nacos完成spring cloud应用的服务注册与发现

## 采坑
1.Caused by: java.lang.ClassNotFoundException: org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata
spring-boot和spring cloud版本冲突

2.feginClient服务接口定义与服务提供者定义必须完全一致。但传递对象参数必须用@RequestBody注解标注吗？（还是说对象 implements Serializable就行？）