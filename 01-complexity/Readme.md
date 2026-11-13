# Time and Space Complexity

Before learning any data structure or algorithm, you need a way to answer one question: **how good is this solution?** Complexity analysis gives us that answer.

## What is complexity?

- **Time complexity** measures how the running time of an algorithm grows as the input size (`n`) grows.
- **Space complexity** measures how the extra memory an algorithm uses grows as the input size grows.

We don't measure in seconds, because that depends on your computer. Instead, we count how the number of steps grows as `n` gets larger.

## Big O notation

Big O describes the **upper bound** of growth. It focuses on what happens when `n` is very large and ignores constants and small terms.

- `O(2n + 5)` becomes `O(n)`
- `O(n² + n)` becomes `O(n²)`

## Common complexities

From fastest to slowest:

| Notation | Name | Example |
|---|---|---|
| O(1) | Constant | Accessing an array element by index |
| O(log n) | Logarithmic | Binary search |
| O(n) | Linear | Linear search, finding the max |
| O(n log n) | Linearithmic | Merge sort |
| O(n²) | Quadratic | Bubble sort, checking all pairs |
| O(2ⁿ) | Exponential | Naive recursive Fibonacci |
| O(n!) | Factorial | Generating all permutations |

## Best, average and worst case

The same algorithm can behave differently depending on the input. For example, linear search:

- **Best case:** the item is the first element, so O(1).
- **Worst case:** the item is last or missing, so O(n).
- **Average case:** the item is somewhere in the middle, so O(n).

Big O usually refers to the **worst case**, because it gives a guarantee.

You may also see two related terms:

- **Big Omega (Ω):** lower bound (best case).
- **Big Theta (Θ):** tight bound (when upper and lower bounds match).

## How to analyse code

1. **A single loop over `n` items** is O(n).
2. **Nested loops multiply.** Two nested loops over `n` is O(n²).
3. **Sequential steps add**, and you keep the biggest term. An O(n) loop followed by an O(n²) loop is O(n²).
4. **Drop constants and lower-order terms.**
5. **Halving the input each step** (like binary search) suggests O(log n).
6. **Recursion:** count how many calls are made and how much work each call does.

## Examples

```java
public class TimeComplexityExamples {

    // O(1): one step, no matter how big the array is
    static int getFirst(int[] arr) {
        return arr[0];
    }

    // O(n): looks at every element once
    static int findMax(int[] arr) {
        int best = arr[0];
        for (int x : arr) {
            if (x > best) {
                best = x;
            }
        }
        return best;
    }

    // O(n^2): compares every pair of elements
    static boolean hasDuplicate(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // O(log n): halves the search range each step (arr must be sorted)
    static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {2, 5, 8, 12, 16, 23};
        System.out.println(getFirst(nums));          // 2
        System.out.println(findMax(nums));           // 23
        System.out.println(hasDuplicate(nums));      // false
        System.out.println(binarySearch(nums, 12));  // 3
    }
}
```

## Space complexity examples

```java
public class SpaceComplexityExamples {

    // O(1) extra space: only one variable is used
    static int sumArray(int[] arr) {
        int total = 0;
        for (int x : arr) {
            total += x;
        }
        return total;
    }

    // O(n) extra space: builds a new array the same size as the input
    static int[] doubleArray(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * 2;
        }
        return result;
    }
}
```

## Practice questions

What is the time complexity of each snippet?

**1.**
```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

**2.**
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + " " + j);
    }
}
```

**3.**
```java
int i = 1;
while (i < n) {
    i *= 2;
}
```

**4.**
```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
for (int j = 0; j < n; j++) {
    System.out.println(j);
}
```

**5.**
```java
static int fib(int n) {
    if (n <= 1) {
        return n;
    }
    return fib(n - 1) + fib(n - 2);
}
```

<details>
<summary>Answers</summary>

1. **O(n):** one loop over `n` items.
2. **O(n²):** nested loops.
3. **O(log n):** `i` doubles each time.
4. **O(n):** two sequential loops, O(n + n) = O(2n), which simplifies to O(n).
5. **O(2ⁿ):** each call makes two more calls.

</details>

## Quick cheat sheet

| Operation | Complexity |
|---|---|
| Array access by index | O(1) |
| Array search (unsorted) | O(n) |
| Binary search (sorted) | O(log n) |
| Hash table lookup (average) | O(1) |
| Merge sort | O(n log n) |
| Quick sort (average) | O(n log n) |
| Bubble / selection / insertion sort | O(n²) |

## Key takeaways

- Complexity tells us how an algorithm **scales**, not how fast it runs on one machine.
- Always think about the **worst case** unless told otherwise.
- Aim for the lowest complexity that is still simple and correct.
- Time and space often trade off: using more memory can make an algorithm faster.