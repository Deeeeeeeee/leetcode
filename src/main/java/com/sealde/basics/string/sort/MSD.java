package com.sealde.basics.string.sort;

/**
 * 高位优先-基数排序
 *
 * key-indexed counting 排序
 * 1. 遍历得到 count 数据（每个字符的统计，字符有向前移1位）
 * 2. 累加 count 数据（）
 * 3. 根据 count 对应的 index，把a[i]移动到aux[i]
 * 4. 将 aux[i] 复制回 a[i]
 *
 * MSD 排序
 * 1. 字符串从左到右，依次做一遍 key-indexed counting sort
 * 2. 每次划分会出现相同前缀的数组，然后迭代操作
 */
public class MSD {
    private static final int BITS_PER_BYTE =   8;
    private static final int BITS_PER_INT  =  32;   // each Java int is 32 bits
    private static final int R             = 256;   // extended ASCII alphabet size
    private static final int CUTOFF        =  15;   // cutoff to insertion sort

    private MSD() { }

    public static void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n-1, 0, aux);
    }

    // 如果 d == s.length()，则返回 -1
    private static int charAt(String s, int d) {
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    /**
     * MSD 排序中，R+2 是因为多了一个 -1 表示结束的字符
     */
    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        // 数组小的时候，使用插入排序
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // key-indexed counting 排序部分

        // 1. 遍历得到 count 数据（每个字符的统计，字符有向前移1位）
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c+2]++;
        }


        // 2. 累加 count 数据（）
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // 3. 根据 count 对应的 index，把a[i]移动到aux[i]
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        // 4. 将 aux[i] 复制回 a[i]
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // 遍历
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {

    }
}
