package spring.aop.concurrency;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionRollbackTest {

	private static ApplicationContext ctx = null;
	private static AccountService accountService = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        accountService = ctx.getBean(AccountService.class);
	}



	@Test
	public void testTransfer(){
	    try {
	        accountService.transfer("Tom", "Amy",1000);
        } catch (ArithmeticException e1) {
            System.out.println("中间插入的计算异常");
        } catch (RuntimeException e2) {
            System.out.println("余额不足或插入失败，事务回滚，中止");
        }
	}

    // 这里所做的实验是保证事务的原子性、隔离性

}
