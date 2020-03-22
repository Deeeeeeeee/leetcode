package com.sealde.leetcode.stack;

public class BracketValid {
    /**
     * No.20 有效括号
     *
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     *
     * 示例 1:
     *
     * 输入: "()"
     * 输出: true
     * 示例 2:
     *
     * 输入: "()[]{}"
     * 输出: true
     * 示例 3:
     *
     * 输入: "(]"
     * 输出: false
     * 示例 4:
     *
     * 输入: "([)]"
     * 输出: false
     * 示例 5:
     *
     * 输入: "{[]}"
     * 输出: true
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/valid-parentheses
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 思路：如果是左括号，入栈；如果不是，出栈，进行比较
     */
    public boolean isValid(String s) {
        int n = s.length();
        int index = 0;
        Character[] arr = new Character[n>>1];
        for (int i = 0; i < n; i++) {
            Character c = s.charAt(i);
            if (c.equals('(') || c.equals('{') || c.equals('[')) {
                // 如果 index 达到了 n 的一半，再加入数据就说明不能抵消了
                if (index == n>>1) {
                    return false;
                }
                arr[index++] = c;
            } else if (index == 0) {
                return false;
            } else {
                Character p = arr[--index];
                if ((c.equals(')') && !p.equals('(')) ||
                        (c.equals('}') && !p.equals('{')) ||
                        (c.equals(']') && !p.equals('['))
                ) {
                    return false;
                }
            }
        }
        return index == 0;
    }
}
