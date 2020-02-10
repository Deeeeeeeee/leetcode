package com.sealde.basics.datastruct.stack;

/**
 * @Author: sealde
 * @Date: 2020/2/3 下午4:44
 */
public class FixedCapacityStack<T> {
    private Object[] arr = null;
    private int size = 0;

    public FixedCapacityStack(int capacity) {
        this.arr = new Object[capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T item) {
        this.arr[size++] = item;
    }

    public T pop() {
        Object item = this.arr[--size];
        this.arr[size] = null;
        return (T) item;
    }
}
