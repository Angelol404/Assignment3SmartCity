
package testing;

import graph.Graph;
import java.util.Random;

public class GraphGenerator {

    public static Graph generateUndirectedGraph(
            int vertices, int edges, long seed) {

        if (vertices < 1 || edges < vertices - 1 ||
                edges > vertices * (vertices - 1) / 2) {
            throw new IllegalArgumentException("Invalid graph parameters");
        }

        Graph graph = new Graph(vertices, false);
        Random random = new Random(seed);

        // First, connect all vertices to guarantee connectivity
        for (int i = 1; i < vertices; i++) {
            int parent = random.nextInt(i);
            int weight = random.nextInt(100) + 1;
            graph.addEdge(i, parent, weight);
        }

        // Add remaining edges
        int addedEdges = vertices - 1;

        while (addedEdges < edges) {
            int source = random.nextInt(vertices);
            int destination = random.nextInt(vertices);

            if (source == destination) {
                continue;
            }

            // Avoid duplicate edges
            if (hasEdge(graph, source, destination)) {
                continue;
            }

            int weight = random.nextInt(100) + 1;
            graph.addEdge(source, destination, weight);
            addedEdges++;
        }

        return graph;
    }

    private static boolean hasEdge(
            Graph graph, int source, int destination) {

        for (Graph.Edge edge :
                graph.getAdjacencyList().get(source)) {
            if (edge.destination == destination) {
                return true;
            }
        }

        return false;
    }

    public static Graph generateDirectedDAG(
            int vertices, int edges, long seed) {

        if (vertices < 1 || edges < 0 ||
                edges > vertices * (vertices - 1) / 2) {
            throw new IllegalArgumentException("Invalid DAG parameters");
        }

        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);

        int added = 0;

        while (added < edges) {
            int source = random.nextInt(vertices);
            int destination = random.nextInt(vertices);

            // This condition guarantees no directed cycles
            if (source >= destination) {
                continue;
            }

            if (hasDirectedEdge(graph, source, destination)) {
                continue;
            }

            graph.addEdge(source, destination, random.nextInt(100) + 1);
            added++;
        }

        return graph;
    }

    public static Graph generateDirectedGraph(
            int vertices, int edges, long seed) {

        if (vertices < 1 || edges < 0 ||
                edges > vertices * (vertices - 1)) {
            throw new IllegalArgumentException("Invalid graph parameters");
        }

        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);

        int added = 0;

        while (added < edges) {
            int source = random.nextInt(vertices);
            int destination = random.nextInt(vertices);

            if (source == destination ||
                    hasDirectedEdge(graph, source, destination)) {
                continue;
            }

            graph.addEdge(source, destination, random.nextInt(100) + 1);
            added++;
        }

        return graph;
    }

    private static boolean hasDirectedEdge(
            Graph graph, int source, int destination) {

        for (Graph.Edge edge : graph.getAdjacencyList().get(source)) {
            if (edge.destination == destination) {
                return true;
            }
        }

        return false;
    }

}
