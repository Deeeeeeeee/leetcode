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
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> container = new ArrayList<>();
        for (int i : nums) {
            container.add(i);
        }

        cal(0, nums.length, container, result);
        return result;
    }

    private void cal(int first, int len, List<Integer> container, List<List<Integer>> result) {
        if (first == len) {
            result.add(new ArrayList<>(container));
        }
        for (int i = first; i < len; i++) {
            Collections.swap(container, i, first);
            cal(first+1, len, container, result);
            // 回溯
            Collections.swap(container, first, i);
        }
    }

    public static void main(String[] args) {
        Permute p = new Permute();
        int[] s = new int[] {1, 2, 3, 4};
        System.out.println(p.permute(s));
    }
}
