import collections
from typing import List

class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

# 定义：输入一棵二叉树的根节点，返回这棵树的前序遍历结果
def preorder(root: TreeNode) -> List[int]:
    res = []
    if not root:
        return res
    # 前序遍历的结果，root.val 在第一个
    res.append(root.val)
    # 后面接着左子树的前序遍历结果
    res.extend(preorder(root.left))
    # 最后接着右子树的前序遍历结果
    res.extend(preorder(root.right))
    return res

# 输入一棵二叉树的根节点，层序遍历这棵二叉树
def levelOrder(root: TreeNode) -> List[List[int]]:
    if not root:
        return []
    q = collections.deque()
    q.append(root)
    res = []
    while q:
        sz = len(q)
        tmp = []
        for i in range(sz):
            cur = q.popleft()
            tmp.append(cur.val)
            if cur.left:
                q.append(cur.left)
            if cur.right:
                q.append(cur.right)
        res.append(tmp)
    return res


if __name__ == '__main__':
    root = TreeNode(1)
    node2 = TreeNode(2)
    node3 = TreeNode(3)

    root.left = node2
    root.right = node3

    node2.left = TreeNode(5)
    node2.right = node4 = TreeNode(4)

    node4.left = TreeNode(6)
    node4.right = TreeNode(7)

    node3.left = TreeNode(8)
    node3.right = TreeNode(9)

    print(preorder(root))
    print(levelOrder(root))