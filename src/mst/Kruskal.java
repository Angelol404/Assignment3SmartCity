
package mst;

import dsu.DisjointSet;
import graph.Graph;
import testing.OperationCounter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static class Result {
        public int totalWeight;
        public List<String> selectedEdges;
        public boolean connected;
        public long operations;

        public Result(int totalWeight, List<String> selectedEdges,
                      boolean connected, long operations) {
            this.totalWeight = totalWeight;
            this.selectedEdges = selectedEdges;
            this.connected = connected;
            this.operations = operations;
        }
    }

    public static Result findMST(Graph graph) {
        OperationCounter counter = new OperationCounter();

        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kruskal requires an undirected graph"
            );
        }

        int vertices = graph.getVertices();
        List<int[]> edges = new ArrayList<>();

        // Collect edges
        for (int source = 0; source < vertices; source++) {
            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {

                counter.increment();

                if (source < edge.destination) {
                    edges.add(new int[]{
                            source, edge.destination, edge.weight
                    });
                    counter.increment();
                }
            }
        }

        // Sort edges
        edges.sort(Comparator.comparingInt(edge -> edge[2]));
        counter.add((long) edges.size() *
                (long) (Math.log(Math.max(2, edges.size())) /
                        Math.log(2)));

        DisjointSet dsu = new DisjointSet(vertices);

        List<String> selectedEdges = new ArrayList<>();
        int totalWeight = 0;

        for (int[] edge : edges) {
            counter.increment();

            int source = edge[0];
            int destination = edge[1];
            int weight = edge[2];

            counter.add(2); // DSU find/union checks, approximate

            if (dsu.union(source, destination)) {
                selectedEdges.add(
                        source + " - " + destination +
                                " (weight: " + weight + ")"
                );

                totalWeight += weight;
                counter.add(2);
            }

            if (selectedEdges.size() == vertices - 1) {
                break;
            }
        }

        boolean connected = vertices <= 1 ||
                selectedEdges.size() == vertices - 1;

        return new Result(
                totalWeight,
                selectedEdges,
                connected,
                counter.getOperations()
        );
    }
}
