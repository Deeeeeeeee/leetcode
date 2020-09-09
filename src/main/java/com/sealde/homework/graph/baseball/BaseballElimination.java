package com.sealde.homework.graph.baseball;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BaseballElimination {
    private final List<String> names;   // 队伍名称
    private final int[][] teams;        // 队伍基本信息 wins, loss, left
    private final int[][] matches;      // 队伍对战信息
    private final int num;              // 队伍数量

    public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified below
        In input = new In(filename);
        this.num = input.readInt();
        input.readLine();
        names = new ArrayList<>(num);
        teams = new int[num][2];
        matches = new int[num][num];
        for (int i = 0; i < num; i++) {
            String line = input.readLine();
            String[] items = line.trim().split("\\s+");
            names.add(items[0]);                                // 队名
            teams[i] = new int[] {
                    Integer.parseInt(items[1]),                 // wins 已经赢的场数
                    Integer.parseInt(items[2]),                 // loss 已经输的场数
                    Integer.parseInt(items[3])};                // left 剩余的场数
            for (int j = 0; j < num; j++) {
                matches[i][j] = Integer.parseInt(items[4+j]);
            }
        }
    }

    /**
     * 构建 FlowNetwork
     */
    private FlowNetwork drawNetwork(int s) {
        int n = num*(num-1)/2 + 2;
        int gameij = 1;
        int v = 1 + (num-1)*(num-2)/2;
        FlowNetwork network = new FlowNetwork(n);
        // 各vertex -> t
        int ws = teams[s][0];
        int rs = teams[s][2];
        for (int i = 0; i < num-1; i++) {
            int wi = i >= s ? teams[i+1][0] : teams[i][0];
            // 如果 w[s] + r[s] = w[i] 已经小于 0，那么被淘汰
            if (ws+rs-wi < 0) return null;
            network.addEdge(new FlowEdge(v+i, n-1, ws+rs-wi));
        }
        // s -> gameij -> 各vertex
        for (int i = 0; i < num; i++) {
            if (i == s) continue;
            for (int j = i + 1; j < num; j++) {
                if (j == s) continue;
                int vi = i > s ? v+i-1 : v+i;
                int vj = j > s ? v+j-1 : v+j;
                network.addEdge(new FlowEdge(0, gameij, matches[i][j]));
                network.addEdge(new FlowEdge(gameij, vi, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(gameij, vj, Double.POSITIVE_INFINITY));
                gameij++;
            }
        }
        return network;
    }

    public              int numberOfTeams() {                       // number of teams
        return num;
    }

    public Iterable<String> teams() {                               // all teams
        return names;
    }

    public              int wins(String team) {                     // number of wins for given team
        int i = names.indexOf(team);
        if (i == -1) throw new IllegalArgumentException();
        return teams[i][0];
    }

    public              int losses(String team) {                   // number of losses for given team
        int i = names.indexOf(team);
        if (i == -1) throw new IllegalArgumentException();
        return teams[i][1];
    }

    public              int remaining(String team) {                // number of remaining games for given team
        int i = names.indexOf(team);
        if (i == -1) throw new IllegalArgumentException();
        return teams[i][2];
    }

    public              int against(String team1, String team2) {   // number of remaining games between team1 and team2
        int i = names.indexOf(team1);
        int j = names.indexOf(team2);
        if (i == -1 || j == -1) throw new IllegalArgumentException();
        return matches[i][j];
    }

    public          boolean isEliminated(String team) {             // is given team eliminated?
        int i = names.indexOf(team);
        if (i == -1) throw new IllegalArgumentException();
        FlowNetwork network = drawNetwork(i);
        if (network == null) return true;
        FordFulkerson ffk = new FordFulkerson(network, 0, network.V()-1);
        for (int j = 1; j <= (num-1)*(num-2)/2; j++) {
            if (ffk.inCut(j)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        int s = names.indexOf(team);
        if (s == -1) throw new IllegalArgumentException();
        FlowNetwork network = drawNetwork(s);
        if (network == null) {
            int ws = teams[s][0];
            int rs = teams[s][2];
            for (int j = 0; j < num-1; j++) {
                int i = j >= s ? j + 1 : j;
                int wi = teams[i][0];
                // 如果 w[s] + r[s] = w[i] 已经小于 0，那么被淘汰
                if (ws+rs-wi < 0) return Collections.singletonList(names.get(i));
            }
        } else {
            Set<String> result = new HashSet<>();
            FordFulkerson ffk = new FordFulkerson(network, 0, network.V() - 1);
            int gameij = 1;
            for (int i = 0; i < num; i++) {
                if (i == s) continue;
                for (int j = i + 1; j < num; j++) {
                    if (j == s) continue;
                    if (ffk.inCut(gameij)) {
                        result.add(names.get(i));
                        result.add(names.get(j));
                    }
                    gameij++;
                }
            }
            return result.size() > 0 ? result : null;
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
//        for (String team : division.teams()) {
//            if (division.isEliminated(team)) {
//                StdOut.print(team + " is eliminated by the subset R = { ");
//                for (String t : division.certificateOfElimination(team)) {
//                    StdOut.print(t + " ");
//                }
//                StdOut.println("}");
//            }
//            else {
//                StdOut.println(team + " is not eliminated");
//            }
//        }
        System.out.println(division.certificateOfElimination("New_York"));
    }
}
