package com.sealde.basics.datastruct.stack;

import java.util.NoSuchElementException;

/**
 * @Author: sealde
 * @Date: 2020/2/3 下午4:35
 */
public class LinkedOfStack<T> {
    private Node<T> first = null;
    private int size = 0;

    public LinkedOfStack() {
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return this.size;
    }

    public void push(T item) {
        Node<T> oldFirst = this.first;
        this.first = new Node<>();
        this.first.next = oldFirst;
        this.first.item = item;
        this.size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow.");
        }
        Node<T> oldFirst = this.first;
        this.first = oldFirst.next;
        T item = oldFirst.item;
        oldFirst.item = null;
        oldFirst.next = null;
        this.size--;
        return item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
    }

    public static void main(String[] args) {
        LinkedOfStack<String> stack = new LinkedOfStack<>();
        String[] strings = new String[]{"this", "is", "-", "a", "-", "-", "test", "-"};
        for (String item: strings) {
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                System.out.println(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}
