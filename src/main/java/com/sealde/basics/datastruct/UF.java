package com.sealde.basics.datastruct;

/**
 * 并查集
 * @Author: sealde
 * @Date: 2020/2/2 下午4:42
 */
public class UF {
    private int count;
    private int[] parent;
    private byte[] rank;

    public UF(int len) {
        this.count = len;
        this.rank = new byte[len];
        this.parent = new int[len];
        for (int i = 0; i < len; i++) {
            this.rank[i] = 0;
            this.parent[i] = i;
        }
    }

    private int find(int index) {
        while (index != this.parent[index]) {
            // 路径压缩：父节点指向爷爷节点
            this.parent[index] = this.parent[this.parent[index]];
            index = this.parent[index];
        }
        return index;
    }

    public int count() {
        return this.count;
    }

    public void union(int p, int q) {
        int rootP = this.find(p);
        int rootQ = this.find(q);
        if (rootP == rootQ) {
            return;
        }
        if (this.rank[rootP] < this.rank[rootQ]) {
            this.parent[rootP] = rootQ;
        } else if (this.rank[rootP] > this.rank[rootQ]) {
            this.parent[rootQ] = rootP;
        } else {
            this.parent[rootP] = rootQ;
            this.rank[rootQ]++;
        }
        this.count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
