package com.shpp.p2p.cs.ylushch.assignment14;

/*
 * File: Assignment14.java
 * ---------------------
 * This program archives and unarchives files
 */

import java.io.File;
import java.io.IOException;

public class Assignment14Part1 implements Constants {
    /**
     * main method which runs the program
     * @param args are passed through the "Edit configurations"
     *             arg[0]- file to be archived, arg[1] - unarchived output (! all set by default in Constants)
     */
    public static void main (String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String[] preparedArgs = readAndPrepareArgs(args);
        File fileIn = new File(preparedArgs[0]);
        File fileOut = new File(preparedArgs[1]);
        boolean toBeArchived = preparedArgs[2].equals("-a");            //automatically determines archive or un-
        if (toBeArchived){
            new Archiver(fileIn, fileOut);
        } else {
            new Unarchiver(fileIn, fileOut);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time of archiving/unarchiving: " + (endTime - startTime)/1000f );
    }

    /**
     * Method which prepares the files to their positions and determines which action to be taken
     * @param args from "Edit configuration" - by default in Constants
     * @return
     */
    public static String[]  readAndPrepareArgs(String[] args) {
        String[] formattedArgs = new String[3];
        try {
            String file = args.length > 0 ? args[0] : DEFAULT_FILE;
            int position = file.lastIndexOf(EXTENSION);
            formattedArgs[1] = (position == -1) ? file+ EXTENSION : file.substring(0, position);
            formattedArgs[2] = (position == -1) ? "-a" : "-u";
            formattedArgs[0] = file;
        } catch (IllegalArgumentException e) {
            System.out.println("Something is wrong with the path. Please check it and try one more time");
        }
        return formattedArgs;
    }



}
