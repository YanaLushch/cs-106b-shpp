package com.shpp.p2p.cs.ylushch.assignment16;

import java.util.NoSuchElementException;

/**
 * <>My Linked List is the dynamic Collection which is bounded to the previous and next element.
 * It can remove, add, get, set elements either from/into the beginning or from/into the end of the Linked List.
 * It also can manipulate with elements by its index.
 * </>
 *
 * @param <T> is the generic type of the LinkedList
 */

public class MyLinkedList<T> {
    private int size;
    //initialization of the first and last node
    private Node<T> first = null;
    private Node<T> last = null;

    /**
     * This method returns the length of the elements in LinkedList
     *
     * @return length of elements in LinkedList
     */
    public int size() {
        return size;
    }

    /**
     * This method adds the specific value by the specific index
     * while shifting the next values by 1 index further
     *
     * @param index is the position of the element to be inserted
     * @param value is the value to be added on a specified index
     * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the LinkedList length
     */
    public void add(int index, T value) {
        if (index == size - 1 || index == size){
            addLast(value);
        } else if(index == 0){
            addFirst(value);
        } else if (index < size && index > 0) {
            Node<T> shifted = findByIndex(index);
            shifted.prev.next = new Node<>(value, shifted.prev, shifted);
            size++;
        } else{
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * This method adds the specific value at the beginning of the list (index - 0)
     *
     * @param value is the value to be added at the beginning of the list
     */
    public void addFirst(T value) {
        Node<T> newElement = new Node<>(value, null, first);
        if (first != null) {
            first.prev = newElement;
        }
        first = newElement;
        if (last == null) {
            last = newElement;
        }
        size++;
    }

    /**
     * This method adds the specific value at the end of the list (index -  (size - 1))
     *
     * @param value is the value to be added at the end of the list
     */
    public void addLast(T value) {
        Node<T> newElement = new Node<>(value, last, null);
        if (last != null) {
            last.next = newElement;
        }
        last = newElement;
        if (first == null) {
            first = newElement;
        }
        size++;
    }

    /**
     * This method returns the value by the specific index
     *
     * @param index is the position of the element to be returned
     * @return value  on a specified index
     * * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the LinkedList length
     */
    public T get(int index) {
        checkIndexInBonds(index);
        Node<T> getNode = findByIndex(index);
        return getNode.value;
    }

    /**
     * This method returns the value which is at the beginning of the list (index - 0)
     *
     * @return the value by index 0
     * @NoSuchElementException - Exception thrown if the LinkedList is empty,
     * eventually there is no First element in the LinkedList
     */
    public T getFirst() {
        if (first != null) {
            return first.value;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * This method returns the value which is at the end of the list (index -  (size - 1))
     *
     * @return the value by last index
     * @NoSuchElementException - Exception is thrown if the LinkedList is empty,
     * eventually there is no Last element is the LinkedList
     */
    public T getLast() {
        if (last != null) {
            return last.value;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * This method sets the specific value at the specific index by replacing with it the previous value.
     *
     * @param index is the position of the element to be replaced on the new value
     * @param value is the new value to be replaced
     * @return value which was replaced
     * * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the LinkedList length
     */
    public T set(int index, T value) {
        checkIndexInBonds(index);
        Node<T> oldVal = findByIndex(index);
        oldVal.value = value;
        return oldVal.value;
    }

    /**
     * This method removes the element by its position index
     * @param index is the position of the element needs to be removed
     * @return the element that was removed from the LinkedList
     * * @IndexOutOfBoundsException() - Exception is thrown if the index provided is beyond the LinkedList length
     */
    public T remove(int index) {
        checkIndexInBonds(index);
        if (index == 0){
            return removeFirst();
        }else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> toBeRemoved = findByIndex(index);
            toBeRemoved.prev.next = toBeRemoved.next;
            toBeRemoved.next.prev = toBeRemoved.prev;
            size--;
            return toBeRemoved.value;
        }
    }

    /**
     * This method removes the first element from the list
     *
     * @return removed Element
     * @NoSuchElementException - Exception thrown if the LinkedList is empty,
     * eventually there is no First element in the LinkedList
     */
    public T removeFirst() {
        Node<T> removed = first;
        if (size == 1) {
            first = null;
            last = null;
        } else if (last != null) {
            removed = first.next.prev;
            first.next.prev = null;
            first = first.next;
        } else {
            throw new NoSuchElementException();
        }
        size--;
        return removed.value;
    }

    /**
     * This method removes the last element from the list
     *
     * @return removed Element
     * @NoSuchElementException - Exception thrown if the LinkedList is empty,
     * eventually there is no First element in the LinkedList
     */
    public T removeLast() {
        Node<T> removed = last;
        if (size == 1) {
            last = null;
            first = null;
        } else if (first != null) {
            removed = last.prev.next;
            last.prev.next = null;
            last = last.prev;
        } else {
            throw new NoSuchElementException();
        }
        size--;
        return removed.value;
    }

    /**
     * This method checks if the LinkedList is empty or not
     *
     * @return true is the LinkedList is empty, false - if it is not empty
     */
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * This is the helper-method to get the value by its index
     *
     * @param index is the position of the element that needs to be returned
     * @return the value which is found by the specific index
     */
    private Node<T> findByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * This is the helper method which checks whether the given index is in the LinkedList size
     * @param index is the position needs to be checked
     * @IndexOutOfBoundsException() - this Exception is thrown if the index provided is beyond the LinkedList length
     */
    private void checkIndexInBonds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Created Node which keeps in itself the actual value and previous and last element
     *
     * @param <T>
     */
    static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}


