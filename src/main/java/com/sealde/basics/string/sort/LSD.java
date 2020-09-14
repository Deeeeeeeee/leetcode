package com.sealde.basics.string.sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 低位优先-基数排序
 *
 * key-indexed counting 排序
 * 1. 遍历得到 count 数据（每个字符的统计，字符有向前移1位）
 * 2. 累加 count 数据（）
 * 3. 根据 count 对应的 index，把a[i]移动到aux[i]
 * 4. 将 aux[i] 复制回 a[i]
 *
 * LSD 排序
 * 1. 字符串从右到左，依次做一遍 key-indexed counting sort
 */
public class LSD {
    private static final int BITS_PER_BYTE = 8;

    private LSD() {}

    /**
     * 对字符串数组固定 w 长度的字符进行排序
     */
    public static void sort(String[] a, int w) {
        int n = a.length;
        int R = 256;    // extend ASCII 字母表的大小
        String[] aux = new String[n];

        for (int d = w-1; d >= 0; d--) {
            // key-indexed counting 排序

            // 1. 遍历得到 count 数据（每个字符的统计，字符有向前移1位）
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;

            // 2. 累加 count 数据（）
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // 3. 根据 count 对应的 index，把a[i]移动到aux[i]
            for (int i = 0; i < n; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // 4. 将 aux[i] 复制回 a[i]
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

    /**
     * 对int数组排序
     *
     * 将int分成4个字节，进行LSD排序
     * (a[i] >> BITS_PER_BYTE*d) 表示将 int 往右移 d 个字节
     */
    public static void sort(int[] a) {
        final int BITS = 32;                 // int 是 32 位
        final int R = 1 << BITS_PER_BYTE;    // 字节在 0 ~ 255 之间
        final int MASK = R - 1;              // 0xFF
        final int w = BITS / BITS_PER_BYTE;  // int 有 4 个字节

        int n = a.length;
        int[] aux = new int[n];

        for (int d = 0; d < w; d++) {
            // 1. 遍历得到 count 数据（每个字符的统计，字符有向前移1位）
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE*d) & MASK;
                count[c + 1]++;
            }

            // 2. 累加 count 数据（）
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
            if (d == w-1) {
                int shift1 = count[R] - count[R/2];
                int shift2 = count[R/2];
                for (int r = 0; r < R/2; r++)
                    count[r] += shift1;
                for (int r = R/2; r < R; r++)
                    count[r] -= shift2;
            }

            // 3. 根据 count 对应的 index，把a[i]移动到aux[i]
            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE*d) & MASK;
                aux[count[c]++] = a[i];
            }

            // 4. 将 aux[i] 复制回 a[i]
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // check that strings have fixed length
        int w = a[0].length();
        for (int i = 0; i < n; i++)
            assert a[i].length() == w : "Strings must have fixed length";

        // sort the strings
        sort(a, w);

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
