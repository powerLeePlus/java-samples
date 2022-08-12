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
### 1、需要知道的（结合源码）
#### （1）spring创建bean时：先执行构造器（实例化|new），再执行属性注入，再执行初始化方法
```
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args) {
	......
	// 创建对象（执行构造器（new））
	instanceWrapper = createBeanInstance(beanName, mbd, args); 
	......
	// 属性注入（依赖注入）
	populateBean(beanName, mbd, instanceWrapper);
	......
	// 初始化方法（为Bean实例对象添加BeanPostProcessor后置处理器的入口就是这个initializeBean方法。）
	exposedObject = initializeBean(beanName, exposedObject, mbd);
	......
}
```
#### （2）在执行构造器时会将当前bean放入“正在创建的bean”集合中，
见源码 `DefaultSingletonBeanRegistry#singletonsCurrentlyInCreation`
```
public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
	......
	// 将当前bean放入“正在创建的bean”集合中
	beforeSingletonCreation(beanName);
	......
}
```
#### （3）spring创建单例bean时会用到3级缓存
- singletonObjects 一级缓存，用于保存实例化、注入、初始化完成的bean实例
- earlySingletonObjects 二级缓存，用于保存实例化完成的bean实例
- singletonFactories 三级缓存，用于保存bean创建工厂，以便于后面扩展有机会创建代理对象。

```
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

	/** Cache of singleton objects: bean name to bean instance. */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** Cache of singleton factories: bean name to ObjectFactory. */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

	/** Cache of early singleton objects: bean name to bean instance. */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

	......
}
```
#### （4）对于单例bean会放入缓存，原型(prototype)bean不会放入缓存，而是每次使用时生成新的bean
```
protected <T> T doGetBean(
	......
	// 原型bean的创建
	else if (mbd.isPrototype()) {
	......
}
```
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
protected Object getSingleton(String beanName, boolean allowEarlyReference) {
	......
	Object singletonObject = this.singletonObjects.get(beanName);
	if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
	......
}
```
#### （7）执行完构造器，会根据条件，将“半成品”bean放入三级缓存singletonFactories 
```
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args) {
	......
	// 创建对象（执行构造器（new））
	instanceWrapper = createBeanInstance(beanName, mbd, args); 
	......
	// 如果是单例bean，并且允许循环依赖，并且当前bean正在“正在创建的bean”集合中，则将该bean放入三级缓存
	// Eagerly cache singletons to be able to resolve circular references
		// even when triggered by lifecycle interfaces like BeanFactoryAware.
		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
				isSingletonCurrentlyInCreation(beanName));
		if (earlySingletonExposure) {
			if (logger.isTraceEnabled()) {
				logger.trace("Eagerly caching bean '" + beanName +
						"' to allow for resolving potential circular references");
			}
			addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
		}
	......
	// 属性注入（依赖注入）
	populateBean(beanName, mbd, instanceWrapper);
	......
	// 初始化方法
	exposedObject = initializeBean(beanName, exposedObject, mbd);
	......
}
```
#### （8）无论通过构造器注入还是setter方法注入，遇到依赖的其他bean，都会先查找和创建其他bean。
- 构造器注入
```
//创建Bean的实例对象
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
	......
	// 有参构造方法进行属性注入
	autowireConstructor(beanName, mbd, ctors, args);
	......
}

protected BeanWrapper autowireConstructor(
		String beanName, RootBeanDefinition mbd, @Nullable Constructor<?>[] ctors, @Nullable Object[] explicitArgs) {
	return new ConstructorResolver(this).autowireConstructor(beanName, mbd, ctors, explicitArgs);
}

public BeanWrapper autowireConstructor(String beanName, RootBeanDefinition mbd,
			@Nullable Constructor<?>[] chosenCtors, @Nullable Object[] explicitArgs) {
	......
	// 构造参数解析注入
	resolvePreparedArguments(beanName, mbd, bw, constructorToUse, argsToResolve, true);
	......
}

private Object[] resolvePreparedArguments(String beanName, RootBeanDefinition mbd, BeanWrapper bw,
			Executable executable, Object[] argsToResolve, boolean fallback) {
	......
	// 解析属性值，对注入类型进行转换
	argValue = valueResolver.resolveValueIfNecessary("constructor argument", argValue);
	......
}
```
- setter方法注入
```
// 属性注入
protected void populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw) {
	......
	// /解析并注入依赖属性的过程
	applyPropertyValues(beanName, mbd, bw, pvs);
	......
}

