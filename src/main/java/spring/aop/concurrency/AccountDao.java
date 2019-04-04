package spring.aop.concurrency;

public interface AccountDao {
    // 扣钱
    public int outMoney(String from, int amount);


    // 加钱
    public int inMoney(String to, int amount);
}
