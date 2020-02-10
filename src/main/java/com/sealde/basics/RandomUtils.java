package com.sealde.basics;

import java.util.Random;

/**
 * @Author: sealde
 * @Date: 2020/2/6 下午2:34
 */
public class RandomUtils {
    private static Random random;

    static {
        random = new Random();
    }

    public static int random(int i) {
        return random.nextInt(i);
    }

    public static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + random(n-i);    // i 到 n-1
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
}
