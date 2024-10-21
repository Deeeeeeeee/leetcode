# -*- coding: utf-8 -*-
# 零钱兑换
# https://leetcode.cn/problems/coin-change/description/
# 用动态规划解。上面注释部分是二维数组，下面没注释的是一维数组

class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:
        # # 看成是完全背包问题
        # dp = [[0]*(amount+1) for _ in range(len(coins)+1)]
        # # 第一行设置成无穷大
        # dp[0] = [float('inf') for _ in range(amount+1)]

        # # 遍历
        # for i in range(1, len(coins)+1):
        #     for j in range(1, amount+1):
        #         # 先继承上一层
        #         dp[i][j] = dp[i-1][j]
        #         if j >= coins[i-1]:
        #             # 不选和选择取最小值
        #             dp[i][j] = min(dp[i][j], dp[i][j-coins[i-1]]+1)

        # return -1 if dp[len(coins)][amount] == float('inf') else dp[len(coins)][amount]

        # 看成是完全背包问题
        dp = [float('inf') for _ in range(amount+1)]
        # 第一个设置成0
        dp[0] = 0

        # 遍历
        for i in range(1, len(coins)+1):
            # 从 当前需要的背包容量 开始遍历. 取最小值
            for j in range(coins[i-1], amount+1):
                dp[j] = min(dp[j], dp[j-coins[i-1]]+1)

        return -1 if dp[amount] == float('inf') else dp[amount]