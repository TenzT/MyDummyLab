# IOC演变
- 分离接口与实现（依赖多态）
- 工厂模式（对象只需要知道接口类型和工厂，耦合度降低，但是还是要由对象自己申请）
- 采用反转控制（对象只需要知道接口和IOC容器即可，直接问IOC拿，并不需要具体的工厂类）

# Spring两种类型的IOC容器实现
- BeanFactory：IOC容器的基本实现
- ApplicationContext：一路继承自BeanFactory，对于开发者而言大部分场合只关注这个

# ApplicationContext
- 主要实现类：
    - ClassPathApplicationContext
    - FileSystemXmlApplicationContext
- Application在初始化上下文时就实例化所有单例的Bean(饿汉式，便于组装成其他bean)

# 3种依赖注入的方式
- 属性注入（最常用）：通过setter方法注入bean的属性或依赖的对象，在xml中配置是直接在<propertity>里设置
- 构造器注入：在<constructor-arg>元素里面生米功能属性，里面的属性没有name，是在构造时就注入,这种情况下不用提供get/set****
- 工厂方法注入（很少使用）

# 自动装配
- 是相对手动配置属性而言的，当一些bean的id名和bean的属性名相同时，可以通`"autowire="byname""`进行自动注入，有匹配则装配，没有就算了

# IOC容器管理Bean的生命周期
- 通过构造器或工厂方法创建Bean实例
- 为Bean的属性设置值和对其他Bean的引用，即Getter/Setter
- 调用Bean的初始化方法 在bean中要指定 init-method 和 destroy-method
- Bean可以使用了
- 当容器关闭时，调用bean的小慧方法