
package mst;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Prim {

    private static class EdgeCandidate {
        int source;
        int destination;
        int weight;

        EdgeCandidate(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static class Result {
        public int totalWeight;
        public List<String> selectedEdges;
        public boolean connected;

        public Result(int totalWeight, List<String> selectedEdges,
                      boolean connected) {
            this.totalWeight = totalWeight;
            this.selectedEdges = selectedEdges;
            this.connected = connected;
        }
    }

    public static Result findMST(Graph graph) {

        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Prim requires an undirected graph"
            );
        }

        int vertices = graph.getVertices();

        List<String> selectedEdges = new ArrayList<>();

        if (vertices == 0) {
            return new Result(0, selectedEdges, true);
        }

        boolean[] visited = new boolean[vertices];

        PriorityQueue<EdgeCandidate> queue =
                new PriorityQueue<>(
                        Comparator.comparingInt(edge -> edge.weight)
                );

        int totalWeight = 0;
        int visitedCount = 1;

        visited[0] = true;

        for (Graph.Edge edge : graph.getAdjacencyList().get(0)) {
            queue.add(new EdgeCandidate(
                    0, edge.destination, edge.weight
            ));
        }

        while (!queue.isEmpty() && visitedCount < vertices) {

            EdgeCandidate current = queue.poll();

            if (visited[current.destination]) {
                continue;
            }

            visited[current.destination] = true;
            visitedCount++;

            totalWeight += current.weight;

            selectedEdges.add(
                    current.source + " - " + current.destination +
                            " (weight: " + current.weight + ")"
            );

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(current.destination)) {

                if (!visited[edge.destination]) {
                    queue.add(new EdgeCandidate(
                            current.destination,
                            edge.destination,
                            edge.weight
                    ));
                }
            }
        }

        boolean connected = visitedCount == vertices;

        return new Result(totalWeight, selectedEdges, connected);
    }
}
