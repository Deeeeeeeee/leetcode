package com.sealde.leetcode.sort;

public class SortList {
    public ListNode sortList(ListNode head) {
        ListNode aux = null;
        ListNode ac = null;
        ListNode hc = head;
        int n = 0;
        while (hc != null) {
            if (ac == null) {
                aux = new ListNode(hc.val);
                ac = aux;
            } else {
                ac.next = new ListNode(hc.val);
                ac = ac.next;
            }
            hc = hc.next;
            n++;
        }
        hc = head;
        ac = aux;

        for (int len = 1; len < n; len = len<<1) {
            for (int lo = 0; lo < n-len; lo+=len+len) {
                merge(hc, ac, len);
                for (int i = 0; i < len+len; i++) {
                    hc = hc.next;
                    ac = ac.next;
                }
            }
        }

        return head;
    }

    private void merge(ListNode l, ListNode aux, int step) {
        ListNode r = l;
        for (int i = 0; i < step; i++) {
            r = r.next;
        }

        for (int i = 0, lstep = 0; i < step+step && lstep < step; i++) {
            int tmp;
            if (r != null && l.val > r.val) {
                tmp = r.val;
                r = r.next;
            } else {
                tmp = l.val;
                l = l.next;
                lstep++;
            }
            aux.val = tmp;
            aux = aux.next;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        SortList sl = new SortList();
        System.out.println(sl.sortList(head));
    }
}
