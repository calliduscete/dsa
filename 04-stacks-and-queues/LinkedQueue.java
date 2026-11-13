/**
 * Queue built on a singly linked list with head and tail pointers.
 *
 * Enqueue adds at the tail, dequeue removes from the head: both O(1),
 * and the queue never gets "full".
 */
public class LinkedQueue {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // O(1)
    public void enqueue(int value) {
        Node node = new Node(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // O(1)
    public int dequeue() {
        if (head == null) {
            throw new IllegalStateException("Queue is empty");
        }
        int value = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    // O(1)
    public int peek() {
        if (head == null) {
            throw new IllegalStateException("Queue is empty");
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("peek: " + queue.peek());         // 10
        System.out.println("dequeue: " + queue.dequeue());   // 10
        System.out.println("dequeue: " + queue.dequeue());   // 20
        System.out.println("size: " + queue.size());         // 1
    }
}
