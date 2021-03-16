package com.shpp.p2p.cs.ylushch.assignment15;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanTree {

    /**
     * This method creates the priority queue of the nodes - where the right and left neighbor are yet null
     *
     * @param freqMap frequency Map
     */
    static Queue<Node> createQueue(Map<Integer, Integer> freqMap) {
        Queue<Node> priorityQueue = new PriorityQueue<>(50, new HuffComparator());
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }
        return priorityQueue;
    }

    /**
     * This method builds the tree from the Nodes
     *
     * @param freqMap frequency Map
     */
    static Node buildTree(Map<Integer, Integer> freqMap) {
        Queue<Node> nodeQueue = HuffmanTree.createQueue(freqMap);
        while (nodeQueue.size() > 1) {
            Node node1 = nodeQueue.remove();
            Node node2 = nodeQueue.remove();
            Node node = new Node('\0', node1.frequency + node2.frequency, node1, node2);
            nodeQueue.add(node);
        }
        return nodeQueue.remove();
    }
}
