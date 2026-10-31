
package testing;

import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.StronglyConnectedComponents;
import shortestpath.Dijkstra;

public class Main {

    public static void main(String[] args) {

        // 1. Testing Kruskal and Prim
        System.out.println("___MST ALGORITHMS___");

        Graph undirectedGraph = new Graph(5, false);

        undirectedGraph.addEdge(0, 1, 2);
        undirectedGraph.addEdge(0, 2, 3);
        undirectedGraph.addEdge(1, 2, 1);
        undirectedGraph.addEdge(1, 3, 4);
        undirectedGraph.addEdge(2, 3, 5);
        undirectedGraph.addEdge(3, 4, 2);
        undirectedGraph.addEdge(2, 4, 6);

        Kruskal.Result kruskalResult =
                Kruskal.findMST(undirectedGraph);

        System.out.println("Kruskal:");
        System.out.println("Edges: " + kruskalResult.selectedEdges);
        System.out.println("Total weight: " + kruskalResult.totalWeight);
        System.out.println("Connected: " + kruskalResult.connected);

        Prim.Result primResult =
                Prim.findMST(undirectedGraph);

        System.out.println("\nPrim:");
        System.out.println("Edges: " + primResult.selectedEdges);
        System.out.println("Total weight: " + primResult.totalWeight);
        System.out.println("Connected: " + primResult.connected);

        // 2. Testing Topological Sort
        System.out.println("\n ___TOPOLOGICAL SORT___ ");

        Graph dag = new Graph(6, true);

        dag.addEdge(5, 2, 1);
        dag.addEdge(5, 0, 1);
        dag.addEdge(4, 0, 1);
        dag.addEdge(4, 1, 1);
        dag.addEdge(2, 3, 1);
        dag.addEdge(3, 1, 1);

        TopologicalSort.Result topoResult =
                TopologicalSort.sort(dag);

        System.out.println("Order: " + topoResult.order);
        System.out.println("Has cycle: " + topoResult.hasCycle);

        // 3. Testing Strongly Connected Components
        System.out.println("\n___STRONGLY CONNECTED COMPONENTS___");

        Graph directedGraph = new Graph(5, true);

        directedGraph.addEdge(0, 1, 1);
        directedGraph.addEdge(1, 2, 1);
        directedGraph.addEdge(2, 0, 1);
        directedGraph.addEdge(1, 3, 1);
        directedGraph.addEdge(3, 4, 1);
        directedGraph.addEdge(4, 3, 1);

        System.out.println(
                StronglyConnectedComponents.findSCC(directedGraph)
        );

        // 4. Testing Dijkstra
        System.out.println("\n___DIJKSTRA___");

        Graph shortestPathGraph = new Graph(5, false);

        shortestPathGraph.addEdge(0, 1, 4);
        shortestPathGraph.addEdge(0, 2, 1);
        shortestPathGraph.addEdge(2, 1, 2);
        shortestPathGraph.addEdge(1, 3, 1);
        shortestPathGraph.addEdge(2, 3, 5);
        shortestPathGraph.addEdge(3, 4, 3);

        Dijkstra.Result dijkstraResult =
                Dijkstra.findShortestPaths(shortestPathGraph, 0);

        dijkstraResult.printPaths(0);
    }
}
