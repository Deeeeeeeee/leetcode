package com.sealde.homework.string.burrows;

import java.util.Arrays;

/**
 * 后缀数组。思路，将输入的 s 保存起来但不真正创建 String[]，而是通过引用这个 s。这个操作通过 CircularSuffix 实现
 * 1. 先给 CircularSuffix 赋初值 i，表示原始的 suffix 顺序
 * 2. 再给 CircularSuffix[] 排序，排完序后，CircularSuffix[i] 保存的初值即为所求的 index[i]
 *
 * 比如：
 * 输入s: ABRA
 * 那么CircularSuffix[] 初值: ABRA 0, BRAA 1, RAAB 2, AABR 3
 * 排序后CircularSuffix[] 初值: AABR 3, ABRA 0, BRAA 1, RAAB 2
 * 此时 CircularSuffix[i] 对应的初值，即为所求的 index[i]
 */
public class CircularSuffixArray {
    private final String source;
    private final int length;
    private CircularSuffix[] suffix;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        this.source = s;
        this.length = s.length();
        this.suffix = new CircularSuffix[this.length];
        // 给 suffix 赋初值
        for (int i = 0; i < this.length; i++) {
            this.suffix[i] = new CircularSuffix(i);
        }
        // 排序
        Arrays.sort(suffix);
    }

    // length of s
    public int length() {
        return this.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= this.length) throw new IllegalArgumentException();
        return this.suffix[i].startIndex;
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private int startIndex;

        public CircularSuffix(int startIndex) {
            this.startIndex = startIndex;
        }

        @Override
        public int compareTo(CircularSuffix s) {
            int sStart = s.startIndex;
            for (int i = 0; i < length; i++) {
                int nowIndex = startIndex + i >= length ? startIndex + i - length : startIndex + i;
                int sNowIndex = sStart + i >= length ? sStart + i - length : sStart + i;
                if (source.charAt(nowIndex) > source.charAt(sNowIndex)) {
                    return 1;
                } else if (source.charAt(nowIndex) < source.charAt(sNowIndex)) {
                    return -1;
                }
            }
            return 0;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    }
}
