import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Classic stack and queue problems.
 *
 * In Java, use ArrayDeque for both stacks and queues. It is faster than the
 * legacy Stack class and LinkedList.
 *   Stack: push(), pop(), peek()
 *   Queue: offer() / add(), poll(), peek()
 */
public class StackQueueProblems {

    // Balanced parentheses: O(n) time, O(n) space
    static boolean isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if ((c == ')' && open != '(')
                        || (c == ']' && open != '[')
                        || (c == '}' && open != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // Reverse a string with a stack: O(n) time, O(n) space
    static String reverseWithStack(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    // Evaluate a postfix expression, e.g. "2 3 4 * +" = 14: O(n) time, O(n) space
    static int evaluatePostfix(String expression) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : expression.split(" ")) {
            switch (token) {
                case "+": stack.push(stack.pop() + stack.pop()); break;
                case "*": stack.push(stack.pop() * stack.pop()); break;
                case "-": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "/": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                default: stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    // Next greater element using a monotonic stack: O(n) time, O(n) space
    // For each element, find the next larger value to its right (or -1).
    static int[] nextGreater(int[] nums) {
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();   // stores indices
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return result;
    }

    // Queue implemented with two stacks: enqueue O(1), dequeue amortised O(1)
    static class QueueUsingStacks {
        private final Deque<Integer> in = new ArrayDeque<>();
        private final Deque<Integer> out = new ArrayDeque<>();

        void enqueue(int value) {
            in.push(value);
        }

        int dequeue() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
            if (out.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            return out.pop();
        }
    }

    // Stack that returns the minimum in O(1): all operations O(1)
    static class MinStack {
        private final Deque<Integer> stack = new ArrayDeque<>();
        private final Deque<Integer> mins = new ArrayDeque<>();

        void push(int value) {
            stack.push(value);
            if (mins.isEmpty() || value <= mins.peek()) {
                mins.push(value);
            }
        }

        int pop() {
            int value = stack.pop();
            if (value == mins.peek()) {
                mins.pop();
            }
            return value;
        }

        int getMin() {
            return mins.peek();
        }
    }

    public static void main(String[] args) {
        System.out.println("isBalanced({[()]}): " + isBalanced("{[()]}"));
        System.out.println("isBalanced([)]): " + isBalanced("[)]"));
        System.out.println("reverseWithStack(stack): " + reverseWithStack("stack"));
        System.out.println("evaluatePostfix(2 3 4 * +): " + evaluatePostfix("2 3 4 * +"));
        System.out.println("nextGreater: " + Arrays.toString(nextGreater(new int[]{4, 5, 2, 25, 7})));

        QueueUsingStacks q = new QueueUsingStacks();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println("queue via two stacks: " + q.dequeue() + " " + q.dequeue());

        MinStack ms = new MinStack();
        ms.push(5);
        ms.push(2);
        ms.push(8);
        System.out.println("getMin: " + ms.getMin());
        ms.pop();
        ms.pop();
        System.out.println("getMin after popping 8 and 2: " + ms.getMin());
    }
}
