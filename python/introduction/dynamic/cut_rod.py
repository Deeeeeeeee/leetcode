#- coding: utf-8 -#
# 动态规划-切钢条
# 《算法导论》15章

# 钢条长度对应的价格
p = [1, 5, 8, 9, 10, 17, 17, 20, 24, 30]

# 1. 递归求解
def cut_rod(p, n):
    if n == 0:
        return 0

    q = -float('inf')
    for i in range(0, n):
        # 分成 i 和 n-i-1 两部分子问题求解
        q = max(q, p[i] + cut_rod(p, n-i-1))

    return q

# 2. 带备忘录自顶向下
def memorized_cut_rod(p, n):
    # 备忘录中全部解先设置为 -inf
    memo = [0]*(n+1)
    for i in range(0, n+1):
        memo[i] = -float('inf')
    
    return _memorized_cut_rod_aux(p, n, memo)

def _memorized_cut_rod_aux(p, n, memo):
    # 如果已经计算过了，则直接返回
    if memo[n] >= 0:
        return memo[n]

    if n == 0:
        q = 0
    else:
        q = -float('inf')
        for i in range(0, n):
            # 分成 i 和 n-i-1 两部分子问题求解
            q = max(q, p[i] + _memorized_cut_rod_aux(p, n-i-1, memo))

    # 设置备忘录的解
    memo[n] = q
    return q

# 3. 自底向上
def bottom_up_cut_rod(p, n):
    memo = [0]*(n+1)
    memo[0] = 0

    # 从小规模开始
    for j in range(1, n+1):
        # 每个小规模跟上面一样，求最优解
        q = -float('inf')
        for i in range(0, j):
            q = max(q, p[i] + memo[j-i-1])
        memo[j] = q

    return memo[n]

# 4. 返回最优解和最优解的方案
def extended_bottom_up_cut_rod(p, n):
    # memo 是备忘录；s 是方案
    memo = [0]*(n+1)
    s = [0]*(n+1)
    memo[0] = 0

    for j in range(1, n+1):
        q = -float('inf')
        for i in range(0, j):
            # print(i, j-i-1)
            if q < p[i] + memo[j-i-1]:
                q = p[i] + memo[j-i-1]
                # 将最优解的第1段保存在s[j]
                s[j] = i+1
        memo[j] = q

    return memo, s

# 输出钢条方案
def print_cut_rod_solution(p, n):
    r, s = extended_bottom_up_cut_rod(p, n)
    while n > 0:
        print(s[n])
        n -= s[n]


if __name__ == '__main__':
    import time
    start = time.time()

    # print(cut_rod(p, 10))
    # print(memorized_cut_rod(p, 10))
    # print(bottom_up_cut_rod(p, 10))

    print(extended_bottom_up_cut_rod(p, 8))
    # print_cut_rod_solution(p, 8)

    print(time.time() - start)