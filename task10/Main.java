package task10;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        MyThreadPool threadPool = new MyThreadPool(3);

        threadPool.execute(createTask(1000, "1 working", countDownLatch));
        threadPool.execute(createTask(2000, "2 working", countDownLatch));
        threadPool.execute(createTask(3000, "3 working", countDownLatch));
        threadPool.execute(createTask(10, "4 working", countDownLatch));
        threadPool.execute(createTask(10, "5 working", countDownLatch));

        countDownLatch.await();
        threadPool.shutDownPool();
    }

    private static Runnable createTask(long sleep, String msg, CountDownLatch countDownLatch) {
        return () -> {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(msg);
            countDownLatch.countDown();
        };
    }
}