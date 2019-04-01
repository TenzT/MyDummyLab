package main.java.designpattern.publishersubscriber;

public class ConcreteSubcriber extends AbstractSubscriber {
    private String name;
    private String state;

    public ConcreteSubcriber(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public void update(String newState) {
        this.state = newState;
        System.out.printf("%s 的新状态是 %s \n", name, state);
    }

}
