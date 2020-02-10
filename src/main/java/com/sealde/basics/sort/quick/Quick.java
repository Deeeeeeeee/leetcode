package com.sealde.basics.sort.quick;

import com.sealde.basics.RandomUtils;
import com.sealde.basics.sort.SortHelper;

/**
 * @Author: sealde
 * @Date: 2020/2/6 下午2:30
 */
public class Quick {
    public static void sort(Comparable[] a) {
        RandomUtils.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        Comparable k = a[lo];
        while (true) {
            while (SortHelper.less(a[++i], k)) {
                if (i == hi) {
                    break;
                }
            }

            while (SortHelper.less(k, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            SortHelper.exch(a, i, j);
        }
        SortHelper.exch(a, lo, j);
        return j;
    }

    public static Comparable select(Comparable[] a, int k) {
        RandomUtils.shuffle(a);
        int lo = 0, hi = a.length-1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) {
                lo = j+1;
            } else if (j > k) {
                hi = j-1;
            } else {
                return a[j];
            }
        }
        return a[lo];
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test", "b", "pop", "push", "set", "map", "list", "merge", "insertion", "quick"};
//        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
