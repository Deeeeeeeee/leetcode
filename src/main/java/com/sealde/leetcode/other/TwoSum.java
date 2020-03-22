package com.sealde.leetcode.other;

import java.util.*;

/**
 * @Author: sealde
 * @Date: 2020/2/9 下午3:58
 */
public class TwoSum {
    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     *
     * 示例:
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int[] twoSum(int[] nums, int target) {
        List<Integer> result = new ArrayList();
        List<Integer> tmp = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < tmp.size(); j++) {
                if (nums[i] + tmp.get(j) == target) {
                    result.add(i);
                    result.add(j);
                }
            }
            tmp.add(nums[i]);
        }
        return result.stream().mapToInt(l -> l).toArray();
    }

    /**
     * 思路： 求出 target - nums[i] 的结果，如果在 map 里面有，则表示有满足条件的结果；
     *      否则，将 nums[i] 放到 map 里面，等待其他结果来匹配
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[] {};
    }
}
