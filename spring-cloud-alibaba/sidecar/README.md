## spring-cloud-alibaba sidecar 
### sidecar作用
自 Spring Cloud Alibaba 2.1.1 版本后增加了 spring-cloud-alibaba-sidecar 模块作为一个代理的服务来间接性的让其他语言可以使用spring cloud alibaba等相关组件。通过与网关的来进行路由的映射，从而可以做到服务的获取，然后可以使用Ribbon间接性调用。

![](.README_images/10aac241.png)

如上图, Spring Cloud 应用 请求 sidercar 然后转发给其他语言的模块，优势是对于异构服务代码 零侵入，不需要直接根据 nacos 或其他注册中心 api 注册等

### 架构说明
- sidecar服务：作为异构服务的代理，可以将异构服务注册到nacos；其他服务可以通过该服务通过微服务访问的方式（如feign、restTemplate等）访问到异构服务；
- nodejs异构服务：非java语言实现的服务，或者非spring实现的java服务。需要集成到springcloud微服务架构下。完成统一的服务注册与发现，负载，网关等。
- 服务调用者（本项目没写）：要调用异构服务的服务，同时又想使用微服务调用的方式调用；所以通过调用sidecar间接完成对异构服务的调用