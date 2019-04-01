package main.java.designpattern.publishersubscriber;

public class ConcretePublisher extends AbstractPublisher{
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifySubscribers(this.state);
    }
}
