# spring核心-IOC

## 一、spring循环依赖问题
- 描述：Bean A依赖B，B依赖C，C依赖A
### 1、构造器注入单例bean的循环依赖
- 代码: StudentA + StudentB + StudentC + applicationContext.xml中的<!--1、构造器注入-->部分
- 结果：
```
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
```
### 2、setter方法注入单例bean的循环依赖
- 代码：StudentA + StudentB + StudentC + applicationContext.xml中的<!--2、setter注入单例-->部分
- 结果：
```
com.lwq.spring.ioc.circular_reference.StudentA@2d928643
```
### 3、setter方法注入原型bean的循环依赖
- 代码：StudentA + StudentB + StudentC + applicationContext.xml中的<!--3、setter注入原型-->部分
- 结果：
```
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
```

## 二、结果分析
### 1、需要知道的
#### （1）spring创建bean时：先执行构造器，再执行属性注入，再执行初始化方法
#### （2）在执行构造器前会先将当前bean放入“正在创建的bean”集合中，
见源码 `DefaultSingletonBeanRegistry#singletonsCurrentlyInCreation`
#### （3）spring创建单例bean时会用到3级缓存
- singletonObjects 一级缓存，用于保存实例化、注入、初始化完成的bean实例
- earlySingletonObjects 二级缓存，用于保存实例化完成的bean实例
- singletonFactories 三级缓存，用于保存bean创建工厂，以便于后面扩展有机会创建代理对象。
#### （4）对于单例bean会放入缓存，原型(prototype)bean不会放入缓存，而是每次使用时生成新的bean
#### （5）创建单例bean时，查找缓存中是否存在已创建bean的顺序 singletonObjects -》earlySingletonObjects -》singletonFactories；缓存创建bean的顺序恰好相反。如下源码
```
protected Object getSingleton(String beanName, boolean allowEarlyReference) {
    Object singletonObject = this.singletonObjects.get(beanName);
    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
        synchronized (this.singletonObjects) {
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null && allowEarlyReference) {
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    this.singletonFactories.remove(beanName);
                }
            }
        }
    }
    return singletonObject;
}
```
#### （6）从缓存获取实例bean时，从一级缓存singletonObjects获取不到bean时，只有该bean正在创建中（“正在创建的bean”集合中），才会接着去二级、三级缓存找。
```
Object singletonObject = this.singletonObjects.get(beanName);
if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
```
有了这些结论，可以接着往下分析了：
### 2、构造器注入单例bean的循环依赖结果为啥报错
- 创建a，先从缓存找；一级缓存singletonObjects获取不到，同时a没有在“正在创建的bean”集合中；a加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用b，所以此时需要转而先去查找或创建b；
- 创建b，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；b加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用c，所以此时需要转而先去查找或创建c；
- 创建c，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；c加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用a，所以此时需要转而先去查找或创建a；
- 回到查找a（这里很关键了）：先从一级缓存找，找不到；但是此时a在“正在创建的bean”集合中，所以会从二级缓存找，显然找不到；再去3三级缓存找，依然找不到；所以返回null。那么就需要创建a了。
- 回到创建a（这里很关键了）：创建a时，发现a在“正在创建的bean”集合中，直接抛出异常`BeanCurrentlyInCreationException`，就是结果看到的错误了。

所以构造器注入单例bean的循环依赖，会直接出错，无法循环依赖
### 3、setter方法注入单例bean的循环依赖是可以成功的
- 创建a，先从缓存找；一级缓存singletonObjects获取不到，同时a没有在“正在创建的bean”集合中；a加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建b；
- 创建b，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；b加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建c；
- 创建c，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；c加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建a；
- 回到查找a（这里很关键了）：先从一级缓存找，找不到；但是此时a在“正在创建的bean”集合中，所以会从二级缓存找，显然找不到；再去3三级缓存找，找到了（虽然是没有注入属性的不完整实例），此时同时会将a放入二级缓存，删除三级缓存；c注入属性a成功；
- 回到c，c创建成功，c加到一级缓存；
- 回到b，b能找到c，注入c成功；b创建成功，b加入一级缓存；
- 回到a，a能找到b，注入b成功；a创建成功，a加入一级缓存；

所以setter方法注入单例bean的循环依赖是可以成功的，关键就在三级缓存的设计。

### 4、setter方法注入原型bean的循环依赖结果为啥报错
很简单原型(prototype)bean是不缓存在spring容器中的，每次使用时都是生成新的实例。

