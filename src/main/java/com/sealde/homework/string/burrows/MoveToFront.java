package com.sealde.homework.string.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] tmp = new int[R];
        for (int i = 0; i < R; i++) tmp[i] = i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            // 寻找目标c的位置
            for (int i = 0; i < R; i++) {
                if (tmp[0] == (int) c) {
                    BinaryStdOut.write(i, 8);
                    break;
                }
                exch(tmp, i+1, 0);
            }
        }
        BinaryStdOut.close();
    }

    private static void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] tmp = new int[R];
        for (int i = 0; i < R; i++) tmp[i] = i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            // 寻找目标c的位置
            for (int i = 0; i < R; i++) {
                if (i == (int) c) {
                    BinaryStdOut.write((char) tmp[0]);
                    break;
                }
                exch(tmp, i+1, 0);
            }
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        String op = args[0];
        if ("-".equals(op)) encode();
        else if ("+".equals(op)) decode();
        else throw new IllegalArgumentException();
    }
}
