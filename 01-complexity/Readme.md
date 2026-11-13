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

```python
def get_first(arr):
    # O(1): one step, no matter how big the array is
    return arr[0]


def find_max(arr):
    # O(n): looks at every element once
    best = arr[0]
    for x in arr:
        if x > best:
            best = x
    return best


def has_duplicate(arr):
    # O(n^2): compares every pair of elements
    for i in range(len(arr)):
        for j in range(i + 1, len(arr)):
            if arr[i] == arr[j]:
                return True
    return False


def binary_search(arr, target):
    # O(log n): halves the search range each step (arr must be sorted)
    low, high = 0, len(arr) - 1
    while low <= high:
        mid = (low + high) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            low = mid + 1
        else:
            high = mid - 1
    return -1
```

## Space complexity examples

```python
def sum_list(arr):
    # O(1) extra space: only one variable is used
    total = 0
    for x in arr:
        total += x
    return total


def double_list(arr):
    # O(n) extra space: builds a new list the same size as the input
    return [x * 2 for x in arr]
```

## Practice questions

What is the time complexity of each snippet?

**1.**
```python
for i in range(n):
    print(i)
```

**2.**
```python
for i in range(n):
    for j in range(n):
        print(i, j)
```

**3.**
```python
i = 1
while i < n:
    i *= 2
```

**4.**
```python
for i in range(n):
    print(i)
for j in range(n):
    print(j)
```

**5.**
```python
def fib(n):
    if n <= 1:
        return n
    return fib(n - 1) + fib(n - 2)
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