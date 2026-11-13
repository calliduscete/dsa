# Stacks and Queues

Stacks and queues are simple structures that control **the order** in which items are added and removed. Both give O(1) insertion and removal, and they show up everywhere: undo buttons, browser history, task scheduling, and graph algorithms.

## Stack (LIFO)

**Last in, first out.** Like a stack of plates, you add and remove only at the **top**.

```
push(3)     push(7)     pop() -> 7
          +-----+
          |  7  |  <- top
+-----+   +-----+     +-----+
|  3  |   |  3  |     |  3  | <- top
+-----+   +-----+     +-----+
```

| Operation | Meaning | Time |
|---|---|---|
| `push(x)` | Add `x` to the top | O(1) |
| `pop()` | Remove and return the top | O(1) |
| `peek()` | Look at the top without removing | O(1) |
| `isEmpty()` | Is the stack empty? | O(1) |

**Real uses:** undo/redo, browser back button, function call stack, balanced brackets, expression evaluation, depth-first search.

## Queue (FIFO)

**First in, first out.** Like a line at a shop, people join at the **rear** and leave from the **front**.

```
front                    rear
  |                        |
  v                        v
[ 1 ] <- [ 2 ] <- [ 3 ] <- [ 4 ]
 dequeue()                 enqueue(4)
```

| Operation | Meaning | Time |
|---|---|---|
| `enqueue(x)` | Add `x` at the rear | O(1) |
| `dequeue()` | Remove and return the front | O(1) |
| `peek()` | Look at the front without removing | O(1) |
| `isEmpty()` | Is the queue empty? | O(1) |

**Real uses:** print queues, task scheduling, customer support lines, buffering data, breadth-first search.

## Other kinds of queues

- **Circular queue:** an array where the indices wrap around, so freed slots are reused (see `CircularQueue.java`).
- **Deque (double-ended queue):** add and remove at both ends. Java's `ArrayDeque` is a deque.
- **Priority queue:** items come out by priority, not arrival order. This is covered in the heaps folder.

## Implementing them

| Approach | Pros | Cons |
|---|---|---|
| **Array** | Simple, cache-friendly | Fixed size, or needs resizing |
| **Linked list** | Grows freely, no resizing | Extra memory per node |

For a stack on a linked list, push and pop at the head. For a queue on a linked list, keep a tail pointer: enqueue at the tail, dequeue at the head.

## Using them in Java

Use `ArrayDeque` for both. It is faster than the old `Stack` class.

```java
// Stack
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.push(2);
stack.pop();    // 2
stack.peek();   // 1

// Queue
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);
queue.offer(2);
queue.poll();   // 1
queue.peek();   // 2
```

## Key techniques

- **Matching pairs with a stack:** push openers, pop when you see a closer (brackets, tags).
- **Monotonic stack:** keep the stack sorted so you can find the "next greater" or "next smaller" element in O(n).
- **Reversing order:** pushing everything onto a stack and popping it reverses the sequence.
- **Level-by-level processing with a queue:** the basis of breadth-first search.
- **One structure from another:** a queue from two stacks, or a stack from two queues.

## Common mistakes

- Calling `pop`, `peek` or `dequeue` on an **empty** structure. Always check `isEmpty()` first.
- Mixing up the Java method names: stack uses `push/pop`, queue uses `offer/poll`.
- In a circular queue, mixing up the "full" and "empty" conditions. Tracking a `size` counter avoids this.

## Files in this folder

| File | What it covers |
|---|---|
| [`ArrayStack.java`](ArrayStack.java) | Stack on a resizable array |
| [`LinkedStack.java`](LinkedStack.java) | Stack on a linked list |
| [`CircularQueue.java`](CircularQueue.java) | Fixed-size queue on a circular array |
| [`LinkedQueue.java`](LinkedQueue.java) | Queue on a linked list with head and tail pointers |
| [`StackQueueProblems.java`](StackQueueProblems.java) | Balanced brackets, reverse a string, postfix evaluation, next greater element, queue from two stacks, min stack |

Run any file with:

```
java ArrayStack.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Reverse an array using a stack.
2. Check if a string of brackets is balanced (try it without looking at the example).
3. Implement a stack that also supports `size()` and `clear()`.
4. Reverse a queue.
5. Generate binary numbers from 1 to `n` using a queue.

**Medium**
6. Convert an infix expression to postfix.
7. Implement a stack using two queues.
8. Find the daily temperatures problem: how many days until a warmer day (monotonic stack).
9. Sliding window maximum using a deque.
10. Implement a circular queue that resizes when full.

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- A stack is LIFO, a queue is FIFO, and every core operation is O(1).
- Stacks handle nesting and "undo" problems. Queues handle fairness and level-order problems.
- Use `ArrayDeque` in Java for both.
- Always guard against empty-structure errors.
