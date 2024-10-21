#- coding: utf-8 -#
# 动态规划-最长公共子序列
# 《算法导论》15章

# 1. 最长公共子序列问题
def lcs_length(x, y):
    m = len(x)
    n = len(y)
    # b 用于记录最优解的选择. c 用于记录最长公共子序列的长度
    b = [[0 for _ in range(n)] for _ in range(m)]
    c = [[0 for _ in range(n + 1)] for _ in range(m + 1)]

    for i in range(1, m+1):
        for j in range(1, n+1):
            # 如果相等，则由左上方的值加1得到
            if x[i-1] == y[j-1]:
                c[i][j] = c[i-1][j-1] + 1
                b[i-1][j-1] = '↖'
            # 否则比较哪边比较大，大的为最优解
            elif c[i-1][j] >= c[i][j-1]:
                c[i][j] = c[i-1][j]
                b[i-1][j-1] = '↑'
            else:
                c[i][j] = c[i][j-1]
                b[i-1][j-1] = '←'

    return c, b


# 2. 构造LCS
def print_lcs(b, x, i, j):
    if i == 0 or j == 0:
        return
    if b[i-1][j-1] == '↖':
        print_lcs(b, x, i-1, j-1)
        print(x[i-1], end='')
    elif b[i-1][j-1] == '↑':
        print_lcs(b, x, i-1, j)
    else:
        print_lcs(b, x, i, j-1)


if __name__ == '__main__':
    X = "ABCBDAB"
    Y = "BDCABA"
    
    def print_matrix(c, b):
        for i, l in enumerate(c):
            for j, n in enumerate(l):
                if i == 0 or j == 0:
                    print(' ' + str(n), end=' ')
                else:
                    print(str(*b[i-1][j-1]) + str(n), end=' ')
            print()

    c, b = lcs_length(X, Y)
    print_matrix(c, b)

    print_lcs(b, X, len(X), len(Y))