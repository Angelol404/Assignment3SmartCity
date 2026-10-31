
package connectivity;

import graph.Graph;
import testing.OperationCounter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

    public static class Result {
        public List<Integer> order;
        public boolean hasCycle;
        public long operations;

        public Result(List<Integer> order, boolean hasCycle,
                      long operations) {
            this.order = order;
            this.hasCycle = hasCycle;
            this.operations = operations;
        }
    }

    public static Result sort(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Topological sort requires a directed graph"
            );
        }

        OperationCounter counter = new OperationCounter();

        int vertices = graph.getVertices();
        int[] indegree = new int[vertices];

        // Calculate incoming edges
        for (int source = 0; source < vertices; source++) {
            counter.increment();

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(source)) {
                indegree[edge.destination]++;
                counter.increment();
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        // Add vertices with zero indegree
        for (int i = 0; i < vertices; i++) {
            counter.increment();

            if (indegree[i] == 0) {
                queue.add(i);
                counter.increment();
            }
        }

        List<Integer> order = new ArrayList<>();

        // Process vertices
        while (!queue.isEmpty()) {
            counter.increment();

            int current = queue.poll();
            order.add(current);

            for (Graph.Edge edge :
                    graph.getAdjacencyList().get(current)) {

                counter.increment();
                indegree[edge.destination]--;

                if (indegree[edge.destination] == 0) {
                    queue.add(edge.destination);
                    counter.increment();
                }
            }
        }

        boolean hasCycle = order.size() != vertices;
        counter.increment();

        return new Result(order, hasCycle,
                counter.getOperations());
    }
}
