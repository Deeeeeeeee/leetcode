package com.sealde.basics.graph.directed;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;
import com.sealde.basics.datastruct.stack.ResizingArrayStack;

public class BreadthFirstDirectedPaths {
    private static final int INFINIT = Integer.MAX_VALUE;
    private boolean marked[];
    private int edgeTo[];                                   // 表示已经走过的路径 last edge on shortest s->v path
    private int distTo[];                                   // 表示距离 distTo[v] = length of shortest s->v path

    public BreadthFirstDirectedPaths(Digraph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        validateVertex(s);
        bfs(g, s);
    }

    public BreadthFirstDirectedPaths(Digraph g, Iterable<Integer> sources) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        validateVertices(sources);
        bfs(g, sources);
    }

    private void bfs(Digraph g, int s) {
        ResizingArrayQueue<Integer> queue = new ResizingArrayQueue<>();
        for (int v = 0; v < g.V(); v++) {
            distTo[v] = INFINIT;
        }
        marked[s] = true;
        distTo[s] = 0;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
            }
        }
    }

    private void bfs(Digraph g, Iterable<Integer> sources) {
        ResizingArrayQueue<Integer> queue = new ResizingArrayQueue<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            queue.enqueue(s);
        }

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * s -> v 的距离
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * 从目标往 parent 向上寻找，需要放到 stack 里，将这个结果反转
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        ResizingArrayStack<Integer> path = new ResizingArrayStack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
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
        Digraph G = new Digraph(13);
        for (int i = 0; i < input.length/2; i++) {
            G.addEdge(Integer.parseInt(input[i*2]), Integer.parseInt(input[i*2+1]));
        }

        int s = Integer.parseInt(args[0]);
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.printf("%d to %d:  ", s, v);
                for (int x : bfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else System.out.print("->" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d:  not connected\n", s, v);
            }
        }
    }
}
