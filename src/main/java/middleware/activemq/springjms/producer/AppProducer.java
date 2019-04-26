package middleware.activemq.springjms.producer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducer {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        ProducerService service = context.getBean(ProducerService.class);
        for (int i=0; i<100; i++) {
            service.sendMessage("test " + i);
        }
    }
}
