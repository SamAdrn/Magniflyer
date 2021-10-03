package magniflyer.flight;

import java.util.LinkedList;

public class WeightedGraph {

    static class Edge {
        int origin;
        int destination;
        int weight;

        public Edge(int origin, int destination, int weight) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
        }
    }

    int vertices;
    LinkedList<Edge>[] adjacencyList;

    public WeightedGraph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        //initialize adjacency lists for all the vertices
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList[source].addFirst(edge); //for directed graph
    }


}
