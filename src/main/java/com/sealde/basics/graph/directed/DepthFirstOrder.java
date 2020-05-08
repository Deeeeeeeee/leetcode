package com.sealde.basics.graph.directed;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;
import com.sealde.basics.datastruct.stack.ResizingArrayStack;

public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre;                              // preOrder 的 num. 相当于层数
    private int[] post;
    private ResizingArrayQueue<Integer> preOrder;   // 正常顺序
    private ResizingArrayQueue<Integer> postOrder;  // 倒序
    private int preCount;
    private int postCount;

    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        pre = new int[G.V()];
        post = new int[G.V()];
        preOrder = new ResizingArrayQueue<>();
        postOrder = new ResizingArrayQueue<>();
        for (int v = 0; v < G.V(); v++) {
            dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCount++;
        preOrder.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post[v] = postCount++;
        postOrder.enqueue(v);
    }

    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    public Iterable<Integer> pre() {
        return preOrder;
    }

    public Iterable<Integer> post() {
        return postOrder;
    }

    public Iterable<Integer> reversePost() {
        ResizingArrayStack<Integer> stack = new ResizingArrayStack<>();
        for (int v : postOrder) {
            stack.push(v);
        }
        return stack;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
