package com.sealde.leetcode.heap;

public class LeastNumbers {
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0) {
            return new int[0];
        }
        MaxPQ pq = new MaxPQ(k);
        for (int i : arr) {
            pq.insert(i);
        }
        return pq.result();
    }

    private class MaxPQ {
        private int size = 0;
        private int[] arr;

        public MaxPQ(int k) {
            this.arr = new int[k];
        }

        public int[] result() {
            return arr;
        }

        public void insert(int k) {
            if (size == arr.length) {
                // 如果新增的数已经是 >= max，就不插入了
                if (k >= arr[0]) {
                    return;
                } else {
                    // 如果存储已经满了，那么直接替换掉 max，然后 sink
                    arr[0] = k;
                    sink(1);
                }
            } else {
                // 否则从尾部添加并且 swim
                arr[size++] = k;
                swim(size);
            }
        }

        private void swim(int k) {
            while (k > 1 && less((k>>1), k)) {
                exch((k>>1), k);
                k = (k>>1);
            }
        }

        private void sink(int k) {
            while ((k<<1) <= size) {
                int j = (k<<1);
                if (j < size && less(j, j+1)) {
                    j++;
                }
                if (!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }

        private void exch(int i, int j) {
            int tmp = arr[i-1];
            arr[i-1] = arr[j-1];
            arr[j-1] = tmp;
        }

        private boolean less(int i, int j) {
            return arr[i-1] < arr[j-1];
        }
    }

    public static void main(String[] args) {
        LeastNumbers l = new LeastNumbers();
        int[] result = l.getLeastNumbers(new int[]{3, 2, 1}, 2);
        for (int i : result) {
            System.out.printf("%d ", i);
        }
    }
}
