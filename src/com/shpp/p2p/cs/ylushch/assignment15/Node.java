package com.shpp.p2p.cs.ylushch.assignment15;

public class Node {
    int oneByte;
    int frequency;
    Node rightSymbol;
    Node leftSymbol;

    /**
     * Constructor of node which has 4 parameters - the first one is the Byte itself, second - frequncy, and then two symbols - ledt and right
     */
    Node (int oneByte, int frequency,  Node leftSymbol,  Node rightSymbol) {
        this.oneByte = oneByte;
        this.frequency = frequency;
        this.leftSymbol = leftSymbol;
        this.rightSymbol = rightSymbol;
    }


}
