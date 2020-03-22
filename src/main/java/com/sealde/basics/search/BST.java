package com.sealde.basics.search;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> {
    private Node<K> root;

    public BST() {}

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node<K> node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null.");
        }
        return get(key) != null;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node<K> node, K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls get() with a null key.");
        }
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.val;
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls put() with a null key.");
        }
        root = put(root, key, value);
    }

    private Node<K> put(Node<K> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.val = value;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow.");
        }
        root = delMin(root);
    }

    private Node<K> delMin(Node<K> node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = delMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow.");
        }
        root = delMax(root);
    }

    private Node<K> delMax(Node<K> node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = delMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls delete() with a null key.");
        }
        root = delete(root, key);
    }

    /**
     * Hibbard deletion
     * 这个删除可能会让树变得不平衡，树高变得很高
     */
    private Node<K> delete(Node<K> node, K key) {
        if (node == null) {
            return null;
        }
        // 先搜索需要删除的节点
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node = delete(node.left, key);
        } else if (cmp > 0) {
            node = delete(node.right, key);
        } else {
            // 没有子节点或只有一个子节点时，直接删除或者跟 delMin 一样进行删除
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // node 有两个字节点时，删除右子树最小的节点 x。再把这个 x 节点替换掉 t
            Node<K> t = node;
            node = min(node.right);
            node.right = delMin(node.right);
            node.left = t.left;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public K floor(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() with a null key.");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table.");
        }
        Node<K> node = floor(root, key);
        if (node == null) {
            throw new NoSuchElementException("argument to floor() is too small.");
        } else {
            return node.key;
        }
    }

    private Node<K> floor(Node<K> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return floor(node.left, key);
        }
        Node<K> n = floor(node.right, key);
        if (n == null) {
            return node;
        } else {
            return n;
        }
    }

    public K ceiling(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() with a null key.");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table.");
        }
        Node<K> node = ceiling(root, key);
        if (node == null) {
            throw new NoSuchElementException("argument to ceiling() is too small.");
        } else {
            return node.key;
        }
    }

    private Node<K> ceiling(Node<K> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp > 0) {
            return ceiling(node.right, key);
        }
        Node<K> n = ceiling(node.left, key);
        if (n == null) {
            return node;
        } else {
            return n;
        }
    }

    public K select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        return select(root, k).key;
    }

    public Node<K> select(Node<K> node, int k) {
        if (node.left == null) {
            return null;
        }
        int ls = size(node.left);
        if (k < ls) {
            return select(node.left, k);
        } else if (k > ls) {
            return select(node.right, k-ls-1);
        } else {
            return node;
        }
    }

    public int rank(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() with a null key.");
        }
        return rank(root, key);
    }

    private int rank(Node<K> node, K key) {
        if (node == null) {
            return 0;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return rank(node.left, key);
        } else if (cmp > 0) {
            return 1 + size(node.left) + rank(node.right, key);
        } else {
            return size(node.left);
        }
    }

    public K min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table.");
        }
        return min(root).key;
    }

    private Node<K> min(Node<K> node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }

    public K max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table.");
        }
        return max(root).key;
    }

    private Node<K> max(Node<K> node) {
        if (node.right == null) {
            return node;
        } else {
            return max(node.right);
        }
    }

    public Iterable<K> keys() {
        if (isEmpty()) {
            return new ResizingArrayQueue<>();
        }
        return keys(min(), max());
    }

    private Iterable<K> keys(K lo, K hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null.");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null.");
        }

        ResizingArrayQueue<K> queue = new ResizingArrayQueue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    // 中序遍历。左子树 -> 根节点 -> 右子树
    private void keys(Node<K> node, ResizingArrayQueue<K> queue, K lo, K hi) {
        if (node == null) {
            return;
        }
        int loCmp = node.key.compareTo(lo);
        int hiCmp = node.key.compareTo(hi);
        if (loCmp > 0) {
            keys(node.left, queue, lo, hi);
        }
        if (loCmp >= 0 && hiCmp <= 0) {
            queue.enqueue(node.key);
        }
        if (hiCmp < 0) {
            keys(node.right, queue, lo, hi);
        }
    }

    private class Node<K> {
        private Node<K> left;
        private Node<K> right;
        private K key;
        private V val;
        private int size;

        public Node() {}

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        BST<String, Integer> st = new BST<>();
        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);
        st.put("a", 4);
        st.put("d", 5);

        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
