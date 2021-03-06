package com.sealde.homework.string.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


public class BoggleSolver {
    private final Trie trie = new Trie();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String pwd : dictionary)
            if (pwd.length() >= 3)
                trie.add(pwd.toUpperCase());
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> result = new HashSet<>();
        int row = board.rows();
        int col = board.cols();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean[][] marked = new boolean[row][col];
                StringBuilder sb = new StringBuilder();
                dsf(marked, i, j, result, sb, board);
            }
        }
        return result;
    }

    private void dsf(boolean[][] marked, int i, int j, Set<String> result, StringBuilder sb, BoggleBoard board) {
        if (marked[i][j]) return;
        marked[i][j] = true;

        char letter = board.getLetter(i, j);
        if (letter == 'Q') sb.append("QU");
        else sb.append(letter);

        Trie.Node node = trie.getNode(sb.toString());
        if (node == null) {
            marked[i][j] = false;
            if (letter == 'Q') sb.setLength(sb.length() - 2);
            else sb.setLength(sb.length() - 1);
            return;
        }
        // 如果找到，加到结果里面
        if (node.isString) result.add(sb.toString());

        for (List<Integer> next : adj(i, j, marked.length, marked[0].length)) {
            int nextI = next.get(0);
            int nextJ = next.get(1);
            dsf(marked, nextI, nextJ, result, sb, board);
        }
        // 回溯
        marked[i][j] = false;
        if (letter == 'Q') sb.setLength(sb.length() - 2);
        else sb.setLength(sb.length() - 1);
    }

    // 拿到 i,j 相邻的点
    private List<List<Integer>> adj(int i, int j, int row, int col) {
        List<List<Integer>> result = new ArrayList<>();
        for (int m = i - 1; m <= i + 1; m++)
            for (int n = j - 1; n <= j + 1; n++)
                if (m >= 0 && m < row && n >= 0 && n < col) result.add(Arrays.asList(m, n));
        return result;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();
        if (!trie.contains(word)) return 0;
        int n = word.length();
        if (n == 3 || n == 4) return 1;
        else if (n == 5) return 2;
        else if (n == 6) return 3;
        else if (n == 7) return 5;
        else if (n >= 5) return 11;
        else return 0;
    }

    // 字典树 26-way
    private static class Trie {
        private static final int R = 26;

        private Node root;
        private int n;

        // R-way 节点
        private static class Node {
            private boolean isString;
            private Node[] next = new Node[R];
        }

        public void add(String key) {
            if (key == null) throw new IllegalArgumentException();
            root = add(root, key, 0);
        }

        // 迭代遍历，沿着字符串的字符进行。如果 node 为 null 则 new Node
        private Node add(Node x, String key, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                if (!x.isString) n++;
                x.isString = true;
            } else {
                char c = (char) (key.charAt(d) - 'A');
                x.next[c] = add(x.next[c], key, d + 1);
            }
            return x;
        }

        public boolean contains(String key) {
            Node node = getNode(key);
            return node != null && node.isString;
        }

        public Node getNode(String prefix) {
            if (prefix == null) throw new IllegalArgumentException();
            return get(root, prefix, 0);
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = (char) (key.charAt(d) - 'A');
            return get(x.next[c], key, d+1);
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