protected void applyPropertyValues(String beanName, BeanDefinition mbd, BeanWrapper bw, PropertyValues pvs) {
	......
	// 解析属性值，对注入类型进行转换
	Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);
	......
}
```
- 共同走到的代码
```
public Object resolveValueIfNecessary(Object argName, @Nullable Object value) {
	......
	// 对引用类型的属性进行解析
	if (value instanceof RuntimeBeanReference) {
		RuntimeBeanReference ref = (RuntimeBeanReference) value;
		return resolveReference(argName, ref);
	}
	// 对属性值是引用容器中另一个Bean名称的解析
	else if (value instanceof RuntimeBeanNameReference) {
		......
	}
	//对Bean类型属性的解析，主要是Bean中的内部类
	else if (value instanceof BeanDefinitionHolder) {
	......
}

private Object resolveReference(Object argName, RuntimeBeanReference ref) {
	......
	//从当前的容器中获取指定的引用Bean对象，如果指定的Bean没有被实例化，则会递归触发引用Bean的实例化和依赖注入
	bean = this.beanFactory.getBean(refName);
	this.beanFactory.registerDependentBean(refName, this.beanName);
	......
}
```
有了这些结论，可以接着往下分析了：
### 2、构造器注入单例bean的循环依赖结果为啥报错
- 创建a，先从缓存找；一级缓存singletonObjects获取不到，同时a没有在“正在创建的bean”集合中；a加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用b，所以此时需要转而先去查找或创建b；
- 创建b，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；b加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用c，所以此时需要转而先去查找或创建c；
- 创建c，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；c加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器参数应用a，所以此时需要转而先去查找或创建a；
- 回到查找a（这里很关键了）：先从一级缓存找，找不到；但是此时a在“正在创建的bean”集合中，所以会从二级缓存找，显然找不到；再去3三级缓存找，依然找不到；所以返回null。那么就需要创建a了。
- 回到创建a（这里很关键了）：创建a时，发现a在“正在创建的bean”集合中，直接抛出异常`BeanCurrentlyInCreationException`，就是结果看到的错误了。

**所以构造器注入单例bean的循环依赖，会直接出错，无法循环依赖**

### 3、setter方法注入单例bean的循环依赖是可以成功的
- 创建a，先从缓存找；一级缓存singletonObjects获取不到，同时a没有在“正在创建的bean”集合中；a加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建b；
- 创建b，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；b加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建c；
- 创建c，先从缓存找；一级缓存singletonObjects获取不到，同时b没有在“正在创建的bean”集合中；c加入“正在创建的bean”集合中，并尝试执行构造器；因为构造器执行的是空构造器，没有参数，所以可以执行成功；构造器执行成功会放入三级缓存；接着执行属性注入，所以此时需要转而先去查找或创建a；
- 回到查找a（这里很关键了）：先从一级缓存找，找不到；但是此时a在“正在创建的bean”集合中，所以会从二级缓存找，显然找不到；再去3三级缓存找，找到了（虽然是没有注入属性的不完整实例），此时同时会将a放入二级缓存，删除三级缓存；c注入属性a成功；
- 回到c，c创建成功，c加到一级缓存；
- 回到b，b能找到c，注入c成功；b创建成功，b加入一级缓存；
- 回到a，a能找到b，注入b成功；a创建成功，a加入一级缓存；

**所以setter方法注入单例bean的循环依赖是可以成功的，关键就在三级缓存的设计。**

**至于二级缓存的设计目的** 是为了解决共同依赖时从三级缓存获取同一bean出现的不是同一实例的问题，如：
a同时依赖b和c，b依赖a，c依赖a，那么a注入b，c时，因为b,c都依赖a，就需要从三级缓存获取a，假如恰巧b,c同时去三级缓存获取a（a还没正式创建完成），他们得到的将是两个实例（因为三级缓存获取bean其实就是工厂实时生成一个bean），这与a单例的意图是有冲突的。而有了二级缓存，就可以在不管是b或者c在第一次从三级缓存获取a时，顺便将a移动到二级缓存，后面c或者b再获取a时，实际是从二级缓存拿到的同一实例。

### 4、setter方法注入原型bean的循环依赖结果为啥报错
很简单原型(prototype)bean是不缓存在spring容器中的，每次使用时都是生成新的实例。

## 备注
### Bean创建过程中的“**实例化**”与“**初始化**”概念
- 实例化(Instantiation): 生成对象，如new对象（内存中分配地址给该对象）
- 初始化(Initialization): 对象已经生成.，赋值操作（spring中还区别于调用setter方法，可以理解为调用setter后做的其他赋值操作）

**先实例化，再初始化。**

