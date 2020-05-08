package com.sealde.basics.graph.undirected;

public class NonrecursiveDFS {
    private boolean marked[];

    public NonrecursiveDFS(Graph g, int s) {
        marked = new boolean[g.V()];
        validateVertex(s);

        // todo
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
}
