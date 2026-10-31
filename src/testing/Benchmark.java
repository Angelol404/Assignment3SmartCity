
package testing;

import graph.Graph;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.StronglyConnectedComponents;
import shortestpath.Dijkstra;

public class Benchmark {

    private static final int RUNS = 5;
    private static final int WARMUP_RUNS = 2;
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

        System.out.printf(
                "%-8s %-8s %-16s %-14s %-16s %-14s%n",
                "Vertices", "Edges",
                "Kruskal(ns)", "Kruskal(ops)",
                "Prim(ns)", "Prim(ops)"
        );

        for (int[] config : configurations) {

            int vertices = config[0];
            int edges = config[1];

            long kruskalTime = 0;
            long primTime = 0;
            long kruskalOps = 0;
            long primOps = 0;

            // JVM warm-up
            for (int run = 0; run < WARMUP_RUNS; run++) {

                Graph graph = GraphGenerator.generateUndirectedGraph(
                        vertices, edges, SEED + run
                );

                Kruskal.findMST(graph);
                Prim.findMST(graph);
            }

            // Measured runs
            for (int run = 0; run < RUNS; run++) {

                Graph graph = GraphGenerator.generateUndirectedGraph(
                        vertices, edges, SEED + 100 + run
                );

                long start = System.nanoTime();
                Kruskal.Result kruskal = Kruskal.findMST(graph);
                kruskalTime += System.nanoTime() - start;
                kruskalOps += kruskal.operations;

                start = System.nanoTime();
                Prim.Result prim = Prim.findMST(graph);
                primTime += System.nanoTime() - start;
                primOps += prim.operations;
            }

            System.out.printf(
                    "%-8d %-8d %-16d %-14d %-16d %-14d%n",
                    vertices, edges,
                    kruskalTime / RUNS,
                    kruskalOps / RUNS,
                    primTime / RUNS,
                    primOps / RUNS
            );
        }
    }

    private static void benchmarkDirected(int[][] configurations) {

        System.out.printf(
                "%-8s %-8s %-14s %-14s %-14s %-14s %-14s %-14s%n",
                "Vertices", "Edges",
                "Dijkstra(ns)", "Dijkstra(ops)",
                "Topo(ns)", "Topo(ops)",
                "SCC(ns)", "SCC(ops)"
        );

        for (int[] config : configurations) {

            int vertices = config[0];
            int edges = config[1];

            long dijkstraTime = 0;
            long topoTime = 0;
            long sccTime = 0;

            long dijkstraOps = 0;
            long topoOps = 0;
            long sccOps = 0;

            // JVM warm-up
            for (int run = 0; run < WARMUP_RUNS; run++) {

                Graph dag = GraphGenerator.generateDirectedDAG(
                        vertices, edges, SEED + run
                );

                Graph directed = GraphGenerator.generateDirectedGraph(
                        vertices, edges, SEED + 100 + run
                );

                Dijkstra.findShortestPaths(directed, 0);
                TopologicalSort.sort(dag);
                StronglyConnectedComponents.findSCCWithStats(directed);
            }

            // Measured runs
            for (int run = 0; run < RUNS; run++) {

                Graph dag = GraphGenerator.generateDirectedDAG(
                        vertices, edges, SEED + 200 + run
                );

                Graph directed = GraphGenerator.generateDirectedGraph(
                        vertices, edges, SEED + 300 + run
                );

                long start = System.nanoTime();
                Dijkstra.Result dijkstra =
                        Dijkstra.findShortestPaths(directed, 0);
                dijkstraTime += System.nanoTime() - start;
                dijkstraOps += dijkstra.operations;

                start = System.nanoTime();
                TopologicalSort.Result topo = TopologicalSort.sort(dag);
                topoTime += System.nanoTime() - start;
                topoOps += topo.operations;

                start = System.nanoTime();
                StronglyConnectedComponents.Result scc =
                        StronglyConnectedComponents.findSCCWithStats(directed);
                sccTime += System.nanoTime() - start;
                sccOps += scc.operations;
            }

            System.out.printf(
                    "%-8d %-8d %-14d %-14d %-14d %-14d %-14d %-14d%n",
                    vertices, edges,
                    dijkstraTime / RUNS,
                    dijkstraOps / RUNS,
                    topoTime / RUNS,
                    topoOps / RUNS,
                    sccTime / RUNS,
                    sccOps / RUNS
            );
        }
    }
}
