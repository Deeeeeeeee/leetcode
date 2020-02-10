package com.sealde.basics.sort;

import java.util.Comparator;

/**
 * @Author: sealde
 * @Date: 2020/2/5 下午3:37
 */
public class Shell {
    public static void sort(Comparable[] a) {
        int n = a.length;

        // 3x+1 序列: 1, 4, 13, 40, 121, 364...
        int h = 1;
        while (h < n/3) {
            h = 3*h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && SortHelper.less(a[j], a[j-h]); j=j-h) {
                    SortHelper.exch(a, j, j-h);
                }
            }
            h = h/3;
        }
    }

    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;

        // 3x+1 序列: 1, 4, 13, 40, 121, 364...
        int h = 1;
        while (n > 3*h) {
            h = 3*h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && SortHelper.less(comparator, a[j], a[j-h]); j=j-h) {
                    SortHelper.exch(a, j, j-h);
                }
            }
            h = h/3;
        }
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
