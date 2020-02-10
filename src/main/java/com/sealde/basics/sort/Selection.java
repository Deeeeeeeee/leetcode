package com.sealde.basics.sort;

import java.util.Comparator;

/**
 * @Author: sealde
 * @Date: 2020/2/4 下午6:18
 */
public class Selection {
    private Selection() {}

    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (SortHelper.less(a[j], a[min])) {
                    min = j;
                }
            }
            SortHelper.exch(a, i, min);
        }
    }

    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (SortHelper.less(comparator, a[j], a[min])) {
                    min = j;
                }
            }
            SortHelper.exch(a, i, min);
        }
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
