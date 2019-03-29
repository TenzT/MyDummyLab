package readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLockTest2 {
	ReadWriteLock lock = new ReentrantReadWriteLock();
	public void get (Thread thread) {
		lock.readLock().lock();
		try {
			System.out.println("start time:"+System.currentTimeMillis());
			for(int i=0; i<5; i++){
				try {
					Thread.sleep(20);
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
	
	public static void main(String[] args) {
		final ReadAndWriteLockTest2 resource = new ReadAndWriteLockTest2();
		// 线程1
		new Thread(new Runnable() {
			@Override
			public void run() {
				resource.get(Thread.currentThread());
			}
		}).start();
		
		// 线程2
		new Thread(new Runnable() {
			@Override
			public void run() {
				resource.get(Thread.currentThread());
			}
		}).start();
	}
}
