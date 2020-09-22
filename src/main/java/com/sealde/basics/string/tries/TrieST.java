package com.sealde.basics.string.tries;

import edu.princeton.cs.algs4.Queue;

/**
 * R-way tries
 *
 * key 保存的是字符，而不是字符串
 * 每个节点都保存字母表的字符个数
 */
public class TrieST<Value> {
    private static final int R = 256;        // extended ASCII

    private Node root;
    private int n;

    // R-way 节点
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public TrieST() {}

    // 寻找 key 对应的值
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException();
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    // 迭代遍历，沿着字符串的字符寻找
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) delete(key);
        else put(root, key, val, 0);
    }

    // 迭代遍历，沿着字符串的字符进行。如果 node 为 null 则 new Node
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    public void delete(String key) {

    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // 所有 key 的集合
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    // 匹配 prefix 前缀的 key
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) throw new IllegalArgumentException();
        Queue<String> queue = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
