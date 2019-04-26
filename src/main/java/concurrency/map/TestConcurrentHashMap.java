package concurrency.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestConcurrentHashMap {
    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public void add (String key) {
        Integer value = map.get(key);
        if (value == null) {
            map.put(key, 1);
        } else {
            map.put(key, value+1);
        }
    }

    public int get(String key) {
        return map.get(key);
    }

    public static void main(String[] args) {
        final TestConcurrentHashMap t = new TestConcurrentHashMap();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0; i<=1000; i++) {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    t.add("key");
                }
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.get("key"));
        exec.shutdown();

    }

}
