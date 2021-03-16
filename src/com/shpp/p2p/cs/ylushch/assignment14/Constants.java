package com.shpp.p2p.cs.ylushch.assignment14;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public interface Constants {

    String DEFAULT_FILE = "assets/test.txt";
    String EXTENSION = ".par";
    int BYTE_SIZE = 8;
    int BYTES_FOR_TABLE_SIZE = 4;
    int BYTES_FOR_FILE_SIZE = 8;
    int BYTES_FOR_SIZE = BYTES_FOR_FILE_SIZE + BYTES_FOR_TABLE_SIZE;
    String FORMAT_FOR_8_BITS = "%8s";

    /**
     *  Method reads bytes and pushes it to Array
     * @return array of bytes
     */
    static ArrayList<Integer> readBytesAndPushToArray(InputStream file) throws IOException {
        ArrayList <Integer> bytes = new ArrayList<>();
        int oneByte;
        while ((oneByte = file.read()) != -1){
            bytes.add(oneByte);
        }
        return bytes;
    }

    /**
     * Method to determine what size of the bits-string should be taken
     * @param biteSize
     * @return
     */
    static Integer chooseBitsSize(int biteSize){
        if (biteSize >= 0 && biteSize <= 2){
            return 1;
        } else if(biteSize >= 3 && biteSize <= 4){
            return 2;
        } else if(biteSize >= 5 && biteSize <= 8){
            return 3;
        } else if(biteSize >= 9 && biteSize <= 16){
            return 4;
        }else if(biteSize >= 17 && biteSize <= 32){
            return 5;
        } else if(biteSize >= 33 && biteSize <= 64){
            return 6;
        }else if(biteSize >= 65 && biteSize <= 128){
            return 7;
        }else if(biteSize >= 129 && biteSize <= 256){
            return 8;
        }
        return null;
    }

    /**
     * Method creates bits with needed length - n
     * @return arrayList of those bits
     */
    static ArrayList<String> getBitsArray( int n, int fileSize) {
        ArrayList<String> bitsArray = new ArrayList<>();
        // Print all binary strings
        for (int i = 0; i < fileSize; i++) {
            bitsArray.add(String.format("%"+n+"s", Integer.toBinaryString(i)).replace(" ", "0"));
        }
        return bitsArray;
    }
}
