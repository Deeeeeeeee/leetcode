package com.sealde.basics.string.tries;

/**
 * R-way tries
 */
public class TrieST<Value> {
    private static final int R = 256;        // extended ASCII

    // R-way 节点
    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    
}
