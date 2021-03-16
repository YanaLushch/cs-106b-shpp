package com.shpp.p2p.cs.ylushch.assignment16.tests;

import com.shpp.p2p.cs.ylushch.assignment16.MyQueue;

/**
 * This class tests each method of MyQueue and prints the test results to the user
 */

public class TestMyQueue extends Helper {
    MyQueue<Integer> queue = new MyQueue<>();

    public TestMyQueue(){
        checkMethodAdd();
        checkMethodPeekAndPoll();
        checkMethodIsEmpty();
        checkMethodSize();
        System.out.println("Exception");
        checkPollException();
    }

    /**
     * This method checks whether add() in Queue works correctly
     */
    private void checkMethodAdd(){
        System.out.println("Adding the  " + VALUE + " elements to the beginning of the Queue...");
        for (int i = 1; i <= VALUE; i++) {
            queue.add(i);
        }
        System.out.println("Checking if size of Queue matches " + VALUE + " " + isPassed(queue.size() == VALUE));
    }
    /**
     * This method checks whether peek() && poll() in Queue works correctly
     */
    private void checkMethodPeekAndPoll(){
        System.out.println("Checking if peeked value is equal to polled " + isPassed(queue.peek().equals(queue.poll())));
    }
    /**
     * This method checks whether isEmpty() in Queue works correctly
     */
    private void checkMethodIsEmpty(){
        System.out.println("Polling  " + VALUE + " elements from the end of the Queue...");
        System.out.println("Polling those elements while queue is not empty by isEmpty() method...");
        while (!queue.isEmpty()){
            queue.poll();
        }
    }
    /**
     * This method checks whether size() in Queue works correctly
     */
    private void checkMethodSize(){
        System.out.println("Checking if size of Queue equals 0 after removal " + isPassed(queue.size() == EMPTY_ARR_SIZE));
    }
    /**
     * This method checks whether poll() throws appropriate Exception
     */
    private void checkPollException(){
        System.out.println("Checking if poll() on empty queue gives NoSuchElementException()");
        try {
            queue.poll();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
}
