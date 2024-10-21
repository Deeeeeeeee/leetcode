#- coding: utf-8 -#
# 网易算法题

# 1. 投资 [100, 200, 500] 回报 [20, 50, 200]，给900块钱，怎么样策略最大回报
# 完全背包问题. 动态规划
def max_profit():
    # 投资
    invest = [100, 200, 500]
    # 回报
    back = [20, 50, 200]
    # 资金
    money = 900

    # 二维数组 dp
    dp = [[0]*(money+1) for _ in range(len(invest)+1)]

    for i in range(1, len(invest)+1):
        for j in range(1, money+1):
            # 继承上一行的
            dp[i][j] = dp[i-1][j]
            # 是否装的下
            if j >= invest[i-1]:
                # 装的下则选最大值
                dp[i][j] = max(dp[i][j], back[i-1] + dp[i][j-invest[i-1]])

    print(dp[len(invest)][money])

    # 输出具体的选择
    i = len(invest)
    j = money
    while True:
        if i == 0 or j == 0:
            break

        # 如果跟上一行的一样，表示没有装下当前的值
        if dp[i][j] == dp[i-1][j]:
            i -= 1
        else:
            print(invest[i-1])
            j -= invest[i-1]


# 2. 岛屿数量。 深度遍历
# leetcode 200 https://leetcode.cn/problems/number-of-islands/
class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        self.n = len(grid)
        self.m = len(grid[0])

        # 记录已经遍历过的
        self.marked = [[False]*self.m for _ in range(self.n)]
        # 岛屿数量
        islands_cnt = 0

        # 一边遍历一边深度遍历
        for i in range(self.n):
            for j in range(self.m):
                # 如果是水，则不进行深度遍历
                if grid[i][j] == "0":
                    self.marked[i][j] = True
                    continue

                # 表示已经遍历过的岛屿
                if self.marked[i][j]:
                    continue
                
                # 新岛屿，进行深度遍历
                islands_cnt += 1
                self.marked[i][j] = True

                # 深度遍历的方法
                self.dfs(grid, i, j)

        return islands_cnt


    def dfs(self, grid, i, j):
        self.marked[i][j] = True
        if grid[i][j] == "0":
            return

        for ii, jj in self._adj(i, j):
            if not self.marked[ii][jj]:
                self.dfs(grid, ii, jj)

    def _adj(self, i, j):
        result = list()
        # 左边
        if j > 0:
            result.append((i, j-1))
        # 上面
        if i > 0:
            result.append((i-1, j))
        # 右边
        if j < self.m - 1:
            result.append((i, j+1))
        # 下面
        if i < self.n - 1:
            result.append((i+1, j))
        return result



if __name__ == '__main__':
    max_profit()
