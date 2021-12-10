# springboot-cache

## 一、最简单的-本地缓存

使用SimpleCacheManager

- 1、开启缓存 

@EnableCaching

- 2、使用缓存的方法加 

@Cacheable("mycache")

## 二、缓存的更新与删除
- 1、更新缓存

@CachePut("mycache")

- 2、删除缓存

@CacheEvict("mycache")

## 三、使用redis作为缓存
- 1、加依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
- 2、配好redis环境

spring会根据环境自动找合适的缓存方式，所以配了redis环境，就能自动使用redis缓存

- 3、进阶配置

配置redisTemplate；序列化与反序列化；缓存失效时间；缓存key生成规则等；详见
`com.lwq.spring.boot.cache.RedisConfig`

## 四、直接使用代码操作缓存
spring cache除了可以使用注解操作缓存，还可以直接使用代码调用操作缓存
详见：com.lwq.spring.boot.cache.web.HelloController#update2

## 五、使用spring EL 表达式配置缓存key
详见：com.lwq.spring.boot.cache.web.HelloController#getOne

## 六、理解
### 1. cache name 和 key
@Cacheable注解中，value相当于一个hash的唯一标识，key相当于该hash下的hashkey，方法结果存在该hash下的某个hashkey中，结构如下：
```
---hash1
------hash1-key1
------hash1-key2
------hash1-keyn
---hash2
------hash2-key1
------hash2-keyn
---hashn
```
所以，定义好key是关键。不同的返回值key不能相同。key的定义方式：1.key(结合SpEL);2.keyGenerator
### 2. cache 生效的条件，可以用`condition`和`unless`


