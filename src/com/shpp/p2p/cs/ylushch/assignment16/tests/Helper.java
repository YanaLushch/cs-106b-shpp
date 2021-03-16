package com.shpp.p2p.cs.ylushch.assignment16.tests;

/**
 * This class contains the constants needed for the testing purposes
 * and one method which is also used in each testing
 */

public class Helper {
    public final String GREEN_TICK = "\033[38;2;0;255;0;02m" +  "\u2713" + "\033[m";   //✓
    public final String RED_CROSS = "\033[0;31m" + "\u2715"+ "\033[m";      //x
    public final int VALUE = 100500;
    public final int VAL_BY_INDEX_1 = 1;
    public final int INDEX_OF_VALUE = VALUE - 1;
    public final int REMOVE_VAL = 130;
    public final int VALUE_AFTER_REMOVAL = VALUE - REMOVE_VAL;
    public final int EMPTY_ARR_SIZE = 0;
    public final int RANDOM_EL_INDEX = (int) Math.floor(Math.random()* VALUE);
    public final int INDEX_OUT_OF_BONDS  = VALUE + 10;

    /**
     * This method determines if the specific boolean test-case passed or failed
     * @param condition condition of the test-case
     * @return Green tick if condition passed, Red cross - if failed
     */
    public String isPassed( Boolean condition){
        if (condition){
            return GREEN_TICK;
        }else {
            return RED_CROSS;
        }
    }
}
