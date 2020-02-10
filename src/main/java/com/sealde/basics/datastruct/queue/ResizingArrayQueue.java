package com.sealde.basics.datastruct.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Author: sealde
 * @Date: 2020/2/4 下午3:20
 */
public class ResizingArrayQueue<T> implements Iterable<T> {
    private Object[] arr;
    private int size = 0;
    private int first = 0;
    private int last = 0;

    public ResizingArrayQueue() {
        this.arr = new Object[2];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void enqueue(T item) {
        if (this.arr.length == this.size) {
            resize(this.size*2);
        }
        this.arr[last++] = item;
        // wrap-around
        if (this.last == this.arr.length) {
            this.last = 0;
        }
        this.size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue overflow.");
        }
        // 从头部dequeue
        T item = (T) this.arr[first];
        this.arr[first++] = null;
        this.size--;
        // wrap-around
        if (this.first == this.arr.length) {
            this.first = 0;
        }
        // 缩容，size为容量的1/4时
        if (this.size > 0 && this.size == this.arr.length/4) {
            resize(this.arr.length/2);
        }
        return item;
    }

    public int size() {
        return this.size;
    }

    private void resize(int capacity) {
        Object[] copy = new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            copy[i] = this.arr[first+i];
        }
        this.arr = copy;
        // 扩容之后，first和last需要修改
        this.first = 0;
        this.last = this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = (T) arr[i + first];
            this.i++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        String[] strings = new String[]{"this", "is", "a", "-", "a", "-", "-", "test", "-", "-"};
        for (String item: strings) {
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                System.out.println(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}
