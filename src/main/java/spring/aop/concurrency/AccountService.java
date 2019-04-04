package spring.aop.concurrency;

public interface AccountService {
    // 转账
    public void transfer(String from, String to, int amount);
}
