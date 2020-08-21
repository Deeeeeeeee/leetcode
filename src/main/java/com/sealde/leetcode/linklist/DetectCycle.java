package com.sealde.leetcode.linklist;

/**
 * 142. 环形链表 II
 *
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 说明：不允许修改给定的链表。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：tail connects to node index 1
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：tail connects to node index 0
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：no cycle
 * 解释：链表中没有环。
 *
 *
 *  
 *
 * 进阶：
 * 你是否可以不用额外空间解决此题？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DetectCycle {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 其中【*】为快慢指针首次相遇点，入环前距离为【D】，慢指针入环后走过的距离为【S1】，环剩下距离为【S2】
     *
     * 首次相遇
     * 1.慢指针 S = D + S1
     * 2.快指针 F = D + n(S1 + S2) + S1 其中n>=1,快指针起码走了一圈以上才可能相遇
     * 3.又因为 F = 2S 慢指针走一步，快指针走两步
     * 4.代入1，2 可得 2(D + S1) = D + n(S1 + S2) + S1
     * 各种移项可得 D = (n-1)S1 + nS2 = (n-1)(S1 + S2) + S2
     * 5.其中 n为快指针绕的圈数
     * n=1 D = S2
     * n=2 D = 一圈 + S2
     * n=3 D = 两圈 + S2
     * .....
     * 所以其实我们并不关心绕了多少圈，就知道 n圈+S2就是入环点了
     * 6.人为构造碰撞机会，让快指针重新出发（但这次一次走一步），只要碰撞了，就是入环位置了，管他慢指针在环里绕了多少圈
     *
     * 作者：darin-ldr
     * 链接：https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/ji-ju-hua-jiang-ming-kuai-man-zhi-zhen-by-darin-ld/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        // 第一遍相遇
        while (true) {
            if (slow == null || fast == null || fast.next == null) {
                return null;
            } else if (slow == fast) {
                break;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        // 第二遍在环入口相遇
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
