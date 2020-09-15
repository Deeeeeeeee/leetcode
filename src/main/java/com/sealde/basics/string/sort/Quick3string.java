package com.sealde.basics.string.sort;

/**
 * 三切分字符比较
 *
 * 跟三切分快排不同，三切分快排是比较字符串，这里是比较字符
 *
 * 思路跟三切分快排一样
 * 1. 取第一个字符作为标准
 * 2. 分为大于的部分，小于的部分和等于的部分
 * 3. 对每个部分进行迭代操作
 *
 * 跟 MSD 不一样的是，MSD 的 count[R] 是以字母表的总数来划分
 * 即每次都分成 R 路，三切分只分成三路，较少开销。而且不占用新空间
 */
public class Quick3string {
    private static final int CUTOFF        =  15;   // cutoff to insertion sort

    private Quick3string() {}

    public static void sort(String[] a) {

    }

    private static void sort(String[] a, int lo, int hi, int d) {
        // 数组小的时候，使用插入排序
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, i++, lt++);
            else if (t > v) exch(a, gt--, i);
            else i++;
        }

        // 三切分，a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    // 如果 d == s.length()，则返回 -1
    private static int charAt(String s, int d) {
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo + 1; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    // 直接比较两个字符串，从第 d 个字符开始比较
    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
