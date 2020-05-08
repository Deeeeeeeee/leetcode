package com.sealde.basics.graph.directed;

public class DirectedDFS {
    private boolean[] marked;
    private int count;

    public DirectedDFS(Digraph g, int s) {
        marked = new boolean[g.V()];
        validateVertex(s);
        dfs(g, s);
    }

    private void dfs(Digraph g, int v) {
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

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {
        String[] input = new String[] {
                "4",  "2",
                "2",  "3",
                "3",  "2",
                "6",  "0",
                "0",  "1",
                "2",  "0",
                "11",  "12",
                "12",  "9",
                "9",  "10",
                "9",  "11",
                "7",  "9",
                "10",  "12",
                "11",  "4",
                "4",  "3",
                "3",  "5",
                "6",  "8",
                "8",  "6",
        };
        Digraph G = new Digraph(17);
        for (int i = 0; i < input.length/2; i++) {
            G.addEdge(Integer.parseInt(input[i*2]), Integer.parseInt(input[i*2+1]));
        }

        int s = Integer.parseInt(args[0]);
        DirectedDFS dfs = new DirectedDFS(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) System.out.print(v + " ");
        }
        System.out.println();
    }
}
