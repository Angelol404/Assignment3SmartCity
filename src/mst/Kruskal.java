
package mst;

import dsu.DisjointSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

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
                    "Kruskal requires an undirected graph"
            );
        }

        int vertices = graph.getVertices();

        List<int[]> edges = new ArrayList<>();

        for (int source = 0; source < vertices; source++) {
            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {

                if (source < edge.destination) {
                    edges.add(new int[]{
                            source, edge.destination, edge.weight
                    });
                }
            }
        }

        edges.sort(Comparator.comparingInt(edge -> edge[2]));

        DisjointSet dsu = new DisjointSet(vertices);

        List<String> selectedEdges = new ArrayList<>();
        int totalWeight = 0;

        for (int[] edge : edges) {
            int source = edge[0];
            int destination = edge[1];
            int weight = edge[2];

            if (dsu.union(source, destination)) {
                selectedEdges.add(
                        source + " - " + destination + " (weight: " + weight + ")"
                );

                totalWeight += weight;
            }

            if (selectedEdges.size() == vertices - 1) {
                break;
            }
        }

        boolean connected = vertices <= 1 ||
                selectedEdges.size() == vertices - 1;

        return new Result(totalWeight, selectedEdges, connected);
    }
}
