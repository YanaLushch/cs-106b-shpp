package com.shpp.p2p.cs.ylushch.assignment11;

/*
 * File: Assignment11.java
 * ---------------------
 * This program simulates the calculator with basic operations as adding, subtracting, multiplying, dividing,and
 * functions sin, cos, tan, atan, log10, log2, sqrt;
 */

import com.shpp.cs.a.graphics.WindowProgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.Collectors;

//Pls write args as such: "1+a*2" "a=2"


public class Calculator extends WindowProgram implements Constants {
    public static String topOperator;

    /**
     * This is the main function which executes the program
     *
     * @param args - params from the configurations
     */
    public static void main(String[] args) {
        String formula = args[0].replaceAll(" ", "");
        Constants.pushOperatorsImportance();
        ArrayList<String> preparedFormula = Formula.getPreparedFormula(args, formula);
        System.out.println("Your formula: " + preparedFormula.stream().map(Object::toString)
                .collect(Collectors.joining("")));
        ArrayList<String> polishNotation = parsePolishNotation(preparedFormula);
        try {
            String result = calcPolishNotation(polishNotation).toString();
            System.out.println("Your calculated value: " + result);
        } catch (NumberFormatException e) {
            System.out.println("You did not specify the value of variable");
        }
    }


    /**
     * this function parses the formula in the ReversePolishNotation
     * this is the Algorithm which changes the position of the digits and operators in understandable for computer way
     *
     * @param formula
     * @return the arrayList which is written in the reversedPolishNotation
     */
    public static ArrayList<String> parsePolishNotation(ArrayList<String> formula) {
        for (int i = 0; i < formula.size(); i++) {
            String letter = formula.get(i);
            if (letter.equals("(") || letter.equals(")")) {
                parseIfBrackets(letter);
            } else if (operatorsImportance.containsKey(letter)) {
                parseIfOperator(letter);
            } else {
                output.add(letter);
            }
        }
        if (operatorStack.size() > 0) {                                                     //add left in stack oper
            while (operatorStack.size() > 0) {                                              //to the end of output
                output.add(operatorStack.pop());
            }
        }
        return output;
    }

    /**
     * The function which invokes the logic if there are brackets
     *
     * @param letter one letter
     */
    public static void parseIfBrackets(String letter) {
        if (letter.equals("(")) {
            operatorStack.add(letter);
        }
        if (letter.equals(")")) {
            topOperator = operatorStack.peek();
            while (!topOperator.equals("(")) {
                output.add(operatorStack.pop());
                topOperator = operatorStack.peek();
            }
            operatorStack.pop();
            try {
                topOperator = operatorStack.peek();
            } catch (EmptyStackException ignored) {
            }
        }
    }

    /**
     * The function which executes the logic if the letter is an operator
     *
     * @param letter one letter
     */
    public static void parseIfOperator(String letter) {
        try {
            topOperator = operatorStack.peek();
        } catch (EmptyStackException e) {                                           //if no operators in stack
            operatorStack.push(letter);//push operator to stack
            return;
        }
        Integer topOperatorImportance = operatorsImportance.get(topOperator);
        Integer currentOperatorImportance = operatorsImportance.get(letter);
        try {
            while (currentOperatorImportance < topOperatorImportance) {
                output.add(operatorStack.pop());

                try {
                    topOperator = operatorStack.peek();
                    topOperatorImportance = operatorsImportance.get(topOperator);
                } catch (EmptyStackException e) {
                    operatorStack.push(letter);
                    return;
                }
            }
        } catch (NullPointerException e) {
            operatorStack.add(letter);
            return;
        }
        if (currentOperatorImportance.equals(topOperatorImportance)) {              //same importance
            output.add(operatorStack.pop());
            operatorStack.add(letter);
        }
        if (currentOperatorImportance > topOperatorImportance) {                    //curr > top
            operatorStack.add(letter);
        }
    }

    /**
     * this function calculates the result from the polishNotation
     *
     * @param polishNotation
     * @return stack of the calculated value
     */
    public static Stack calcPolishNotation(ArrayList<String> polishNotation) {
        Stack numbers = new Stack();                                                          //create stack for nums
        for (int i = 0; i < polishNotation.size(); i++) {
            String operator = polishNotation.get(i);
            if (operatorsImportance.containsKey(operator)) {
                Double numToRight = Double.parseDouble(numbers.pop().toString());
                Double numToLeft = null;
                if (operatorsImportance.get(operator) < Collections.max(operatorsImportance.values())) {
                    numToLeft = Double.parseDouble(numbers.pop().toString());//assign num only if operator importance>4
                }
                Double result = null;
                switch (operator) {
                    case "sin":
                        result = Math.sin(numToRight);
                        break;
                    case "cos":
                        result = Math.cos(numToRight);
                        break;
                    case "sqrt":
                        result = Math.sqrt(numToRight);
                        break;
                    case "tan":
                        result = Math.tan(numToRight);
                        break;
                    case "atan":
                        result = Math.atan(numToRight);
                        break;
                    case "log2":
                        result = Math.log(numToRight) / Math.log(2);
                        break;
                    case "log10":
                        result = Math.log10(numToRight);
                        break;//make operation with nums
                    case "+":
                        result = numToLeft + numToRight;
                        break;
                    case "-":
                        result = numToLeft - numToRight;
                        break;
                    case "*":
                        result = numToLeft * numToRight;
                        break;
                    case "/":
                        result = numToLeft / numToRight;
                        if (numToRight == 0) {
                            System.out.println("No division by 0");
                            System.exit(0);
                        }
                        break;
                    case "^":
                        result = Math.pow(numToLeft, numToRight);
                        break;
                }
                numbers.add(result);                                                           //push result to stack
            } else {
                numbers.add(operator);                                                          //push nums to stack
            }
        }
        return numbers;                                                                         //last num left is result
    }
}
