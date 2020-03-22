package com.sealde.leetcode.linklist;

public class MergeTwoList {
    /**
     * No.21 合并两个有序链表
     *
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
     *
     * 示例：
     *
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1);
        ListNode curr = pre;
        while (l1 != null || l2 != null) {
            // l1 <= l2 或者 l2 == null
            if (l2 == null || (l1 != null && l1.val <= l2.val)) {
                curr.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                // l2 < l1 或者 l1 == null
                curr.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            curr = curr.next;
        }
        return pre.next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
