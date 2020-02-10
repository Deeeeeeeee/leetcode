package com.sealde.basics.sort.quick;

import com.sealde.basics.RandomUtils;
import com.sealde.basics.sort.Insertion;
import com.sealde.basics.sort.SortHelper;

/**
 * @Author: sealde
 * @Date: 2020/2/6 下午3:47
 */
public class QuickX {
    private static final int CUTOFF = 7;

    public static void sort(Comparable[] a) {
        RandomUtils.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        // 如果小于等于小数组阈值，进行 insertion sort
        if ((hi - lo + 1) <= CUTOFF) {
            Insertion.sort(a, lo, hi+1);
            return;
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        // 从 lo, lo + (hi-lo+1)/2, hi 中选取中位数做为 k 元素
        int n = hi - lo + 1;
        int m = median3(a, lo, lo + n/2, hi);
        SortHelper.exch(a, lo, m);

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

    private static int median3(Comparable[] a, int i, int j, int k) {
        return SortHelper.less(a[i], a[j]) ?
                (SortHelper.less(a[j], a[k]) ? j : SortHelper.less(a[i], a[k]) ? k : i) :
                (SortHelper.less(a[i], a[k]) ? i : SortHelper.less(a[j], a[k]) ? k : j);
    }

    public static void main(String[] args) {
        String[] s = new String[] {"this", "is", "a", "test", "b", "pop", "push", "set", "map", "list", "merge", "insertion", "quick"};
//        String[] s = new String[] {"this", "is", "a", "test"};
        sort(s);
        SortHelper.show(s);
    }
}
