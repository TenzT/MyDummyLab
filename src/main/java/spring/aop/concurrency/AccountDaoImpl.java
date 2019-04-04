package spring.aop.concurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int outMoney(String from, int amount) {
        String sql = "UPDATE account set balance=balance-? where username=? and balance>=?";
        return jdbcTemplate.update(sql, amount, from, amount);
    }

    @Override
    public int inMoney(String to, int amount) {
        String sql = "UPDATE account set balance=balance+? where username=?";
        return jdbcTemplate.update(sql, amount, to);

    }
}
