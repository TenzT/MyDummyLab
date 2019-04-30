package spring.ioc.autowiring;

public class BServiceImpl implements IService{
    @Override
    public void say() {
        System.out.println("Implementation B");
    }
}
