# Hash Tables

A hash table stores **key-value pairs** and lets you look up, insert and delete by key in **O(1) on average**. It is one of the most useful data structures: almost every language has one built in (`HashMap` in Java, `dict` in Python, `Map` in JavaScript).

## How it works

1. The table is an array of **buckets**.
2. A **hash function** turns a key into a number, and that number (modulo the array size) is the bucket index.
3. The value is stored at that index, so finding it later means computing the same index again, with no searching.

```
key "apple" --hash--> 1234567 --% 8--> index 7

index:   0     1     2     3     4     5     6     7
       [   ] [   ] [   ] [   ] [   ] [   ] [   ] [apple:3]
```

In Java, `key.hashCode()` produces the number, and `(hash & 0x7fffffff) % capacity` turns it into a valid index.

## Collisions

Two different keys can land on the same index. This is called a **collision**, and every hash table needs a strategy for it.

| Strategy | How it works | Pros | Cons |
|---|---|---|---|
| **Separate chaining** | Each bucket holds a linked list of entries | Simple, deletion is easy | Extra memory for list nodes |
| **Open addressing (linear probing)** | On a collision, try the next slot, then the next, wrapping around | Cache-friendly, no extra nodes | Clustering, deletion needs tombstones |

```
Chaining:                      Linear probing:
[0] -> null                    [0] empty
[1] -> (A) -> (D) -> null      [1] A
[2] -> (B) -> null             [2] D   <- D collided with A, moved to the next slot
[3] -> null                    [3] B
```

## Load factor and resizing

The **load factor** is `entries / buckets`. As it grows, collisions become more common and operations slow down. When it passes a threshold (0.75 for Java's `HashMap`), the table **doubles in size** and re-inserts every entry. That is O(n), but it happens rarely, so `put` stays O(1) amortised.

## Complexity

| Operation | Average | Worst case |
|---|---|---|
| Insert (`put`) | O(1) | O(n) |
| Lookup (`get`) | O(1) | O(n) |
| Delete (`remove`) | O(1) | O(n) |
| Iterate all entries | O(n) | O(n) |

The worst case happens when many keys collide into the same bucket. A good hash function and resizing keep this very unlikely in practice.

## Using them in Java

```java
// HashMap: key -> value
Map<String, Integer> ages = new HashMap<>();
ages.put("Ana", 21);
ages.put("Ben", 25);
ages.get("Ana");                      // 21
ages.getOrDefault("Zoe", 0);          // 0 (missing key)
ages.containsKey("Ben");              // true
ages.remove("Ben");

// Count occurrences
Map<Character, Integer> freq = new HashMap<>();
freq.put('a', freq.getOrDefault('a', 0) + 1);

// HashSet: unique values only
Set<Integer> seen = new HashSet<>();
seen.add(5);                          // true  (new)
seen.add(5);                          // false (already there)
seen.contains(5);                     // true
```

`HashMap` and `HashSet` do not keep a guaranteed order. Use `LinkedHashMap` to keep insertion order or `TreeMap` to keep keys sorted (at O(log n) per operation).

## Key techniques

- **Frequency counting:** count how many times each item appears.
- **Seen set:** remember what you've already visited to detect duplicates.
- **Complement lookup:** for each element, check if the value you need already exists (Two Sum).
- **Grouping by a key:** map a computed key to a list of items (group anagrams).
- **Prefix sum + hash map:** count subarrays with a target sum in one pass.

## Common mistakes

- Using **mutable objects as keys** and then changing them, so they can never be found again.
- Overriding `equals()` without overriding `hashCode()` (or vice versa) for your own key classes. Equal objects must have equal hash codes.
- Forgetting that `get` returns `null` for a missing key, which causes a `NullPointerException` if you unbox it into an `int`.
- Assuming iteration order is stable.

## Files in this folder

| File | What it covers |
|---|---|
| [`HashTableChaining.java`](HashTableChaining.java) | Hash table with separate chaining and automatic resizing |
| [`HashTableLinearProbing.java`](HashTableLinearProbing.java) | Open addressing with linear probing and tombstone deletion |
| [`HashProblems.java`](HashProblems.java) | Duplicates, frequency count, Two Sum, group anagrams, longest consecutive sequence, subarray sum equals k |

Run any file with:

```
java HashTableChaining.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Check if two strings are anagrams using a hash map.
2. Find the first repeating element in an array.
3. Find the intersection of two arrays.
4. Check if an array contains two numbers that add up to a target.
5. Count the distinct elements in an array.

**Medium**
6. Find the longest substring without repeating characters.
7. Find the top `k` most frequent elements.
8. Check if two strings are isomorphic.
9. Find all pairs in an array that sum to a target.
10. Implement an LRU cache (hash map + doubly linked list).

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- A hash table gives O(1) average lookup, insert and delete by turning keys into array indices.
- Collisions are unavoidable, so every design needs chaining or probing.
- Keeping the load factor low (by resizing) is what keeps operations fast.
- If a problem asks "have I seen this before?" or "how many of each?", reach for a hash map or set.
