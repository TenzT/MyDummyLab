package distributed.lock.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * 使用50个线程模拟秒杀一个商品，使用--运算符来实现商品减少，从结果有序性就可以看出是否为加锁状态
 */
public class Service {
    private static JedisPool pool = null;
	
    static {
    	JedisPoolConfig config = new JedisPoolConfig();
    	// 设最大连接数
    	config.setMaxTotal(200);
    	// 设置最大空闲数
    	config.setMaxIdle(8);
    	// 设置最大等待时间 最多等等10秒
    	config.setMaxWaitMillis(1000 * 100);
    	// 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
    	config.setTestOnBorrow(true);
    	pool = new JedisPool(config, "127.0.0.1", 6379, 3000);
    }
    DistributedLock lock = new DistributedLock(pool);
    
    int goods = 500;	// 500件商品
    
    public void seckill() {
    	String indentifier = null;
    	// 返回锁的value值，供释放锁时判断,指定key值为resource
    	if ((indentifier = lock.lockWithTimeout("resource", 5000, 1000)) == null) {
    		return;
    	}
    	System.out.println(Thread.currentThread().getName() + "获得了锁" + indentifier);
    	System.out.println(--goods);
    	if (lock.releaseLock("resource", indentifier)) {
    		System.out.println(Thread.currentThread().getName() + "释放锁成功\n");
    	}
    }
    
}
