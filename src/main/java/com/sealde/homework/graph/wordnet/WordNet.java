package com.sealde.homework.graph.wordnet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private HashMap<String, Bag<Integer>> wordIds;
    private ArrayList<String> idList;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordIds = new HashMap<>();
        idList = new ArrayList<>();
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        int count = 0;
        In sIn = new In(synsets);
        while (sIn.hasNextLine()) {
            String[] split = sIn.readLine().split(",");
            Integer index = Integer.parseInt(split[0]);
            String[] nouns = split[1].split(" ");

            for (String noun : nouns) {
                if (wordIds.containsKey(noun)) {
                    wordIds.get(noun).add(index);
                } else {
                    Bag<Integer> b = new Bag<>();
                    b.add(index);
                    wordIds.put(noun, b);
                }
            }
            idList.add(split[1]);
            count++;
        }
        Digraph g = new Digraph(count);
        sIn.close();

        DirectedCycle dc = new DirectedCycle(g);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        boolean[] isNotRoot = new boolean[count];
        int rootNumber = 0;

        In hIn = new In(hypernyms);
        while (hIn.hasNextLine()) {
            String line = hIn.readLine();
            String[] split = line.split(",");
            int v = Integer.parseInt(split[0]);
            isNotRoot[v] = true;
            for (int i = 1; i < split.length; i++) {
                int w = Integer.parseInt(split[i]);
                g.addEdge(v, w);
            }
        }
        hIn.close();

        for (boolean b : isNotRoot) {
            if (!b) {
                rootNumber++;
            }
        }
        if (rootNumber > 1) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return wordIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(wordIds.get(nounA), wordIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return idList.get(sap.ancestor(wordIds.get(nounA), wordIds.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
