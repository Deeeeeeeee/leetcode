#- coding: utf-8 -#
# leetcode 946 出栈顺序问题 https://leetcode.cn/problems/validate-stack-sequences/
#
# ====== 题目
# 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
#
# 示例 1：
# 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
# 输出：true
# 解释：我们可以按以下顺序执行：
# push(1), push(2), push(3), push(4), pop() -> 4,
# push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
#
# 示例 2：
# 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
# 输出：false
# 解释：1 不能在 2 之前弹出。
#
# 提示：
# 1 <= pushed.length <= 1000
# 0 <= pushed[i] <= 1000
# pushed 的所有元素 互不相同
# popped.length == pushed.length
# popped 是 pushed 的一个排列
#
# 解题思路
# 一个栈，一个队列 模拟入栈出栈
# https://www.zhihu.com/zvideo/1285248090853838848
from collections import deque

class Solution:
    def validateStackSequences(self, pushed: List[int], popped: List[int]) -> bool:
        # 模拟入栈出栈
        stack = deque()
        # 记录 popped 出栈位置
        pop_idx = 0

        # 遍历入栈元素
        for pushed_item in pushed:
            # 先进栈
            stack.append(pushed_item)

            # 如果栈顶和出栈序列相同，则同时出栈
            while stack and stack[-1] == popped[pop_idx]:
                stack.pop()
                pop_idx += 1

        # 如果顺序合法，则 stack 应该为空
        return False if stack else True