package com.sealde.homework.graph.wordnet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private HashMap<String, Bag<Integer>> wordIds;
    private ArrayList<String> idList;
    private Digraph g;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordIds = new HashMap<>();
        idList = new ArrayList<>();
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        int count = 0;
        In sIn = new In(new File(synsets));
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
        g = new Digraph(count);
        sIn.close();

        In hIn = new In(new File(hypernyms));
        while (hIn.hasNextLine()) {
            String line = hIn.readLine();
            String[] split = line.split(",");
            int v = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                int w = Integer.parseInt(split[i]);
                g.addEdge(v, w);
            }
        }
        hIn.close();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
