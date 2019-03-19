package lock.readwritelock;

public class ReadWithSynchronized {
	public synchronized void get(Thread thread) {
		System.out.println("start time:"+System.currentTimeMillis());
		// 在一个线程进入临界区后，其他线程会被阻塞
		for (int i=0; i<5; i++) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(thread.getName() + ":正在进行读操作……");
		}
		System.out.println(thread.getName() + ":读操作完毕！");
		System.out.println("end time:"+System.currentTimeMillis());
		
	}
	
	public static void main(String[] args) {
		final ReadWithSynchronized resource = new ReadWithSynchronized();
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
