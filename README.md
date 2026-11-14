# DSA in Java

A beginner-friendly collection of **data structures and algorithms** implemented in Java. Every topic has a short lesson, clean code with time and space complexity noted in the comments, runnable examples, and practice problems.

All code was written to be read, so it favours clarity over cleverness.

## Who is this for?

- Students learning data structures and algorithms for the first time
- Anyone revising for exams or coding interviews
- Anyone who wants plain Java implementations they can run and modify

## Topics

Work through the folders in order. Each one builds on the ones before it.

| # | Topic | What's inside |
|---|---|---|
| 01 | [Complexity](01-complexity) | Big O notation, time and space analysis, how to read code for its complexity |
| 02 | [Arrays and Strings](02-arrays-and-strings) | Array operations, two pointers, sliding window, prefix sums, string basics |
| 03 | [Linked Lists](03-linked-lists) | Singly and doubly linked lists, reversal, cycle detection, merging |
| 04 | [Stacks and Queues](04-stacks-and-queues) | Array and linked implementations, circular queue, balanced brackets, monotonic stack |
| 05 | [Hash Tables](05-hash-tables) | Chaining and linear probing built from scratch, frequency counting, Two Sum |
| 06 | [Trees](06-trees) | Binary trees, all four traversals, binary search trees, common tree problems |
| 07 | [Heaps](07-heaps) | Min-heap from scratch, heap sort, priority queues, top K and running median |
| 08 | [Graphs](08-graphs) | Adjacency lists, BFS and DFS, topological sort, cycle detection, Dijkstra |
| 09 | [Searching and Sorting](09-searching-and-sorting) | Binary search and its variations, bubble to quick sort, counting sort |
| 10 | [Recursion and Backtracking](10-recursion-and-backtracking) | Recursion basics, subsets, permutations, N-Queens, Sudoku solver |
| 11 | [Greedy](11-greedy) | Interval scheduling, fractional knapsack, Huffman coding, when greedy fails |
| 12 | [Dynamic Programming](12-dynamic-programming) | Memoisation and tabulation, knapsack, coin change, LCS, edit distance, grid DP |

## Repository structure

```
.
├── README.md
├── LICENSE
├── 01-complexity/
│   └── README.md
├── 02-arrays-and-strings/
│   ├── README.md
│   ├── ArrayOperations.java
│   └── ...
├── 03-linked-lists/
│   └── ...
└── 12-dynamic-programming/
    └── ...
```

Each topic folder contains:

- **`README.md`**: the lesson, with explanations, diagrams, complexity tables, common mistakes and practice problems
- **`.java` files**: implementations and classic problems, each with a `main` method showing example output

## How to run the code

1. Install the **JDK** (Java 11 or later).
2. Clone the repository:
   ```
   git clone https://github.com/calliduscete/dsa.git
   cd dsa
   ```
3. Run any file directly:
   ```
   cd 06-trees
   java BinarySearchTree.java
   ```

Java 11 and later can run a single source file without a separate compile step. On older versions, compile first with `javac BinarySearchTree.java` and then run `java BinarySearchTree`.

## How each file is organised

- A comment at the top explaining the idea
- A comment on each method with its **time and space complexity**
- A `main` method that demonstrates the code and prints the results

## Suggested way to learn

1. Read the folder's `README.md` first.
2. Read the code and run it.
3. Change the inputs and predict the output before you run it.
4. Try the **practice problems** at the end of each README without looking at the examples.
5. Move on only when the topic feels comfortable.

A good rhythm is one topic, then 5 to 10 easy problems on it, before starting the next one.

## Quick complexity reference

| Structure | Access | Search | Insert | Delete |
|---|---|---|---|---|
| Array | O(1) | O(n) | O(n) | O(n) |
| Linked list | O(n) | O(n) | O(1) at the front | O(1) at the front |
| Stack / Queue | O(n) | O(n) | O(1) | O(1) |
| Hash table (average) | n/a | O(1) | O(1) | O(1) |
| Binary search tree (balanced) | n/a | O(log n) | O(log n) | O(log n) |
| Heap | O(1) min or max | O(n) | O(log n) | O(log n) |

| Algorithm | Time (average) | Space |
|---|---|---|
| Binary search | O(log n) | O(1) |
| Merge sort | O(n log n) | O(n) |
| Quick sort | O(n log n) | O(log n) |
| BFS / DFS | O(V + E) | O(V) |
| Dijkstra | O((V + E) log V) | O(V) |

See [Complexity](01-complexity) for how to work these out yourself.

## Contributing

Contributions are welcome, whether that's a fix, a clearer explanation, or a new problem.

1. Fork the repository
2. Create a branch (`git checkout -b add-trie`)
3. Make your changes and make sure the code runs
4. Commit with a clear message (`Add trie with insert and search`)
5. Open a pull request

Please keep code beginner-friendly, with comments and complexity notes, and add a `main` method that shows how it works.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

If this repository helped you, consider giving it a star. It helps other learners find it.