package com.sealde.leetcode.other;

import java.util.*;
import java.util.stream.Collectors;

/**
 * #46. 全排列
 *
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Permute {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int[] container = new int[len];
            container[i] = 1;
            cal(i+1, len, container, result);
        }
        return result;
    }

    private int cal(int row, int len, int[] container, List<List<Integer>> result) {
        if (row == len) {
            result.add(Arrays.stream(container).boxed().collect(Collectors.toList()));
            return container[len-1];
        }
        int r = 0;
        for (int col = 0; col < len; col++) {
            if (container[col] == 0) {
                container[col] = row + 1;
                r = cal(row + 1, len, container, result);
                container[r-1] = 0;
            }
        }
        return r - 1;
    }

    public static void main(String[] args) {
        Permute p = new Permute();
        int[] s = new int[] {1, 2, 3, 4};
        System.out.println(p.permute(s));
    }
}
