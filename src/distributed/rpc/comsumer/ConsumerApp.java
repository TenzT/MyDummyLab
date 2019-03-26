package distributed.rpc.comsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import distributed.rpc.Calculator;

public class ConsumerApp {
	private static Logger log = LoggerFactory.getLogger(ConsumerApp.class);
	
	public static void main(String[] args) {
		Calculator calculator = new CalculatorRemoteImpl();
		int res = calculator.add(1, 2);
		System.out.println(res);
	}
}
