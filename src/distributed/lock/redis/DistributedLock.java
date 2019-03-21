package distributed.lock.redis;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

public class DistributedLock {
	private final JedisPool jedisPool;
	
	public DistributedLock(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 加锁
	 * @param localName 锁的key的名字，是全局唯一的，一旦有一个线程在redis上插入了之后，其他线程会因为setnx失败而不能获得锁
	 * @param acquireTimeout 获取锁超时时间
	 * @param timeout 锁的超时时间
	 * @return 锁标识，只有持有相应锁标识的人才可以正确解锁
	 */
	public String lockWithTimeout(String localName, long acquireTimeout, long timeout) {
		Jedis conn = null;
		String retIdentifier = null;
		try {
			// 获取连接
			conn = jedisPool.getResource();
			// 随机生成一个value
			String identifier = UUID.randomUUID().toString();
			// 锁名，即key值
			String lockKey = "lock:" + localName;
			// 超时时间，上锁后超过此时间则放弃获取锁
			int lockExpire = (int)(timeout / 1000);
			
			// 获取锁的超时时间，超过这个时间则放弃获取
			long end = System.currentTimeMillis() + acquireTimeout;
			SetParams setParams = new SetParams();
			setParams.ex(lockExpire);
			setParams.nx();
			while (System.currentTimeMillis() < end) {
				/*尝试用这个条件的话，会进入下面设置过期时间，但其实是不安全的，因为setnx和expire之间不是原子的，
				 * 可能在一个线程刚申请完锁就被其他线程抢过去设置过期时间了，结果就是其他线程获得了一个为null的indentifier
				 * 解决的方法就是使用redis提供的原子操作，在set的同时设置好了setnx和expire
				 */
//				if (conn.setnx(lockKey, identifier)==1) {
//					conn.expire(lockKey, lockExpire);	// setnx和expire之间不是原子操作，可能被打断
				
				if (conn.set(lockKey, identifier,setParams) != null) {
					// 返回value值，用于释放锁时间确认
					retIdentifier = identifier;
					return retIdentifier;
				}
				// 返回-1标识key没有设置超时时间，为key设置一个超时时间
				// ttl函数用于查看key的生存时间
				if (conn.ttl(lockKey) == -1) {
					System.out.println(Thread.currentThread().getName() + "设置过期时间");
					conn.expire(lockKey, lockExpire);
				}
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return retIdentifier;
	}
	
	/**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
	public boolean releaseLock(String lockName, String identifier) {
		Jedis conn = null;
		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
		try {
			conn = jedisPool.getResource();
			while (true) {
				// 监视lock，准备开始事务
				conn.watch(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				if (identifier.equals(conn.get(lockKey))) {
					// 将锁的释放当作一个事务来保证原子性
					Transaction transaction = conn.multi();
					transaction.del(lockKey);
					List<Object> results = transaction.exec();
					if (results == null) {
						continue;
					}
					retFlag = true;
				}
				conn.unwatch();
				break;
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return retFlag;
	}
}
