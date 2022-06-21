# SPI
SPI ，全称为 Service Provider Interface，是一种服务发现机制。它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类。

这一机制为很多框架扩展提供了可能，比如在Dubbo、JDBC中都使用到了SPI机制。

我们通过一个很简单的例子来看下它是怎么用的。
1、定义SPI服务接口
2、服务接口实现
3、服务实现所在classpath路径下的`META-INF/services`下，创建名字为服务实现类的全限定类名，内容是实现类的全限定类名，多个实现换行分割
4、测试服务实现调用，可以通过`ServiceLoader.load`或者`Service.providers`方法拿到实现类的实例。
其中，Service.providers包位于sun.misc.Service，而ServiceLoader.load包位于java.util.ServiceLoader


注意：要结合类加载机制理解，静态代码块什么时候执行：参考（https://www.cnblogs.com/jswang/p/7699643.html）

参考：https://www.jianshu.com/p/3a3edbcd8f24
