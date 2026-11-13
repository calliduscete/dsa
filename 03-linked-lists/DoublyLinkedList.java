/**
 * Doubly linked list: each node points to both the next and the previous node.
 *
 * Keeping a tail pointer makes insertion and deletion at BOTH ends O(1).
 */
public class DoublyLinkedList {

    private static class Node {
        int data;
        Node prev;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // O(1)
    public void addFirst(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    // O(1) thanks to the tail pointer
    public void addLast(int value) {
        Node node = new Node(value);
        if (tail == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // O(1)
    public int removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        int value = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return value;
    }

    // O(1)
    public int removeLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        int value = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return value;
    }

    // O(n)
    public boolean contains(int value) {
        for (Node current = head; current != null; current = current.next) {
            if (current.data == value) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    // Forward print: O(n)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("null <-> ");
        for (Node current = head; current != null; current = current.next) {
            sb.append(current.data).append(" <-> ");
        }
        sb.append("null");
        return sb.toString();
    }

    // Backward print: O(n)
    public String toStringReversed() {
        StringBuilder sb = new StringBuilder("null <-> ");
        for (Node current = tail; current != null; current = current.prev) {
            sb.append(current.data).append(" <-> ");
        }
        sb.append("null");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addLast(2);
        list.addLast(3);
        list.addFirst(1);
        list.addLast(4);
        System.out.println(list);
        System.out.println(list.toStringReversed());

        System.out.println("removeFirst: " + list.removeFirst());
        System.out.println("removeLast: " + list.removeLast());
        System.out.println(list);
        System.out.println("size: " + list.size());
    }
}
