### 适配器模式示例二
一、先模拟计算机读取SD卡（已存在接口）
二、接下来在不改变计算机读取SD卡接口的情况下，通过适配器模式读取TF卡(读取SD卡接口适配读取TF卡)

适配者,目标接口(targetinterface)：已存在接口（SDCard）
被适配者(adaptee)：另外一个接口（TFCard）
适配器(adapter)：实现适配者，持有被适配者对象，实现适配者方法中调用被适配者的方法（SDAdapaterTF）

你有动机修改一个已经投产中的接口时，适配器模式可能是适合你的模式。比 如系统扩展了，需要使用一个已有或新建立的类，但这个类又不符合系统的接 口，怎么办？使用适
配器模式，如本例。
