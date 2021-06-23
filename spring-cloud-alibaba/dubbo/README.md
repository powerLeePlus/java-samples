spring cloud 集成 dubbo

#项目说明

## server+client
@DubboService + @DubboReference dubbo协议

## provider(非web)+consumer
1. @DubboService + dubbo协议 -> @DubboReference + 直接调用
2. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + rest协议 + 直接调用
3. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + @FeignClient调用
4. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + @FeignClient + @DubboTransported调用
5. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + RestTemplate + @DubboTransported调用

## provider-web+consumer
1. @DubboService + dubbo协议 -> @DubboReference + 直接调用
2. 直接调用springmvc接口（其实就是一套代码既支持了dubbo又支持了springmvc，见SpringRestService.class）

## servlet-gateway
### 1.说明
通过@WebServlet声明servlet容器。然后通过dubbo元数据获取dubbo消费者信息。
通过请求中的serviceName，rest path，参数及header通过dubbo完成调用消费端的rest服务的功能。
### 2.示例请求：
#### (1) GET http://localhost:8081/dsc/dubbo-provider/param?param=a1  其中：
- dubbo-provider ：服务提供者的serviceName
- /param?param=a1 ：要调用的服务提供者的某一个rest接口地址
- 结果：返回 服务提供者dubbo-provider 的 rest接口 /param?param=a1 的返回值：a1
#### (2) POST http://localhost:8081/dsc/dubbo-provider/params?a=2&b=boss
- dubbo-provider ：服务提供者的serviceName
- /params?a=2&b=boss ：要调用的服务提供者的某一个rest接口地址
- 结果：返回 服务提供者dubbo-provider 的 rest接口 /params?a=2&b=boss 的返回值：2boss
### 3.注
- 该示例没有支持@PathVariable