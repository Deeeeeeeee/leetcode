package com.sealde.leetcode.heap;

import java.util.PriorityQueue;

public class MergeKList {
    /**
     * No.23 合并 K 个排序链表
     *
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     *
     * 示例:
     *
     * 输入:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 输出: 1->1->2->3->4->4->5->6
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 思路，使用 min 堆；先把所有首节点初始到 pq 中，然后利用堆的特性，不断地 poll 最小的 node
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        // 创建优先队列，放入首节点
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> (a.val - b.val));
        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }
        // 临时创建一个节点作为 result
        ListNode result = new ListNode(-1);
        ListNode curr = result;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            curr.next = new ListNode(node.val);
            curr = curr.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }
        // 返回 result.next
        return result.next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
