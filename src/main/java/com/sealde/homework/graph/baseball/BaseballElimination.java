package com.sealde.homework.graph.baseball;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BaseballElimination {
    private final List<String> names;
    private final int[][] teams;
    private final int[][] matches;
    private final int num;

    public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified below
        In input = new In(filename);
        int num = input.readInt();
        input.readLine();
        this.num = num;
        names = new ArrayList<>(num);
        teams = new int[num][3];
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

    private void drawNetwork(int s) {
        FlowNetwork network = new FlowNetwork(num);
    }

    public              int numberOfTeams() {                       // number of teams
        return num;
    }

    public Iterable<String> teams() {                               // all teams
        return names;
    }

    public              int wins(String team) {                     // number of wins for given team
        return 0;
    }

    public              int losses(String team) {                   // number of losses for given team
        return 0;
    }

    public              int remaining(String team) {                // number of remaining games for given team
        return 0;
    }

    public              int against(String team1, String team2) {   // number of remaining games between team1 and team2
        return 0;
    }

    public          boolean isEliminated(String team) {             // is given team eliminated?
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
