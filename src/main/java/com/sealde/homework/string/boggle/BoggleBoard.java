package com.sealde.homework.string.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int row;
    private final int col;
    private final char[][] b;

    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        row = 4;
        col = 4;
        b = new char[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                b[i][j] = (char) (StdRandom.uniform(26) + 'A');
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        if (m <= 0) throw new IllegalArgumentException();
        if (n <= 0) throw new IllegalArgumentException();
        row = m;
        col = n;
        b = new char[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                b[i][j] = (char) (StdRandom.uniform(26) + 'A');
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        In in = new In(filename);
        row = in.readInt();
        col = in.readInt();
        if (row <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
        if (col <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
        b = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String letter = in.readString().toUpperCase();
                if (letter.equals("QU"))
                    b[i][j] = 'Q';
                else if (letter.length() != 1)
                    throw new IllegalArgumentException("invalid character: " + letter);
                else if (!ALPHABET.contains(letter))
                    throw new IllegalArgumentException("invalid character: " + letter);
                else
                    b[i][j] = letter.charAt(0);
            }
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        row = a.length;
        col = a[0].length;
        b = new char[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                b[i][j] = a[i][j];
    }

    // Returns the number of rows.
    public int rows() {
        return row;
    }

    // Returns the number of columns.
    public int cols() {
        return col;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        return b[i][j];
    }

    // Returns a string representation of the board.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if ('Q' == b[i][j]) sb.append("Qu");
                else sb.append(b[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BoggleBoard bb = new BoggleBoard(4, 4);
        System.out.println(bb.rows());
        System.out.println(bb.cols());
        System.out.println(bb);
    }
}
