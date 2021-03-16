package com.shpp.p2p.cs.ylushch.assignment15;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * this method unarchives the file based on Huffman Algorithm
 */
public class Unarchiver implements Constants {
    Unarchiver(File fileIn, File fileOut) throws IOException {
        int totalFrequency = getFreqSize(fileIn);
        int treeSize = getMapSize(fileIn);
        Node tree = HuffmanTree.buildTree(restoreFreqMap(fileIn, treeSize));
        Map<String, Integer> dictionary = createHuffCoding(tree);
        FileOutputStream writer = new FileOutputStream(fileOut);
        ByteArrayOutputStream writeData = unarchiveFile(fileIn, treeSize, totalFrequency, dictionary);
        writeData.writeTo(writer);

        System.out.println("The size of the file which was IN: " + fileIn.length());
        System.out.println("The size of the file which was OUT: " + fileOut.length());
        System.out.println("File unarchived is greater than archived by " + ((100 - (fileIn.length() * 100) / fileOut.length())) + "%.");
    }

    /**
     * this method gets the length of the elements in the file
     */
    public static int getFreqSize(File file) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        byte[] fileSize = new byte[BYTES_TOTAL_FREQ];
        Integer oneByte;
        int counter = 0;
        while ((oneByte = fileIn.read()) != -1) {
            if (counter < BYTES_TOTAL_FREQ) {
                fileSize[counter] = oneByte.byteValue();
            }
            counter++;
        }
        return ByteBuffer.wrap(fileSize, 0, BYTES_TOTAL_FREQ).getInt();
    }

    /**
     * This method gets the length of the map size stored in the archive
     */
    public static int getMapSize(File file) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        byte[] fileSize = new byte[BYTES_FRQ_MAP];
        Integer oneByte;
        int counter = 0;
        while ((oneByte = fileIn.read()) != -1) {
            if (counter >= BYTES_TOTAL_FREQ && counter < HEADER_STORAGE) {
                fileSize[counter - BYTES_TOTAL_FREQ] = oneByte.byteValue();
            }
            counter++;
        }
        return ByteBuffer.wrap(fileSize, 0, BYTES_FRQ_MAP).getInt();
    }

    /**
     * This method restores the frequency map from the archived file
     *
     * @param file     initial file
     * @param treeSize the length of the frequency
     */
    public static Map<Integer, Integer> restoreFreqMap(File file, int treeSize) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        Map<Integer, Integer> mapFr = new HashMap<>();
        Integer readByte;
        int counter = 0;
        ArrayList<Integer> oneNode = new ArrayList<>();
        while ((readByte = fileIn.read()) != -1) {
            if (counter >= HEADER_STORAGE && counter < treeSize + HEADER_STORAGE) {
                if (oneNode.size() <= STORE_FOR_1_VAL) {
                    oneNode.add(readByte);
                    if (oneNode.size() == STORE_FOR_1_VAL) {
                        int oneByte = oneNode.get(0) & 0xff;
                        int frequency =
                                ((oneNode.get(1) & 0xff)) |
                                        ((oneNode.get(2) & 0xff) << 8) |
                                        ((oneNode.get(3) & 0xff) << 16);
                        mapFr.put(oneByte, frequency);
                        oneNode.removeAll(oneNode);
                    }
                }
            }
            counter++;
        }
        return mapFr;
    }

    /**
     * This method creates the map from the byte and coded in bits String
     */
    static Map<String, Integer> createHuffCoding(Node node) {
        Map<String, Integer> map = new HashMap<>();
        recursionToSetCode(node, map, "");
        return map;
    }

    /**
     * This method uses depth-first search algorithm to generate the Huffman coded symbols
     */
    private static void recursionToSetCode(Node node, Map<String, Integer> map, String s) {
        if (node.leftSymbol == null && node.rightSymbol == null) {
            map.put(s, node.oneByte);
            return;
        }
        recursionToSetCode(node.leftSymbol, map, s + '0');
        recursionToSetCode(node.rightSymbol, map, s + '1');
    }

    /**
     * This method unarchives the file by reading the actual data in the stream and constantly writing into the file output
     */
    public static ByteArrayOutputStream unarchiveFile(File file, int treeSize, int totalFreq, Map<String, Integer> dictionary) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int oneByte;
        int counter = 0;
        int innerCounter = 0;
        StringBuilder bufferFor1Byte = new StringBuilder();
        StringBuilder bufferForSymbol = new StringBuilder();
        //reading the file
        while ((oneByte = fileIn.read()) != -1) {
            if (counter >= HEADER_STORAGE + treeSize && counter < HEADER_STORAGE + treeSize + totalFreq) {       //condition to read the actual coded data
                bufferFor1Byte.append(String.format("%8s", Integer.toBinaryString(oneByte)).replace(" ", "0"));
                while (bufferFor1Byte.length() != 0) {
                    bufferForSymbol.append(bufferFor1Byte.substring(0, 1));
                    bufferFor1Byte = new StringBuilder(bufferFor1Byte.substring(1));
                    if (dictionary.containsKey(bufferForSymbol.toString()) && innerCounter < totalFreq) {
                        result.write(dictionary.get(bufferForSymbol.toString()));
                        bufferForSymbol = new StringBuilder();
                        innerCounter++;
                    }
                }
            }
            counter++;
        }
        return result;
    }
}
