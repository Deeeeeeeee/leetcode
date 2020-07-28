package com.sealde.leetcode.dp;

public class MaxProfit {
    private int max = 0;

    public int maxProfit(int[] prices) {
        int len = prices.length;
        for (int i = 0; i < len-1; i++) {
            f(i, i+1, prices);
        }
        return max;
    }

    /**
     * i, o 表示买入第 i 个和卖出第 o 个
     */
    private void f(int i, int o, int[] prices) {
        if (o > prices.length - 1) {
            return;
        }
        int diff = prices[o] - prices[i];
        if (diff > 0 && diff > max) {
            max = diff;
        }
        int source = o;
        // 后面比较小的数可以丢弃
        while (o < prices.length - 1 && prices[o+1] <= prices[o]) {
            o++;
        }
        f(i, o == source ? o+1 : o, prices);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7,1,5,3,6,4};
        MaxProfit t = new MaxProfit();
        System.out.println(t.maxProfit(arr));
    }
}
