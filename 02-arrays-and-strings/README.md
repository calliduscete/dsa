# Arrays and Strings

Arrays and strings are the most common data structures in programming, and the foundation for almost everything else in this repo.

## Arrays

An array stores a fixed number of elements of the same type in **contiguous memory**. Because every element sits right next to the previous one, Java can jump straight to any index, which is why access is O(1).

```java
int[] nums = new int[5];            // five zeros
int[] primes = {2, 3, 5, 7, 11};    // with values
System.out.println(primes[2]);      // 5
System.out.println(primes.length);  // 5
```

### Array operations and complexity

| Operation | Time | Why |
|---|---|---|
| Access by index | O(1) | Direct jump in memory |
| Search (unsorted) | O(n) | May check every element |
| Search (sorted, binary search) | O(log n) | Halves the range each step |
| Insert / delete at the end (if space) | O(1) | No shifting |
| Insert / delete at the start or middle | O(n) | Elements must shift |

Java arrays have a **fixed size**. To grow one you create a bigger array and copy the elements over. `ArrayList` does this for you automatically.

## Strings

A `String` in Java is an **immutable** sequence of characters. Every "change" creates a brand new String, so repeated concatenation in a loop is slow (O(n²) overall).

```java
String s = "hello";
s.charAt(1);         // 'e'
s.length();          // 5
s.substring(1, 4);   // "ell"
s.indexOf('l');      // 2
s.toCharArray();     // {'h','e','l','l','o'}
```

Use **`StringBuilder`** when building or modifying text:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 5; i++) {
    sb.append(i);
}
String result = sb.toString();   // "01234"
```

### String operations and complexity

| Operation | Time |
|---|---|
| `charAt(i)` | O(1) |
| `length()` | O(1) |
| `substring(a, b)` | O(b - a) |
| `indexOf`, `contains` | O(n) in the worst case |
| Concatenate two strings | O(n + m) |
| Compare two strings | O(n) |

## Key techniques

These patterns solve a large share of array and string problems:

- **Two pointers:** one pointer at each end (or both moving the same way) to avoid nested loops.
- **Sliding window:** keep a running sum or count over a window that moves across the data.
- **Prefix sums:** precompute running totals so any range sum takes O(1).
- **Hash map / frequency array:** remember what you've seen to avoid re-scanning.
- **In-place modification:** overwrite the array instead of building a new one to save space.

## Files in this folder

| File | What it covers |
|---|---|
| [`ArrayOperations.java`](ArrayOperations.java) | Traverse, search, find max, insert, delete, reverse, rotate |
| [`ArrayProblems.java`](ArrayProblems.java) | Two Sum, move zeros, remove duplicates, prefix sums, sliding window |
| [`StringOperations.java`](StringOperations.java) | Reverse, palindrome, vowels, anagram, first unique character, reverse words |

Run any file with:

```
java ArrayOperations.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Find the second largest element in an array.
2. Check if an array is sorted.
3. Merge two sorted arrays into one sorted array.
4. Check if a string contains only unique characters.
5. Count the occurrences of each character in a string.

**Medium**
6. Rotate an array left by `k` positions.
7. Find the longest substring without repeating characters (sliding window).
8. Find the maximum subarray sum (Kadane's algorithm).
9. Check if one string is a rotation of another.
10. Group a list of words into anagram groups.

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- Arrays give O(1) access but O(n) insertion and deletion in the middle.
- Strings are immutable in Java, so use `StringBuilder` for repeated changes.
- Two pointers, sliding window, prefix sums and hash maps turn many O(n²) solutions into O(n).
