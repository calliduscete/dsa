# Linked Lists

A linked list is a chain of **nodes**. Each node stores a value and a reference (pointer) to the next node. Unlike an array, the nodes are scattered in memory and connected only by these references.

```
head
 |
 v
[10 | * ] -> [20 | * ] -> [30 | * ] -> null
```

## Why use a linked list?

- **Fast insertion and deletion at the front:** just change a pointer, with no shifting of elements.
- **Dynamic size:** it grows and shrinks one node at a time, with no resizing or copying.
- **The cost:** there is no index access. To reach the 5th element you must walk through the first four.

## Types of linked lists

| Type | Description |
|---|---|
| **Singly linked** | Each node points to the next node only |
| **Doubly linked** | Each node points to both the next and previous node |
| **Circular** | The last node points back to the first instead of `null` |

## Linked list vs array

| Operation | Array | Singly linked list |
|---|---|---|
| Access by index | O(1) | O(n) |
| Search | O(n) | O(n) |
| Insert / delete at the front | O(n) | O(1) |
| Insert / delete at the end | O(1) (if space) | O(n), or O(1) with a tail pointer and doubly linked |
| Insert / delete in the middle | O(n) | O(1) once you have the node, O(n) to find it |
| Extra memory per element | None | One pointer (two in a doubly linked list) |

## The node

```java
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}
```

## Key techniques

- **Dummy (sentinel) node:** a fake node placed before the head so you don't need special cases for the first node. Used in merging and deleting.
- **Slow and fast pointers:** one pointer moves one step, the other moves two. Finds the middle of a list and detects cycles.
- **Two pointers with a gap:** move one pointer `n` steps ahead, then move both together. Finds the nth node from the end in one pass.
- **Reversing pointers:** walk the list and flip each `next` pointer, keeping track of the previous node.

## Common mistakes

- Forgetting to handle an **empty list** or a **single-node list**.
- Losing the rest of the list by overwriting `next` before saving it.
- Forgetting to update `head` (or `tail`) when inserting or deleting at the ends.
- Dereferencing `null` (`current.next.next` when `current.next` is `null`).

Tip: draw the nodes and arrows on paper before writing the pointer code.

## Files in this folder

| File | What it covers |
|---|---|
| [`SinglyLinkedList.java`](SinglyLinkedList.java) | Full implementation: add first/last/at index, remove, search, get, print |
| [`DoublyLinkedList.java`](DoublyLinkedList.java) | Head and tail pointers, O(1) add and remove at both ends, backward traversal |
| [`LinkedListProblems.java`](LinkedListProblems.java) | Reverse, find middle, detect cycle, merge sorted lists, remove nth from end |

Run any file with:

```
java SinglyLinkedList.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Find the length of a linked list.
2. Delete the last node of a list.
3. Check if a linked list is sorted.
4. Remove duplicates from a sorted linked list.
5. Find the nth node from the beginning.

**Medium**
6. Check if a linked list is a palindrome.
7. Find the node where a cycle begins.
8. Add two numbers stored as linked lists (digits in reverse order).
9. Find the intersection of two linked lists.
10. Reorder a list: `L0 -> Ln -> L1 -> Ln-1 -> ...`.

Try each one yourself first, using the techniques above.

## Key takeaways

- Linked lists trade fast access for fast insertion and deletion.
- Most problems are solved with pointer techniques: dummy node, slow and fast pointers, and reversing links.
- Always check the empty and single-node cases.
- Java's built-in `LinkedList` is a doubly linked list, but it's still worth building your own to understand how pointers work.
