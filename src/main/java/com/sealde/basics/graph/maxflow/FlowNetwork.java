package com.sealde.basics.graph.maxflow;

import com.sealde.basics.datastruct.bag.Bag;
import edu.princeton.cs.algs4.StdRandom;

public class FlowNetwork {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;        // 顶点的数量
    private int E;        // 边界的数量
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException();
        this.V = V;
        this.E = 0;
        this.adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public FlowNetwork(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException();
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double capacity = StdRandom.uniform(100);
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public void addEdge(FlowEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    // return list of all edges - excludes self loops
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
