import java.util.Arrays;

/**
 * Stack (LIFO: last in, first out) built on a resizable array.
 *
 * Think of a stack of plates: you add and remove only at the top.
 * push, pop and peek are all O(1) (push is amortised O(1) because of resizing).
 */
public class ArrayStack {

    private int[] data;
    private int top;   // index of the next free slot, also the size

    public ArrayStack() {
        data = new int[4];
    }

    // O(1) amortised: doubles the array when full
    public void push(int value) {
        if (top == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[top++] = value;
    }

    // O(1)
    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return data[--top];
    }

    // O(1)
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return data[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, top)) + " (top is on the right)";
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack();
        for (int i = 1; i <= 6; i++) {
            stack.push(i * 10);
        }
        System.out.println(stack);
        System.out.println("peek: " + stack.peek());
        System.out.println("pop: " + stack.pop());
        System.out.println("pop: " + stack.pop());
        System.out.println(stack);
        System.out.println("size: " + stack.size());
        System.out.println("isEmpty: " + stack.isEmpty());
    }
}
