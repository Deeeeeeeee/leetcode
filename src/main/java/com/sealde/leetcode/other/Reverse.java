package com.sealde.leetcode.other;

/**
 * #7. 整数反转
 *
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 *
 * 输入: 123
 * 输出: 321
 *  示例 2:
 *
 * 输入: -123
 * 输出: -321
 * 示例 3:
 *
 * 输入: 120
 * 输出: 21
 * 注意:
 *
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Reverse {
    public int reverse(int x) {
        long result = 0;
        // n = x % 10; m = n*10+n; x /= 10;
        // 正数时 x > 0; 负数时 x < 0
        while (x != 0) {
            result = result*10 + x%10;
            x /= 10;
            if ((int) result != result) {
                return 0;
            }
        }
        return (int) result;
    }

    public static void main(String[] args) {
        Reverse r = new Reverse();
        System.out.println(r.reverse(1534236469));
//        System.out.println((-9)%10);
    }
}
