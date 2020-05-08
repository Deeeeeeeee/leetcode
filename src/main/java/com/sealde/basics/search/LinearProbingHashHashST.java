package com.sealde.basics.search;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

public class LinearProbingHashHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;      // 个数
    private int m;      // 容量
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashHashST(int capacity) {
        this.n = 0;
        this.m = capacity;
        this.keys = (Key[]) new Object[capacity];
        this.vals = (Value[]) new Object[capacity];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        LinearProbingHashHashST<Key, Value> tmp = new LinearProbingHashHashST<>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                tmp.put(keys[i], vals[i]);
            }
        }
        keys = tmp.keys;
        vals = tmp.vals;
        m = tmp.m;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * 不断向后寻找 hash 对应的 Key
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        for (int i = hash(key); keys[i] != null; i++) {
            if (key.equals(keys[i])) {
                return vals[i];
            }
        }
        return null;
    }

    /**
     * 如果冲突，不断地向后寻找 key 为 null 的下标插入
     */
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument to put() is null");
        }
        if (value == null) {
            delete(key);
            return;
        }

        if (n >= m/2) {
            resize(2*m);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i+1)%m) {
            if (keys[i].equals(key)) {
                vals[i] = value;
                return;
            }
        }
        keys[i] = key;
        vals[i] = value;
        n++;
    }

    /**
     * 不断地向后寻找 hash 对应的 key，然后删除
     * 再接着重新 rehash 一遍同一个 hash 的 key
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!contains(key)) {
            return;
        }

        int i = hash(key);
        while (!keys[i].equals(key)) {
            i = (i+1) % m;
        }

        vals[i] = null;
        keys[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        if (n > 0 && n <= m/8) {
            resize(m / 2);
        }
    }

    public Iterable<Key> keys() {
        ResizingArrayQueue<Key> queue = new ResizingArrayQueue<>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] input = new String[] {"just", "a", "simple", "test", "!"};
        LinearProbingHashHashST<String, Integer> st = new LinearProbingHashHashST<>();
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
