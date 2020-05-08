package com.sealde.basics.search;

import com.sealde.basics.datastruct.queue.ResizingArrayQueue;

public class SequentialSearchST<Key, Value> {
    private int n;
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
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

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = val;
                return;
            }
        }
        first = new Node(key, val, first);
        n++;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("first argument to delete() is null");
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    public Iterable<Key> keys() {
        ResizingArrayQueue<Key> queue = new ResizingArrayQueue<>();
        for (Node x = first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] input = new String[] {"just", "a", "simple", "test", "!"};
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
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
