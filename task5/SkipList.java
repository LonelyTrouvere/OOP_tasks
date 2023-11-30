package task5;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T> {
    final T value;
    final int level;
    final AtomicMarkableReference<Node<T>>[] next;

    Node(T value, int level) {
        this.value = value;
        this.level = level;
        this.next = new AtomicMarkableReference[level + 1];
        for (int i = 0; i <= level; i++) {
            this.next[i] = new AtomicMarkableReference<>(null, false);
        }
    }
}

public class SkipList<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 32;

    private final Node<T> head = new Node<>(null, MAX_LEVEL);
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public void add(T value) {
        int topLevel = randomLevel();
        Node<T> newNode = new Node<>(value, topLevel);

        Node<T>[] preds = new Node[MAX_LEVEL + 1];
        Node<T>[] succs = new Node[MAX_LEVEL + 1];

        while (true) {
            boolean found = find(value, preds, succs);

            if (!found) {
                for (int i = 0; i <= topLevel; i++) {
                    newNode.next[i].set(succs[i], false);
                }

                Node<T> pred = preds[0];
                Node<T> succ = succs[0];

                if (!pred.next[0].compareAndSet(succ, newNode, false, false)) {
                    continue;
                }

                for (int i = 1; i <= topLevel; i++) {
                    while (true) {
                        pred = preds[i];
                        succ = succs[i];

                        if (pred.next[i].compareAndSet(succ, newNode, false, false)) {
                            break;
                        }

                        find(value, preds, succs);
                    }
                }

                return;
            }
        }
    }

    public boolean contains(T value) {
        Node<T>[] preds = new Node[MAX_LEVEL + 1];
        Node<T>[] succs = new Node[MAX_LEVEL + 1];
        return find(value, preds, succs);
    }

    private boolean find(T value, Node<T>[] preds, Node<T>[] succs) {
        int keyLevel = -1;
        Node<T> pred = head;

        for (int i = MAX_LEVEL; i >= 0; i--) {
            Node<T> curr = pred.next[i].getReference();
            while (curr != null && curr.value.compareTo(value) < 0) {
                pred = curr;
                curr = pred.next[i].getReference();
            }

            preds[i] = pred;
            succs[i] = curr;

            if (keyLevel == -1 && i > 0 && pred == head && curr == null) {
                continue;
            }

            if (keyLevel == -1 && curr != null && curr.value.equals(value)) {
                keyLevel = i;
            }
        }

        return keyLevel != -1;
    }

    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }
}
