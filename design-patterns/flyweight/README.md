# 结构型模式之---享元模式

使用共享对象的方法，用来尽可能减少内存使用量以及分享资讯。

主要用于减少创建对象的数量，以减少内存占用和提高性能。这种类型的设计模式属于结构型模式，它提供了减少对象数量从而改善应用所需的对象结构的方式。

享元模式尝试重用现有的同类对象，如果未找到匹配的对象，则创建新对象。

包含角色：
1. 抽象享元角色（IFlyweight）：享元对象抽象基类或者接口，同时定义出对象的外部状态和内部状态的接口或者实现；
2. 具体享元角色（ConcreteFlyweight）：实现抽象角色定义的业务。该角色的内部状态处理应该与环境无关，不能出现会有一个操作改变内部状态 ，同时修改了外部状态；
3. 享元工厂（FlyweightFactory）：负责管理享元对象池和创建享元对象。

使用场景：
1. 系统中存在大量的相似对象。
2. 细粒度的对象都具备较接近的外部状态，而且内部状态与环境无关，也就是说对象没有特定身份。
3. 需要缓冲池的场景。

应用实例： 
1. JAVA 中的 String，如果有则返回，如果没有则创建一个字符串保存在字符串缓存池里面。 
2. 数据库的数据池。

一句话理解：通过对象复用避免频繁创建对象
