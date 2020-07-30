package com.sealde.leetcode.sort;

public class SortColor {
    public void sortColors(int[] nums) {
        int i = 0, t = 0;
        int j = nums.length - 1;
        while (t <= j) {
            if (nums[i] == 0) {
                if (i == t) {
                    t++;
                }
                i++;
            } else if (nums[j] == 2) {
                j--;
            } else {
                if (nums[t] == 2) {
                    swap(nums, t, j--);
                } else if (nums[t] == 0) {
                    swap(nums, t++, i++);
                } else {
                    t++;
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2,0,2,1,1,0};
        SortColor a = new SortColor();
        a.sortColors(nums);
    }
}
