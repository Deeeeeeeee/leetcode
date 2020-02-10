package com.sealde.basics.sort.quick;

import com.sealde.basics.RandomUtils;
import com.sealde.basics.sort.SortHelper;

/**
 * @Author: sealde
 * @Date: 2020/2/8 下午1:32
 */
public class Quick3Way {
    public static void sort(Comparable[] a) {
        RandomUtils.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo + 1;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                SortHelper.exch(a, lt++, i++);
            } else if (cmp > 0) {
                SortHelper.exch(a, gt--, i);
            } else {
                i++;
            }
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test", "b", "pop", "push", "set", "map", "list", "merge", "insertion", "quick"};
//        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
