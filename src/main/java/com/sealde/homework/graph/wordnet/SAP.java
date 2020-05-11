package com.sealde.homework.graph.wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.HashMap;

public class SAP {
    private HashMap<Integer, BreadthFirstDirectedPaths> container;
    private Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(g, w);
        for (int n = 0; n < g.V(); n++) {
            if (vbfs.hasPathTo(n) && wbfs.hasPathTo(n)) {

            }
        }
        return 0;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
