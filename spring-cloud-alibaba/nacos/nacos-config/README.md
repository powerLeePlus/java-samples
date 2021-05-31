nacos作为配置中心

向Nacos Server添加一条配置
```shell script
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos-config-example.properties&group=DEFAULT_GROUP&content=user.id=1%0Auser.name=james%0Auser.age=18"
```

##添加配置：
1、通过API，如上
2、在nacos控制台添加

##动态刷新配置：
1. 先启用：
    - ConfigService增加监听器，如代码中SampleRunner
    - 配置：`spring.cloud.nacos.config.shared-configs[0].refresh=true`

2. 再应用：
加注解@RefreshScope

##使用配置
1. @Value
2. Environment
3. @ConfigurationProperties