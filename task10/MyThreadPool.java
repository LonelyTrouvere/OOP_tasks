package task10;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThreadPool {
    private final ConcurrentLinkedDeque<Runnable> tasks = new ConcurrentLinkedDeque<>();
    private final AtomicBoolean poolShutDown = new AtomicBoolean(false);

    public MyThreadPool(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            ExecutorThread thread = new ExecutorThread();
            thread.setName("Executor thread #" + i);
            thread.start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (poolIsShutDown()) {
            throw new InterruptedException("Thread pool is shut down!");
        }

        tasks.add(task);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void shutDownPool() {
        this.poolShutDown.getAndSet(true);
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void shutDownImmediately() {
        this.tasks.clear();
        shutDownPool();
    }

    private boolean poolIsShutDown() {
        return this.poolShutDown.get();
    }

    private class ExecutorThread extends Thread {
        @Override
        public void run() {
            try{
                while (!poolIsShutDown()) {
                    Runnable task = tasks.poll();
                    if (task == null) {
                        synchronized (MyThreadPool.this) {
                            MyThreadPool.this.wait();
                        }
                    } else {
                        task.run();
                    }
                }
            } catch(InterruptedException e) {e.printStackTrace();}
        }
    }
}