
package connectivity;

import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StronglyConnectedComponents {

    public static List<List<Integer>> findSCC(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "SCC requires a directed graph"
            );
        }

        int vertices = graph.getVertices();
        boolean[] visited = new boolean[vertices];
        List<Integer> order = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsFirst(graph, i, visited, order);
            }
        }

        Graph reversed = reverseGraph(graph);

        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        Collections.reverse(order);

        List<List<Integer>> components = new ArrayList<>();

        for (int vertex : order) {
            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();

                dfsSecond(reversed, vertex, visited, component);

                components.add(component);
            }
        }

        return components;
    }

    private static void dfsFirst(
            Graph graph, int vertex,
            boolean[] visited, List<Integer> order) {

        visited[vertex] = true;

        for (Graph.Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            if (!visited[edge.destination]) {
                dfsFirst(graph, edge.destination, visited, order);
            }
        }

        order.add(vertex);
    }

    private static void dfsSecond(
            Graph graph, int vertex,
            boolean[] visited, List<Integer> component) {

        visited[vertex] = true;
        component.add(vertex);

        for (Graph.Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            if (!visited[edge.destination]) {
                dfsSecond(
                        graph, edge.destination, visited, component
                );
            }
        }
    }

    private static Graph reverseGraph(Graph graph) {

        int vertices = graph.getVertices();
        Graph reversed = new Graph(vertices, true);

        for (int source = 0; source < vertices; source++) {
            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {

                reversed.addEdge(
                        edge.destination, source, edge.weight
                );
            }
        }

        return reversed;
    }
}
