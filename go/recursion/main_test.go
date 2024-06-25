package recursion

import (
	"fmt"
	"testing"
)

func TestReverseKGroup(t *testing.T) {
	var cur = &ListNode{1, nil}
	head := cur
	for _, i := range []int{2} {
		cur.Next = &ListNode{i, nil}
		cur = cur.Next
	}

	res := reverseKGroup(head, 2)

	for res != nil {
		fmt.Printf("%v\n", res.Val)
		res = res.Next
	}
}

func TestReverseList(t *testing.T) {
	var cur = &ListNode{1, nil}
	head := cur
	for _, i := range []int{2, 3, 4, 5} {
		cur.Next = &ListNode{i, nil}
		cur = cur.Next
	}

	// res := reverseList(head)
	res := reverseList2(head)

	for res != nil {
		fmt.Printf("%v\n", res.Val)
		res = res.Next
	}
}
