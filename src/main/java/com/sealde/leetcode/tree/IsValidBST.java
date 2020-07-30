package com.sealde.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

public class IsValidBST {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // 广度优先
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) {
                    q.offer(node.left);
                    if (node.val >= node.left.val) {
                        return false;
                    }
                }
                if (node.right != null) {
                    q.offer(node.right);
                    if (node.val <= node.right.val) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
//        TreeNode root = new TreeNode(10);
//        root.left = new TreeNode(5);
//        root.right = new TreeNode(15);
//        root.right.left = new TreeNode(6);
//        root.right.right = new TreeNode(20);
//        IsValidBST b = new IsValidBST();
//        b.isValidBST(root);
        long a = 10;
        int b = (int) a;
        System.out.println(b);
    }
}
