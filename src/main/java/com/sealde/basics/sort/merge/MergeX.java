package com.sealde.basics.sort.merge;

import com.sealde.basics.sort.Insertion;
import com.sealde.basics.sort.SortHelper;

/**
 * @Author: sealde
 * @Date: 2020/2/6 上午9:53
 */
public class MergeX {
    private static final int CUTOFF = 7;

    public static void sort(Comparable[] a) {
        Comparable[] aux = a.clone();
        sort(aux, a, 0, a.length-1);
    }

    public static void sort(Comparable[] src, Comparable[] dest, int lo, int hi) {
        // 如果是小数组，使用 insertion sort
        if (hi <= lo + CUTOFF) {
            Insertion.sort(dest, lo, hi+1);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(dest, src, lo, mid);
        sort(dest, src, mid+1, hi);

        // 如果已经排好序
        if (!SortHelper.less(src[mid+1], src[mid])) {
            System.arraycopy(src, lo, dest, lo, hi - lo + 1);
            return;
        }

        merge(src, dest, lo, mid, hi);
    }

    public static void merge(Comparable[] src, Comparable[] dest, int lo, int mid, int hi) {
        // merge
        int i = lo, j = mid+1;
        for (int k=lo; k <= hi; k++) {
            if (i > mid) {
                dest[k] = src[j++];
            } else if (j > hi) {
                dest[k] = src[i++];
            } else if (SortHelper.less(src[j], src[i])) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test", "b", "pop", "push", "set", "map", "list", "merge", "insertion", "quick"};
        sort(s);
        SortHelper.show(s);
    }
}
