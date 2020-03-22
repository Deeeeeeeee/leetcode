package com.sealde.basics.sort.heap;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class MinPQ<T> {
    private int n = 0;
    private T[] arr;
    private Comparator<T> comparator;

    public MinPQ() {
        this.arr = (T[]) new Object[20];
    }

    public MinPQ(Comparator<T> comparator) {
        this.arr = (T[]) new Object[20];
        this.comparator = comparator;
    }

    public void insert(T x) {
        // 容量不够，扩容成原来的 2 倍
        if (this.n == this.arr.length - 1) {
            this.resize(this.arr.length);
        }
        this.arr[++n] = x;
        this.swim(n);
    }

    public T delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow.");
        }
        T item = this.arr[1];
        this.exch(1, n--);
        this.sink(1);
        this.arr[n+1] = null;       // 释放空间
        // 存储数据太少，缩容成原来的 1/2
        if (this.n > 0 && this.n == (this.arr.length-1)/4) {
            this.resize(this.arr.length/2);
        }
        return item;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public T max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow.");
        }
        return this.arr[1];
    }

    public int size() {
        return  this.n;
    }

    private void swim(int k) {
        while (k > 1 && this.greater(k/2, k)) {
            this.exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= this.n) {
            int c = 2*k;
            if (c < this.n && this.greater(c, c+1)) {
                c++;
            }
            if (!this.greater(k, c)) {
                break;
            }
            this.exch(k, c);
            k = c;
        }
    }

    private void resize(int capacity) {
        T[] copy = (T[]) new Object[capacity];
        System.arraycopy(this.arr, 1, copy, 1, capacity);
        this.arr = copy;
    }

    private void exch(int i, int j) {
        T swap = this.arr[i];
        this.arr[i] = this.arr[j];
        this.arr[j] = swap;
    }

    private boolean greater(int i, int j) {
        if (this.comparator == null) {
            return ((Comparable<T>) this.arr[i]).compareTo(this.arr[j]) > 0;
        } else {
            return this.comparator.compare(this.arr[i], this.arr[j]) > 0;
        }
    }

    public static void main(String[] args) {
        MinPQ<String> pq = new MinPQ<>();
        String[] strings = new String[]{"this", "is", "-", "a", "-", "-", "test", "-"};
        for (String item: strings) {
            if (!item.equals("-"))
                pq.insert(item);
            else if (!pq.isEmpty())
                System.out.println(pq.delMin() + " ");
        }
        System.out.println("(" + pq.size() + " left on stack)");
    }
}
