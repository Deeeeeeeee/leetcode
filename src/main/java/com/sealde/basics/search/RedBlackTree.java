package com.sealde.basics.search;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

import java.util.NoSuchElementException;

public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node<K> root;

    public RedBlackTree() {}

    /****************************************
     * Node helper methods
     ****************************************/
    private boolean isRed(Node<K> node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    private int size(Node<K> node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    /****************************************
     * llrb basic ops
     ****************************************/
    private Node<K> rotateLeft(Node<K> node) {
        Node<K> x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        x.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return x;
    }

    private Node<K> rotateRight(Node<K> node) {
        Node<K> x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        x.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return x;
    }

    private void flipColors(Node<K> node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    // 修改前： node red, left black, left-left black
    // 修改后： left 或者 left 的子节点为 red
    private Node<K> moveRedLeft(Node<K> node) {
        flipColors(node);
        // 节点往左边合并
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    // 修改前： node red, right black, right-right black
    // 修改后： right 或者 right 的子节点为 red
    private Node<K> moveRedRight(Node<K> node) {
        flipColors(node);
        // 节点往右边合并
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private Node<K> balance(Node<K> node) {
        // 如果 right red, left black；对 node 左旋
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        // 如果 left red, left-left red；对 node 右旋
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        // 如果 left red, right red；对 node flip
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    /****************************************
     * Standard BST search
     ****************************************/
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

    /****************************************
     * llrb insertion
     ****************************************/
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls put() with a null key.");
        }
        root = put(root, key, value);
        // 跟节点为 black
        root.color = BLACK;
    }

    private Node<K> put(Node<K> node, K key, V value) {
        // 新增节点默认为 red
        if (node == null) {
            return new Node<>(key, value, 1, RED);
        }

        // 正常的 BST 插入操作
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.val = value;
        }

        // 如果 right red, left black；对 node 左旋
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        // 如果 left red, left-left red；对 node 右旋
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        // 如果 left red, right red；对 node flip
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /****************************************
     * llrb deletion 删除部分看不懂
     ****************************************/
    public void delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow.");
        }

        // 兼容 moveRedLeft 和 moveRedRight 操作
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node<K> delMin(Node<K> node) {
        if (node.left == null) {
            return node.right;
        }

        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }

        node.left = delMin(node.left);
        return balance(node);
    }

    public void delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow.");
        }

        // 兼容 moveRedLeft 和 moveRedRight 操作
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node<K> delMax(Node<K> node) {
        if (isRed(node.left)) {
            node = rotateRight(node);
        }

        if (node.right == null) {
            return null;
        }

        if (!isRed(node.right) && !isRed(node.right.left)) {
            node = moveRedRight(node);
        }

        node.right = delMax(node.right);
        return balance(node);
    }

    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls delete() with a null key.");
        }
        if (!contains(key)) {
            return;
        }

        // 兼容 moveRedLeft 和 moveRedRight 操作
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node<K> delete(Node<K> node, K key) {
        // 先搜索需要删除的节点
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node = delete(node.left, key);
        } else {
            if (isRed(node.left)) {
                node = rotateRight(node);
            }
            if (cmp == 0 && node.right == null) {
                return null;
            }
            if (!isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }
            if (cmp == 0) {
                // node 有两个字节点时，删除右子树最小的节点 x。再把这个 x 节点替换掉 t
                Node<K> t = min(node.right);
                node.key = t.key;
                node.val = t.val;
                node.right = delMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
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
        private boolean color;

        public Node() {}

        public Node(K key, V val, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
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
