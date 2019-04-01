package main.java.designpattern.publishersubscriber;

import java.util.LinkedList;

/**
 * 抽象发布者，即被观察对象
 */
public abstract class AbstractPublisher {
    // 建造队列来存储订阅者
    private LinkedList<AbstractSubscriber> subscribers = new LinkedList<AbstractSubscriber>();

    // 注册观察者
    public void registerSubscriber(AbstractSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    // 已出观察者
    public void removeSubscribe(AbstractSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    // 状态改变时，会发布信息
    public void notifySubscribers(String newState) {
        for (AbstractSubscriber subscriber : subscribers) {
            subscriber.update(newState);
        }
    }
}
