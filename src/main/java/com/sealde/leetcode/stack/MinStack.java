package com.sealde.leetcode.stack;

import java.util.Stack;

/**
 * @Author: sealde
 * @Date: 2020/2/9 下午6:03
 */
public class MinStack {
    /**
     * No.155 最小栈
     *
     * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
     *
     * push(x) -- 将元素 x 推入栈中。
     * pop() -- 删除栈顶的元素。
     * top() -- 获取栈顶元素。
     * getMin() -- 检索栈中的最小元素。
     * 示例:
     *
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/min-stack
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    private Stack<Integer> data = new Stack<>();
    private Stack<Integer> helper = new Stack<>();
    private int min;

    /**
     * 思路： 一个存数据的栈，一个辅助栈
     *      push 当有 <= min 的值进来时，辅助栈 push 元素
     *      pop 当 min == 数据栈的 top 时，辅助栈 pop 元素
     */
    public MinStack() {

    }

    public void push(int x) {
        this.data.push(x);
        if (this.helper.isEmpty() || x <= min) {
            this.helper.push(x);
            min = x;
        }
    }

    public void pop() {
        int top = this.data.pop();
        if (min == top) {
            this.helper.pop();
            if (!this.helper.isEmpty()) {
                min = this.helper.peek();
            }
        }
    }

    public int top() {
        return this.data.peek();
    }

    public int getMin() {
        return min;
    }
}
