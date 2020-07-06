package com.sealde.homework.graph.wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class SAP {
//    private HashMap<Integer, BreadthFirstDirectedPaths> container;
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int result = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(g, w);
        for (int n = 0; n < g.V(); n++) {
            if (vbfs.hasPathTo(n) && wbfs.hasPathTo(n)) {
                int newDistance = vbfs.distTo(n) + wbfs.distTo(n);
                if (newDistance < result) {
                    result = newDistance;
                }
            }
        }
        if (result == Integer.MAX_VALUE) {
            return -1;
        }
        return result;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int result =- 1;
        int shortLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(g, w);
        for (int n = 0; n < g.V(); n++) {
            if (vbfs.hasPathTo(n) && wbfs.hasPathTo(n)) {
                int newDistance = vbfs.distTo(n) + wbfs.distTo(n);
                if (newDistance < shortLength) {
                    shortLength = newDistance;
                    result = n;
                }
            }
        }
        return result;
    }

    private void check(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException();
        }
        for (Integer i : v) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        check(v);
        check(w);
        int result = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(g, w);
        for (int n = 0; n < g.V(); n++) {
            if (vbfs.hasPathTo(n) && wbfs.hasPathTo(n)) {
                int newDistance = vbfs.distTo(n) + wbfs.distTo(n);
                if (newDistance < result) {
                    result = newDistance;
                }
            }
        }
        if (result == Integer.MAX_VALUE) {
            return -1;
        }
        return result;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        check(v);
        check(w);
        int result =- 1;
        int shortLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(g, w);
        for (int n = 0; n < g.V(); n++) {
            if (vbfs.hasPathTo(n) && wbfs.hasPathTo(n)) {
                int newDistance = vbfs.distTo(n) + wbfs.distTo(n);
                if (newDistance < shortLength) {
                    shortLength = newDistance;
                    result = n;
                }
            }
        }
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
