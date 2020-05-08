package com.sealde.basics.graph.directed;

import com.sealde.basics.datastruct.bag.Bag;
import com.sealde.basics.datastruct.stack.ResizingArrayStack;

public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;

    public Digraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Digraph(Digraph g) {
        if (g == null) {
            throw new IllegalArgumentException("argument is null");
        }

        this.V = g.V();
        this.E = g.E();
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }

        indegree = new int[V];
        for (int i = 0; i < V; i++) {
            indegree[i] = g.indegree(i);
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
     * 给 v, w 连接. adj 添加即可
     * v -> w
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
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
     * 返回 v 顶点出发到其他顶点个数
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 指向 v 顶点的顶点
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
