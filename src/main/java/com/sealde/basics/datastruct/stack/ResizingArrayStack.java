package com.sealde.basics.datastruct.stack;

import java.util.NoSuchElementException;

/**
 * @Author: sealde
 * @Date: 2020/2/3 下午5:30
 */
public class ResizingArrayStack<T> {
    private int size;
    private T[] arr;

    public ResizingArrayStack() {
        this.arr = (T[]) new Object[20];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void push(T item) {
        // 达到容量上限的时候，扩容成原来的2倍
        if (this.size == this.arr.length) {
            resize(size*2);
        }
        this.arr[size++] = item;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow.");
        }
        Object item = this.arr[--size];
        this.arr[size] = null;
        // 存储的数量缩减到容量的1/4的时候，容量变成原来的1/2
        if (this.size > 0 && this.size == this.arr.length/4) {
            resize(this.arr.length/2);
        }
        return (T) item;
    }

    private void resize(int capacity) {
        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            copy[i] = this.arr[i];
        }
        this.arr = copy;
    }
}
