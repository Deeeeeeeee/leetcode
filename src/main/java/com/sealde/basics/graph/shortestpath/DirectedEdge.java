package com.sealde.basics.graph.shortestpath;

public class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException();
        }
        if (w < 0) {
            throw new IllegalArgumentException();
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }

    public static void main(String[] args) {
        String a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
        String b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
        long aInt = Long.parseLong(a, 2);
        long bInt = Long.parseLong(b, 2);
        System.out.println(Long.toBinaryString(aInt + bInt));
    }
}
