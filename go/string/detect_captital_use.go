package string

import "unicode"

// 520. 检测大写字母
// 简单
// 我们定义，在以下情况时，单词的大写用法是正确的：

// 全部字母都是大写，比如 "USA" 。
// 单词中所有字母都不是大写，比如 "leetcode" 。
// 如果单词不只含有一个字母，只有首字母大写， 比如 "Google" 。
// 给你一个字符串 word 。如果大写用法正确，返回 true ；否则，返回 false 。

// 示例 1：
// 输入：word = "USA"
// 输出：true

// 示例 2：
// 输入：word = "FlaG"
// 输出：false

// 提示：
// 1 <= word.length <= 100
// word 由小写和大写英文字母组成

func detectCapitalUse(word string) bool {
	if len(word) == 1 {
		return true
	}

	// 如果首字母是小写字母，则后面都得小写
	// 如果是 AA 则后面都得大写，如果是 Aa 后面都得小写
	mustLow := false
	startIndex := 1

	if unicode.IsLower(rune(word[0])) {
		mustLow = true
	} else if unicode.IsLower(rune(word[1])) {
		mustLow = true
		startIndex = 2
	}

	for _, c := range word[startIndex:] {
		if mustLow {
			if !unicode.IsLower(rune(c)) {
				return false
			}
		} else {
			if !unicode.IsUpper(rune(c)) {
				return false
			}
		}
	}

	return true
}
