package com.sealde.leetcode.dp;

/**
 * 一次遍历，非动态规划方法
 */
public class MaxProfit2 {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            int p = prices[i];
            // 如果差值比历史差值大，更新这个差值
            if (p - minPrice > maxProfit) {
                maxProfit = p - minPrice;
            }
            // 如果比历史价格低，更新最低价格
            if (p < minPrice) {
                minPrice = p;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7,1,5,3,6,4};
        MaxProfit2 t = new MaxProfit2();
        System.out.println(t.maxProfit(arr));
    }
}
