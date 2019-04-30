package spring.ioc.autowiring;

public class AServiceImpl implements IService{
    @Override
    public void say() {
        System.out.println("Implementation A");
    }
}
