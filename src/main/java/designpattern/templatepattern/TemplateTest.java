package designpattern.templatepattern;

public class TemplateTest {
    public static void main(String[] args) {
        AbstractClass template1 = new ConcreteClassA();
        AbstractClass template2 = new ConcreteClassB();

        template1.templateMethod();

        System.out.println();

        template2.templateMethod();
    }
}
