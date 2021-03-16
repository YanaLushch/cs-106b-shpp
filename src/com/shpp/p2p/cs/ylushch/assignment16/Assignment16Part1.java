package com.shpp.p2p.cs.ylushch.assignment16;

import com.shpp.p2p.cs.ylushch.assignment16.tests.TestMyArrayList;
import com.shpp.p2p.cs.ylushch.assignment16.tests.TestMyLinkedList;
import com.shpp.p2p.cs.ylushch.assignment16.tests.TestMyQueue;
import com.shpp.p2p.cs.ylushch.assignment16.tests.TestMyStack;

/**
 * This class runs the tests of my own Collections
 */
public class Assignment16Part1 {
    /**
     * This method initiates the tests to be run
     */
    public static void main(String[] args){
        final String RESET = "\033[m";
        final String BRIGHT_GREEN = "\033[38;2;0;255;0;02m";
        System.out.println( BRIGHT_GREEN + "Let's test MyArrayList" + RESET);
        new TestMyArrayList();
        System.out.println(BRIGHT_GREEN +  "Let's test MyLinkedList" + RESET );
        new TestMyLinkedList();
        System.out.println(BRIGHT_GREEN +  "Let's test MyStack" + RESET );
        new TestMyStack();
        System.out.println(BRIGHT_GREEN +  "Let's test MyQueue" + RESET );
        new TestMyQueue();
    }
}
