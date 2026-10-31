
package testing;

import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.StronglyConnectedComponents;
import shortestpath.Dijkstra;

public class Benchmark {

    private static final int RUNS = 5;
    private static final long SEED = 12345L;

    public static void main(String[] args) {

        int[][] configurations = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };

        System.out.println("___MST BENCHMARK___");
        benchmarkMST(configurations);

        System.out.println("\n___DIRECTED GRAPH BENCHMARK___");
        benchmarkDirected(configurations);
    }

    private static void benchmarkMST(int[][] configurations) {

        System.out.printf("%-10s %-10s %-18s %-18s%n",
                "Vertices", "Edges", "Kruskal (ns)", "Prim (ns)");

        for (int[] config : configurations) {

            int vertices = config[0];
            int edges = config[1];

            long kruskalTotal = 0;
            long primTotal = 0;

            for (int run = 0; run < RUNS; run++) {

                Graph graph = GraphGenerator.generateUndirectedGraph(
                        vertices, edges, SEED + run);

                long start = System.nanoTime();
                Kruskal.findMST(graph);
                kruskalTotal += System.nanoTime() - start;

                start = System.nanoTime();
                Prim.findMST(graph);
                primTotal += System.nanoTime() - start;
            }

            System.out.printf("%-10d %-10d %-18d %-18d%n",
                    vertices, edges,
                    kruskalTotal / RUNS,
                    primTotal / RUNS);
        }
    }

    private static void benchmarkDirected(int[][] configurations) {

        System.out.printf("%-10s %-10s %-18s %-18s %-18s%n",
                "Vertices", "Edges", "Dijkstra (ns)",
                "Topological (ns)", "SCC (ns)");

        for (int[] config : configurations) {

            int vertices = config[0];
            int edges = config[1];

            long dijkstraTotal = 0;
            long topoTotal = 0;
            long sccTotal = 0;

            for (int run = 0; run < RUNS; run++) {

                Graph dag = GraphGenerator.generateDirectedDAG(
                        vertices, edges, SEED + run);

                Graph directed = GraphGenerator.generateDirectedGraph(
                        vertices, edges, SEED + 100 + run);

                // Dijkstra uses a directed graph with non-negative weights
                long start = System.nanoTime();
                Dijkstra.findShortestPaths(directed, 0);
                dijkstraTotal += System.nanoTime() - start;

                start = System.nanoTime();
                TopologicalSort.sort(dag);
                topoTotal += System.nanoTime() - start;

                start = System.nanoTime();
                StronglyConnectedComponents.findSCC(directed);
                sccTotal += System.nanoTime() - start;
            }

            System.out.printf("%-10d %-10d %-18d %-18d %-18d%n",
                    vertices, edges,
                    dijkstraTotal / RUNS,
                    topoTotal / RUNS,
                    sccTotal / RUNS);
        }
    }
}
