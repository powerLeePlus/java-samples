## 自定义springboot starter
### 一、自定义starter实现的内容
1. A应用提供加法计算的服务；
2. B应用提供减法计算的服务；
3. C应用要使用加法计算和减法计算的服务，并且减法服务可以通过配置来实现是否支持负数；
### 二、项目模块设计

| 模块名称 | 作用 | 备注 |
| :---- | :---- | :---- |
| customizer-api | 包含了接口和异常的定义 | 实现和调用服务时用到的接口和异常都在此工程中 |
| add-service | 提供加法服务 | 普通的maven工程，里面加法接口的实现类 |
| minus-service | 提供减法服务 | 普通的maven工程，里面有两个减法接口的实现类，一个支持负数，另一个不支持 |
| customizer-service-starter | 自定义starter模块 | pom.xml中依赖了customizeapi、addservice、minusservice，自身有个Configuration类，通过@Bean注解向spring容器注册AddService和MinusService的实例 |
| customizer-starter-test-demo | 最简单springboot web项目，测试模块 | pom.xml中只需要依赖customizer-service-starter，就可以将add-service和minus-service服务引用进来 |

### 三、步骤
1. 创建工程sprinboot-starter-customizer
2. 创建模块customizer-api；
3. 创建模块add-service；
4. 创建模块minus-service；
5. 创建模块customizer-service-starter；
6. 构建工程sprinboot-starter-customizer，并安装到本地maven仓库
(`mvn clean install -Dmaven.test.skip=true -U`)
7. 创建工程customizer-starter-test-demo；
8. 构建工程customizer-starter-test-demo，得到jar包(`mvn clean package -Dmaven.test.skip-true`)
9. 启动customizer-starter-test-demo工程的jar包，并带上一个启动参数，验证支持负数的减法服务(`java -DminusService.supportnegative=true -jar customizer-starter-test-demo-1.0-SNAPSHOT.jar`)
10. 启动customizer-starter-test-demo工程的jar包，验证不支持服务的减法服务(`java -jar customizer-starter-test-demo-1.0-SNAPSHOT.jar`)

### 四、关键
模块customizer-service-starter下，
- 通过@Configuration+@Bean实例化addService和minusService服务(CustomizerConfiguration)
- 在src/main/resources下新建META-INF目录，其下新建spring.factories,并将如下自动配置内容写入：
```org.springframework.boot.autoconfigure.EnableAutoConfiguration=demo.lwq.spring.boot.starter.customizer.CustomizerConfiguration```

### 五、思考
思考如下问题：
1. spring容器如何处理配置类；
2. spring boot配置类的加载情况；
3. spring.factories中的EnableAutoConfiguration配置何时被加载？
4. spring.factories中的EnableAutoConfiguration配置被加载后做了什么处理；



