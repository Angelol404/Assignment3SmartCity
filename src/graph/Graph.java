
package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static class Edge {
        public int destination;
        public int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private int vertices;
    private boolean directed;
    private List<List<Edge>> adjacencyList;

    public Graph(int vertices, boolean directed) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative");
        }

        this.vertices = vertices;
        this.directed = directed;
        adjacencyList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        if (source < 0 || source >= vertices ||
                destination < 0 || destination >= vertices) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        adjacencyList.get(source).add(new Edge(destination, weight));

        if (!directed) {
            adjacencyList.get(destination).add(new Edge(source, weight));
        }
    }

    public int getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " -> ");

            for (Edge edge : adjacencyList.get(i)) {
                System.out.print(
                        "(" + edge.destination + ", weight: " + edge.weight + ") "
                );
            }

            System.out.println();
        }
    }
}
