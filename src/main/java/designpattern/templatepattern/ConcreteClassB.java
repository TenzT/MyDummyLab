package designpattern.templatepattern;

public class ConcreteClassB extends AbstractClass {

    @Override
    protected String primiriveOperation1() {
        return "ClassB";
    }

    @Override
    protected int primiriveOperation2() {
        return 666;
    }
}
