import java.util.Arrays;

/**
 * Min-heap stored in an array.
 *
 * A heap is a COMPLETE binary tree where every parent is smaller than (or equal
 * to) its children, so the smallest value is always at the root.
 *
 * Because the tree is complete, we don't need node objects. For the node at index i:
 *   parent       = (i - 1) / 2
 *   left child   = 2 * i + 1
 *   right child  = 2 * i + 2
 *
 * peek: O(1)   insert: O(log n)   extractMin: O(log n)   build from array: O(n)
 */
public class MinHeap {

    private int[] data;
    private int size;

    public MinHeap() {
        data = new int[8];
    }

    // Build a heap from an existing array in O(n) (the "heapify" approach)
    public MinHeap(int[] values) {
        data = Arrays.copyOf(values, Math.max(values.length, 8));
        size = values.length;
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    // Insert: add at the end, then bubble up. O(log n)
    public void insert(int value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[size] = value;
        siftUp(size);
        size++;
    }

    // Look at the smallest value: O(1)
    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return data[0];
    }

    // Remove the smallest: move the last element to the root, then sink it. O(log n)
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = data[0];
        data[0] = data[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    // Bubble a value up while it is smaller than its parent: O(log n)
    private void siftUp(int i) {
        while (i > 0 && data[i] < data[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // Sink a value down while a child is smaller: O(log n)
    private void siftDown(int i) {
        while (true) {
            int smallest = i;
            int l = left(i);
            int r = right(i);
            if (l < size && data[l] < data[smallest]) {
                smallest = l;
            }
            if (r < size && data[r] < data[smallest]) {
                smallest = r;
            }
            if (smallest == i) {
                return;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, size));
    }

    public static void main(String[] args) {
        MinHeap heap = new MinHeap();
        int[] values = {40, 10, 30, 5, 20, 15};
        for (int v : values) {
            heap.insert(v);
        }
        System.out.println("heap array: " + heap);          // smallest at index 0
        System.out.println("peek: " + heap.peek());           // 5

        System.out.print("extract in order: ");
        while (!heap.isEmpty()) {
            System.out.print(heap.extractMin() + " ");        // sorted ascending
        }
        System.out.println();

        MinHeap built = new MinHeap(new int[]{9, 4, 7, 1, 8, 2});
        System.out.println("built from array: " + built);
        System.out.println("peek: " + built.peek());           // 1
    }
}
