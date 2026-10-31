
package shortestpath;

import graph.Graph;
import testing.OperationCounter;

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
        public long operations;

        public Result(int[] distances, int[] previous,
                      long operations) {
            this.distances = distances;
            this.previous = previous;
            this.operations = operations;
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

        OperationCounter counter = new OperationCounter();

        int vertices = graph.getVertices();

        if (source < 0 || source >= vertices) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        int[] distances = new int[vertices];
        int[] previous = new int[vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        counter.add(2L * vertices);

        // Validate edge weights
        for (List<Graph.Edge> edges : graph.getAdjacencyList()) {
            for (Graph.Edge edge : edges) {
                counter.increment();

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
        counter.add(2);

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            counter.increment();

            int vertex = current.vertex;

            if (current.distance != distances[vertex]) {
                counter.increment();
                continue;
            }

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(vertex)) {

                counter.increment();

                // Avoid integer overflow
                if (distances[vertex] >
                        Integer.MAX_VALUE - edge.weight) {
                    continue;
                }

                int newDistance =
                        distances[vertex] + edge.weight;
                counter.increment();

                if (newDistance < distances[edge.destination]) {

                    distances[edge.destination] = newDistance;
                    previous[edge.destination] = vertex;

                    queue.add(new Node(
                            edge.destination, newDistance
                    ));

                    counter.add(3);
                }
            }
        }

        return new Result(
                distances,
                previous,
                counter.getOperations()
        );
    }
}
