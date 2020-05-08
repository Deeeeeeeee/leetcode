package com.sealde.basics.search;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // size
    private int m;  // 容量
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int m) {
        this.m = m;
        this.st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++) {
            this.st[i] = new SequentialSearchST<>();
        }
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> tmp = new SeparateChainingHashST<>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                tmp.put(key, st[i].get(key));
            }
        }
        this.m = tmp.m;
        this.n = tmp.n;
        this.st = tmp.st;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * 先 hash 寻找 i，然后遍历链表
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        int i = hash(key);
        return st[i].get(key);
    }

    /**
     * 先 hash 寻找 i，冲突放到链表
     */
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument to put() is null");
        }
        if (value == null) {
            delete(key);
            return;
        }

        if (n > 10*m) {
            resize(2*m);
        }

        int i = hash(key);
        if (!st[i].contains(key)) {
            n++;
        }
        st[i].put(key, value);
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        int i = hash(key);
        if (st[i].contains(key)) {
            n--;
        }
        st[i].delete(key);

        if (m > INIT_CAPACITY && n <= 2*m) {
            resize(m/2);
        }
    }

    public Iterable<Key> keys() {
        ResizingArrayQueue<Key> queue = new ResizingArrayQueue<>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] input = new String[] {"just", "a", "simple", "test", "!"};
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        int tmp = 0;
        for (String s : input) {
            st.put(s, tmp);
            tmp++;
        }

        // print keys
        for (String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
