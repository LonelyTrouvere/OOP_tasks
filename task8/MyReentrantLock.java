package task8;

public class MyReentrantLock {
    private boolean locked = false;

    public synchronized void lock() throws InterruptedException {
        if (this.locked) {
            this.wait();
        }
        this.locked = true;
    }

    public synchronized void unlock() {
        if (locked) {
            this.notifyAll();
            this.locked = false;
        }
    }

    public synchronized boolean tryLock() throws InterruptedException {
        if (this.locked) {
            return false;
        }
        lock();
        return true;
    }

    public synchronized boolean isLocked() {
        return this.locked;
    }
}