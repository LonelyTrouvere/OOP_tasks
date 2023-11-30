package task7;

public class MyCyclicBarrier {
    private final int parties;
    private int currentParties;

    public MyCyclicBarrier(int parties) {
        this.parties = parties;
        this.currentParties = parties;
    }

    public synchronized void await() throws InterruptedException {
        currentParties--;

        if (currentParties == 0) {
            this.currentParties = parties;
            notifyAll();
        } else {
            this.wait();
        }
    }
}