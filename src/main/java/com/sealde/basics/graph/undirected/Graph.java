package com.sealde.basics.graph.undirected;

import com.sealde.basics.datastruct.bag.Bag;
import com.sealde.basics.datastruct.stack.ResizingArrayStack;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer> adj[];

    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Graph(Graph g) {
        this.V = g.V();
        this.E = g.E();
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }

        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }

        // 使用 stack. 保证 adj 里面的顺序是一样的
        for (int v = 0; v < g.V(); v++) {
            ResizingArrayStack<Integer> reverse = new ResizingArrayStack<>();
            for (int w : g.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 给 v, w 连接. adj 互相添加即可
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    /**
     * 获取 v 顶点相连的顶点
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 返回 v 顶点连接个数
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < V; v++) {
            sb.append(v + ": ");
            for (int w : adj[v]) {
                sb.append(w + " ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] input = new String[] {
                "0", "5",
                "4", "3",
                "0", "1",
                "9", "12",
                "6", "4",
                "5", "4",
                "0", "2",
                "11", "12",
                "9", "10",
                "0", "6",
                "7", "8",
                "9", "11",
                "5", "3",
        };
        Graph G = new Graph(13);
        for (int i = 0; i < input.length/2; i++) {
            G.addEdge(Integer.parseInt(input[i*2]), Integer.parseInt(input[i*2+1]));
        }
        System.out.println(G);
    }
}
