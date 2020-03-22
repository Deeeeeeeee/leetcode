package com.sealde.leetcode.queue;

import java.util.Arrays;

public class LeastInterval {
    /**
     * No.621 任务调度器
     *
     * 给定一个用字符数组表示的 CPU 需要执行的任务列表。其中包含使用大写的 A - Z 字母表示的26 种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。CPU 在任何一个单位时间内都可以执行一个任务，或者在待命状态。
     *
     * 然而，两个相同种类的任务之间必须有长度为 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
     *
     * 你需要计算完成所有任务所需要的最短时间。
     *
     * 示例 1：
     *
     * 输入: tasks = ["A","A","A","B","B","B"], n = 2
     * 输出: 8
     * 执行顺序: A -> B -> (待命) -> A -> B -> (待命) -> A -> B.
     * 注：
     *
     * 任务的总个数为 [1, 10000]。
     * n 的取值范围为 [0, 100]。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/task-scheduler
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 思路： 解题思路跟官方题解方法一类似，但对 num <= n+1 和 num > n+1 这两种情况作了区分。['A', 'A', 'A', 'B', 'B', 'B', 'C', 'C']，这里 num 为 3，分别是 'A','B','c'
     * case1: 如果 n = 3 (即 num <= n+1) 时，我们是可以 'A'->'B'->'C'->空闲->'A'->'B'...这样子的，意味着取决于最高的个数；但最高的个数有两个，即 'A' 和 'B' 都为 3，这种情况考虑进去就容易得出 (remain[25]-1)*(n+1) 这个公式
     * case2: 如果 n = 1 (即 num > n+1) 时，我们对最高的 n+1 个数进行 remain[25-i]--，然后再重新排序就可以了。即在每个 n+1 个轮回中消耗掉最高的 n+1 的数量，这个跟官方题解方法一思路是一样的
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }
        int result = 0;
        int num = 0;    // 存储 remain 里 > 1 的个数
        // 初始化数组
        int[] remain = new int[26];
        for (char c : tasks) {
            int cn = c - 'A';
            if (remain[cn] == 0) {
                num++;
            }
            remain[cn]++;
        }
        // 排序
        Arrays.sort(remain);
        // 判断 > n
        if (num <= n+1) {
            return getLessOrEqualsN(remain, n);
        }
        while (num > n+1) {
            int del = 0;
            for (int i = 0; i <= n; i++) {
                remain[25-i]--;
                if (remain[25-i] == 0) {
                    del++;
                }
            }
            result += (n+1);
            // 判断是否需要重新排序
            if (remain[25-n] < remain[24-n]) {
                Arrays.sort(remain);
            }
            num -= del;
        }
        result += getLessOrEqualsN(remain, n);
        return result;
    }

    private int getLessOrEqualsN(int remain[], int n) {
        int result = (remain[25]-1)*(n+1);
        int i = 25;
        while (i >= 0 && remain[i] == remain[25]) {
            result++;
            i--;
        }
        return result;
    }

    public static void main(String[] args) {
        LeastInterval li = new LeastInterval();
//        System.out.println(li.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 50));
        System.out.println(li.leastInterval(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'}, 2));
    }
}
