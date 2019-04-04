package spring.aop.concurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountDao accountDao;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void transfer(String from, String to, int amount) {
        int resOut = accountDao.outMoney(from, amount);
        if (resOut != 1) {
            throw new RuntimeException("余额不足");
        }

//        int d = 1/0;     // 人为制造异常，事务会回滚，然后退出
        int resIn = accountDao.inMoney(to, amount);
        if (resIn != 1) {
            throw new RuntimeException("插入失败");
        }
    }
}
