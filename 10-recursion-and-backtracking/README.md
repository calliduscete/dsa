# Recursion and Backtracking

**Recursion** is when a function solves a problem by calling itself on a smaller version of the same problem. **Backtracking** is recursion used to explore every possible choice, undoing a choice whenever it leads to a dead end. Trees, graphs, divide and conquer, and dynamic programming all build on these ideas.

## Recursion

Every recursive function needs two things:

1. **Base case:** the simplest input, answered directly, which stops the recursion.
2. **Recursive case:** reduce the problem to a smaller one and call the function again.

```java
static long factorial(int n) {
    if (n <= 1) {
        return 1;                     // base case
    }
    return n * factorial(n - 1);      // recursive case
}
```

Tracing `factorial(4)`:

```
factorial(4)
 = 4 * factorial(3)
 = 4 * (3 * factorial(2))
 = 4 * (3 * (2 * factorial(1)))
 = 4 * (3 * (2 * 1))
 = 24
```

### The call stack

Each call waits for the call below it to finish, and Java keeps these waiting calls on the **call stack**. Recursion that goes `d` levels deep uses **O(d)** stack space. If there is no base case, or the depth is huge, you get a `StackOverflowError`.

### Recursion tree and complexity

Draw the calls as a tree to analyse them:

- **Factorial:** one call per level, so O(n) time.
- **Naive Fibonacci:** each call makes two more, so O(2ⁿ) time.

```
              fib(4)
             /      \
        fib(3)      fib(2)
        /    \      /    \
    fib(2) fib(1) fib(1) fib(0)
    /    \
 fib(1) fib(0)
```

Notice that `fib(2)` is computed twice. The waste grows exponentially. **Memoisation** (saving each result in a map or array) removes the repeats and makes Fibonacci O(n). That idea becomes **dynamic programming** in the last folder.

### Recursion vs iteration

| | Recursion | Iteration (loops) |
|---|---|---|
| Readability | Often shorter for trees, graphs, divide and conquer | Often simpler for linear problems |
| Space | Uses the call stack | Usually O(1) |
| Risk | Stack overflow if too deep | None |

Anything recursive can be rewritten with a loop and an explicit stack. Use recursion when the problem has a naturally nested structure.

## Backtracking

Backtracking builds a solution one choice at a time. At each step it tries every option, goes deeper, and then **undoes** the choice before trying the next one. It explores a tree of possibilities, but **prunes** branches that cannot possibly work.

### The template

```java
void backtrack(state, choices) {
    if (state is a complete solution) {
        record it;
        return;
    }
    for (each choice available) {
        if (choice is valid) {
            make the choice;            // choose
            backtrack(new state);       // explore
            undo the choice;            // un-choose (the "backtrack")
        }
    }
}
```

Almost every backtracking problem fits this shape. The skill is deciding what the **state** is, what the **choices** are, and when a choice is **invalid** (that last part is your pruning).

### Subsets, permutations and combinations

| Problem | Number of results | Key idea |
|---|---|---|
| **Subsets** of n items | 2ⁿ | At each item, include it or skip it. Record every path. |
| **Permutations** of n items | n! | Try every unused item in every position. Track a `used` array. |
| **Combinations** (choose k of n) | C(n, k) | Only move forward (`start = i + 1`) so order doesn't matter. |

For `[1, 2, 3]`:

- Subsets: `[] [1] [1,2] [1,2,3] [1,3] [2] [2,3] [3]`
- Permutations: `[1,2,3] [1,3,2] [2,1,3] [2,3,1] [3,1,2] [3,2,1]`

### How the `start` index controls repetition

- **Permutations** loop from `0` each time, because order matters.
- **Combinations and subsets** loop from `start`, so each pick only looks forward and never repeats a set in a different order.
- **Allowing reuse** of an element (combination sum) passes `i` instead of `i + 1` to the recursive call.

### Pruning

Pruning is what makes backtracking practical. Examples in this folder:

- **Combination sum:** sort the candidates, then `break` once a candidate exceeds the remaining target.
- **Generate parentheses:** only add `)` when there is an unmatched `(`.
- **N-Queens:** skip a square when it is attacked, instead of placing the queen and checking later.
- **Sudoku:** only try digits that break no row, column or box rule.

### Complexity

Backtracking typically has **exponential or factorial** time, because the number of solutions grows that fast. Pruning helps a lot in practice but rarely changes the worst case. Space is usually O(depth) for the recursion plus the output.

## Backtracking vs other approaches

| Approach | Use when |
|---|---|
| **Backtracking** | You need **all** solutions, or any solution under constraints |
| **Dynamic programming** | The same subproblems repeat, and you need a count or an optimal value |
| **Greedy** | A locally best choice is provably globally best |
| **Brute force** | The input is tiny and clarity matters most |

## Common mistakes

- Missing or wrong **base case**, causing infinite recursion.
- Not **copying** the current path before adding it to the results. Write `result.add(new ArrayList<>(current))`, not `result.add(current)`, or every result ends up empty.
- Forgetting to **undo** a choice (remove the item, reset the `used` flag, restore the grid cell).
- Looping from the wrong start index and producing duplicates or missing answers.
- Recursing too deep for the Java stack (roughly 10,000 to 20,000 frames by default).

## Files in this folder

| File | What it covers |
|---|---|
| [`RecursionBasics.java`](RecursionBasics.java) | Factorial, sum of digits, reverse, palindrome, GCD, naive vs memoised Fibonacci, fast power, Tower of Hanoi |
| [`BacktrackingProblems.java`](BacktrackingProblems.java) | Subsets, permutations, combinations, combination sum, generate parentheses, word search |
| [`NQueens.java`](NQueens.java) | N-Queens with column and diagonal tracking |
| [`SudokuSolver.java`](SudokuSolver.java) | A complete Sudoku solver |

Run any file with:

```
java NQueens.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Print the numbers from 1 to n using recursion.
2. Find the nth Fibonacci number with memoisation.
3. Check if a number is a power of two recursively.
4. Compute the sum of an array recursively.
5. Print all binary strings of length n.

**Medium**
6. Subsets with duplicates in the input (skip repeated values).
7. Permutations with duplicates.
8. Letter combinations of a phone number.
9. Palindrome partitioning: split a string into all-palindrome pieces.
10. Rat in a maze: find all paths from top-left to bottom-right.

Try each one yourself first, using the template above.

## Key takeaways

- Every recursive function needs a base case and a smaller recursive call.
- Recursion uses the call stack, and repeated subproblems (like naive Fibonacci) can blow up exponentially.
- Backtracking is **choose, explore, un-choose**, applied to a tree of possibilities.
- Pruning invalid branches early is what makes backtracking fast enough to use.
- When subproblems repeat, switch to memoisation or dynamic programming.
