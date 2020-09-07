package com.sealde.basics.graph.maxflow;

public class FlowEdge {
    private static final double FLOATING_POINT_EPSILON = 1E-10;

    private final int v;
    private final int w;
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capacity) {
        if (v < 0) throw new IllegalArgumentException();
        if (w < 0) throw new IllegalArgumentException();
        if (capacity < 0.0) throw new IllegalArgumentException();
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public FlowEdge(int v, int w, double capacity, double flow) {
        if (v < 0) throw new IllegalArgumentException();
        if (w < 0) throw new IllegalArgumentException();
        if (capacity < 0.0) throw new IllegalArgumentException();
        if (flow < 0.0) throw new IllegalArgumentException();
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = flow;
    }

    public FlowEdge(FlowEdge e) {
        this.v = e.v;
        this.w = e.w;
        this.capacity = e.capacity;
        this.flow = e.flow;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (v == vertex) return w;
        else if (w == vertex) return v;
        else throw new IllegalArgumentException();
    }

    // 残差容量。即往这个顶点方向还可以增加的流量
    // 边界 v -> w。 flow 表示当前的流量
    // to v 的残差容量，即为 flow。因为可以增加 flow 这么多流量（相当于减小边界的流量）
    // to w 的残差容量，为 capacity - flow。相当于正常理解的剩余可以增加的流量
    public double residualCapacityTo(int vertex) {
        if (v == vertex) return flow;
        else if (w == vertex) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (delta < 0.0) throw new IllegalArgumentException();

        if (v == vertex) flow -= delta;
        else if (w == vertex) flow += delta;
        else throw new IllegalArgumentException();

        if (Math.abs(flow) <= FLOATING_POINT_EPSILON) {
            flow = 0.0;
        }
        if (Math.abs(capacity - flow) <= FLOATING_POINT_EPSILON) {
            flow = capacity;
        }

        if (flow > capacity || flow < 0.0) throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }
}
