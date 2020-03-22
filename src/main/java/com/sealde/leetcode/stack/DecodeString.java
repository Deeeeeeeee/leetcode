package com.sealde.leetcode.stack;

import java.util.Stack;

public class DecodeString {
    /**
     * No.394 字符串解码
     *
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     *
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     *
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     *
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     *
     * 示例:
     *
     * s = "3[a]2[bc]", 返回 "aaabcbc".
     * s = "3[a2[c]]", 返回 "accaccacc".
     * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/decode-string
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 思路：使用栈来存储数据
     */
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<String> cs = new Stack<>();    // 存放字符串
        Stack<Integer> is = new Stack<>();   // 存放数字
        int n = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // Ascii 码性质
                n = n*10 + (c - '0');
            } else if (c == '[') {
                if (sb.length() != 0) {
                    cs.push(sb.toString());
                    sb.delete(0, sb.length());
                }
                is.push(n);
                cs.push("[");
                n = 0;
            } else if (c == ']') {
                // 取 is
                int nn = is.pop();
                // 取 char
                while (!"[".equals(cs.peek())) {
                    sb.insert(0, cs.pop());
                }
                cs.pop();
                String ss = sb.toString();
                // for 遍历 is 次
                for (int ii = 1; ii < nn; ii++) {
                    sb.append(ss);
                }
                cs.push(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(c);
            }
        }

        while (!cs.isEmpty()) {
            sb.insert(0, cs.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DecodeString ds = new DecodeString();
        System.out.println(ds.decodeString("3[a2[c]]"));
//        System.out.println(ds.decodeString("3[a]2[bc]"));
    }
}
