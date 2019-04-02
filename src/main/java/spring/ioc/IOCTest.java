package spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCTest {

    public static void main(String[] args) {
        // 1. 解析xml文件拿到IOC容器对象
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        // 2. 拿到bean
        // 利用id定位
        HelloSpring helloSpring = (HelloSpring)context.getBean("helloSpring");
        // 使用类型加载bean，可以不用自己做转型，但要求容器中只有一个实例
        HelloSpring helloSpring1 = context.getBean(HelloSpring.class);

        // 3. 调用方法
        helloSpring.hello();
        helloSpring1.hello();   // 可以看到构造方法只被调用一次，只是单例的


        // 4. 尝试构造器注入方式产生的Bean
        Car car = (Car)context.getBean("car");
        System.out.println(car);
        Car car2 = (Car)context.getBean("car2");
        System.out.println(car2);

        // 5. 尝试自动装配的bean
        IncludeHello includeHello = (IncludeHello) context.getBean("includeHello");
        System.out.println(includeHello);
    }
}
