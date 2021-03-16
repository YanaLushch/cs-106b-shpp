package com.shpp.p2p.cs.ylushch.assignment16.tests;

import com.shpp.p2p.cs.ylushch.assignment16.MyArrayList;

/**
 * This class tests each method of MyArrayList and prints the test results to the user
 */
public class TestMyArrayList extends Helper {
    MyArrayList<Integer> arrayList = new MyArrayList<>();

    public TestMyArrayList() {
        checkMethodAdd();
        checkMethodGet();
        checkMethodContains();
        checkMethodIndexOf();
        checkMethodRemove();
        checkMethodSize();
        checkMethodAddByIdx();
        checkMethodSetByIdx();
        checkMethodClear();
        System.out.println("Exceptions");
        checkExceptionOnGet();
        checkExceptionOnAdd();
        checkExceptionOnSet();
        checkExceptionOnRemove();
    }

    /**
     * This method checks whether add() in ArrayList works correctly
     */
    private void checkMethodAdd() {
        System.out.println("Adding " + VALUE + " elements to the ArrayList...");
        for (int i = 1; i <= VALUE; i++) {
            arrayList.add(i);
        }
        System.out.println("Checking if size of ArrayList matches " + VALUE + " " + isPassed(arrayList.size() == VALUE));
    }

    /**
     * This method checks whether get() in ArrayList works correctly
     */
    private void checkMethodGet() {
        System.out.println("Checking if the last element in the list is equal to " + VALUE + " " + isPassed(arrayList.get(arrayList.size() - 1) == VALUE));
    }

    /**
     * This method checks whether contains() in ArrayList works correctly
     */
    private void checkMethodContains() {
        System.out.println("Checking if the array contains the value " + VALUE + " " + isPassed(arrayList.contains(VALUE)));
    }

    /**
     * This method checks whether indexOf() in ArrayList works correctly
     */
    private void checkMethodIndexOf() {
        System.out.println("Checking if there is a value at specified index " + isPassed(arrayList.indexOf(VALUE) == INDEX_OF_VALUE));
    }

    /**
     * This method checks whether remove() in ArrayList works correctly
     */
    private void checkMethodRemove() {
        System.out.println("Removing " + REMOVE_VAL + " elements..");
        for (int i = 1; i <= REMOVE_VAL; i++) {
            arrayList.remove(i);
        }
    }

    /**
     * This method checks whether size() in ArrayList works correctly
     */
    private void checkMethodSize() {
        System.out.println("Checking the size of the ArrayList after removal " + isPassed(arrayList.size() == VALUE_AFTER_REMOVAL));
    }

    /**
     * This method checks whether clear() in ArrayList works correctly
     */
    private void checkMethodClear() {
        arrayList.clear();
        System.out.println("Checking if method clear() clears out all arrayList " + isPassed(arrayList.size() == EMPTY_ARR_SIZE));
    }

    /**
     * This method checks whether add(index, value) in ArrayList works correctly
     */
    private void checkMethodAddByIdx(){
        System.out.println("Adding 2 elements to the ArrayList by index 0 and 1...");
        arrayList.add(0, VALUE);
        arrayList.add(1, VALUE - 1);
        System.out.println("Checking if those elements has been added to the LinkedList by 0 and 1 indexes " +
                isPassed(arrayList.get(0) == VALUE && arrayList.get(1) == VALUE - 1));
    }

    /**
     * This method checks whether set(index, value) in ArrayList works correctly
     */
    private void checkMethodSetByIdx(){
        System.out.println("Setting 2 elements to the ArrayList by index 0 and 1...");
        arrayList.add(0, VALUE);
        arrayList.add(1, VALUE - 1);
        System.out.println("Checking if those elements has been added to the LinkedList by 0 and 1 indexes " +
                isPassed(arrayList.get(0) == VALUE && arrayList.get(1) == VALUE - 1));
    }

    /**
     * This method checks whether get(index) throws appropriate IndexOutOfBoundsException()
     * if index of value to be gotten is beyond the ArrayList length
     */
    private void checkExceptionOnGet() {
        System.out.println("Checking if get(index) on empty ArrayList gives IndexOutOfBondsException()...");
        try {
            arrayList.get(VAL_BY_INDEX_1);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
    /**
     * This method checks whether add(index, value) throws appropriate IndexOutOfBoundsException()
     * if index of value to be added is beyond the ArrayList length
     */
    private void checkExceptionOnAdd() {
        System.out.println("Checking if add(index, value) on empty ArrayList gives IndexOutOfBondsException()...");
        try {
            arrayList.add(INDEX_OUT_OF_BONDS, VALUE);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
    /**
     * This method checks whether set(index, value) throws appropriate IndexOutOfBoundsException()
     * if index of value to be set is beyond the ArrayList length
     */
    private void checkExceptionOnSet() {
        System.out.println("Checking if set(index, value) on empty ArrayList gives IndexOutOfBondsException()...");
        try {
            arrayList.set(INDEX_OUT_OF_BONDS, VALUE);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }
    /**
     * This method checks whether remove(index) throws appropriate IndexOutOfBoundsException()
     * if index of value to be removed is beyond the ArrayList length
     */
    private void checkExceptionOnRemove() {
        System.out.println("Checking if remove(index) on empty ArrayList gives IndexOutOfBondsException()...");
        try {
            arrayList.remove(INDEX_OUT_OF_BONDS);
            System.out.println("No Exception - test case failed: " + RED_CROSS);
        } catch (Exception e) {
            System.out.println("Error: " + e + " " + GREEN_TICK);
        }
    }

}
