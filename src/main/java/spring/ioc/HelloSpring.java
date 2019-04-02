package spring.ioc;

public class HelloSpring {
    private String name;

    // 一定要提供get/set以便IOC注入
    public String getName() {
        return name;
    }

    // 不提供setter则报错说属性是not writable的
    public void setName(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("hello: " + name);
    }

    // IOC创建Bean时会调用构造器
    public HelloSpring() {
        System.out.println("构造HelloSpring");
    }

    @Override
    public String toString() {
        return "HelloSpring{" +
                "name='" + name + '\'' +
                '}';
    }
}
