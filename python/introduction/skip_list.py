#- coding: utf-8 -#
# skiplist
# 插入和删除 logN
# 有序
# 论文1990年 https://15721.courses.cs.cmu.edu/spring2018/papers/08-oltpindexes1/pugh-skiplists-cacm1990.pdf
import random

# 最高32层
MAX_LEVEL = 32
# random max
RAND_MAX = (1 << 31) - 1
# random threshold. rand_max 的 0.25
RAND_THRESHOLD = RAND_MAX >> 2


def random_level():
    """
    返回随机层数，创建 SkipListNode 的时候使用。介于 1 到 MAX_LEVEL 之间。
    powerlaw-alike 分布
    """
    level = 1
    while (random.randint(0, RAND_MAX) < RAND_THRESHOLD) and level < MAX_LEVEL:
        level += 1
    return level


class SkipListNode():
    def __init__(self, level, key, value):
        self.key = key
        self.value = value
        self.level = [SkipListLevel()]*level


class SkipListLevel():
    def __init__(self):
        self.forward = None


class SkipList():
    def __init__(self):
        self.header = SkipListNode(level=MAX_LEVEL, key=None, value=None)
        self.length = 0
        self.level = 1

    def insert(self, key, value):
        """
        在 key 位置，插入或者覆盖 value
        """
        # 临时保存，需要更新的 level
        update = [None]*MAX_LEVEL

        # 从最顶的 level 开始遍历，寻找 key 的位置
        x = self.header
        for i in range(self.level-1, -1, -1):
            while x.level[i].forward and x.level[i].forward.key < key:
                x = x.level[i].forward
            update[i] = x

        x = x.level[0].forward
        # 如果 key 相同，则更新 value
        if x and x.key == key:
            x.value = value
            return x

        # 如果新的 level 大于头节点的 level，则需要更新头节点的 level
        level = random_level()
        if level > self.level:
            for i in range(self.level, level):
                update[i] = self.header
            self.level = level

        # 将 x 的每层 level 插入到 update 和 update.forward 之间
        x = SkipListNode(level, key, value)
        for i in range(level):
            x.level[i].forward = update[i].level[i].forward
            update[i].level[i].forward = x

        self.length += 1

        return x

    def delete(self, key):
        """
        删除 key
        """
        # 需要更新的 level
        update = [None]*MAX_LEVEL

        # 从最顶的 level 开始遍历，寻找 key 的位置
        x = self.header
        for i in range(self.level-1, -1, -1):
            while x.level[i].forward and x.level[i].forward.key < key:
                x = x.level[i].forward
            update[i] = x

        # x 节点的值，是下一个节点最小值
        x = x.level[0].forward

        if x and x.key == key:
            # 节点跳过 x 节点
            # update.forward = x.forward
            for i in range(self.level):
                if update[i].level[i].forward != x:
                    break
                update[i].level[i].forward = x.level[i].forward
            # free(x)

            # 如果头节点的 forward 为空，则 level 降 1
            while self.level > 1 and self.header.level[self.level-1].forward == None:
                self.level -= 1

            self.length -= 1
            return 1

        return 0

    def search(self, key):
        # 从最顶的 level 开始遍历，寻找 key 的位置
        x = self.header
        for i in range(self.level-1, -1, -1):
            while x.level[i].forward and x.level[i].forward.key < key:
                x = x.level[i].forward

        # x 节点的值，是下一个节点最小值
        x = x.level[0].forward

        # 找到返回 x.value
        if x and x.key == key:
            return x.value
        else:
            return None


if __name__ == '__main__':
    slist = SkipList()

    print(slist.search(2))

    slist.insert(1, "a")
    # slist.insert(1, "b")
    slist.delete(1)

    print(slist.search(1))

    from collections import defaultdict
    d = defaultdict(int)
    for _ in range(1000000):
        d[random_level()] += 1
    print(sorted(d.items(), key=lambda x: x[0]))