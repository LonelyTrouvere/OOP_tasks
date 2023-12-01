package task9;

public class MyPhaser {
    private int currentParties;
    private int partiesAtStart;
    private int phase;

    public MyPhaser(int parties) {
        this.currentParties = parties;
        this.partiesAtStart = parties;
    }

    public synchronized void register() {
        currentParties++;
        partiesAtStart++;
    }

    public synchronized void arrive() {
        currentParties--;
        if (currentParties == 0) {
            this.resetPhaser();
        }
    }

    public synchronized void arriveAndAwaitAdvance() throws InterruptedException {
        currentParties--;
        if (currentParties > 0) {
            this.wait();
        } else {
            this.resetPhaser();
        }
    }

    public synchronized void arriveAndDeregister() throws InterruptedException {
        currentParties--;
        partiesAtStart--;
        if (currentParties == 0) {
            this.resetPhaser();
        }
    }

    public int getPhase() {
        return this.phase;
    }

    private void resetPhaser() {
        this.currentParties = partiesAtStart;
        this.phase++;
        this.notifyAll();
    }
}