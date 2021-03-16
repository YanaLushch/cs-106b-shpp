package com.shpp.p2p.cs.ylushch.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public interface Constants {
    //HashMap for keeping the operators and their importance
    static HashMap<String, Integer> operatorsImportance = new HashMap<>();
    static final Stack<String> operatorStack = new Stack<>();            //stack for  storing the operators
    static final ArrayList<String> output = new ArrayList<>();//for keeping the whole output from reversedPN

    /**
     * void for putting all the operators in the hashMap
     */
    public static void pushOperatorsImportance() {
        operatorsImportance.put("log10", 4);
        operatorsImportance.put("log2", 4);
        operatorsImportance.put("sqrt", 4);
        operatorsImportance.put("tan", 4);
        operatorsImportance.put("atan", 4);
        operatorsImportance.put("cos", 4);
        operatorsImportance.put("sin", 4);
        operatorsImportance.put("^", 3);
        operatorsImportance.put("*", 2);
        operatorsImportance.put("/", 2);
        operatorsImportance.put("+", 1);
        operatorsImportance.put("-", 1);
        operatorsImportance.put("=", 0);
    }

}
