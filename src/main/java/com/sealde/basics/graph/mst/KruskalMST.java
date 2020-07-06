package com.sealde.basics.graph.mst;

import com.sealde.basics.datastruct.UF;
import com.sealde.basics.sort.heap.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;
    private Queue<Edge> mst = new Queue<>();

    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    /**
     * 最小生成树所有的边界
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * mst 最小生成树的权重
     */
    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
    }
}
