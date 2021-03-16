package com.shpp.p2p.cs.ylushch.assignment16.tests;

import com.shpp.p2p.cs.ylushch.assignment16.MyLinkedList;

/**
 * This class tests each method of MyLinkedList and prints the test results to the user
 */

public class TestMyLinkedList extends Helper {
    MyLinkedList<Integer> linkedList = new MyLinkedList<>();

    public TestMyLinkedList() {
        checkMethodAddFirst();
        checkMethodSize();
        checkMethodRemoveLast();
        checkMethodAdd();
        checkMethodRemove();
        checkMethodAddLast();
        checkMethodSize();
        checkMethodSet();
        checkMethodGetFirst();
        checkMethodGetLast();
        checkMethodRemoveFirst();
        System.out.println("Exceptions:");
        checkExceptionOnGetFirst();
        checkExceptionOnGetLast();
        checkExceptionOnRemoveFirst();
        checkExceptionOnRemoveLast();
        checkExceptionOnGet();
        checkExceptionOnAdd();
        checkExceptionOnSet();
        checkExceptionOnRemove();
    }

    /**
     * This method checks whether addFirst() in LinkedList works correctly
     */
    private void checkMethodAddFirst() {
        System.out.println("Adding the  " + VALUE + " elements to the beginning of the LinkedList...");
        for (int i = 1; i <= VALUE; i++) {
            linkedList.addFirst(i);
        }

    }

    /**
     * This method checks whether size() in LinkedList works correctly
     */
    private void checkMethodSize() {
        System.out.println("Checking if size of LinkedList matches " + VALUE + " " +
                isPassed(linkedList.size() == VALUE));
    }

    /**
     * This method checks whether removeLast() in LinkedList works correctly
     */
    private void checkMethodRemoveLast() {
        System.out.println("Removing  " + VALUE + " elements from the end of the LinkedList...");
        while (!linkedList.isEmpty()){
            linkedList.removeLast();
        }
        System.out.println("Checking if size of Linked List equals 0 after removal " +
                isPassed(linkedList.size() == EMPTY_ARR_SIZE));
    }

    /**
     * This method checks whether add(index, value) in LinkedList works correctly
     */
    private void checkMethodAdd(){
        System.out.println("Adding 2 elements to the ArrayList by index 0 and 1...");
        linkedList.add(0, VALUE);
        linkedList.add(1, VALUE - 1);
        System.out.println("Checking if those elements has been added to the LinkedList by 0 and 1 indexes " +
                isPassed(linkedList.get(0) == VALUE && linkedList.get(1) == VALUE - 1));
    }

    /**
     * This method checks whether remove(index) in LinkedList works correctly
     */
    private void checkMethodRemove(){
        System.out.println("Removing 2 elements to the ArrayList by index 0 and 1...");
        System.out.println("Checking if the right elements by right indexes has been removed from the LinkedList " +
                isPassed(linkedList.remove(0) == VALUE && linkedList.remove(0) == VALUE - 1));
    }

    /**
     * This method checks whether addLast() in LinkedList works correctly
     */
    private void checkMethodAddLast() {
        System.out.println("Adding the  " + VALUE + " elements to the end of the LinkedList...");
        for (int i = 1; i <= VALUE; i++) {
            linkedList.addLast(i);
        }
    }

    /**
     * This method checks whether set() in LinkedList works correctly
     */
    private void checkMethodSet() {
        System.out.println("Getting the element by the random index from the list and getting el's neighbors...");
        int randPrevNeighbor = linkedList.get(RANDOM_EL_INDEX - 1);
        int randNextNeighbor = linkedList.get(RANDOM_EL_INDEX + 1);
        System.out.println("Setting random value to that random element...");
        linkedList.set(RANDOM_EL_INDEX, RANDOM_EL_INDEX);
        System.out.println("Checking if random element has been set and if the neighbors of that element didn't change " +
                isPassed(linkedList.get(RANDOM_EL_INDEX) == RANDOM_EL_INDEX &&
                        randPrevNeighbor == linkedList.get(RANDOM_EL_INDEX - 1) &&
                        randNextNeighbor == linkedList.get(RANDOM_EL_INDEX + 1)));
    }

