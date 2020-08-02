package com.sealde.leetcode.other;

/**
 * 70. 爬楼梯
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 *
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：
 *
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ClimbStairs {
    int[] memory;

    /**
     * 使用斐波那契数列求解
     */
    public int climbStairs(int n) {
        memory = new int[n+1];
        return fb(n);
    }

    private int fb(int n) {
        if (n <= 1) {
            return 1;
        }
        if (memory[n] != 0) {
            return memory[n];
        }
        int result = fb(n-1) + fb(n-2);
        memory[n] = result;
        return result;
    }

    /**
     * 动态规划求解
     */
    public int climbStairs2(int n) {
        int first = 0;
        int second = 1;
        int third = 1;
        // 动态规划. x3 = x2 + x1
        for (int i = 1; i < n; i++) {
            first = second;
            second = third;
            third = first + second;
        }
        return third;
    }

    public static void main(String[] args) {
        ClimbStairs t = new ClimbStairs();
        System.out.println(t.climbStairs2(3));
    }
}
