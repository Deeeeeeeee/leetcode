#- coding: utf-8 -#
# 动态规划-矩阵链乘法
# 《算法导论》15章

# 1. 矩阵乘法. 数学上的矩阵乘法，cij 为 ai行对应乘以bj列求和的结果
def matrix_multiply(a, b):
    if a.columns != b.rows:
        raise ValueError('incompatible dimensions')

    # C 为 a 行 b 列
    c = [[0]*b.columns for _ in range(a.rows)]
    for i in range(a.rows):
        for j in range(b.columns):
            for k in range(a.columns):
                # cij = cij + aik*bkj
                c[i][j] += a[i][k]*b[k][j]

    return c

# 2. 矩阵链乘法。求最优解
def matrix_chain_order(p):
    n = len(p) - 1
    # m[i][j] 为 i 到 j 的最优解
    m = [[0]*n for _ in range(n)]
    # s[i][j] 为 i 到 j 的最优解对应的分割点
    s = [[0]*n for _ in range(n)]

    # l 是链的长度
    for l in range(2, n+1):
        for i in range(1, n-l+2):
            j = i + l - 1
            m[i-1][j-1] = float('inf')
            for k in range(i, j):
                q = m[i-1][k-1] + m[k][j-1] + p[i-1]*p[k]*p[j]
                if q < m[i-1][j-1]:
                    m[i-1][j-1] = q
                    s[i-1][j-1] = k

    return m, s

# 3. 构造最优解
def print_optimal_parens(s, i, j):
    if i == j:
        print('A%d' % i, end='')
    else:
        print('(', end='')
        print_optimal_parens(s, i, s[i-1][j-1])
        print_optimal_parens(s, s[i-1][j-1]+1, j)
        print(')', end='')


if __name__ == '__main__':
    p = [30, 35, 15, 5, 10, 20, 25]
    # p = [3, 2, 5, 10, 2, 3]

    def print_matrix(m, s):
        for row in m:
            print(row)
        print()
        for row in s:
            print(row)
        print()


    m, s = matrix_chain_order(p)
    print_matrix(m, s)
    print_optimal_parens(s, 1, len(p)-1)