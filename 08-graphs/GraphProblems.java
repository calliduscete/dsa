import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Classic graph problems. V = vertices, E = edges.
 * Graphs here are adjacency lists: List<List<Integer>>.
 */
public class GraphProblems {

    // Build an adjacency list from an edge array
    static List<List<Integer>> build(int n, int[][] edges, boolean directed) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            if (!directed) {
                adj.get(e[1]).add(e[0]);
            }
        }
        return adj;
    }

    // Is there a path from source to target? (DFS) O(V + E) time, O(V) space
    static boolean hasPath(List<List<Integer>> adj, int source, int target) {
        return dfsPath(adj, source, target, new boolean[adj.size()]);
    }

    private static boolean dfsPath(List<List<Integer>> adj, int v, int target, boolean[] visited) {
        if (v == target) {
            return true;
        }
        visited[v] = true;
        for (int next : adj.get(v)) {
            if (!visited[next] && dfsPath(adj, next, target, visited)) {
                return true;
            }
        }
        return false;
    }

    // Shortest path length in edges in an unweighted graph (BFS).
    // Returns -1 if unreachable. O(V + E) time, O(V) space
    static int shortestPath(List<List<Integer>> adj, int source, int target) {
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        dist[source] = 0;
        queue.offer(source);
        while (!queue.isEmpty()) {
            int v = queue.poll();
            if (v == target) {
                return dist[v];
            }
            for (int next : adj.get(v)) {
                if (dist[next] == -1) {
                    dist[next] = dist[v] + 1;
                    queue.offer(next);
                }
            }
        }
        return -1;
    }

    // Count connected components in an undirected graph: O(V + E) time, O(V) space
    static int countComponents(List<List<Integer>> adj) {
        boolean[] visited = new boolean[adj.size()];
        int count = 0;
        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i]) {
                count++;
                markComponent(adj, i, visited);
            }
        }
        return count;
    }

    private static void markComponent(List<List<Integer>> adj, int v, boolean[] visited) {
        visited[v] = true;
        for (int next : adj.get(v)) {
            if (!visited[next]) {
                markComponent(adj, next, visited);
            }
        }
    }

    // Cycle detection in an UNDIRECTED graph (DFS, track the parent): O(V + E)
    static boolean hasCycleUndirected(List<List<Integer>> adj) {
        boolean[] visited = new boolean[adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i] && cycleDfs(adj, i, -1, visited)) {
                return true;
            }
        }
        return false;
    }

    private static boolean cycleDfs(List<List<Integer>> adj, int v, int parent, boolean[] visited) {
        visited[v] = true;
        for (int next : adj.get(v)) {
            if (!visited[next]) {
                if (cycleDfs(adj, next, v, visited)) {
                    return true;
                }
            } else if (next != parent) {
                return true;      // reached a visited vertex that isn't where we came from
            }
        }
        return false;
    }

    // Cycle detection in a DIRECTED graph (DFS with a "currently on the path" set): O(V + E)
    static boolean hasCycleDirected(List<List<Integer>> adj) {
        int[] state = new int[adj.size()];   // 0 = unvisited, 1 = on current path, 2 = done
        for (int i = 0; i < adj.size(); i++) {
            if (state[i] == 0 && directedCycleDfs(adj, i, state)) {
                return true;
            }
        }
        return false;
    }

    private static boolean directedCycleDfs(List<List<Integer>> adj, int v, int[] state) {
        state[v] = 1;
        for (int next : adj.get(v)) {
            if (state[next] == 1) {
                return true;                 // back edge: found a cycle
            }
            if (state[next] == 0 && directedCycleDfs(adj, next, state)) {
                return true;
            }
        }
        state[v] = 2;
        return false;
    }

    // Topological sort (Kahn's algorithm, BFS) for a directed acyclic graph.
    // Returns an empty list if the graph has a cycle. O(V + E) time, O(V) space
    static List<Integer> topologicalSort(List<List<Integer>> adj) {
        int n = adj.size();
        int[] inDegree = new int[n];
        for (List<Integer> neighbours : adj) {
            for (int next : neighbours) {
                inDegree[next]++;
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);
            for (int next : adj.get(v)) {
                if (--inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return order.size() == n ? order : Collections.emptyList();
    }

    // Is the graph bipartite (2-colourable)? BFS colouring: O(V + E) time, O(V) space
    static boolean isBipartite(List<List<Integer>> adj) {
        int[] colour = new int[adj.size()];
        Arrays.fill(colour, -1);
        for (int start = 0; start < adj.size(); start++) {
            if (colour[start] != -1) {
                continue;
            }
            Queue<Integer> queue = new ArrayDeque<>();
            colour[start] = 0;
            queue.offer(start);
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (int next : adj.get(v)) {
                    if (colour[next] == -1) {
                        colour[next] = 1 - colour[v];
                        queue.offer(next);
                    } else if (colour[next] == colour[v]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Number of islands in a grid ('1' = land, '0' = water): O(R * C) time and space.
    // The grid is a graph where each cell is connected to its 4 neighbours.
    static int numIslands(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    sink(grid, r, c);
                }
            }
        }
        return count;
    }

    private static void sink(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '0';    // mark as visited
        sink(grid, r + 1, c);
        sink(grid, r - 1, c);
        sink(grid, r, c + 1);
        sink(grid, r, c - 1);
    }

    public static void main(String[] args) {
        // Undirected: 0-1-2-3 connected path, 4-5 connected pair, 6 alone
        List<List<Integer>> g = build(7, new int[][]{{0, 1}, {1, 2}, {2, 3}, {4, 5}}, false);
        System.out.println("hasPath(0 -> 3): " + hasPath(g, 0, 3));
        System.out.println("hasPath(0 -> 5): " + hasPath(g, 0, 5));
        System.out.println("shortestPath(0 -> 3): " + shortestPath(g, 0, 3));
        System.out.println("shortestPath(0 -> 5): " + shortestPath(g, 0, 5));
        System.out.println("components: " + countComponents(g));
        System.out.println("hasCycleUndirected (no cycle): " + hasCycleUndirected(g));

        List<List<Integer>> triangle = build(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}, false);
        System.out.println("hasCycleUndirected (triangle): " + hasCycleUndirected(triangle));
        System.out.println("isBipartite (triangle): " + isBipartite(triangle));
        System.out.println("isBipartite (path): " + isBipartite(g));

        // Directed: course prerequisites 0 -> 1 -> 3, 0 -> 2 -> 3
        List<List<Integer>> dag = build(4, new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}}, true);
        System.out.println("topologicalSort: " + topologicalSort(dag));
        System.out.println("hasCycleDirected (DAG): " + hasCycleDirected(dag));

        List<List<Integer>> cyclic = build(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}, true);
        System.out.println("hasCycleDirected (cycle): " + hasCycleDirected(cyclic));
        System.out.println("topologicalSort (cycle): " + topologicalSort(cyclic));

        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        System.out.println("numIslands: " + numIslands(grid));
    }
}
