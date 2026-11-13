# Trees

A tree is a hierarchical structure made of **nodes** connected by **edges**. Unlike arrays, linked lists, stacks and queues (which are linear), trees branch. They model file systems, HTML pages, organisation charts, and they power fast searching in databases.

## Terminology

```
          1          <- root (the top node, has no parent)
         / \
        2   3        <- 2 and 3 are siblings; both are children of 1
       / \   \
      4   5   6      <- leaves (nodes with no children)
```

| Term | Meaning |
|---|---|
| **Root** | The top node |
| **Parent / child** | A node directly above / below another |
| **Leaf** | A node with no children |
| **Subtree** | A node together with all of its descendants |
| **Depth of a node** | Number of edges from the root to that node |
| **Height of a tree** | Number of edges on the longest root-to-leaf path |
| **Level** | All nodes at the same depth |

## Binary tree

A **binary tree** is a tree where each node has **at most two children**, called left and right.

```java
class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
    }
}
```

Special kinds worth knowing:

- **Full:** every node has 0 or 2 children.
- **Complete:** every level is full except possibly the last, which fills from the left (heaps use this).
- **Perfect:** all levels are completely full.
- **Balanced:** the heights of the left and right subtrees differ by at most 1 at every node.

## Tree traversals

Visiting every node can be done in four standard orders. For the tree above:

| Traversal | Order | Result | Typical use |
|---|---|---|---|
| **Preorder** | Root, left, right | 1 2 4 5 3 6 | Copying a tree, prefix expressions |
| **Inorder** | Left, root, right | 4 2 5 1 3 6 | Sorted output from a BST |
| **Postorder** | Left, right, root | 4 5 2 6 3 1 | Deleting a tree, postfix expressions |
| **Level order** | Level by level | 1 2 3 4 5 6 | Shortest path, printing by level |

The first three are **depth-first** (usually recursive, or iterative with a stack). Level order is **breadth-first** and uses a queue.

All four visit each node once, so they take **O(n)** time.

## Binary Search Tree (BST)

A BST is a binary tree with one extra rule: for every node, **everything in the left subtree is smaller and everything in the right subtree is larger**.

```
        50
       /  \
     30    70
    / \   / \
  20  40 60  80
```

To search for 60: start at 50, go right (60 > 50), reach 70, go left (60 < 70), found. Each step discards half of the remaining tree.

| Operation | Balanced tree | Worst case (skewed) |
|---|---|---|
| Search | O(log n) | O(n) |
| Insert | O(log n) | O(n) |
| Delete | O(log n) | O(n) |
| Find min / max | O(log n) | O(n) |
| Inorder traversal | O(n) | O(n) |

The worst case happens when you insert already-sorted data. The tree degenerates into a linked list with height n. **Self-balancing trees** (AVL, Red-Black) fix this by rotating nodes to keep the height at O(log n). Java's `TreeMap` and `TreeSet` are Red-Black trees.

### Deleting from a BST

1. **Leaf:** remove it.
2. **One child:** replace the node with its child.
3. **Two children:** replace its value with the **inorder successor** (smallest value in the right subtree), then delete that successor.

## Key techniques

- **Recursion on subtrees:** solve for the left and right subtrees, then combine the results. Most tree problems work this way.
- **Base case first:** `if (node == null) return ...` handles empty trees and the bottom of the tree.
- **Passing information down:** for example, an allowed value range when validating a BST.
- **Returning information up:** for example, a subtree's height when computing the diameter.
- **BFS with a queue and a level counter:** process one level at a time.

## Common mistakes

- Forgetting the `null` base case, which causes a `NullPointerException`.
- Validating a BST by only comparing a node with its direct children. You must check the whole allowed range.
- Mixing up **height in edges** and **height in nodes**. Check which one the problem wants.
- Assuming a BST is balanced, so its operations are always O(log n).

## Files in this folder

| File | What it covers |
|---|---|
| [`BinaryTree.java`](BinaryTree.java) | Preorder, inorder, postorder, level order, iterative inorder, height, size, leaf count |
| [`BinarySearchTree.java`](BinarySearchTree.java) | Insert, search, min, max, delete (all three cases), sorted output, a skewed-tree demo |
| [`TreeProblems.java`](TreeProblems.java) | Max depth, invert, same tree, symmetric, path sum, validate BST, diameter, level order by level, lowest common ancestor |

Run any file with:

```
java BinaryTree.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Find the minimum value in a binary tree (not a BST).
2. Count the number of leaf nodes.
3. Check if a value exists in a BST.
4. Find the sum of all node values.
5. Print the left view of a binary tree.

**Medium**
6. Find the kth smallest element in a BST (hint: inorder).
7. Build a binary tree from its preorder and inorder traversals.
8. Check if a binary tree is height-balanced.
9. Zigzag level order traversal.
10. Serialise and deserialise a binary tree.

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- Trees are hierarchical, and most problems are solved by recursing on the left and right subtrees.
- The four traversals each have their own use. Inorder on a BST gives sorted output.
- A BST gives O(log n) search, insert and delete only if it stays balanced.
- Know your base case, and know whether you pass information down or return it up.
