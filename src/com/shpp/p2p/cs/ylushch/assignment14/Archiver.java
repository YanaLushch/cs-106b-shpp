package com.shpp.p2p.cs.ylushch.assignment14;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Archiver implements Constants {
    /**
     * Class archives the file
     * @param fileIn file needs to be archived
     * @param fileOut file in which should be archived(already with .par)
     */
    Archiver(File fileIn, File fileOut) throws IOException {

        InputStream fileStreamIn = new FileInputStream(fileIn);
        ArrayList<Integer> bytes = Constants.readBytesAndPushToArray(fileStreamIn);
        HashSet<Integer> uniqueBytesArray = getUniqueBytes(bytes);
        ArrayList<String> bitsArray = Constants.getBitsArray(Constants.chooseBitsSize(uniqueBytesArray.size()), bytes.size());
        HashMap<Integer, String> dictionary = createDictionary(uniqueBytesArray, bitsArray);
        ByteArrayOutputStream archivedFile = archiveFile(bytes, dictionary);
        FileOutputStream newFile = new FileOutputStream(fileOut);
        archivedFile.writeTo(newFile);
        System.out.println( "The size of the file which was IN: " + fileIn.length());
        System.out.println("The size of the file which was OUT: " + fileOut.length());
        System.out.println("File archived is smaller than unarchived by " + (100 - ((fileOut.length() * 100) / fileIn.length())) +"%." );
    }

    /**
     * Method stores values in Set
     * @return set of unique bytes
     */
    public static HashSet<Integer> getUniqueBytes(ArrayList<Integer> bytes){
        HashSet<Integer> uniqBites = new HashSet<>();
        uniqBites.addAll(bytes);
        return uniqBites;
    }

    /**
     * Method creates the dictionary of keys - unarchived  and their values - archived bytes
     * @param byteKey - unarchived byte
     * @param bitsValue - archived bit
     * @return hash map of stored keys and values
     */
    public static HashMap<Integer, String> createDictionary(HashSet<Integer> byteKey, ArrayList<String> bitsValue){
        HashMap<Integer, String> dictionary = new HashMap<>();
        ArrayList <Integer> bytesKey  = new ArrayList<>(byteKey);
        for (int i = 0; i < bytesKey.size(); i++) {
            dictionary.put(bytesKey.get(i), bitsValue.get(i));
        }
        return dictionary;
    }

    /**
     * Method converts Map to Array (cause only byte array need to written into the file)
     * @return byte array of HashMap
     */
    public static byte[] convertMapToArray(HashMap<Integer, String> dictionary){
        byte[] out = new byte[dictionary.size()];
        for (Integer b: dictionary.keySet()){
            out[Integer.parseUnsignedInt(dictionary.get(b), 2)] = (byte) (b & 0xff);
        }
        return out;
    }

    /**
     * Method archives the file by writing into the file 4 parts of items:
     * dictionary size -in first 4 bytes, in next 8 bytes -  file size, dictionary itself, file itself;
     * @param bytes - array of bytes
     * @param dictionary - hashMap dictionary
     * @return ByteArrayOutputStream which will be written into the file
     */
    public static ByteArrayOutputStream archiveFile(ArrayList<Integer> bytes, HashMap<Integer, String> dictionary) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        StringBuilder buffer = new StringBuilder();
        //writing all info and dictionary into the file
        result.write(ByteBuffer.allocate(BYTES_FOR_TABLE_SIZE).putInt(dictionary.size()).array());
        result.write(ByteBuffer.allocate(BYTES_FOR_FILE_SIZE).putInt(bytes.size()).array());
        result.write(convertMapToArray(dictionary));

        //writing each byte into the file
        for (int i = 0; i < bytes.size(); i++) {
            if (dictionary.containsKey(bytes.get(i))){
                buffer.append(dictionary.get(bytes.get(i)));
            }
            while (buffer.length() >= BYTE_SIZE){
                result.write(Integer.parseUnsignedInt(buffer.substring(0, BYTE_SIZE), 2));
                if (buffer.length() == BYTE_SIZE){
                    buffer = new StringBuilder();
                }else {
                    buffer = new StringBuilder(buffer.substring(BYTE_SIZE));
                }
            }
        }
        if (buffer.length() != 0){              //adding zeroes if needed
            StringBuilder remainedZeros = new StringBuilder();
            for (int i = 0; i < BYTE_SIZE - buffer.length(); i++) {
                remainedZeros.append("0");
            }
            result.write(Integer.parseUnsignedInt(String.valueOf(buffer.append(remainedZeros))));
        }
        return result;
    }
}
