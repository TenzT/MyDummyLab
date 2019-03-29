package distributed.lock.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestService {
	
	public static void main(String[] args) {
		Service service = new Service();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i=0; i<3; i++) {
			exec.execute(new SecKillHandler(service));
		}
		exec.shutdown();
	}
}

class SecKillHandler implements Runnable {
	private Service service;
	
	public SecKillHandler(Service service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service.seckill();
	}
	
}