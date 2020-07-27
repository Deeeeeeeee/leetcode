package com.sealde.basics.graph.shortestpath;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                throw new IllegalArgumentException();
            }
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        // validate
        validateVertex(s);

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq = new IndexMinPQ<>(G.V());
        pq.insert(0, distTo[s]);
        while (!pq.isEmpty()) {
            int index = pq.delMin();
            for (DirectedEdge e : G.adj(index)) {
                relax(e);
            }
        }

        // todo check
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        // 如果当前权重大于 from + e.weight，那么替换边界；否则什么事情都不干
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            // 如果 pq 已经有该顶点数据，那么更新数据；否则插入新增数据
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            } else {
                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * s 距离 v
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * s 是否能到达 v
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * s 到 v 的路径
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
