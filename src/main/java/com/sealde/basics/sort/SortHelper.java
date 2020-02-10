package com.sealde.basics.sort;

import java.util.Comparator;

/**
 * @Author: sealde
 * @Date: 2020/2/4 下午8:01
 */
public class SortHelper {
    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static boolean less(Comparator comparator, Object a, Object b) {
        return comparator.compare(a, b) < 0;
    }

    public static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void show(Comparable[] a) {
        for (Comparable item : a) {
            System.out.println(item);
        }
    }
}
