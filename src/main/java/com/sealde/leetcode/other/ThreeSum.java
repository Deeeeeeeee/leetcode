package com.sealde.leetcode.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            // 如果 nums[i] > 0
            if (nums[i] > 0) {
                break;
            }
            //  第 i 个和 i-1 个数相同
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            // 来回二分查找标记
            boolean flag = true;
            for (int j = i + 1, k = len - 1; j < k; j++) {
                if (nums[i] + nums[j] > 0) {
                    break;
                }
                // 如果 nums[i] + nums[j] > 0 或者第 j 个和 j-1 个数相同
                if (j > i+1 && nums[j] == nums[j-1]) {
                    continue;
                }
                // 二分查找
                int lo = flag ? j+1 : j;
                int hi = k;
                int target = flag ? -(nums[i]+nums[j]) : -(nums[i]+nums[k]);
                int mid = -1;
                while (lo <= hi) {
                    mid = (lo + hi) >> 1;
                    if (nums[mid] < target) {
                        lo = mid + 1;
                    } else if (nums[mid] > target) {
                        hi = mid - 1;
                    } else {
                        break;
                    }
                }
                if (nums[i] + nums[j] + nums[mid] == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[mid]));
                    while (mid > j && nums[mid] == nums[mid-1]) {
                        mid--;
                    }
                    mid--;
                    k = mid;
                    flag = true;
                } else {
                    j = mid-1;
                    flag = false;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,-1,-1,0};
        ThreeSum t = new ThreeSum();
        t.threeSum(nums);
    }
}
