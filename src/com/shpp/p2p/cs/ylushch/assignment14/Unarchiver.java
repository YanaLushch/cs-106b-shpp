package com.shpp.p2p.cs.ylushch.assignment14;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Unarchiver implements Constants {
    /**
     * Class unarchives the file
     *
     * @param fileIn  needs to be unarchived
     * @param fileOut needs to be archived
     */
    Unarchiver(File fileIn, File fileOut) throws IOException {
        InputStream fileStreamIn = new FileInputStream(fileIn);
        FileOutputStream newFile = new FileOutputStream(fileOut);
        ArrayList<Integer> bytesArray = Constants.readBytesAndPushToArray(fileStreamIn);
        int dictionarySize = getSize(bytesArray, 0);
        HashMap<Integer, String> dictionary = getDictionary(bytesArray, dictionarySize);
        List<String> fileBytes = getFileBytes(bytesArray, dictionarySize);
        int fileSize = getSize(bytesArray, 4);
        ByteArrayOutputStream unarchivedFile = unarchiveFile(fileBytes, dictionary, fileSize);
        unarchivedFile.writeTo(newFile);
        System.out.println("The size of the file which was IN: " + fileIn.length());
        System.out.println("The size of the file which was OUT: " + fileOut.length());
        System.out.println("File unarchived is greater than archived by " + ((100 - (fileIn.length() * 100) / fileOut.length())) + "%.");
    }

    /**
     * Method gets the size of Dictionary or File itself which needs to be4 obtained for further operations
     *
     * @param bytesArray - array of all bytes stored in archived file
     * @return size of needed file
     */
    public static int getSize(ArrayList<Integer> bytesArray, int startIndex) {
        byte[] fileSize = new byte[BYTES_FOR_TABLE_SIZE];
        for (int i = 0; i < fileSize.length; i++) {
            fileSize[i] = bytesArray.get(i + startIndex).byteValue();
        }
        return ByteBuffer.wrap(fileSize, 0, BYTES_FOR_TABLE_SIZE).getInt();
    }

    /**
     * Method gets the dictionary from archived file.
     *
     * @param bytesArray - array of all bytes stored in archived file
     * @return hashMap of dictionary
     */
    public static HashMap<Integer, String> getDictionary(ArrayList<Integer> bytesArray, int dictionarySize) {
        HashMap<Integer, String> dictionary = new HashMap<>();
        List<Integer> dictionaryArray = bytesArray.subList(BYTES_FOR_SIZE, BYTES_FOR_SIZE + dictionarySize);
        for (int i = 0; i < dictionaryArray.size(); i++) {
            dictionary.put(i, String.format(FORMAT_FOR_8_BITS, Integer.toBinaryString(dictionaryArray.get(i))).replace(" ", "0"));
        }
        return dictionary;
    }
    /**
     * Method gets all bits which are the file itself
     *
     * @return list of file bytes converted in bits
     */
    public static List<String> getFileBytes(ArrayList<Integer> bytesArray, int dictionarySize) {
        List<Integer> fileBytes = bytesArray.subList(BYTES_FOR_SIZE + dictionarySize, bytesArray.size());
        ArrayList<String> fileBytesString = new ArrayList<>();
        for (int i = 0; i < fileBytes.size(); i++) {
            fileBytesString.add(String.format(FORMAT_FOR_8_BITS, Integer.toBinaryString(fileBytes.get(i))).replace(" ", "0"));
        }
        return fileBytesString;
    }

    /**
     * Method which unarchives the file by reading  bits and converting it to previous 8 bits size
     *
     * @return ByteArrayOutputStream which will be written into the file
     */
    public static ByteArrayOutputStream unarchiveFile(List<String> fileBytes, HashMap<Integer, String> dictionary, int fileSize) {
        ByteArrayOutputStream fileStreamOut = new ByteArrayOutputStream();
        int bitSize = Constants.chooseBitsSize(dictionary.size());
        StringBuilder buffer = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < fileBytes.size(); i++) {
            if (buffer.length() < bitSize) {
                buffer.append(fileBytes.get(i));
            }
            while (buffer.length() >= bitSize) {
                if (counter < fileSize) {
                    fileStreamOut.write(Integer.parseInt(dictionary.get(Integer.parseInt(buffer.substring(0, bitSize), 2)), 2));
                    counter++;
                }
                if (buffer.length() == bitSize) {
                    buffer = new StringBuilder();
                } else {
                    buffer = new StringBuilder(buffer.substring(bitSize));
                }
            }
        }
        return fileStreamOut;
    }
}
