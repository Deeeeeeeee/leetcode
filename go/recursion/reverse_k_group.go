package recursion

// 25. K 个一组翻转链表
// 困难
// 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
// k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
// 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。

// 示例 1：
// 输入：head = [1,2,3,4,5], k = 2
// 输出：[2,1,4,3,5]

// 示例 2：
// 输入：head = [1,2,3,4,5], k = 3
// 输出：[3,2,1,4,5]

// 提示：
// 链表中的节点数目为 n
// 1 <= k <= n <= 5000
// 0 <= Node.val <= 1000

// 进阶：你可以设计一个只用 O(1) 额外内存空间的算法解决此问题吗？

type ListNode struct {
	Val  int
	Next *ListNode
}

func reverseKGroup(head *ListNode, k int) *ListNode {
	// 用栈来实现
	i := k
	stack := []*ListNode{}
	var v *ListNode = &ListNode{0, nil}

	// 从头遍历到尾部
	cur := head
	curV := v
	for cur != nil {
		// 入栈
		stack = append(stack, cur)
		cur = cur.Next

		i--
		if i > 0 {
			continue
		}
		i = k

		for j := 0; j < k; j++ {
			next := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			next.Next = nil
			curV.Next = next
			curV = next
		}
	}

	for _, next := range stack {
		curV.Next = next
		curV = next
	}

	return v.Next
}