    /**
     * This method checks whether getFirst() in LinkedList works correctly
     */
    private void checkMethodGetFirst() {
        System.out.println("Checking if method getFirst pulling out the right value " +
                isPassed(linkedList.getFirst() == VAL_BY_INDEX_1));
    }

    /**
     * This method checks whether getLast() in LinkedList works correctly
     */
    private void checkMethodGetLast() {
        System.out.println("Checking if method getLast pulling out the right value " +
                isPassed(linkedList.getLast() == VALUE));
    }

    /**
     * This method checks whether removeFirst() in LinkedList works correctly
     *
     */
    private void checkMethodRemoveFirst() {
        for (int i = 0; i < VALUE; i++) {
            linkedList.removeFirst();
        }
        System.out.println("Checking if size of Linked List equals 0 after removal " +
                isPassed(linkedList.size() == EMPTY_ARR_SIZE));
    }

    /**
     * This method checks whether getFirst() throws appropriate Exception
     * It will be thrown if the LinkedList is empty, eventually there is no First element in the LinkedList
     */
    private void checkExceptionOnGetFirst() {
        System.out.println("Checking if getFirst() on empty LinkedList gives NoSuchElementException()...");
        try {
            linkedList.getFirst();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

    /**
     * This method checks whether getFirst() throws appropriate Exception
     * It will be thrown if the LinkedList is empty, eventually there is no First element in the LinkedList
     */
    private void checkExceptionOnGetLast() {
        System.out.println("Checking if getLast() on empty LinkedList gives NoSuchElementException()...");
        try {
            linkedList.getLast();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

    /**
     * This method checks whether removeLast() throws appropriate Exception
     * It will be thrown if the LinkedList is empty, eventually there is no First element in the LinkedList
     */
    private void checkExceptionOnRemoveLast() {
        System.out.println("Checking if removeFirst() on empty LinkedList gives NoSuchElementException()...");
        try {
            linkedList.removeLast();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

    /**
     * This method checks whether removeLast() throws appropriate Exception
     * It will be thrown if the LinkedList is empty, eventually there is no First element in the LinkedList
     */
    private void checkExceptionOnRemoveFirst() {
        System.out.println("Checking if removeLast() on empty LinkedList gives NoSuchElementException()...");
        try {
            linkedList.removeFirst();
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

    /**
     * This method checks whether get(index) throws appropriate IndexOutOfBoundsException()
     * if index of value to be gotten is beyond the LinkedList length
     */
    private void checkExceptionOnGet() {
        System.out.println("Checking if get() on empty LinkedList gives IndexOutOfBoundsException()...");
        try {
            linkedList.get(VAL_BY_INDEX_1);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
    /**
     * This method checks whether add(index, value) throws appropriate IndexOutOfBoundsException()
     * if index of value to be gotten is beyond the LinkedList length
     */
    private void checkExceptionOnAdd() {
        System.out.println("Checking if add(index, value) on empty LinkedList gives IndexOutOfBoundsException()...");
        try {
            linkedList.add(INDEX_OUT_OF_BONDS, VALUE);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

    /**
     * This method checks whether set(index, value) throws appropriate IndexOutOfBoundsException()
     * if index of value to be gotten is beyond the LinkedList length
     */
    private void checkExceptionOnSet() {
        System.out.println("Checking if set(index, value) on empty LinkedList gives IndexOutOfBoundsException()...");
        try {
            linkedList.set(INDEX_OUT_OF_BONDS, VALUE);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
    /**
     * This method checks whether set(index, value) throws appropriate IndexOutOfBoundsException()
     * if index of value to be gotten is beyond the LinkedList length
     */
    private void checkExceptionOnRemove() {
        System.out.println("Checking if remove(index) on empty LinkedList gives IndexOutOfBoundsException()...");
        try {
            linkedList.set(INDEX_OUT_OF_BONDS, VALUE);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
}

