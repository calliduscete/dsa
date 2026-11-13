/**
 * Stack built on a singly linked list.
 *
 * The top of the stack is the head of the list, so push and pop
 * are O(1) with no resizing needed.
 */
public class LinkedStack {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    // O(1)
    public void push(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
        size++;
    }

    // O(1)
    public int pop() {
        if (head == null) {
            throw new IllegalStateException("Stack is empty");
        }
        int value = head.data;
        head = head.next;
        size--;
        return value;
    }

    // O(1)
    public int peek() {
        if (head == null) {
            throw new IllegalStateException("Stack is empty");
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
        LinkedStack stack = new LinkedStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("peek: " + stack.peek());   // 3
        System.out.println("pop: " + stack.pop());     // 3
        System.out.println("pop: " + stack.pop());     // 2
        System.out.println("size: " + stack.size());   // 1
    }
}
