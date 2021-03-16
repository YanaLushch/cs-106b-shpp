package com.shpp.p2p.cs.ylushch.assignment15;

public class HuffComparator implements java.util.Comparator<Node> {
    /**
     * Based on comparator right and left nodes are compared
     */
    @Override
    public int compare(Node node1, Node node2) {
        return node1.frequency - node2.frequency;
    }
}
