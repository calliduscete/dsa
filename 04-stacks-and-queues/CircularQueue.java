/**
 * Queue (FIFO: first in, first out) built on a fixed-size circular array.
 *
 * Think of a line at a shop: people join at the back and leave from the front.
 * A circular array reuses freed slots by wrapping the indices around with %,
 * so enqueue and dequeue are both O(1) with no shifting.
 */
public class CircularQueue {

    private final int[] data;
    private int front;   // index of the first element
    private int size;

    public CircularQueue(int capacity) {
        data = new int[capacity];
    }

    // O(1)
    public void enqueue(int value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        int rear = (front + size) % data.length;
        data[rear] = value;
        size++;
    }

    // O(1)
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        int value = data[front];
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    // O(1)
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(4);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println("full: " + queue.isFull());           // true
        System.out.println("dequeue: " + queue.dequeue());       // 1
        System.out.println("dequeue: " + queue.dequeue());       // 2

        // These reuse the freed slots by wrapping around
        queue.enqueue(5);
        queue.enqueue(6);
        System.out.println("peek: " + queue.peek());             // 3
        System.out.println("size: " + queue.size());             // 4
        while (!queue.isEmpty()) {
            System.out.print(queue.dequeue() + " ");             // 3 4 5 6
        }
        System.out.println();
    }
}
