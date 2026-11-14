# Dynamic Programming

Dynamic programming (DP) is a way to solve problems that would take exponential time by **remembering answers you have already computed**. It is often the hardest topic in a DSA course, but it follows a repeatable method, and once you see the pattern many problems become routine.

## The core idea

DP applies when a problem has two properties:

1. **Overlapping subproblems:** the same smaller problem is solved again and again.
2. **Optimal substructure:** the best answer to the whole problem is built from the best answers to its subproblems.

Remember naive Fibonacci from the recursion folder:

```
              fib(5)
             /      \
        fib(4)      fib(3)        <- fib(3) is computed twice
        /    \      /    \
    fib(3) fib(2) fib(2) fib(1)   <- fib(2) is computed three times
```

The tree has about 2ⁿ calls, but there are only **n** different subproblems. Solve each one once, store it, and the cost drops from O(2ⁿ) to **O(n)**.

## Two styles

| | Top-down (memoisation) | Bottom-up (tabulation) |
|---|---|---|
| How | Write the recursion, then cache results | Fill a table from the smallest case upward |
| Order | Computes only the subproblems it needs | Computes every subproblem |
| Code | Close to the recursive definition | Loops, no recursion |
| Risk | Deep recursion can overflow the stack | None |
| Space tricks | Hard | Often easy to reduce (keep only the last row or two values) |

Both have the **same time complexity**. Start with whichever you find easier to get right.

## A method for any DP problem

1. **Define the state.** What does `dp[i]` (or `dp[i][j]`) mean? Say it in a sentence, for example "the fewest coins needed to make amount `i`".
2. **Write the recurrence.** How does a state depend on smaller states?
3. **Set the base cases.** The smallest states, answered directly.
4. **Choose the order.** Make sure every state is computed after the states it depends on.
5. **Find the answer.** Often `dp[n]` or the maximum over the table.
6. **Optimise space** if possible, when a row only needs the row before it.

Most mistakes come from step 1: an unclear definition of the state.

## Common DP patterns

| Pattern | State | Examples in this folder |
|---|---|---|
| **1D linear** | `dp[i]` = best answer up to `i` | Climbing stairs, house robber, max subarray |
| **Take or skip (knapsack)** | `dp[i][w]` = best using the first `i` items with capacity `w` | 0/1 knapsack, subset sum, partition |
| **Unbounded choices** | `dp[a]` = best for amount `a`, items reusable | Coin change (min coins, number of ways) |
| **Two strings** | `dp[i][j]` = answer for prefixes of lengths `i` and `j` | LCS, edit distance |
| **Increasing subsequence** | `dp[i]` = best ending at `i` | LIS |
| **Grid paths** | `dp[r][c]` = answer for cell `(r, c)` | Unique paths, min path sum, maximal square |
| **Partition a string** | `dp[i]` = can the first `i` characters be split? | Word break |

## Worked example: coin change

Coins `{1, 3, 4}`, amount `6`. Greedy picks 4 + 1 + 1 (three coins). DP finds the true answer.

- **State:** `dp[a]` = fewest coins to make amount `a`.
- **Recurrence:** `dp[a] = 1 + min(dp[a - coin])` over every coin that fits.
- **Base case:** `dp[0] = 0`.

| a | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|---|
| dp[a] | 0 | 1 | 2 | 1 | 1 | 2 | **2** |

`dp[6] = 1 + dp[3] = 2`, which is 3 + 3.

## Worked example: 0/1 knapsack

For each item you either **skip** it or **take** it:

```
dp[i][w] = max( dp[i-1][w],                         // skip item i
                dp[i-1][w - weight] + value )       // take item i (if it fits)
```

In the one-row version you loop the capacity **backwards**. That stops an item being counted twice, which would turn the problem into the unbounded knapsack.

## Complexity

The time is usually **(number of states) × (work per state)**.

| Problem | States | Time | Space (optimised) |
|---|---|---|---|
| Fibonacci, stairs, robber | n | O(n) | O(1) |
| Knapsack | n × W | O(n · W) | O(W) |
| Coin change | amount | O(amount · coins) | O(amount) |
| LCS, edit distance | m × n | O(m · n) | O(min(m, n)) possible |
| LIS | n | O(n²), or O(n log n) with binary search | O(n) |
| Grid paths | R × C | O(R · C) | O(C) |

Note that knapsack's O(n · W) is **pseudo-polynomial**: it depends on the *value* of the capacity `W`, not just the number of bits used to write it down.

## DP vs greedy vs backtracking

| | Greedy | Backtracking | Dynamic programming |
|---|---|---|---|
| Idea | Take the best option now | Try every option | Try every option, but reuse results |
| Correct for | Special problems | Everything | Overlapping subproblems |
| Speed | Fastest | Exponential | Polynomial |
| Use for | Provably greedy problems | Listing all solutions | Counting or optimising |

A useful rule of thumb: if you need **all** the solutions, use backtracking. If you need **how many** or **the best one**, think DP.

## Common mistakes

- An unclear or wrong **state definition**.
- Wrong **base cases**, especially `dp[0]` and the first row and column.
- Wrong **loop direction** in the 1D knapsack (backwards for 0/1, forwards for unbounded).
- Counting **combinations** (coins outside, amounts inside) versus **permutations** (amounts outside, coins inside).
- Mixing up **index `i` in the table** with **index `i - 1` in the string or array**.
- Using `Integer.MAX_VALUE` as "impossible" and then adding 1 to it, which overflows. Check first.
- Forgetting to initialise a memo array to a sentinel such as `-1`, when `0` is a valid answer.

## Files in this folder

| File | What it covers |
|---|---|
| [`DPBasics.java`](DPBasics.java) | Fibonacci (naive, memo, table, O(1) space), climbing stairs, house robber, Kadane's max subarray |
| [`Knapsack.java`](Knapsack.java) | 0/1 knapsack (2D and 1D), subset sum, partition, coin change (min coins and number of ways) |
| [`StringDP.java`](StringDP.java) | LCS (length and the string itself), edit distance, LIS (O(n²) and O(n log n)), word break |
| [`GridDP.java`](GridDP.java) | Unique paths, paths with obstacles, min path sum, maximal square |

Run any file with:

```
java Knapsack.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Min cost climbing stairs.
2. Tribonacci number.
3. Pascal's triangle.
4. Best time to buy and sell a stock (one transaction).
5. Is a string a subsequence of another?

**Medium**
6. Longest palindromic substring.
7. Decode ways: how many ways can a digit string be decoded as letters?
8. Target sum: assign + or − to each number to reach a target.
9. Longest common substring.
10. Maximum product subarray.

**Hard**
11. Regular expression matching.
12. Burst balloons.
13. Edit distance with different costs for each operation.

For each one, write the **state sentence** and the **recurrence** on paper before coding.

## Key takeaways

- DP is recursion plus remembering. It fixes problems where the same subproblem is solved many times.
- Define the state clearly, write the recurrence, set the base cases, then pick an order.
- Top-down and bottom-up have the same time complexity, and bottom-up often lets you save space.
- Use DP when greedy fails (coin change with odd coins, 0/1 knapsack) and you need a count or an optimum, not a list of every solution.
- With practice the patterns repeat: linear, knapsack, two strings, grid.
