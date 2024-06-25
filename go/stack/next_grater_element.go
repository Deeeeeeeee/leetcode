package stack

// 503. 下一个更大元素 II
// 中等
// 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。

// 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。

// 示例 1:
// 输入: nums = [1,2,1]
// 输出: [2,-1,2]
// 解释: 第一个 1 的下一个更大的数是 2；
// 数字 2 找不到下一个更大的数；
// 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。

// 示例 2:
// 输入: nums = [1,2,3,4,3]
// 输出: [2,3,4,-1,4]

// 提示:
// 1 <= nums.length <= 104
// -109 <= nums[i] <= 109

func nextGreaterElements(nums []int) []int {
	res := make([]int, len(nums))

	// 从头开始进栈
	// 如果当前数字比栈里的小，则继续进栈
	// 如果当前数字比栈顶的大，则出栈且设置对应的值，然后当前的值进栈
	var stack []int
	for i, n := range nums {
		l := len(stack)

		for l > 0 && nums[stack[l-1]] < n {
			res[stack[l-1]] = n
			stack = stack[:l-1]
			l--
		}

		stack = append(stack, i)
	}

	// 循环完后，如果栈里还有元素，则继续从头开始，但不再进栈
	for i, n := range nums {
		l := len(stack)

		if i == stack[0] {
			for _, sn := range stack {
				if nums[sn] < n {
					res[sn] = n
				} else {
					res[sn] = -1
				}
			}
			break
		}

		for l > 0 && nums[stack[l-1]] < n {
			res[stack[l-1]] = n
			stack = stack[:l-1]
			l--
		}
	}

	return res
}
