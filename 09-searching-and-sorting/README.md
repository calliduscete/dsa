# Searching and Sorting

Searching (finding an item) and sorting (putting items in order) are the most studied problems in computer science. They are also linked: **sorted data can be searched far faster**, which is the reason sorting is worth doing.

## Searching

### Linear search

Check each element in turn until you find the target.

- Works on **any** array, sorted or not.
- **O(n)** time, **O(1)** space.

### Binary search

On a **sorted** array, look at the middle element and discard the half that cannot contain the target. Repeat.

```
Find 7 in [1, 3, 5, 7, 9, 11, 13]

low=0 high=6 mid=3 -> arr[3] = 7   found!

Find 11:
low=0 high=6 mid=3 -> 7  < 11, search the right half
low=4 high=6 mid=5 -> 11 found!
```

- **O(log n)** time: searching a million items takes about 20 steps.
- **O(1)** space (iterative).
- Requires **sorted** data.

Compute the middle as `low + (high - low) / 2`, not `(low + high) / 2`, to avoid integer overflow on huge arrays.

### Binary search beyond "find a value"

Binary search works whenever you can ask a yes/no question that flips from "no" to "yes" at some point:

- First or last occurrence of a duplicate value.
- Search in a rotated sorted array.
- "Binary search on the answer", such as an integer square root or the minimum capacity that works.

## Sorting

### Comparison of the main algorithms

| Algorithm | Best | Average | Worst | Space | Stable |
|---|---|---|---|---|---|
| Bubble sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Selection sort | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Merge sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes |
| Quick sort | O(n log n) | O(n log n) | O(n²) | O(log n) | No |
| Heap sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No |
| Counting sort | O(n + k) | O(n + k) | O(n + k) | O(k) | Yes |

(Heap sort lives in the heaps folder. `k` is the range of the values.)

**Stable** means equal elements keep their original relative order. This matters when sorting records by one field after another, such as sorting people by age after sorting by name.

### The simple sorts: bubble, selection, insertion

- **Bubble:** swap adjacent out-of-order pairs; the largest value bubbles to the end each pass.
- **Selection:** repeatedly pick the smallest remaining value and put it in place.
- **Insertion:** grow a sorted section on the left, inserting each new element where it belongs, like sorting playing cards in your hand.

They are all O(n²), so only use them for small inputs. Insertion sort is the exception worth remembering: it is very fast on **nearly sorted** data, which is why real libraries use it for small chunks.

### The fast sorts: merge and quick

Both use **divide and conquer**.

**Merge sort:** split the array in half, sort each half, then merge the two sorted halves.

```
[38, 27, 43, 3]
   /          \
[38, 27]    [43, 3]
 /    \      /    \
[38] [27]  [43]  [3]
 \    /      \    /
[27, 38]    [3, 43]
      \      /
  [3, 27, 38, 43]
```

- Always O(n log n), with a guaranteed worst case.
- Needs O(n) extra space.
- Stable, and good for linked lists and huge files.

**Quick sort:** pick a pivot, partition so smaller values go left and larger values go right (the pivot is now in its final place), then recurse on both sides.

- O(n log n) on average, in place, and usually the fastest in practice.
- Worst case O(n²) with consistently bad pivots, such as always picking the first element of a sorted array.
- A **random pivot** makes the worst case extremely unlikely.
- Not stable.

**Quickselect** uses the same partition step to find the kth smallest element in O(n) average time without sorting everything.

### Counting sort

Not a comparison sort. It counts how many times each value occurs and rebuilds the array from the counts. It runs in **O(n + k)**, which beats O(n log n) when the range `k` is small, but wastes memory if the values are spread over a huge range. It is a good example of why O(n log n) is only the limit for **comparison-based** sorting.

## Sorting in Java

```java
int[] nums = {5, 2, 9, 1};
Arrays.sort(nums);                          // primitives: dual-pivot quicksort

Integer[] boxed = {5, 2, 9, 1};
Arrays.sort(boxed, Collections.reverseOrder());   // objects: Timsort (stable)

List<String> words = new ArrayList<>(List.of("pear", "fig", "apple"));
words.sort(Comparator.comparing(String::length)); // sort by length
Collections.sort(words);                          // natural order

int index = Arrays.binarySearch(nums, 5);   // array must already be sorted
```

For objects, Java uses **Timsort**, a stable hybrid of merge sort and insertion sort. In real projects you will almost always use these built-in sorts, and the algorithms in this folder are for understanding.

## Choosing a sorting algorithm

| Situation | Good choice |
|---|---|
| General purpose | Quick sort or the library sort |
| Need guaranteed O(n log n) | Merge sort or heap sort |
| Need stability | Merge sort (or insertion sort for small data) |
| Nearly sorted data | Insertion sort |
| Small integer range | Counting sort |
| Tight on memory | Heap sort or quick sort (in place) |
| Only the top K or kth value | Heap or quickselect, not a full sort |

## Common mistakes

- Using binary search on **unsorted** data.
- Off-by-one errors in the `low` and `high` boundaries and the `<=` versus `<` loop condition.
- Infinite loops from not moving `low` or `high` past `mid`.
- Overflow in `(low + high) / 2`.
- Writing a comparator with `a - b`, which can overflow. Use `Integer.compare(a, b)`.
- Forgetting that `Arrays.sort` on `int[]` cannot take a custom comparator. Use `Integer[]` for that.

## Files in this folder

| File | What it covers |
|---|---|
| [`Searching.java`](Searching.java) | Linear and binary search (iterative and recursive), first and last occurrence, rotated array, integer square root |
| [`BasicSorts.java`](BasicSorts.java) | Bubble, selection and insertion sort |
| [`MergeSort.java`](MergeSort.java) | Merge sort with a shared temporary array |
| [`QuickSort.java`](QuickSort.java) | Quick sort with a random pivot, and quickselect |
| [`CountingSort.java`](CountingSort.java) | Counting sort for small integer ranges |

Run any file with:

```
java MergeSort.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Check if an array is sorted.
2. Find the missing number in a sorted array of 1 to n.
3. Merge two sorted arrays.
4. Sort an array of 0s, 1s and 2s (Dutch national flag).
5. Find the peak element in an array.

**Medium**
6. Find the first and last position of a target in a sorted array.
7. Find the minimum in a rotated sorted array.
8. Sort a list of intervals and merge the overlapping ones.
9. Find the kth largest element (quickselect or heap).
10. Count inversions in an array (merge sort variant).

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- Linear search is O(n) and works anywhere. Binary search is O(log n) but needs sorted data.
- The simple sorts are O(n²), and the good general-purpose sorts are O(n log n).
- Merge sort is guaranteed and stable. Quick sort is fast in practice but has a rare O(n²) worst case.
- Counting sort beats the O(n log n) limit by not comparing elements.
- Use the built-in library sorts in real code.
