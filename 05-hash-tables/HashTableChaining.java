/**
 * Hash table using SEPARATE CHAINING to handle collisions.
 *
 * The table is an array of "buckets". A hash function turns a key into a bucket
 * index. If two keys land in the same bucket (a collision), they are stored in a
 * linked list inside that bucket.
 *
 * Average time: put / get / remove are O(1).
 * Worst case: O(n) if every key lands in the same bucket.
 */
public class HashTableChaining {

    private static class Node {
        String key;
        int value;
        Node next;

        Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] buckets;
    private int size;
    private static final double MAX_LOAD_FACTOR = 0.75;

    public HashTableChaining() {
        buckets = new Node[8];
    }

    // Hash function: turns a key into a valid bucket index
    private int indexFor(String key, int capacity) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    // Insert or update: O(1) average
    public void put(String key, int value) {
        if ((double) (size + 1) / buckets.length > MAX_LOAD_FACTOR) {
            resize();
        }
        int index = indexFor(key, buckets.length);
        for (Node node = buckets[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                node.value = value;      // key exists: update it
                return;
            }
        }
        Node node = new Node(key, value);
        node.next = buckets[index];      // add at the front of the chain
        buckets[index] = node;
        size++;
    }

    // Lookup: O(1) average. Returns null if the key is missing.
    public Integer get(String key) {
        int index = indexFor(key, buckets.length);
        for (Node node = buckets[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    // Delete: O(1) average
    public boolean remove(String key) {
        int index = indexFor(key, buckets.length);
        Node previous = null;
        for (Node node = buckets[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = node.next;
                } else {
                    previous.next = node.next;
                }
                size--;
                return true;
            }
            previous = node;
        }
        return false;
    }

    // Double the number of buckets and re-insert everything: O(n),
    // but it happens rarely, so put stays O(1) amortised.
    private void resize() {
        Node[] old = buckets;
        buckets = new Node[old.length * 2];
        for (Node head : old) {
            for (Node node = head; node != null; node = node.next) {
                int index = indexFor(node.key, buckets.length);
                Node copy = new Node(node.key, node.value);
                copy.next = buckets[index];
                buckets[index] = copy;
            }
        }
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return buckets.length;
    }

    public static void main(String[] args) {
        HashTableChaining table = new HashTableChaining();
        table.put("apple", 3);
        table.put("banana", 5);
        table.put("cherry", 7);
        System.out.println("apple: " + table.get("apple"));          // 3
        System.out.println("grape: " + table.get("grape"));          // null

        table.put("apple", 10);                                      // update
        System.out.println("apple after update: " + table.get("apple"));

        table.remove("banana");
        System.out.println("contains banana: " + table.containsKey("banana"));
        System.out.println("size: " + table.size());

        // Add many keys to trigger resizing
        for (int i = 0; i < 20; i++) {
            table.put("key" + i, i);
        }
        System.out.println("size after adding 20: " + table.size());
        System.out.println("capacity after resizing: " + table.capacity());
        System.out.println("key7: " + table.get("key7"));
    }
}
