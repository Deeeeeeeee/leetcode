package com.sealde.homework.graph.seamcarver;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class SeamCarver {
    private double[][] energy;
    private int[][] colors;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        width = picture.width();
        height = picture.height();
        colors = new int[width][height];
        energy = new double[width][height];
        // 初始化color
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                colors[x][y] = picture.get(x, y).getRGB();
            }
        }
        // 初始化能量
        buildEnergy();
    }

    private void buildEnergy() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    energy[x][y] = 1000;
                } else {
                    int left = colors[x - 1][y];
                    int right = colors[x + 1][y];
                    int up = colors[x][y + 1];
                    int below = colors[x][y - 1];
                    double rx = getDelta(left, right);
                    double ry = getDelta(up, below);
                    energy[x][y] = Math.sqrt(rx + ry);
                }
            }
        }
    }

    // delta = r^2 + g^2 + b^2
    private double getDelta(int a, int b) {
        int aRed = (a >> 16) & 0xFF, aGreen = (a >> 8) & 0xFF, aBlue = a & 0xFF;
        int bRed = (b >> 16) & 0xFF, bGreen = (b >> 8) & 0xFF, bBlue = b & 0xFF;
        return Math.pow(aRed-bRed, 2)
                + Math.pow(aGreen-bGreen, 2)
                + Math.pow(aBlue-bBlue, 2);
    }

    // current picture
    public Picture picture() {
        Picture p = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                p.set(x, y, new Color(colors[x][y]));
            }
        }
        return p;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException();
        }
        return energy[x][y];
    }

    // sequence of indices for horizontal seam, 水平线
    public int[] findHorizontalSeam() {
        transport();
        int[] result = findVerticalSeam();
        transport();
        return result;
    }

    // 翻转
    private void transport() {
        double[][] newEnergy = new double[height][width];
        int[][] newColors = new int[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newEnergy[j][i] = energy[i][j];
                newColors[j][i] = colors[i][j];
            }
        }
        colors = newColors;
        energy = newEnergy;
        int tmp = width;
        width = height;
        height = tmp;
    }

    // sequence of indices for vertical seam, 垂直线
    public int[] findVerticalSeam() {
        int[] result = new int[height];
        double[][] disTo = new double[width][height+1];
        int[][] edges = new int[width][height+1];
        for (int i = 0; i < width; i++) {
            relax(i, 0, 0, height, disTo, edges);
        }
        int t = edges[0][height];
        for (int i = height-1; i >= 0; i--) {
            int x = t - i*width;
            result[i] = x;
            t = edges[x][i];
        }
        return result;
    }

    private void relax(int xTo, int yTo, int xFrom, int yFrom, double[][] disTo, int[][] edges) {
        double w = energy(xTo, yTo);
        if (disTo[xTo][yTo] == 0 || disTo[xTo][yTo] > disTo[xFrom][yFrom] + w) {
            disTo[xTo][yTo] = disTo[xFrom][yFrom] + w;
            edges[xTo][yTo] = xFrom+yFrom*width;
            // 依次中、左、右
            if (yTo == height-1) {
                return;
            }
            relax(xTo, yTo+1, xTo, yTo, disTo, edges);
            if (xTo > 0) {
                relax(xTo - 1, yTo+1, xTo, yTo, disTo, edges);
            }
            if (xTo < width-1) {
                relax(xTo + 1, yTo+1, xTo, yTo, disTo, edges);
            }
        }
    }

    // remove horizontal seam from current picture, 删除水平线
    public void removeHorizontalSeam(int[] seam) {
        // check
        if (seam == null || seam.length != width) {
            throw new IllegalArgumentException();
        }
        int t = -1;
        for (int i : seam) {
            if (i >= height || i < 0 || (t != -1 && Math.abs(t - i) > 1)) {
                throw new IllegalArgumentException();
            }
            t = i;
        }
        // 交换即可
        int cnt = 0;
        for (int i : seam) {
            int tmp = i;
            while (tmp < height-1) {
                colors[cnt][tmp] = colors[cnt][tmp+1];
                tmp++;
            }
            cnt++;
        }
        height--;
        buildEnergy();
    }

    // remove vertical seam from current picture, 删除垂直线
    public void removeVerticalSeam(int[] seam) {
        // check
        if (seam == null || seam.length != height) {
            throw new IllegalArgumentException();
        }
        int t = -1;
        for (int i : seam) {
            if (i >= width || i < 0 || (t != -1 && Math.abs(t - i) > 1)) {
                throw new IllegalArgumentException();
            }
            t = i;
        }
        // 交换即可
        int cnt = 0;
        for (int i : seam) {
            int tmp = i;
            while (tmp < width - 1) {
                colors[tmp][cnt] = colors[tmp+1][cnt];
                tmp++;
            }
            cnt++;
        }
        width--;
        buildEnergy();
    }

    public static void main(String[] args) {
//        Picture p = new Picture(args[0]);
//        SeamCarver sc = new SeamCarver(p);
//        for (int row = 0; row < sc.height(); row++) {
//            for (int col = 0; col < sc.width(); col++)
//                StdOut.printf("%9d ", p.get(col, row).getRGB());
//            StdOut.println();
//        }
////        for (int row = 0; row < sc.height(); row++) {
////            for (int col = 0; col < sc.width(); col++)
////                StdOut.printf("%9.0f ", sc.energy(col, row));
////            StdOut.println();
////        }
//        sc.removeVerticalSeam(sc.findVerticalSeam());
////        for (int row = 0; row < sc.height(); row++) {
////            for (int col = 0; col < sc.width(); col++)
////                StdOut.printf("%9.0f ", sc.energy(col, row));
////            StdOut.println();
////        }
//        for (int row = 0; row < sc.height(); row++) {
//            for (int col = 0; col < sc.width(); col++)
//                StdOut.printf("%9d ", sc.picture().get(col, row).getRGB());
//            StdOut.println();
//        }
        Picture picture = new Picture(args[0]);
        StdOut.printf("%s (%d-by-%d image)\n", args[0], picture.width(), picture.height());
        StdOut.println();
        StdOut.println("The table gives the dual-gradient energies of each pixel.");
        StdOut.println("The asterisks denote a minimum energy vertical or horizontal seam.");
        StdOut.println();

        SeamCarver carver = new SeamCarver(picture);

        StdOut.printf("Vertical seam: { ");
        int[] verticalSeam = carver.findVerticalSeam();
        for (int x : verticalSeam)
            StdOut.print(x + " ");
        StdOut.println("}");

//        StdOut.printf("Horizontal seam: { ");
//        int[] horizontalSeam = carver.findHorizontalSeam();
//        for (int y : horizontalSeam)
//            StdOut.print(y + " ");
//        StdOut.println("}");
    }
}
