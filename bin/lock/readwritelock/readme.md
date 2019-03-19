# 说明
当前包用来做一些ReadWriteLock的使用，读写锁要解决的synchronized的一个性能问题：读与读之间互斥

# ReadWriteLock
本质是一个接口，原型为
```
public interface ReadWriteLock {
    Lock readLock();
    Lock writeLock();
}
```
# 在 ReadWithSynchronized 中，可以看到单纯使用synchronized，只有一个线程能进入临界区，然而读操作本身不会改变资源的状态，阻塞同步反而增加了开销，更重要的是不同线程之间的阻塞使得响应变慢

# 通过ReadAndWriteLockTest的实验可以看出，读写锁用ReentrantReadWriteLock实现，用法和ReentrantLock相似，读线程之间不会相互阻塞

# 从ReadAndWriteLockTest2的实验可以看出，写锁和其他所有锁之间是互斥的，或者说只有都是读的线程不会互斥，其他都会
