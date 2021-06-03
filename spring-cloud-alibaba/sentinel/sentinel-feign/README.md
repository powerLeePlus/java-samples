sentinel 对feign的支持

## issue
高版本sentinel-datasource-nacos启动时会报
```
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'ds2-sentinel-nacos-datasource': FactoryBean threw exception on object creation; nested exception is java.lang.NullPointerExceptio
```
解决：需要配置nacos的username和password