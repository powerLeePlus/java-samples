# 行为型模式之---中介者模式

用一个中介对象封装一系列的对象交互，中介者使各对象不需要 显示地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。

使用场景： 中介者模式适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中 出现了蜘蛛网状结构，即每个类都与其他的类有直接的联系。

一句话理解：多个对象之间有相互关联关系，把他们的关系维护通过一个中间对象统一管理，这样只需要和中间对象交互即可。