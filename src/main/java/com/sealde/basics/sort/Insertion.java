package com.sealde.basics.sort;

import java.util.Comparator;

/**
 * @Author: sealde
 * @Date: 2020/2/4 下午8:48
 */
public class Insertion {
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && SortHelper.less(a[j], a[j-1]); j--) {
                SortHelper.exch(a, j, j-1);
            }
        }
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        int n = a.length;
        for (int i = lo+1; i < hi; i++) {
            for (int j = i; j > lo && SortHelper.less(a[j], a[j-1]); j--) {
                SortHelper.exch(a, j, j-1);
            }
        }
    }

    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && SortHelper.less(comparator, a[j], a[j-1]); j--) {
                SortHelper.exch(a, j, j-1);
            }
        }
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
