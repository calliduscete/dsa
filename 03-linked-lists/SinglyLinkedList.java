/**
 * Singly linked list: each node stores a value and a pointer to the next node.
 *
 * Unlike an array, nodes are not stored next to each other in memory,
 * so there is no index access, but inserting at the front is cheap.
 */
public class SinglyLinkedList {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    // Insert at the front: O(1) time, O(1) space
    public void addFirst(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
        size++;
    }

    // Insert at the end: O(n) time (walks to the tail), O(1) space
    public void addLast(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        size++;
    }

    // Insert at an index: O(n) time, O(1) space
    public void addAt(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node previous = head;
        for (int i = 0; i < index - 1; i++) {
            previous = previous.next;
        }
        Node node = new Node(value);
        node.next = previous.next;
        previous.next = node;
        size++;
    }

    // Delete the first node: O(1) time, O(1) space
    public int removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        int value = head.data;
        head = head.next;
        size--;
        return value;
    }

    // Delete the first node holding the value: O(n) time, O(1) space
    public boolean remove(int value) {
        if (head == null) {
            return false;
        }
        if (head.data == value) {
            head = head.next;
            size--;
            return true;
        }
        Node current = head;
        while (current.next != null && current.next.data != value) {
            current = current.next;
        }
        if (current.next == null) {
            return false;
        }
        current.next = current.next.next;
        size--;
        return true;
    }

    // Search: O(n) time, O(1) space
    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Get value at an index: O(n) time, O(1) space
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Print as 1 -> 2 -> 3 -> null : O(n)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addFirst(5);
        System.out.println(list);                       // 5 -> 10 -> 20 -> 30 -> null

        list.addAt(2, 15);
        System.out.println(list);                       // 5 -> 10 -> 15 -> 20 -> 30 -> null

        System.out.println("get(3): " + list.get(3));   // 20
        System.out.println("contains(15): " + list.contains(15));
        System.out.println("contains(99): " + list.contains(99));

        list.remove(10);
        System.out.println(list);                       // 5 -> 15 -> 20 -> 30 -> null

        list.removeFirst();
        System.out.println(list);                       // 15 -> 20 -> 30 -> null
        System.out.println("size: " + list.size());
    }
}
