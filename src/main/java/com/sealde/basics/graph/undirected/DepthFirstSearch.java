package com.sealde.basics.graph.undirected;

public class DepthFirstSearch {
    private boolean marked[];
    private int count;          // 连接 s 的顶点数

    public DepthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        validateVertex(s);
        dfs(g, s);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * 递归地方法。遍历 adj 数据（顶点相连的其他顶点），判断是否标记过
     */
    private void dfs(Graph g, int v) {
        count++;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int count() {
        return count;
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

        int s = Integer.parseInt(args[0]);
        DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                System.out.printf(v + " ");
        }

        System.out.println();
        if (search.count() != G.V()) System.out.println("NOT connected");
        else                         System.out.println("connected");
    }
}
