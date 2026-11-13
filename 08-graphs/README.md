# Graphs

A graph is a set of **vertices** (nodes) connected by **edges**. Graphs model anything made of connections: road maps, social networks, the web, course prerequisites, game boards. Trees are actually just a special kind of graph.

## Terminology

```
   0 --- 1 --- 3
   |     |
   2 --- 4 --- 5
```

| Term | Meaning |
|---|---|
| **Vertex (node)** | A point in the graph |
| **Edge** | A connection between two vertices |
| **Directed** | Edges have a direction (A -> B is not B -> A), like following someone |
| **Undirected** | Edges go both ways, like a friendship |
| **Weighted** | Each edge has a cost or distance |
| **Degree** | Number of edges at a vertex (in-degree and out-degree for directed graphs) |
| **Path** | A sequence of vertices connected by edges |
| **Cycle** | A path that starts and ends at the same vertex |
| **Connected** | Every vertex can reach every other (undirected graph) |
| **DAG** | Directed acyclic graph: directed, no cycles |

## Representing a graph

**Adjacency list:** each vertex stores a list of its neighbours.

```
0 -> [1, 2]
1 -> [0, 3, 4]
2 -> [0, 4]
...
```

**Adjacency matrix:** a V x V grid where `matrix[i][j] = 1` means there is an edge.

| | Adjacency list | Adjacency matrix |
|---|---|---|
| Space | O(V + E) | O(V²) |
| Is there an edge u-v? | O(degree of u) | O(1) |
| List all neighbours of v | O(degree of v) | O(V) |
| Best for | Sparse graphs (most real ones) | Dense graphs, quick edge checks |

```java
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < n; i++) {
    adj.add(new ArrayList<>());
}
adj.get(0).add(1);     // edge 0 -> 1
adj.get(1).add(0);     // add this too if the graph is undirected
```

## Traversals

Both visit every vertex and edge once: **O(V + E)** time and **O(V)** space. Always keep a **visited** set, otherwise cycles cause infinite loops.

| | BFS (breadth-first) | DFS (depth-first) |
|---|---|---|
| Strategy | Level by level, nearest first | As deep as possible, then backtrack |
| Data structure | **Queue** | **Stack** (or recursion) |
| Finds shortest path (unweighted)? | **Yes** | No |
| Good for | Shortest path, levels, nearest | Cycle detection, connectivity, topological sort, backtracking |

For the graph above, starting at 0:

- BFS: 0 1 2 3 4 5
- DFS: 0 1 3 4 2 5

## Key algorithms

| Problem | Algorithm | Time |
|---|---|---|
| Shortest path, unweighted | BFS | O(V + E) |
| Shortest path, weighted (non-negative) | **Dijkstra** (min-heap) | O((V + E) log V) |
| Shortest path, negative weights | Bellman-Ford | O(V · E) |
| Order tasks with dependencies | **Topological sort** (Kahn's or DFS) | O(V + E) |
| Minimum spanning tree | Kruskal or Prim | O(E log V) |
| Connected components | BFS or DFS (or Union-Find) | O(V + E) |

## Key techniques

- **Visited array/set:** mark vertices so you never process one twice.
- **BFS with a distance array:** `dist[next] = dist[v] + 1` gives the shortest path length.
- **Cycle detection:** undirected graphs track the **parent**. Directed graphs track the vertices **currently on the DFS path**.
- **In-degree counting** (Kahn's algorithm): repeatedly remove vertices with no incoming edges to get a topological order.
- **Graph colouring:** BFS alternating two colours checks whether a graph is bipartite.
- **Grids as graphs:** each cell is a vertex, connected to its 4 (or 8) neighbours. Many "islands", "flood fill" and "maze" problems are graph traversals.

## Common mistakes

- Forgetting the visited check, which loops forever on any cycle.
- In BFS, marking a vertex visited **when you poll it** instead of **when you add it**, so it enters the queue many times.
- Forgetting that an undirected edge must be added in **both directions**.
- Forgetting disconnected graphs: loop over all vertices and start a search from each unvisited one.
- Using Dijkstra with **negative edge weights**, where it gives wrong answers.
- Using recursion DFS on very deep graphs, which can cause a `StackOverflowError`. Use an explicit stack instead.

## Files in this folder

| File | What it covers |
|---|---|
| [`Graph.java`](Graph.java) | Adjacency list graph, BFS, recursive and iterative DFS, adjacency matrix comparison |
| [`GraphProblems.java`](GraphProblems.java) | Has path, shortest path, connected components, cycle detection (both kinds), topological sort, bipartite check, number of islands |
| [`Dijkstra.java`](Dijkstra.java) | Shortest paths in a weighted graph using a min-heap |

Run any file with:

```
java Graph.java
```

(Java 11 or later runs a single source file directly. On older versions use `javac` then `java`.)

## Practice problems

**Easy**
1. Find the degree of every vertex in a graph.
2. Check if a path exists between two vertices.
3. Count the number of edges in an undirected graph.
4. Find the town judge (the vertex with in-degree n-1 and out-degree 0).
5. Flood fill an image.

**Medium**
6. Clone a graph.
7. Course schedule: can all courses be finished given prerequisites?
8. Rotting oranges: minimum minutes until all oranges rot (multi-source BFS).
9. Word ladder: shortest transformation sequence between two words.
10. Network delay time (Dijkstra).

Try each one yourself first, then compare with the examples in this folder.

## Key takeaways

- A graph is vertices plus edges, and trees, grids and linked lists are all special cases.
- Use an adjacency list for most problems.
- BFS (queue) finds shortest paths in unweighted graphs. DFS (stack or recursion) explores deeply and suits cycle and connectivity problems.
- Always track visited vertices, and remember disconnected components.
- Weighted shortest paths need Dijkstra, which builds on the heap from the previous folder.
