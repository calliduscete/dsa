# Greedy Algorithms

A greedy algorithm builds a solution step by step, and at each step it takes the **choice that looks best right now**, without ever reconsidering it. When the problem has the right structure, this gives the optimal answer with very little code and very little time. When it doesn't, greedy gives a wrong answer, so knowing **when it works** is the real skill.

## The idea

Imagine making change for 63 cents with US coins. You take the biggest coin that fits (25), then again (25), then 10, then three pennies. You never undo a choice, and you end up with the fewest coins. That's greedy.

```
63 -> take 25 -> 38 -> take 25 -> 13 -> take 10 -> 3 -> take 1, 1, 1 -> 0
```

## When does greedy work?

Greedy is correct when the problem has both of these properties:

1. **Greedy choice property:** a locally best choice can be extended to a globally optimal solution.
2. **Optimal substructure:** after making the choice, what remains is a smaller version of the same problem, and its optimal solution combines with your choice to form an optimal whole.

Proving the first property is usually the hard part. A common proof technique is an **exchange argument**: take any optimal solution, and show you can swap its first choice for the greedy one without making it worse.

## When greedy fails

The classic counterexample is coin change with an unusual coin system. With coins `{1, 3, 4}` and amount `6`:

| Approach | Coins | Count |
|---|---|---|
| Greedy (largest first) | 4 + 1 + 1 | 3 |
| Optimal | 3 + 3 | **2** |

Greedy also fails on the **0/1 knapsack** problem (items that can't be split). The same idea works on the **fractional knapsack**, where you can take part of an item. Small changes in a problem can decide whether greedy is correct.

Both of these need **dynamic programming**, which is the next folder.

## Greedy vs other approaches

| | Greedy | Dynamic programming | Backtracking |
|---|---|---|---|
| Considers all options? | No, commits to one | Yes, but reuses subresults | Yes, tries everything |
| Revisits decisions? | Never | Implicitly, via the table | Yes (undo) |
| Speed | Fastest, often O(n log n) | Polynomial | Exponential |
| Always correct? | Only for certain problems | Yes, when the structure fits | Yes |

## Common greedy patterns

| Pattern | The greedy rule | Example problems |
|---|---|---|
| **Sort by end time** | Take what finishes earliest | Activity selection, remove overlapping intervals |
| **Sort by start time, then merge** | Extend the current interval while they overlap | Merge intervals |
| **Best ratio first** | Highest value per unit | Fractional knapsack |
| **Largest first** | Take the biggest piece that fits | Coin change (canonical coins) |
| **Track the furthest reach** | Keep the best reachable position | Jump game |
| **Collect every gain** | Add every positive step | Best time to buy and sell stock II |
| **Match smallest adequate** | Pair the least demanding with the smallest that works | Assign cookies |
| **Min-heap merging** | Always combine the two smallest | Huffman coding |

## Huffman coding

A famous greedy algorithm that compresses data. Frequent characters get **short** codes, rare ones get **long** codes. Repeatedly merge the two least frequent nodes (using a min-heap) into a tree, and read each character's code off the path from the root (left = 0, right = 1).

For `abracadabra`, `a` appears 5 times, so it gets the 1-bit code `0`, and the rarer letters get 3 bits. The whole text takes **23 bits** instead of 88 bits at 8 bits per character.

The codes are **prefix-free** (no code is the start of another), so decoding is unambiguous.

## Complexity

Greedy solutions are usually dominated by a **sort** or a **heap**:

- Sort first, then one pass: **O(n log n)** time.
- One pass with no sort (jump game, stock profit): **O(n)** time.
- Space is usually O(1) or O(n).

## Common mistakes

- **Assuming greedy works** without checking. Always try to find a counterexample with small inputs.
- Sorting by the **wrong key**. Interval scheduling needs the **end** time, but merging needs the **start** time.
- Using greedy for **0/1 knapsack** or **coin change with arbitrary coins**.
- Forgetting **edge cases**: empty input, a single item, ties.
- Believing greedy and "simple" mean the same thing. The code is short, but the proof of correctness is not.

## How to approach a greedy problem

1. Look for a natural **ordering** (by end time, by ratio, by size).
2. Define the **greedy rule** for the next choice.
3. **Test** it by hand on small cases, especially tricky ones.
4. Try to build a **counterexample**. If you can't, justify why none exists (an exchange argument).
5. If greedy fails, switch to dynamic programming or backtracking.

## Files in this folder

| File | What it covers |
|---|---|
| [`ActivitySelection.java`](ActivitySelection.java) | Max non-overlapping intervals, minimum removals, merge intervals, minimum meeting rooms |
| [`FractionalKnapsack.java`](FractionalKnapsack.java) | Take items by best value per weight |
| [`CoinChange.java`](CoinChange.java) | Greedy change-making, plus a case where greedy fails |
| [`GreedyProblems.java`](GreedyProblems.java) | Jump game I and II, stock profit, assign cookies, gas station |
| [`HuffmanCoding.java`](HuffmanCoding.java) | Build codes with a min-heap, encode and decode |

Run any file with:

```
java ActivitySelection.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Find the minimum number of coins for a given amount with US coins.
2. Assign cookies to children (try it before reading the example).
3. Check if you can attend all meetings (do any intervals overlap?).
4. Lemonade change: can you give correct change to every customer paying with $5, $10 or $20 bills?
5. Split a balanced string into the maximum number of balanced pieces.

**Medium**
6. Task scheduler: minimum time to run tasks with a cooldown between identical ones.
7. Partition labels: split a string so each letter appears in at most one part.
8. Queue reconstruction by height.
9. Minimum number of arrows to burst balloons.
10. Candy: give each child at least one candy, and more than a neighbour with a lower rating.

Try each one yourself first, and for each one ask: "Why does greedy work here?"

## Key takeaways

- Greedy picks the best option now and never looks back, which makes it simple and fast.
- It is only correct when the problem has the greedy-choice property and optimal substructure.
- Many greedy problems start with a sort. Choosing the right sort key is half the solution.
- Always test for a counterexample, because coin change with `{1, 3, 4}` is the classic reminder that greedy can fail.
- When greedy fails, dynamic programming is usually the next tool.
