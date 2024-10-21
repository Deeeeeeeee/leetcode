#- coding: utf-8 -#
# 堆
# 《算法导论》

def left(i):
    """
    左节点，i*2 + 1
    """
    return i << 1 | 1


def right(i):
    """
    右节点，i*2+1
    """
    return left(i) + 1


def parent(i):
    """
    父节点，[i/2] 向下取整
    """
    return i >> 1


def max_heapify(arr, i, heap_size):
    """
    不停地向下交换最大值，直到满足堆的性质
    时间复杂度 lgN
    """
    l = left(i)
    r = right(i)
    # 判断左侧是否比当前节点大
    if l <= heap_size - 1 and arr[l] > arr[i]:
        largest = l
    else:
        largest = i

    # 判断右侧是否比当前节点大
    if r <= heap_size - 1 and arr[r] > arr[largest]:
        largest = r

    # 如果当前节点不是最大值，则交换
    # 并且需要递归判断
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]
        max_heapify(arr, largest, heap_size)

def build_max_heap(arr):
    """
    将无序的数组构建成堆
    时间复杂度 N
    """
    heap_size = len(arr)
    for i in range(len(arr)>>1, -1, -1):
        max_heapify(arr, i, heap_size)


def heapsort(arr):
    """
    先构建一个堆，然后每次将顶点放到尾部，然后重新构建堆
    时间复杂度 nlgN
    """
    heap_size = len(arr)
    build_max_heap(arr)

    for i in range(len(arr) - 1, 0, -1):
        arr[i], arr[0] = arr[0], arr[i]
        heap_size -= 1
        max_heapify(arr, 0, heap_size)


class MaxPQ():
    def __init__(self, capacity=16):
        self.heap_size = 0
        self.arr = [None]*capacity

    def is_empty(self):
        return self.heap_size == 0

    def max(self):
        return self.arr[0]

    def insert(self, key):
        # 如果容量不够，则扩容
        if self.heap_size == len(self.arr):
            self.resize(len(self.arr)<<1)

        self._increase_key(self.heap_size, key)
        self.heap_size += 1

    def _increase_key(self, i, key):
        """
        时间复杂度 lgN
        """
        if key < self.arr[i]:
            raise KeyError("new key is smaller than current key")

        # 插入到最大的节点，然后不断地与父节点对比，比父节点大，则交换
        self.arr[i] = key
        while i > 0 and self.arr[parent(i)] < self.arr[i]:
            self.arr[i], self.arr[parent(i)] = self.arr[parent(i)], self.arr[i]
            i = parent(i)

    def extract_max(self):
        """
        移除最大值，并返回
        时间复杂度 lgN
        """
        if self.is_empty():
            raise IndexError("heap underflow")

        max = self.arr[0]
        self.heap_size -= 1

        # 将最后一个节点放到堆顶，然后重新构建堆
        self.arr[0] = self.arr[self.heap_size]
        max_heapify(self.arr, 0, self.heap_size)
        # 释放最后一个节点
        self.arr[self.heap_size] = None

        # 如果数据的长度小于容量的1/4，则缩小容量
        if self.heap_size > 0 and self.heap_size < len(self.arr) >> 2:
            self.resize(len(self.arr) >> 1)

        return max

    def resize(self, capacity):
        if capacity > len(self.arr):
            self.arr.extend([None]*(capacity - len(self.arr)))
        else:
            self.arr = self.arr[:capacity]


if __name__ == "__main__":
    arr = [3, 2, 1, 5, 6, 4]
    # heapsort(arr)
    # print(arr)

    max_pq = MaxPQ(2)
    for i in arr:
        max_pq.insert(i)
        print(max_pq.arr, max_pq.max())

    print()

    for i in range(len(arr)):
        print(max_pq.arr, max_pq.extract_max())