package com.sealde.leetcode.sort;

public class InsertionSortList {
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head.next;
        ListNode v = new ListNode(0);
        v.next = head;
        while (curr != null) {
            ListNode it = v;
            // 寻找合适的位置
            while (curr != it.next && curr.val > it.next.val) {
                it = it.next;
            }
            // 插入到合适位置的后面
            if (curr != it) {
                insertAfter(it, curr.val);
                curr = delNode(curr);
            } else {
                curr = curr.next;
            }
        }
        return head;
    }

    // 插入到节点后面
    private void insertAfter(ListNode src, int val) {
        ListNode nextNode = src.next;
        src.next = new ListNode(val);
        src.next.next = nextNode;
    }

    // 删除当前节点
    private ListNode delNode(ListNode node) {
        if (node.next != null) {
            ListNode nextNode = node.next;
            node.val = nextNode.val;
            node.next = nextNode.next;
            return node;
        }
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;
        public ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        InsertionSortList s = new InsertionSortList();
        s.insertionSortList(head);
    }
}
