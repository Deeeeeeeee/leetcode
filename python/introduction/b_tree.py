#- coding: utf-8 -#
# B树
# 根据《算法导论》实现. search 和 insert 部分

# 节点。一般构造一个节点，需要分配一个磁盘页。 ALLOCATE-NODE
class Node():
    def __init__(self, n=0, leaf=True, key_max=0):
        # 节点关键字数量
        self.n = n
        # 是否叶子节点
        self.leaf = leaf

        # 关键字值
        self.keys = [None] * key_max
        # 叶子节点
        self.childs = [None] * (key_max+1)

        # 需要 ALLOCATE-NODE


class BTree():
    def __init__(self, t):
        # B树最小度数
        self.t = t

        # 根节点以外每个内部节点至少有 t 个子节点，即至少 t-1 个关键字
        self.min_key_num = t - 1
        self.min_child_num = t
        # 每个节点至多包含 2t-1 个关键字，至多 2t 个子节点
        self.max_key_num = 2*t - 1
        self.max_child_num = 2*t

        # 根节点
        self.root = Node(n=0, leaf=True, key_max=self.max_key_num)

    # 搜索
    def search(self, x, k):
        # 线性查找最小下标i, k <= x.keys[i]
        i = 0
        while i <= x.n and k > x.keys[i]:
            i += 1

        # 找到关键字
        if i <= x.n and k == x.keys[i]:
            return (x, i)
        # 如果是叶子节点，则查找失败
        elif x.leaf:
            return None
        # 非叶子节点，递归查找。需要 DISK-READ(x, childs[i])
        else:
            return self.search(x.childs[i], k)

    # 插入关键字. 这里 t=4
    # r     A D F H L N P                  s   H
    #                                          ↓
    #                                     ↓         ↓
    #                                 r A D F     L N P
    #       2t个子节点                t个子节点   t个子节点
    def insert(self, k):
        r = self.root
        # 如果根节点是满节点
        if r.n == self.max_key_num:
            # 分配新节点，并成为根节点，原根节点变成子节点
            s = Node(n=0, leaf=False, key_max=self.max_key_num)
            self.root = s
            s.childs[0] = r

            # 分裂根节点，是增加B树高度的唯一途径
            self._split_child(s, 0)
            self._insert_notfull(s, k)
        else:
            self._insert_notfull(r, k)


    # 分裂，超过 max_key_num 时，即 2*t - 1，需要分裂。分裂成两个 t-1 关键字。这里 t = 4
    # x   ... N W ...                 x ... N W S ...
    #          ↓                              ↓
    # y=x.c[i] ↓                y=x.c[i] ↓         ↓ z=x.c[i+1]
    #   P Q R S T U V               P Q R        T U V
    #   2t个子节点                  t个子节点     t个子节点
    def _split_child(self, x, i):
        # 分配一个新节点
        z = Node(n=0, leaf=True, key_max=self.max_key_num)

        # 新节点继承 leaf 属性
        y = x.childs[i]
        z.leaf = y.leaf
        z.n = self.t - 1

        # 将 y 的后半部分复制到 z。需要复制 keys 和 childs
        for j in range(self.t - 1):
            z.keys[j] = y.keys[j + self.t]
        if not y.leaf:
            for j in range(self.t):
                z.childs[j] = y.childs[j + self.t]

        # y 的关键字数也要修改
        y.n = self.t - 1

        # 将 z 节点插入到父节点 x 的 childs 中
        for j in range(x.n+1, i, -1):
            x.childs[j] = x.childs[j-1]
        x.childs[i+1] = z

        # 提升 y 的中间关键字到 x
        for j in range(x.n, i-1, -1):
            x.keys[j] = x.keys[j-1]
        x.keys[i] = y.keys[self.t-1]

        # 调整 x 的关键字个数
        x.n += 1

        # 需要 DISK-WRITE(y) DISK-WRITE(z) DISK-WRITE(x)

    def _insert_notfull(self, x, k):
        i = x.n
        # 如果是叶子节点，直接插入
        if x.leaf:
            while i >= 1 and k < x.keys[i-1]:
                x.keys[i] = x.keys[i-1]
                i -= 1
            x.keys[i] = k
            # 更新关键字个数
            x.n += 1
            # 需要 DISK-WRITE(x)
        # 不是叶子节点，需要插入到合适的叶子节点中
        else:
            while i >= 1 and k < x.keys[i-1]:
                i -= 1
            i += 1
            # 需要 DISK-READ(x, c[i])

            # 判断是否满的子节点
            if x.childs[i-1].n == self.max_key_num:
                self._split_child(x, i-1)

                # 看插入到左子树还是右子树中
                if k > x.keys[i-1]:
                    i += 1

            # 递归插入到合适的子树中
            self._insert_notfull(x.childs[i-1], k)

    def print_tree(self):
        self._preorder(self.root, "", 0)

    def _preorder(self, x, symbol, deep):
        if x is None:
            return

        blue = "\033[34m"
        white = "\033[0m"
        for i, key in enumerate(x.keys[:x.n]):
            # 判断是否最后一个
            if i == 0:
                prefix = " ┌─ "
            elif i == x.n-1:
                prefix = " └─ "
            else:
                prefix = " ├─ "
            # 如果是叶子节点
            if x.leaf:
                print(f"{symbol}{prefix}{white}{key}")
            else:
                if i == 0:
                    self._preorder(x.childs[i], f"{symbol}{'   '}", deep+1)
                print(f"{symbol}{prefix}{blue}{key}{white}")
                self._preorder(x.childs[i+1], f"{symbol}{'   ' if i == x.n else ' │ '}", deep+1)



if __name__ == '__main__':
    tree = BTree(3)
    for x in ['G','M','P','X', 'A','C','D','E','J','K','N','O','R','S','T','U','V','Y','Z', 'B','Q','L', 'F']:
        tree.insert(x)
        tree.print_tree()
        print("-------------------------- insert", x)