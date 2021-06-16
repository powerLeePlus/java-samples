spring cloud 集成 dubbo

#项目说明

## server+client
@DubboService + @DubboReference dubbo协议

## provider+consumer(非web)
1. @DubboService + dubbo协议 -> @DubboReference + 直接调用
2. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + rest协议 + 直接调用
3. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + @FeignClient调用
4. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + @FeignClient + @DubboTransported调用
5. @DubboService + dubbo,rest协议 + jsr定义rest接口 -> @DubboReference + RestTemplate + @DubboTransported调用