package designpattern.templatepattern;

// 模版类
public abstract class AbstractClass {
    protected abstract String primiriveOperation1();
    protected abstract int primiriveOperation2();

    public void templateMethod() {
        System.out.println("I am template method");
        System.out.println("Method1(): " + primiriveOperation1());
        System.out.println("Method2(): " + primiriveOperation2());
    }
}
