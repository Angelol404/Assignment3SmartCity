import csv
import os
import matplotlib.pyplot as plt

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
CSV_FILE = os.path.join(
    BASE_DIR, "results", "tables", "full_benchmark.csv"
)
PLOTS_DIR = os.path.join(BASE_DIR, "results", "plots")

os.makedirs(PLOTS_DIR, exist_ok=True)

with open(CSV_FILE, "r", newline="", encoding="utf-8") as file:
    data = list(csv.DictReader(file))

vertices = [int(row["Vertices"]) for row in data]
edges = [int(row["Edges"]) for row in data]

algorithms = {
    "Kruskal": "Kruskal_ns",
    "Prim": "Prim_ns",
    "Dijkstra": "Dijkstra_ns",
    "Topological Sort": "Topological_ns",
    "SCC": "SCC_ns"
}

# Plot 1: Execution time vs vertices
plt.figure(figsize=(10, 6))

for name, column in algorithms.items():
    values = [int(row[column]) for row in data]
    plt.plot(vertices, values, marker="o", label=name)

plt.xlabel("Number of Vertices")
plt.ylabel("Average Execution Time (ns)")
plt.title("Execution Time vs Number of Vertices")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(os.path.join(PLOTS_DIR, "execution_time_vs_vertices.png"))
plt.close()

# Plot 2: Execution time vs edges
plt.figure(figsize=(10, 6))

for name, column in algorithms.items():
    values = [int(row[column]) for row in data]
    plt.plot(edges, values, marker="o", label=name)

plt.xlabel("Number of Edges")
plt.ylabel("Average Execution Time (ns)")
plt.title("Execution Time vs Number of Edges")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(os.path.join(PLOTS_DIR, "execution_time_vs_edges.png"))
plt.close()

print("Both plots were generated successfully!")
