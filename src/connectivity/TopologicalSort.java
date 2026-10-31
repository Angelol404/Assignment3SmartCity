
package connectivity;

import graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

    public static class Result {
        public List<Integer> order;
        public boolean hasCycle;

        public Result(List<Integer> order, boolean hasCycle) {
            this.order = order;
            this.hasCycle = hasCycle;
        }
    }

    public static Result sort(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Topological sort requires a directed graph"
            );
        }

        int vertices = graph.getVertices();
        int[] indegree = new int[vertices];

        for (int source = 0; source < vertices; source++) {
            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {
                indegree[edge.destination]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < vertices; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(current)) {

                indegree[edge.destination]--;

                if (indegree[edge.destination] == 0) {
                    queue.add(edge.destination);
                }
            }
        }

        boolean hasCycle = order.size() != vertices;

        return new Result(order, hasCycle);
    }
}
