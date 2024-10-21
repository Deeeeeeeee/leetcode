#- coding: utf-8 -#

# https://zhuanlan.zhihu.com/p/700819525 大小n的背包和m个物品
def sove2(n, m, a):
    if sum(a) < n:
        return -1

    a = sorted(a)
    b = []

    index = {}
    for i in range(m):
        b.append(bin(a[i][2:][::-1]))
        for j in range(len(b)):
            if b[j] == '1':
                index[j] += 1

    t = bin(n)[2:][::-1]
    aim = {}
    for i in range(len(t)):
        if t[i] == '1':
            aim[i] = 1

    print(b)
    print(index)
    print(aim)

# https://www.nowcoder.com/discuss/536861234340155392 矩阵旋转
def _rotate_row_right(matrix, row):
    """右旋转"""
    col_tmp = matrix[row][-1]
    for i in range(len(matrix)-1, 0, -1):
        matrix[row][i] = matrix[row][i-1]
    matrix[row][0] = col_tmp

def _rotate_column_up(matrix, col):
    """上旋转"""
    row0_tmp = matrix[0][col]
    for i in range(len(matrix) - 1):
        matrix[i][col] = matrix[i + 1][col]
    matrix[len(matrix) - 1][col] = row0_tmp

def _matrix_tuple(matrix):
    """将矩阵转换为元组"""
    return tuple(tuple(row) for row in matrix)

def calculate_steps(matrix):
    """计算最少旋转次数"""
    initial = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    queue = [(matrix, 0)]
    memo = set()
    while queue:
        # 如果匹配到了，则返回
        cur_matrix, step = queue.pop(0)
        if cur_matrix == initial:
            return step

        # 备忘录
        cur_tuple = _matrix_tuple(cur_matrix)
        if cur_tuple in memo:
            continue
        memo.add(cur_tuple)

        # 否则右旋转进队列
        for i in range(3):
            new_matrix = [row[:] for row in cur_matrix]
            _rotate_row_right(new_matrix, i)
            queue.append((new_matrix, step + 1))
        # 上旋转进队列
        for i in range(3):
            new_matrix = [row[:] for row in cur_matrix]
            _rotate_column_up(new_matrix, i)
            queue.append((new_matrix, step + 1))

    return -1

# 小马一面 算法题
# A 字符串，B 字符串去猜。输出位置相同且字符相同，输出位置不同但字符相同，每个字符只能统计一次
# A = '11234'
# B = '01114'
# 输出 (2, 1)
def find_same_char():
    from collections import defaultdict
    A = '1123460'
    B = '01114666'
    lenA = len(A)
    lenB = len(B)

    # 相同位置和不同位置的
    right_pos_cnt = wrong_pos_cnt = 0

    # 临时保存A没匹配上的
    not_match_A = defaultdict(int)
    not_match_B = defaultdict(int)

    # 遍历
    for idx in range(max(lenA, lenB)):
        # 匹配上
        if idx < lenA and idx < lenB and A[idx] == B[idx]:
            right_pos_cnt += 1
            continue

        # 没匹配上
        if idx < lenA:
            not_match_A[A[idx]] += 1
        if idx < lenB:
            not_match_B[B[idx]] += 1

    # 位置不同的
    for c, times in not_match_A.items():
        if c in not_match_B:
            wrong_pos_cnt += min(times, not_match_B[c])

    print(right_pos_cnt, wrong_pos_cnt)


if __name__ == '__main__':
    # sove2(10, 3, [1, 1, 32])

    # initial = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    # print(initial)
    # _rotate_row_right(initial, 0)
    # print(initial)
    # _rotate_column_up(initial, 0)
    # print(initial)

    # 示例
    # rotated_matrix = [
    #     [1, 5, 3],
    #     [4, 8, 6],
    #     [7, 2, 9]
    # ]
    # print(calculate_steps(rotated_matrix))

    find_same_char()