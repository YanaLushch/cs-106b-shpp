package com.shpp.p2p.cs.ylushch.assignment16;

/**
 * <>My ArrayList - is the dynamic Collection which can dynamically add, remove, set and get elements into/from ArrayList</>>
 *
 * @param <T> is the generic type of the ArrayList
 */
public class MyArrayList<T> {
    private T[] arrayList;

    public MyArrayList() {
        arrayList = (T[]) new Object[0];
    }

    /**
     * This method returns the length of the ArrayList
     * @return the length of the ArrayList
     */
    public int size() {
        return arrayList.length;
    }

    /**
     * This void method adds the element to the ArrayList at the end of it.
     *
     * @param value is the element needs to be added to the ArrayList
     */
    public void add(T value) {
        T[] largerArray = (T[]) new Object[arrayList.length + 1];
        System.arraycopy(arrayList, 0, largerArray, 0, arrayList.length);
        arrayList = largerArray;
        arrayList[arrayList.length - 1] = value;
    }

    /**
     * This void method adds the element to the ArrayList at the specific index
     * increasing size by 1 and by shifting all elements that are after this added element by 1
     * @param index is the is the position of the element which needs to be added
     * @param value is the element needs to be added to the ArrayList
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the ArrayList length
     */
    public void add(int index, T value) {
        T[] copyArray = (T[]) new Object[arrayList.length + 1];
        if (index <= arrayList.length && index >= 0){
            System.arraycopy(arrayList, 0, copyArray, 0, index);
            copyArray[index] = value;
            System.arraycopy(arrayList, index, copyArray, index + 1, arrayList.length - index);
            arrayList = copyArray;
        } else{
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * This void method sets the element into the ArrayList
     * by replacing current element in its index position
     * @param index is the position of the element which needs to be added
     * @param value is the element needs to be added to the Array List
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the ArrayList length
     */
    public void set(int index, T value){
        checkIndexInBonds(index);
        arrayList[index] = value;
    }
    /**
     * This void method removes the element at the specified index position from the ArrauList
     *
     * @param index is the position of the element which needs to be removed
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the ArrayList length
     */
    public void remove(int index) {
        checkIndexInBonds(index);
        T[] copyArray = (T[]) new Object[arrayList.length - 1];
        System.arraycopy(arrayList, 0, copyArray, 0, index);
        System.arraycopy(arrayList, index + 1, copyArray, index, arrayList.length - index - 1);
        arrayList = copyArray;
    }

    /**
     * This method returns the element at the specified index position
     *
     * @param index is the position of the element which needs to be gotten
     * @return the element which is by the specified position
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the ArrayList length
     */
    public T get(int index) {
        checkIndexInBonds(index);
        return arrayList[index];
    }

    /**
     * This method checks whether the specified by parameter element exists in the ArrayList.
     * Based on that it returns true or false
     *
     * @param value is the element which needs to be looked up in the ArrayList.
     * @return true if ArrayList contains specified element. False - if not.
     */
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    /**
     * This method checks whether the specified by parameter element exists in the ArrayList.
     * If it exists - method returns the index of the first match.
     * If it does not exist - method returns -1.
     *
     * @param value is the element which needs to be looked up in the ArrayList.
     * @return index of the first matched element or -1 if the element does not exist.
     */
    public int indexOf(T value) {
        for (int i = 0; i < arrayList.length; i++) {
            if (arrayList[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This void method clears out all the elements from the LinkedList. It leaves empty LinkedList.
     */
    public void clear() {
        arrayList = (T[]) new Object[0];
    }

    /**
     * This is the helper method which checks whether the index is inside the size of the ArrayList
     * @param index is the position of the element needs to be added to the ArrayList
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the ArrayList length
     */
    private void checkIndexInBonds(int index) {
        if (index >= arrayList.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
