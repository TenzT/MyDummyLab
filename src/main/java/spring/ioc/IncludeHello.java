package spring.ioc;

public class IncludeHello {
    private HelloSpring helloSpring;
    private String info;

    public IncludeHello(HelloSpring springHello, String info) {
        this.helloSpring = springHello;
        this.info = info;
    }

    @Override
    public String toString() {
        return "IncludeHello{" +
                "springHello=" + helloSpring.toString() +
                ", info='" + info + '\'' +
                '}';
    }

    public IncludeHello() {
    }

    public HelloSpring getHelloSpring() {
        return helloSpring;
    }

    public void setHelloSpring(HelloSpring helloSpring) {
        this.helloSpring = helloSpring;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
