package com.sealde.leetcode.heap;

import java.util.PriorityQueue;

public class NthUglyNumber {
    /**
     * No.264 丑数II
     *
     * 编写一个程序，找出第 n 个丑数。
     *
     * 丑数就是只包含质因数 2, 3, 5 的正整数。
     *
     * 示例:
     *
     * 输入: n = 10
     * 输出: 12
     * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
     * 说明:  
     *
     * 1 是丑数。
     * n 不超过1690。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/ugly-number-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 思路：不断地往 pq 里面 offer 丑数，然后 poll 最小的一个；不断地重复
     *      注意去重
     */
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        // 因为 *3 和 *5 会超过 Integer.MAX_VALUE，会产生负数，所以这里使用 long 类型
        long result = 1;
        for (int i = 1; i < n; i++) {
            pq.offer(result<<1);            // *2
            pq.offer((result<<1)+result);   // *3
            pq.offer((result<<2)+result);   // *5
            result = pq.poll();
            while (!pq.isEmpty() && pq.peek().equals(result)) {
                pq.poll();
            }
        }
        return (int) result;
    }

    public static void main(String[] args) {
        NthUglyNumber nth = new NthUglyNumber();
        System.out.println(nth.nthUglyNumber(1407));
    }
}
