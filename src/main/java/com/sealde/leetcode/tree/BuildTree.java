package com.sealde.leetcode.tree;

import java.util.HashMap;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

/**
 * 思路: 使用递归，分治。中序遍历：左 根 右。后序遍历：左 右 根。
 * 拿后序的 root 去中序中找 root 的位置 index，小于 index 的就是 root 左边的，大于 index 的就是 root 右边的；
 * 并且在后序中，左子树元素的个数和中序左子树元素的个数相同。
 *
 * 根据上面思路进行划分，然后子树中继续递归划分，直到剩下最后一个元素，或者没有元素
 */
public class BuildTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private HashMap<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int len = postorder.length;
        if (len == 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            map.put(inorder[i], i);
        }
        return buildChild(postorder, inorder, 0, len-1, 0, len-1);
    }

    private TreeNode buildChild(int[] postorder, int[] inorder, int plo, int phi, int ilo, int ihi) {
        if (ilo == ihi) {
            return new TreeNode(inorder[ilo]);
        }
        if (plo >= postorder.length || ilo > ihi) {
            return null;
        }
        int root = postorder[phi];
        TreeNode node = new TreeNode(root);
        // 寻找下标
        int index = map.get(root);
        int pmid = plo + (index - ilo) - 1;
        node.left = buildChild(postorder, inorder, plo, pmid, ilo, index-1);
        node.right = buildChild(postorder, inorder, pmid+1, phi-1, index+1, ihi);
        return node;
    }

    public static void main(String[] args) {
        int[] post = new int[]{3,2,1};
        int[] in = new int[]{3,2,1};
        BuildTree t = new BuildTree();
        t.buildTree(in, post);
    }
}
