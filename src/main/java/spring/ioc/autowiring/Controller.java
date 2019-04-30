package spring.ioc.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Controller {
    @Autowired
    @Qualifier(value = "AserviceImpl")
    IService service;

    public void say() {
        service.say();
    }
}
