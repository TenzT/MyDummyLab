package spring.aop;


import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTransactionTest {

	private static ApplicationContext ctx = null;
	private static BookShopDao bookShopDao = null;
	private static BookShopService bookShopService = null;
	private static Cashier cashier = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		bookShopDao = ctx.getBean(BookShopDao.class);
		bookShopService = ctx.getBean(BookShopService.class);
		cashier = ctx.getBean(Cashier.class);
	}



	@Test
	public void testTransactionlPropagation(){
		cashier.checkout("Tom", Arrays.asList("0001", "0002"));
	}

	@Test
	public void testBookShopService(){
		bookShopService.purchase("Tom", "0001");
	}

	@Test
	public void testBookShopDaoUpdateUserAccount(){
		bookShopDao.updateUserAccount("Tom", 10);
	}

	@Test
	public void testBookShopDaoUpdateBookStock(){
		bookShopDao.updateBookStock("0001");
	}

	@Test
	public void testBookShopDaoFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceByIsbn("0001"));
	}

    // 这里所做的实验是保证事务的原子性、隔离性

}
