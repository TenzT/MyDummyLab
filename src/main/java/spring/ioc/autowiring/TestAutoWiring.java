package spring.ioc.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAutoWiring {
    public static void main(String[] args) {
        // 1. 解析xml文件拿到IOC容器对象
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        // 2. 拿到bean
        // 利用id定位
        Controller controller = (Controller) context.getBean("controller");

        controller.say();
    }

}
