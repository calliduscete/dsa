import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Dijkstra's algorithm: shortest paths from one source in a WEIGHTED graph
 * with non-negative edge weights.
 *
 * Idea: always expand the closest unvisited vertex next (a greedy choice),
 * using a min-heap to find it quickly.
 *
 * Time: O((V + E) log V) with a binary heap.   Space: O(V + E).
 * Does NOT work with negative weights (use Bellman-Ford for those).
 */
public class Dijkstra {

    // An edge to vertex `to` with the given weight
    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int[] shortestDistances(List<List<Edge>> graph, int source) {
        int[] dist = new int[graph.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Heap entries are {vertex, distance}, smallest distance first
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        heap.offer(new int[]{source, 0});

        while (!heap.isEmpty()) {
            int[] top = heap.poll();
            int v = top[0];
            int d = top[1];
            if (d > dist[v]) {
                continue;       // stale entry: a shorter path was already found
            }
            for (Edge edge : graph.get(v)) {
                int newDist = d + edge.weight;
                if (newDist < dist[edge.to]) {
                    dist[edge.to] = newDist;
                    heap.offer(new int[]{edge.to, newDist});
                }
            }
        }
        return dist;
    }

    static void addEdge(List<List<Edge>> graph, int a, int b, int w) {
        graph.get(a).add(new Edge(b, w));
        graph.get(b).add(new Edge(a, w));     // undirected
    }

    public static void main(String[] args) {
        // Edges (undirected, with weights):
        //   0 - 1 (4)    0 - 2 (1)    2 - 1 (2)    1 - 3 (1)    3 - 4 (5)
        int n = 5;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        addEdge(graph, 0, 1, 4);
        addEdge(graph, 0, 2, 1);
        addEdge(graph, 2, 1, 2);
        addEdge(graph, 1, 3, 1);
        addEdge(graph, 3, 4, 5);

        int[] dist = shortestDistances(graph, 0);
        for (int i = 0; i < n; i++) {
            System.out.println("distance 0 -> " + i + ": " + dist[i]);
        }
        // Note: 0 -> 1 is 3 (via vertex 2), not 4 (the direct edge).
    }
}
