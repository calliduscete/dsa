# Heaps and Priority Queues

A heap is a special tree that always keeps the **smallest** (or **largest**) value at the top, so you can grab it instantly. Heaps are the standard way to build a **priority queue**: a queue where the item with the highest priority comes out first, not the one that arrived first.

## What is a heap?

A heap is a **complete binary tree** (every level is full except possibly the last, which fills from the left) with the **heap property**:

- **Min-heap:** every parent is **less than or equal to** its children, so the minimum is at the root.
- **Max-heap:** every parent is **greater than or equal to** its children, so the maximum is at the root.

```
Min-heap:              Same heap stored in an array:

        5              index:  0   1   2   3   4   5
      /   \                   [5, 10, 15, 40, 20, 30]
    10     15
   /  \    /
  40  20  30
```

Note that a heap is **not sorted**. Only the parent-child relationship is guaranteed. A node's children can be in either order, and siblings are not compared.

## Storing a heap in an array

Because the tree is complete, there are no gaps, so we store it in a plain array with no node objects or pointers. For the node at index `i`:

| Relation | Index |
|---|---|
| Parent | `(i - 1) / 2` |
| Left child | `2 * i + 1` |
| Right child | `2 * i + 2` |

## Core operations

| Operation | How it works | Time |
|---|---|---|
| **peek** | Return `data[0]` | O(1) |
| **insert** | Add at the end, then **sift up** (swap with the parent while smaller) | O(log n) |
| **extract min/max** | Replace the root with the last element, shrink, then **sift down** (swap with the smaller child) | O(log n) |
| **build heap** from an array | Sift down from the last parent back to the root | O(n) |
| **search** for an arbitrary value | Scan the array | O(n) |

Why O(log n)? A complete tree with n nodes has height about log n, and sifting moves along one root-to-leaf path at most.

Why is building a heap O(n) and not O(n log n)? Most nodes are near the bottom and only sink a short distance. The total work adds up to O(n).

## Heap sort

1. Build a max-heap from the array: O(n).
2. Swap the root (the largest) with the last unsorted element, shrink the heap, sift down. Repeat n times.

Total: **O(n log n)** in all cases, **O(1)** extra space, but it is **not stable**.

## Using heaps in Java

Java's `PriorityQueue` is a **min-heap** by default.

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(1);
minHeap.offer(3);
minHeap.peek();   // 1
minHeap.poll();   // 1 (removes it)

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom ordering, e.g. by the second element of each pair
PriorityQueue<int[]> pairs = new PriorityQueue<>((a, b) -> a[1] - b[1]);
```

Iterating over a `PriorityQueue` does **not** give sorted order. Only `poll()` does.

## Key techniques

- **Top K with a heap of size K:** to find the K largest, keep a **min**-heap of size K and drop the smallest whenever it grows past K. For the K smallest, use a max-heap. This is O(n log k).
- **Two heaps:** keep a max-heap for the lower half and a min-heap for the upper half to track a running median.
- **Merge K sorted lists:** put each list's current front into a min-heap and repeatedly take the smallest.
- **Greedy "always take the best next":** scheduling, Dijkstra's shortest path, Huffman coding.

## Heap vs other structures

| Structure | Get min | Insert | Remove min | Sorted order |
|---|---|---|---|---|
| Unsorted array | O(n) | O(1) | O(n) | O(n log n) |
| Sorted array | O(1) | O(n) | O(n) or O(1)* | Already sorted |
| Balanced BST | O(log n) | O(log n) | O(log n) | O(n) |
| **Heap** | **O(1)** | **O(log n)** | **O(log n)** | O(n log n) |

*Removing the smallest from the front of a sorted array needs shifting, unless you track an offset.

Use a heap when you only ever need the min or max, not the whole sorted order.

## Common mistakes

- Thinking a heap is sorted. Only the root is guaranteed to be the min or max.
- Mixing up min and max heaps in Top K problems. **K largest uses a min-heap**, and **K smallest uses a max-heap**.
- Writing `a - b` in a comparator when the values might overflow. Use `Integer.compare(a, b)` for safety.
- Forgetting that `PriorityQueue.remove(x)` is O(n).
- Expecting `PriorityQueue` to be thread-safe.

## Files in this folder

| File | What it covers |
|---|---|
| [`MinHeap.java`](MinHeap.java) | Array-based min-heap: insert, peek, extract, sift up and down, O(n) build |
| [`HeapSort.java`](HeapSort.java) | In-place heap sort using a max-heap |
| [`HeapProblems.java`](HeapProblems.java) | Kth largest, K smallest, top K frequent, last stone weight, merge K sorted arrays, running median |

Run any file with:

```
java MinHeap.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Implement a max-heap from scratch.
2. Check if an array represents a min-heap.
3. Find the kth smallest element in an array.
4. Sort an array using `PriorityQueue`.
5. Find the sum of the K largest elements.

**Medium**
6. Find the K closest points to the origin.
7. Reorganise a string so no two adjacent characters are the same.
8. Task scheduler: minimum time to run tasks with a cooldown.
9. Merge K sorted linked lists.
10. Sliding window median.

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- A heap gives O(1) access to the min or max, and O(log n) insert and remove.
- It is a complete binary tree stored compactly in an array, using index arithmetic for parent and children.
- It is a partial ordering, not a sorted structure.
- Top K, merge K and running median problems are classic heap problems.
