package com.shpp.p2p.cs.ylushch.assignment16.tests;

import com.shpp.p2p.cs.ylushch.assignment16.MyStack;

/**
 * This class tests each method of MyStack and prints the test results to the user
 */
public class TestMyStack extends Helper {
    MyStack<Integer> stack = new MyStack<>();

    public TestMyStack() {
        checkMethodPush();
        checkMethodPeekAndPop();
        checkMethodEmpty();
        checkMethodSize();
        System.out.println("Exception");
        checkPopException();
    }

    /**
     * This method checks whether push() in Stack works correctly
     */
    private void checkMethodPush() {
        System.out.println("Adding the  " + VALUE + " elements to the beginning of the Stack...");
        for (int i = 1; i <= VALUE; i++) {
            stack.push(i);
        }
        System.out.println("Checking if size of LinkedList matches " + VALUE + " " + isPassed(stack.size() == VALUE));
    }

    /**
     * This method checks whether peek() && pop() in Stack works correctly
     */
    private void checkMethodPeekAndPop() {
        System.out.println("Checking if peeked value is equal to popped " + isPassed(stack.peek().equals(stack.pop())));
    }

    /**
     * This method checks whether empty() in Stack works correctly
     */
    private void checkMethodEmpty() {
        System.out.println("Popping  " + VALUE + " elements from the end of the Stack...");
        System.out.println("Popping those elements while stack is not empty by empty() method...");
        while (!stack.empty()) {
            stack.pop();
        }
    }

    /**
     * This method checks whether size() in Stack works correctly
     */
    private void checkMethodSize() {
        System.out.println("Checking if size of Stack equals 0 after removal " + isPassed(stack.size() == EMPTY_ARR_SIZE));
    }

    /**
     * This method checks whether pop() throws appropriate Exception
     */
    private void checkPopException() {
        System.out.println("Checking if pop() on empty stack gives NoSuchElementException()");
        try {
            stack.pop();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
}
