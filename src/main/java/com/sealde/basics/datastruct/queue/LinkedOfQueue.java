package com.sealde.basics.datastruct.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 尾部enqueue，头部dequeue
 * @Author: sealde
 * @Date: 2020/2/3 下午6:40
 */
public class LinkedOfQueue<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public LinkedOfQueue() {
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void enqueue(T item) {
        // 旧的last节点指向新的节点
        Node<T> oldLast = this.last;
        this.last = new Node<>();
        this.last.item = item;
        this.last.next = null;
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldLast.next = this.last;
        }
        this.size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue overflow.");
        }
        // first指向一下个节点
        Node<T> oldFirst = this.first;
        if (this.first == this.last) {
            this.last = null;
        } else {
            this.first = oldFirst.next;
        }
        T item = oldFirst.item;
        oldFirst.item = null;
        oldFirst.next = null;
        this.size--;
        return item;
    }

    public int size() {
        return this.size;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        LinkedOfQueue<String> queue = new LinkedOfQueue<>();
        String[] strings = new String[]{"this", "is", "-", "a", "-", "-", "test", "-"};
        for (String item: strings) {
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                System.out.println(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}
