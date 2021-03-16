package com.shpp.p2p.cs.ylushch.assignment15;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Class which archives the data by Huffman Algorithm
 */
public class Archiver implements Constants {

    Archiver(File fileIn, File fileOut) throws IOException {
        Map<Integer, Integer> byteFrequency = Archiver.getByteFrequency(fileIn);
        Node root = HuffmanTree.buildTree(byteFrequency);
        Map<Integer, String> dictionary = Archiver.createHuffCoding(root);
        FileOutputStream writer = new FileOutputStream(fileOut);
        ByteArrayOutputStream writeData = Archiver.archiveFile(fileIn, dictionary, byteFrequency, root);
        writeData.writeTo(writer);

        System.out.println("The size of the file which was IN: " + fileIn.length());
        System.out.println("The size of the file which was OUT: " + fileOut.length());
        System.out.println("File archived is smaller than unarchived by " + (100 - ((fileOut.length() * 100) / fileIn.length())) + "%.");
    }


    /**
     * This method reads the file and stores the data in map about the byte and its frequency
     *
     * @param file initial file
     */
    static Map<Integer, Integer> getByteFrequency(File file) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        HashMap<Integer, Integer> bytes = new HashMap<>();
        int oneByte;
        while ((oneByte = fileIn.read()) != -1) {
            if (bytes.containsKey(oneByte)) {
                bytes.replace(oneByte, bytes.get(oneByte) + 1);
            } else {
                bytes.put(oneByte, 1);
            }
        }
        return bytes;
    }

    /**
     * This method creates the map from the byte and coded in bits String
     */
    static Map<Integer, String> createHuffCoding(Node node) {
        Map<Integer, String> map = new HashMap<>();
        recursionToSetCode(node, map, "");
        return map;
    }

    /**
     * This method uses depth-first search algorithm to generate the Huffman coded symbols
     */
    private static void recursionToSetCode(Node node, Map<Integer, String> map, String s) {
        if (node.leftSymbol == null && node.rightSymbol == null) {
            map.put(node.oneByte, s);
            return;
        }
        recursionToSetCode(node.leftSymbol, map, s + '0');
        recursionToSetCode(node.rightSymbol, map, s + '1');
    }

    /**
     * This method converts the frequency Map to the byte array to easily write it to file
     *
     */
    public static byte[] convertToByteArray(Map<Integer, Integer> freqMap) {
        byte[] tmp = new byte[freqMap.size() * 4];
        int counter = 0;
        for (Integer item : freqMap.keySet()) {
            int frequency = freqMap.get(item);
            tmp[counter * STORE_FOR_1_VAL] = (byte) ((item) & 0xFF);
            tmp[counter * STORE_FOR_1_VAL + 1] = (byte) ((frequency) & 0xff);
            tmp[counter * STORE_FOR_1_VAL + 2] = (byte) ((frequency >> 8) & 0xff);
            tmp[counter * STORE_FOR_1_VAL + 3] = (byte) ((frequency >> 16) & 0xff);
            counter++;
        }
        return tmp;
    }

    /**
     * this Method archives the file to the file
     *
     */
    public static ByteArrayOutputStream archiveFile(File file, Map<Integer, String> dictionary, Map<Integer, Integer> freqList, Node root) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        StringBuilder buffer = new StringBuilder();
        int oneByte;
        byte[] frequencyByteArray = convertToByteArray(freqList);
        //writing the frequency of the symbols
        result.write(ByteBuffer.allocate(4).putInt(root.frequency).array());
        //writing the size of the frequency array
        result.write(ByteBuffer.allocate(8).putInt(frequencyByteArray.length).array());
        //writing the frequency array
        result.write(frequencyByteArray);
        //reading file and writing to output
        while ((oneByte = fileIn.read()) != -1) {
            if (dictionary.containsKey(oneByte)) {
                buffer.append(dictionary.get(oneByte));
            }
            while (buffer.length() >= BYTE_SIZE) {
                result.write(Integer.parseUnsignedInt(buffer.substring(0, BYTE_SIZE), 2));
                if (buffer.length() == BYTE_SIZE) {
                    buffer = new StringBuilder();
                } else {
                    buffer = new StringBuilder(buffer.substring(BYTE_SIZE));
                }
            }
        }
        if (buffer.length() != 0) {              //adding zeroes if needed
            StringBuilder remainedZeros = new StringBuilder();
            for (int i = 0; i < BYTE_SIZE - buffer.length(); i++) {
                remainedZeros.append("0");
            }
            result.write(Integer.parseUnsignedInt(String.valueOf(buffer.append(remainedZeros)), 2));
        }
        return result;
    }
}
