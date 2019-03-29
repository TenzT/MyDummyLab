package readwritelock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLockTest {
	ReadWriteLock lock = new ReentrantReadWriteLock();
	public void get (Thread thread) {
		lock.readLock().lock();
		try {
			System.out.println("start time:"+System.currentTimeMillis());
			for(int i=0; i<5; i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(thread.getName() + ":正在进行读操作……");
			}
			System.out.println(thread.getName() + ":读操作完毕！");
			System.out.println("end time:"+System.currentTimeMillis());
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public void set (Thread thread) {
		lock.writeLock().lock();
		try {
			System.out.println("start time:"+System.currentTimeMillis());
			for(int i=0; i<5; i++){
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(thread.getName() + ":正在进行写操作……");
			}
			System.out.println(thread.getName() + ":写操作完毕！");
			System.out.println("end time:"+System.currentTimeMillis());
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadAndWriteLockTest resource = new ReadAndWriteLockTest();
		// 建N个线程，同时写
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i=0; i < 4; i++) {
			service.execute(new Runnable() {
				@Override
				public void run() {
					resource.set(Thread.currentThread());
				}
			});			
		}
		// 建N个线程，同时读
		ExecutorService service1 = Executors.newCachedThreadPool();
		for (int i=0; i < 3; i++) {
			service1.execute(new Runnable() {
				@Override
				public void run() {
					resource.get(Thread.currentThread());
				}
			});			
		}
		service.shutdown();
		service1.shutdown();
	}
}
