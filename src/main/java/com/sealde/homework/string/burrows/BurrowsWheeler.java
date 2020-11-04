package com.sealde.homework.string.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    private final static int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = csa.length();
        // 找到 first
        for (int i = 0; i < length; i++)
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        // 遍历输出最后一个字符
        for (int i = 0; i < length; i++) {
            int index = csa.index(i);
            int lastCharIndex = index - 1 < 0 ? index - 1 + length : index - 1;
            BinaryStdOut.write(s.charAt(lastCharIndex));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int[] next = new int[s.length()];
        // key-indexing 排序
        int[] count = new int[R+1];
        for (int i = 0; i < s.length(); i++)
            count[s.charAt(i) + 1]++;

        // 累计
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        // 构建next
        for (int i = 0; i < s.length(); i++)
            next[count[s.charAt(i)]++] = i;

        // 输出
        for (int i = 0, j = first; i < s.length(); i++, j = next[j])
            BinaryStdOut.write(s.charAt(next[j]));
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        String op = args[0];
        if ("-".equals(op)) transform();
        else if ("+".equals(op)) inverseTransform();
        else throw new IllegalArgumentException();
    }
}
