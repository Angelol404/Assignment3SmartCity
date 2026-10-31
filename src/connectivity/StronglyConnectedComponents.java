
package connectivity;

import graph.Graph;
import testing.OperationCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StronglyConnectedComponents {

    public static class Result {
        public List<List<Integer>> components;
        public long operations;

        public Result(List<List<Integer>> components, long operations) {
            this.components = components;
            this.operations = operations;
        }
    }

    // Original method: keeps compatibility with Main and tests
    public static List<List<Integer>> findSCC(Graph graph) {
        return findSCCWithStats(graph).components;
    }

    // New method: returns components and operation count
    public static Result findSCCWithStats(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "SCC requires a directed graph"
            );
        }

        OperationCounter counter = new OperationCounter();

        int vertices = graph.getVertices();
        boolean[] visited = new boolean[vertices];
        List<Integer> order = new ArrayList<>();

        // First DFS pass
        for (int i = 0; i < vertices; i++) {
            counter.increment();

            if (!visited[i]) {
                dfsFirst(graph, i, visited, order, counter);
            }
        }

        // Reverse graph
        Graph reversed = reverseGraph(graph, counter);

        // Reset visited array
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
            counter.increment();
        }

        Collections.reverse(order);

        List<List<Integer>> components = new ArrayList<>();

        // Second DFS pass
        for (int vertex : order) {
            counter.increment();

            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();

                dfsSecond(
                        reversed, vertex, visited, component, counter
                );

                components.add(component);
            }
        }

        return new Result(components, counter.getOperations());
    }

    private static void dfsFirst(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> order,
            OperationCounter counter) {

        visited[vertex] = true;
        counter.increment();

        for (Graph.Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            counter.increment();

            if (!visited[edge.destination]) {
                dfsFirst(
                        graph, edge.destination, visited, order, counter
                );
            }
        }

        order.add(vertex);
        counter.increment();
    }

    private static void dfsSecond(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component,
            OperationCounter counter) {

        visited[vertex] = true;
        component.add(vertex);
        counter.add(2);

        for (Graph.Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            counter.increment();

            if (!visited[edge.destination]) {
                dfsSecond(
                        graph, edge.destination, visited, component, counter
                );
            }
        }
    }

    private static Graph reverseGraph(
            Graph graph,
            OperationCounter counter) {

        int vertices = graph.getVertices();
        Graph reversed = new Graph(vertices, true);

        for (int source = 0; source < vertices; source++) {
            counter.increment();

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {

                reversed.addEdge(
                        edge.destination, source, edge.weight
                );

                counter.increment();
            }
        }

        return reversed;
    }
}
