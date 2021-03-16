package com.shpp.p2p.cs.ylushch.assignment10;

/*
 * File: Assignment10.java
 * ---------------------
 * This program simulates the calculator with basic operations as adding, subtracting, multiplying, dividing;
 */

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

//Pls write args as such: "1 + a * 2" "a = 2"

public class Assignment10 {
    private static final HashMap<String, Integer> operatorsImportance = new HashMap<>();
    /**
     * This is the main function which executes the program
     *
     * @param args - params from the configurations
     */
    public static void main(String[] args) {
        String formula = args[0].replaceAll(" ", "");

        operatorsImportance.put("^", 3);
        operatorsImportance.put("*", 2);
        operatorsImportance.put("/", 2);
        operatorsImportance.put("+", 1);
        operatorsImportance.put("-", 1);
        operatorsImportance.put("=", 0);


        formula = replaceVars(args, formula);
        System.out.println("Your formula: " + formula);
        ArrayList<String> preparedFormula = prepareFormula(formula);
        ArrayList<String> polishNotation = parsePolishNotation(preparedFormula);
        try {
            String result = calcPolishNotation(polishNotation).toString();
            System.out.println("Your calculated value: " + result);
        } catch (NumberFormatException e) {
            System.out.println("You did not specify the value of variable");
        }
    }

    /**
     * This method replaces the variables with their values
     *
     * @param args    arguments from configurations
     * @param formula formula
     * @return if there are the variables - it returns replaces string with values, if no - returns ot changed formula
     */
    public static String replaceVars(String[] args, String formula) {
        HashMap<String, String> variables = new HashMap<>();
        ArrayList<String> changedFormula = new ArrayList<>();
        if (args.length > 0) {
            for (int i = 1; i < args.length; i++) {
                ArrayList<String> parsedVar = prepareFormula(args[i]);
                variables.put(parsedVar.get(0), parsedVar.get(2));
            }
            for (int i = 0; i < formula.length(); i++) {
                String letter = Character.toString(formula.charAt(i));
                if (variables.containsKey(letter)) {
                    letter = variables.get(letter);
                }
                changedFormula.add(letter);
            }
            return String.join("", changedFormula);
        }
        return formula;
    }

    /**
     * this function divides the formula into the ArrayList; it also determines if the number consists
     * of 2 and more digits - the digits join together.
     *
     * @param formula
     * @return arrayList which is ready for the further calculations
     */
    public static ArrayList<String> prepareFormula(String formula) {
        ArrayList<String> charList = new ArrayList<>();
        String wholeNumber = new String();
        if (formula.split("")[0].equals("-")) {
            formula = "0" + formula;                                                //if formula starts with "-" add "0"
        }
        for (int i = 0; i < formula.length(); i++) {
            String letter = Character.toString(formula.charAt(i));
            if (!operatorsImportance.containsKey(letter)) {                         //joins the digits until operator
                wholeNumber = wholeNumber.isEmpty() ? letter : wholeNumber + letter;
            } else {
                charList.add(wholeNumber);
                charList.add(letter);
                wholeNumber = "";
            }
            if (i == formula.length() - 1) {                                         //adds the last number to the list
                charList.add(wholeNumber);
            }
        }
        return charList;
    }

    /**
     * this function parses the formula in the ReversePolishNotation
     * this is the Algorithm which changes the position of the digits and operators in understandable for computer way
     *
     * @param formula
     * @return the arrayList which is written in the reversedPolishNotation
     */
    public static ArrayList<String> parsePolishNotation(ArrayList<String> formula) {
        //stack for storing the operators from the formula(last-in - first-out)
        Stack operatorsStack = new Stack();                                     //for storing operators
        ArrayList<String> output = new ArrayList<>();                           //for storing the whole queue of output
        for (int i = 0; i < formula.size(); i++) {
            String letter = formula.get(i);
            if (operatorsImportance.containsKey(letter)) {                      //if letter is an operator
                String topOperator;
                try {
                    topOperator = operatorsStack.peek().toString();             //take this operator
                } catch (EmptyStackException e) {                               //if no operators in stack
                    operatorsStack.push(letter);                                //push operator to stack
                    continue;
                }
                Integer topOperatorImportance = operatorsImportance.get(topOperator);       //get the importance of Top
                Integer currentOperatorImportance = operatorsImportance.get(letter);        //get the importance of Curr
                while (currentOperatorImportance < topOperatorImportance) {                 //compare curr < top
                    output.add(operatorsStack.pop().toString());                            //add operator to stack
                    topOperator = operatorsStack.peek().toString();                         //reassign
                    topOperatorImportance = operatorsImportance.get(topOperator);
                }
                if (currentOperatorImportance.equals(topOperatorImportance)) {              //same importance
                    output.add(operatorsStack.pop().toString());
                    operatorsStack.push(letter);
                }
                if (currentOperatorImportance > topOperatorImportance) {                    //curr > top
                    operatorsStack.push(letter);
                }
            } else {
                output.add(letter);
            }
        }
        if (operatorsStack.size() > 0) {                                                     //add left in stack oper
            while (operatorsStack.size() > 0) {                                              //to the end of output
                output.add(operatorsStack.pop().toString());
            }
        }
        return output;
    }

    /**
     * this method calculates the polishNotation
     *
     * @param polishNotation
     * @return stack of the calculated value
     */
    public static Stack calcPolishNotation(ArrayList<String> polishNotation) {
        Stack numbers = new Stack();                                                          //create stack for nums
        for (int i = 0; i < polishNotation.size(); i++) {
            String integer = polishNotation.get(i);
            if (operatorsImportance.containsKey(integer)) {                                   //if operator
                Double num1 = Double.parseDouble(numbers.pop().toString());                   //pop 2 nums from stack
                Double num2 = Double.parseDouble(numbers.pop().toString());
                Double result = null;
                switch (integer) {                                                            //make operation with nums
                    case "+":
                        result = num2 + num1;
                        break;
                    case "-":
                        result = num2 - num1;
                        break;
                    case "*":
                        result = num2 * num1;
                        break;
                    case "/":
                        result = num2 / num1;
                        break;
                    case "^":
                        result = Math.pow(num2, num1);
                        break;
                }
                numbers.push(result);                                                           //push result to stack
            } else {
                numbers.push(integer);                                                          //push nums to stack
            }
        }
        return numbers;                                                                         //last num left is result
    }
}
