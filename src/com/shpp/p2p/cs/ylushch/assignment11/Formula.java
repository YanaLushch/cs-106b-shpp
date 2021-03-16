package com.shpp.p2p.cs.ylushch.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

//This is the separate class for Formula
public class Formula implements Constants {


    public static ArrayList<String> getPreparedFormula(String[] args, String formula) {
        return replaceVars(args, convertToArray(formula));
    }

    /**
     * This method replaces the variables with their values if there are such
     *
     * @param args arguments from configurations
     * @return if there are the variables - it returns replaces string with values, if no - returns ot changed formula
     */
    private static ArrayList<String> replaceVars(String[] args, ArrayList<String> parsedFormula) {
        HashMap<String, String> variables = new HashMap<>();
        ArrayList<String> changedFormula = new ArrayList<>();
        if (args.length > 0) {
            for (int i = 1; i < args.length; i++) {
                ArrayList<String> parsedVar = convertToArray(parseComma(args[i]));
                variables.put(parsedVar.get(0), parsedVar.get(2));
            }
            for (int i = 0; i < parsedFormula.size(); i++) {
                String letter = parsedFormula.get(i);
                if (variables.containsKey(letter)) {
                    letter = variables.get(letter);
                }
                changedFormula.add(letter);
            }
            return changedFormula;
        }
        return parsedFormula;
    }

    /**
     * The method parses "-number" into "0-number"
     *
     * @param formula formula
     * @return parsed string
     */
    private static String parseNegativeNums(String formula) {
        ArrayList<String> convertList = new ArrayList<>();
        if (formula.split("")[0].equals("-")) {
            formula = "0" + formula;
        }
        for (int i = 0; i < formula.length(); i++) {
            String letter = Character.toString(formula.charAt(i));
            try {
                String nextLetter = Character.toString(formula.charAt(i + 1));
                if (letter.equals("(") && nextLetter.equals("-")) {
                    convertList.add(letter + "0");
                } else {
                    convertList.add(letter);
                }
            } catch (StringIndexOutOfBoundsException e) {
                convertList.add(letter);
                break;
            }

        }
        return convertList.stream().map(Object::toString)
                .collect(Collectors.joining(""));
    }

    /**
     * Function parses "comma"(because our formula shouldn't have commas) if there are some to the "dot"
     * @param formula
     * @return new parsed String
     */
    private static String parseComma(String formula) {
        formula = parseNegativeNums(formula);
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            String letter = Character.toString(formula.charAt(i));
            if (letter.equals(",")) {
                newString.append(".");
            } else {
                newString.append(letter);
            }
        }
        return newString.toString();
    }

    /**
     * this function divides the formula into the ArrayList; it also determines if the number consists
     * of 2 and more digits - the digits join together.
     *
     * @param formula
     * @return arrayList which is ready for the further calculations
     */
    private static ArrayList<String> convertToArray(String formula) {
        ArrayList<String> charList = new ArrayList<>();
        String replacedFormula = parseComma(formula);
        String wholeNumber = "";


        for (int i = 0; i < replacedFormula.length(); i++) {
            String letter = Character.toString(replacedFormula.charAt(i));
            if (!operatorsImportance.containsKey(letter) && !letter.equals(")") && !letter.equals("(")) {
                //joins the digits until operator
                wholeNumber = wholeNumber.isEmpty() ? letter : wholeNumber + letter;
            } else {
                if (!wholeNumber.isEmpty()) {
                    charList.add(wholeNumber);
                }
                charList.add(letter);
                wholeNumber = "";
            }
            if (i == replacedFormula.length() - 1 && !wholeNumber.isEmpty()) {                                         //adds the last number to the list
                charList.add(wholeNumber);
            }
        }
        return charList;
    }
}