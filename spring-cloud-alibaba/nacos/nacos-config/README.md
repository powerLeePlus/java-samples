# 一、nacos作为配置中心

向Nacos Server添加一条配置
```shell script
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos-config-example.properties&group=DEFAULT_GROUP&content=user.id=1%0Auser.name=james%0Auser.age=18"
```

##添加配置：
1、通过API，如上
2、在nacos控制台添加

##动态刷新配置：
1. 先启用：
    - ConfigService增加监听器，如代码中SampleRunner(有默认不需要手动配置，这里配置是为了演示)
    - 配置：`spring.cloud.nacos.config.shared-configs[0].refresh=true`

2. 再应用：
加注解@RefreshScope

##使用配置
1. @Value
2. Environment
3. @ConfigurationProperties

# 二、说明
## 1. bootstrap.properties先于application.properties加载
- bootstrap.properties先于application.properties加载
- Nacos的服务配置内容必须写在bootstrap.properties/yml中
## 2. 配置文件加载顺序
- Nacos默认会去读取以服务名称开头的配置，这里的配置的唯一id就是dataId
- 在 Nacos Spring Cloud 中，dataId 的完整格式：`${prefix}-${spring.profiles.active}.${file-extension}`
    - prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
    - spring.profiles.active 即为当前环境对应的 profile。注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
    - file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。
- 除了服务名称开头的配置文件，Nacos还支持通过spring.cloud.nacos.config.shared-configs[n].data-id和spring.cloud.nacos.config.extension-configs[n].data-id的方式添加额外的配置，其加载的顺序如下
    - Spring Cloud Alibaba Nacos Config 目前提供了三种配置能力从 Nacos 拉取相关的配置。（${prefix}-${spring.profiles.active}.${file-extension}）
    - A: 通过 spring.cloud.nacos.config.shared-configs[n].data-id 支持多个共享 Data Id 的配置（shared-configs）
    - B: 通过 spring.cloud.nacos.config.extension-configs[n].data-id 的方式支持多个扩展 Data Id 的配置（extension-configs）
    - C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置
    - 当三种方式共同使用时，他们的一个优先级关系是:A < B < C
    - extension-configs[n+1]和shared-configs[n+1]的优先级又大于extension-configs[n]和shared-configs[n]
## 3.参考
- 可以参看：https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config 
- 关于spring-boot的配置加载顺序可以参照Externalized Configuration，关于为什么写在[bootstrap.properties/yml](https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/boot-features-external-config.html) 中可以参照[application-context](https://cloud.spring.io/spring-cloud-static/Greenwich.SR1/single/spring-cloud.html#_the_bootstrap_application_context)
 
  