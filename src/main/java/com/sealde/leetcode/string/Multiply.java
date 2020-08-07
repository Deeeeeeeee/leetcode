package com.sealde.leetcode.string;

public class Multiply {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int n = num1.length(), m = num2.length();
        int[] result = new int[n+m];
        // 乘数逐个去乘被乘数
        for (int i = n - 1; i >= 0; i--) {
            int sNum = num1.charAt(i) - '0';
            int addNum = 0, startNum = num1.length()-1-i, j = num2.length() - 1;
            while (j >= 0 || addNum != 0) {
                int lNum = j >= 0 ? num2.charAt(j) - '0' : 0;
                addNum += (sNum*lNum + result[startNum]);
                result[startNum] = addNum % 10;
                addNum = addNum / 10;
                startNum++;
                j--;
            }
        }
        // 拼接字符串
        StringBuilder sb = new StringBuilder();
        boolean f = false;
        for (int i = n+m-1; i >= 0; i--) {
            if (f) {
                sb.append(result[i]);
            } else if (result[i] != 0) {
                sb.append(result[i]);
                f = true;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Multiply m = new Multiply();
        System.out.println(m.multiply("123", "456"));
    }
}
