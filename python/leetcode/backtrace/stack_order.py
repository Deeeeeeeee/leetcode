from collections import deque

# 个数，编号
n = int(input())
nums = list(input().split())


# 检查出栈顺序是否合法
def check_order(poped):
    # 模拟出栈和入栈
    stack = deque()
    # 临时记录出栈idx
    poped_idx = 0

    for push_item in nums:
        # 入栈
        stack.append(push_item)

        # stack栈顶和出栈顺序相同，则一起出栈
        while stack and poped[poped_idx] == stack[-1]:
            stack.pop()
            poped_idx += 1

    # 如果 stack 还有表示不合法
    return False if stack else True

# 记录出栈队列
poped = list()
# 是否访问过
marked = [False]*n
# 回溯法-全排列
def backtrace(poped, marked):
    if len(poped) == n:
        if check_order(poped):
            print(" ".join(poped))
        return

    for i in range(n):
        if marked[i]:
            continue

        # 选择
        marked[i] = True
        poped.append(nums[i])
        # 迭代
        backtrace(poped, marked)
        # 回溯
        marked[i] = False
        poped.pop()


backtrace(poped, marked)