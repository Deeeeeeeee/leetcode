package com.sealde.basics.graph.undirected;

import com.sealde.basics.datastruct.stack.ResizingArrayStack;

public class DepthFirstPaths {
    private boolean marked[];
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        validateVertex(s);
        dfs(g, s);
    }

    /**
     * 递归地方法。遍历 adj 数据（顶点相连的其他顶点），判断是否标记过
     * 目标 w 保存其 parent 值 v
     */
    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
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
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
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
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d:  not connected\n", s, v);
            }
        }
    }
}
