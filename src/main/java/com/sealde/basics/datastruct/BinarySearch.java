package com.sealde.basics.datastruct;

/**
 * @Author: sealde
 * @Date: 2020/2/2 下午5:08
 */
public class BinarySearch {
    public static int binarySearch(int[] src, int key) {
        int lo = 0;
        int he = src.length - 1;
        while (lo <= he) {
            int mid = (lo + he) >> 1;
//            System.out.printf("%d,%d,%d\n", lo, mid, he);
            if (src[mid] < key) {
                lo = mid + 1;
            } else if (src[mid] > key) {
                he = mid - 1;
            } else {
                return mid;
            }
        }
//        System.out.printf("%d,%d\n", lo, he);
        return -1;
    }

    public static void main(String[] args) {
        int[] src = new int[] {1, 3, 4, 9, 10};
        System.out.println(binarySearch(src, 3));
        System.out.println(binarySearch(src, 7));
    }
}
