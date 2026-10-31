
package testing;

import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.StronglyConnectedComponents;
import shortestpath.Dijkstra;

public class AlgorithmTests {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }
        System.out.println("PASSED: " + message);
    }

    public static void main(String[] args) {

        System.out.println("___ALGORITHM TESTS___");

        // Test 1: Single vertex
        Graph singleVertex = new Graph(1, false);

        Kruskal.Result kruskalSingle =
                Kruskal.findMST(singleVertex);
        Prim.Result primSingle =
                Prim.findMST(singleVertex);

        check(kruskalSingle.connected, "Kruskal: single vertex");
        check(primSingle.connected, "Prim: single vertex");
        check(kruskalSingle.totalWeight == 0, "Single vertex MST weight");

        // Test 2: Disconnected graph
        Graph disconnected = new Graph(4, false);
        disconnected.addEdge(0, 1, 1);
        disconnected.addEdge(2, 3, 2);

        Kruskal.Result kruskalDisconnected =
                Kruskal.findMST(disconnected);
        Prim.Result primDisconnected =
                Prim.findMST(disconnected);

        check(!kruskalDisconnected.connected,
                "Kruskal: disconnected graph");
        check(!primDisconnected.connected,
                "Prim: disconnected graph");

        // Test 3: Multiple MSTs (equal edge weights)
        Graph equalWeights = new Graph(3, false);
        equalWeights.addEdge(0, 1, 1);
        equalWeights.addEdge(1, 2, 1);
        equalWeights.addEdge(0, 2, 1);

        Kruskal.Result kruskalEqual =
                Kruskal.findMST(equalWeights);
        Prim.Result primEqual =
                Prim.findMST(equalWeights);

        check(kruskalEqual.totalWeight == 2,
                "Kruskal: multiple possible MSTs");
        check(primEqual.totalWeight == 2,
                "Prim: multiple possible MSTs");

        // Test 4: Cycle detection
        Graph cyclic = new Graph(3, true);
        cyclic.addEdge(0, 1, 1);
        cyclic.addEdge(1, 2, 1);
        cyclic.addEdge(2, 0, 1);

        TopologicalSort.Result topoResult =
                TopologicalSort.sort(cyclic);

        check(topoResult.hasCycle,
                "Topological sort: cycle detection");

        // Test 5: Strongly connected components
        Graph sccGraph = new Graph(5, true);
        sccGraph.addEdge(0, 1, 1);
        sccGraph.addEdge(1, 0, 1);
        sccGraph.addEdge(1, 2, 1);
        sccGraph.addEdge(2, 3, 1);
        sccGraph.addEdge(3, 2, 1);
        sccGraph.addEdge(3, 4, 1);

        check(
                StronglyConnectedComponents.findSCC(sccGraph).size() == 3,
                "Kosaraju: strongly connected components"
        );

        // Test 6: Unreachable vertex in Dijkstra
        Graph shortestPathGraph = new Graph(4, true);
        shortestPathGraph.addEdge(0, 1, 4);
        shortestPathGraph.addEdge(1, 2, 3);

        Dijkstra.Result dijkstraResult =
                Dijkstra.findShortestPaths(shortestPathGraph, 0);

        check(
                dijkstraResult.distances[3] == Integer.MAX_VALUE,
                "Dijkstra: unreachable vertex"
        );

        System.out.println();
        System.out.println("All tests completed successfully!");
    }
}
