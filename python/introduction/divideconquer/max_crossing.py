#- coding: utf-8 -#
# 最大子数组
# 《算法导论》第4章

# 跨越中点。从中点往两边走，求得最大值
def find_max_crossing_subarry(A, low, mid, high):
    left_sum = -float('inf')
    sum = 0
    # 从 mid 往低走
    for i in range(mid, low - 1, -1):
        sum += A[i]
        if sum > left_sum:
            left_sum = sum
            max_left = i

    right_sum = -float('inf')
    sum = 0
    # 从 mid 往高走
    for j in range(mid + 1, high + 1):
        sum += A[j]
        if sum > right_sum:
            right_sum = sum
            max_right = j

    # 返回最大子数组中之的和
    return (max_left, max_right, left_sum + right_sum)

def find_maximum_subarray(A, low, high):
    if low == high:
        return (low, high, A[low])
    else:
        mid = (low + high) // 2
        # 左边，右边，跨越中点
        left_low, left_high, left_sum = find_maximum_subarray(A, low, mid)
        right_low, right_high, right_sum = find_maximum_subarray(A, mid+1, high)
        cross_low, cross_high, cross_sum = find_max_crossing_subarry(A, low, mid, high)

        # 最大在左边
        if left_sum >= right_sum and left_sum >= cross_sum:
            return left_low, left_high, left_sum
        # 最大在右边
        elif right_sum >= left_sum and right_sum >= cross_sum:
            return right_low, right_high, right_sum
        # 最大跨越了中点
        else:
            return cross_low, cross_high, cross_sum


if __name__ == '__main__':
    A = [13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7]
    print(find_maximum_subarray(A, 0, len(A)-1))

    from pymongo import MongoClient
    conn = MongoClient()
    db = conn.get_database("test")
    db["test"].find()