import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * Graph represented with an ADJACENCY LIST, plus BFS and DFS.
 *
 * A graph is a set of vertices (nodes) connected by edges.
 * V = number of vertices, E = number of edges.
 *
 * Adjacency list: space O(V + E), check one vertex's neighbours in O(degree).
 * BFS and DFS both visit every vertex and edge once: O(V + E) time, O(V) space.
 */
public class Graph {

    private final int vertices;
    private final List<List<Integer>> adj;
    private final boolean directed;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // O(1)
    public void addEdge(int from, int to) {
        adj.get(from).add(to);
        if (!directed) {
            adj.get(to).add(from);
        }
    }

    public List<Integer> neighbours(int v) {
        return adj.get(v);
    }

    public int size() {
        return vertices;
    }

    // Breadth-first search: explores level by level using a queue.
    // Finds the shortest path (fewest edges) in an unweighted graph.
    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.offer(start);
        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);
            for (int next : adj.get(v)) {
                if (!visited[next]) {
                    visited[next] = true;      // mark when ADDING to the queue
                    queue.offer(next);
                }
            }
        }
        return order;
    }

    // Depth-first search (recursive): goes as deep as possible, then backtracks.
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        dfs(start, new boolean[vertices], order);
        return order;
    }

    private void dfs(int v, boolean[] visited, List<Integer> order) {
        visited[v] = true;
        order.add(v);
        for (int next : adj.get(v)) {
            if (!visited[next]) {
                dfs(next, visited, order);
            }
        }
    }

    // Depth-first search (iterative) using a stack
    public List<Integer> dfsIterative(int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            order.add(v);
            // push neighbours in reverse so they are visited in listed order
            for (int i = adj.get(v).size() - 1; i >= 0; i--) {
                int next = adj.get(v).get(i);
                if (!visited[next]) {
                    stack.push(next);
                }
            }
        }
        return order;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices; i++) {
            sb.append(i).append(" -> ").append(adj.get(i)).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //   0 --- 1 --- 3
        //   |     |
        //   2 --- 4 --- 5
        Graph g = new Graph(6, false);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(4, 5);

        System.out.print(g);
        System.out.println("BFS from 0:           " + g.bfs(0));
        System.out.println("DFS from 0:           " + g.dfs(0));
        System.out.println("DFS (iterative) from 0: " + g.dfsIterative(0));

        // The same graph as an ADJACENCY MATRIX, for comparison.
        // Space O(V^2), but checking "is there an edge u-v?" is O(1).
        int[][] matrix = new int[6][6];
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 4}, {4, 5}};
        for (int[] e : edges) {
            matrix[e[0]][e[1]] = 1;
            matrix[e[1]][e[0]] = 1;
        }
        System.out.println("edge 1-4 exists? " + (matrix[1][4] == 1));
        System.out.println("edge 3-5 exists? " + (matrix[3][5] == 1));
    }
}
