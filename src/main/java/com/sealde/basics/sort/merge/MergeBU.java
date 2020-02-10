package com.sealde.basics.sort.merge;

import com.sealde.basics.sort.SortHelper;

/**
 * @Author: sealde
 * @Date: 2020/2/6 上午10:35
 */
public class MergeBU {
    public static void sort(Comparable[] a) {
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for (int len = 1; len < n; len*=2) {
            for (int lo = 0; lo < n - len; lo += len + len) {
                int mid = lo+len-1;
                int hi = Math.min(lo+len+len-1, n-1);
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy
        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }

        // merge
        int i = lo, j = mid+1;
        for (int k=lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (SortHelper.less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test", "b", "pop", "push", "set", "map", "list", "merge", "insertion", "quick"};
//        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
