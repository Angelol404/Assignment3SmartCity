
package shortestpath;

import graph.Graph;
import java.util.List;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Dijkstra {

    private static class Node {
        int vertex;
        int distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static class Result {
        public int[] distances;
        public int[] previous;

        public Result(int[] distances, int[] previous) {
            this.distances = distances;
            this.previous = previous;
        }

        public void printPaths(int source) {
            for (int i = 0; i < distances.length; i++) {

                if (distances[i] == Integer.MAX_VALUE) {
                    System.out.println(
                            "Vertex " + i + ": unreachable"
                    );
                } else {
                    System.out.print(
                            "Vertex " + i + ": distance = " + distances[i]
                                    + ", path = "
                    );

                    printPath(i);
                    System.out.println();
                }
            }
        }

        private void printPath(int vertex) {
            if (previous[vertex] == -1) {
                System.out.print(vertex);
                return;
            }

            printPath(previous[vertex]);
            System.out.print(" -> " + vertex);
        }
    }

    public static Result findShortestPaths(Graph graph, int source) {

        int vertices = graph.getVertices();

        if (source < 0 || source >= vertices) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        int[] distances = new int[vertices];
        int[] previous = new int[vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        for (List<Graph.Edge> edges : graph.getAdjacencyList()) {
            for (Graph.Edge edge : edges) {
                if (edge.weight < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra does not support negative weights"
                    );
                }
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );

        distances[source] = 0;
        queue.add(new Node(source, 0));

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            int vertex = current.vertex;

            if (current.distance != distances[vertex]) {
                continue;
            }

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(vertex)) {

                int newDistance =
                        distances[vertex] + edge.weight;

                if (newDistance < distances[edge.destination]) {

                    distances[edge.destination] = newDistance;
                    previous[edge.destination] = vertex;

                    queue.add(new Node(
                            edge.destination, newDistance
                    ));
                }
            }
        }

        return new Result(distances, previous);
    }
}
