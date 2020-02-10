package com.sealde.basics.sort.merge;

import com.sealde.basics.sort.SortHelper;

import java.util.Comparator;

/**
 * @Author: sealde
 * @Date: 2020/2/5 下午5:27
 */
public class Merge {
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
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
        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
