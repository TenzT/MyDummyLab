package main.java.designpattern.publishersubscriber;

public class Test {
    public static void main(String[] args) {
        AbstractPublisher publisher = new ConcretePublisher();
        AbstractSubscriber subscriber1 = new ConcreteSubcriber("X","X");
        AbstractSubscriber subscriber2 = new ConcreteSubcriber("Y","Y");

        publisher.registerSubscriber(subscriber1);
        publisher.registerSubscriber(subscriber2);

        ((ConcretePublisher) publisher).setState("XYZ");
    }
}
