package recursion

// 206. 反转链表
// 简单
// 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。

// 示例 1：
// 输入：head = [1,2,3,4,5]
// 输出：[5,4,3,2,1]

// 示例 2：
// 输入：head = [1,2]
// 输出：[2,1]

// 示例 3：
// 输入：head = []
// 输出：[]

// 提示：
// 链表中节点的数目范围是 [0, 5000]
// -5000 <= Node.val <= 5000

// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？

func reverseList(head *ListNode) *ListNode {
	if head == nil {
		return nil
	}

	// 不断地将当前地节点拼接到下一个节点后面
	// 例如 1 2 => 2 1     2 1 3 => 3 2 1
	var virtual = &ListNode{0, head}

	cur := head

	for cur.Next != nil {
		vNext := virtual.Next

		next := cur.Next     // 2
		virtual.Next = next  // v.next -> 2
		cur.Next = next.Next // 1.next -> 3
		next.Next = vNext    // 2.next -> 1
	}

	return virtual.Next
}

func reverseList2(head *ListNode) *ListNode {
	var pre *ListNode
	cur := head

	for cur != nil {
		next := cur.Next // 2
		cur.Next = pre   // 1.next -> pre
		pre = cur        // pre 1

		cur = next // cur 2
	}

	return pre
}
