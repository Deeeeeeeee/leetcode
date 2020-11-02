package com.sealde.homework.string.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
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
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = csa.length();
        int[] next = new int[length];
        char tmp = s.charAt(csa.index(0));
        // 初始化 next
        for (int i = 0, count = 0; i < length; i++) {
            next[i] = csa.index(i);
            char c = s.charAt(next[i]);
            if (tmp != c || i == length-1) {
                tmp = c;
                Arrays.sort(next, i-count, i == length-1 ? i+1 : i);
                count = 1;
            } else count++;
        }
        // 输出
        for (int i = 0, j = first; i < length; i++, j = next[j])
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
