package com.sealde.basics.graph.undirected;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

public class CC {
    private boolean marked[];
    private int id[];
    private int size[];
    private int count;

    public CC(Graph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        size = new int[g.V()];
        count = 0;
        // 遍历所有的顶点，如果没有标记过，则进行 dfs
        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }

    /**
     * 标记，记录 id
     * 如果没有标记过，则递归 dfs.
     */
    private void dfs(Graph g, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public int count() {
        return count;
    }

    /**
     * 判断是否相连
     */
    public boolean connected(int w, int v) {
        validateVertex(w);
        validateVertex(v);
        return id(w) == id(v);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
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

        CC cc = new CC(G);

        // number of connected components
        int m = cc.count();
        System.out.println(m + " components");

        // compute list of vertices in each connected component
        ResizingArrayQueue<Integer>[] components = (ResizingArrayQueue<Integer>[]) new ResizingArrayQueue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new ResizingArrayQueue<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
