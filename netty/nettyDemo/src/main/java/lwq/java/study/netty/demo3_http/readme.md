## 使用Netty构建http服务
### 实现功能
- 使用netty构建一个类似于tomcat的web服务器，服务端监听8899端口，当访问8899端口的时候，服务器端给客户端hello world的响应。

### 访问
> curl
- curl localhost:8899
- curl -X post localhost:8899
- curl -X put localhost:8899
- curl -X delete localhost:8899

> rest，例如postman

> 浏览器访问```localhost:8899```