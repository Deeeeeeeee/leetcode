#- coding: utf-8 -#
# 练习

# 1. 问题描述给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。说明：每次只能向下或者向右移动一步。举例：
# 输入:
# arr = [
#   [1,3,1],
#   [1,5,1],
#   [4,2,1]
# ]
# 输出: 7
# 解释: 因为路径 1→3→1→1→1 的总和最小。
def dp1():
    arr = [
      [1,3,1],
      [1,5,1],
      [4,2,1]
    ]

    m, n = len(arr), len(arr[0])

    # 备忘录
    memo = [[0]*n for _ in range(m)]

    for i in range(m):
        for j in range(n):
            # 0, 0
            if i == 0 and j == 0:
                memo[i][j] = arr[0][0]
            # 单边的时候由前一个元素直接累加
            elif i == 0:
                memo[i][j] = arr[i][j] + memo[i][j-1]
            elif j == 0:
                memo[i][j] = arr[i][j] + memo[i-1][j]
            # 其余的由上面和左边两个元素取最小值累加
            else:
                memo[i][j] = arr[i][j] + min(memo[i-1][j], memo[i][j-1])

    print(memo[m-1][n-1])


if __name__ == '__main__':
    dp1()