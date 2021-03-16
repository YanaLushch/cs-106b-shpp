package com.shpp.p2p.cs.ylushch.assignment16;

import java.util.NoSuchElementException;

/**
 * <>MyQueue is the Collection where every element is bounded to its previous and next neighbor
 * The Queue works by the principle FIFO - First In - First Out</>
 * @param <T> is the generic type of the Queue
 */
public class MyQueue<T> {
    private int size;
    //initialization of the first and last node
    private Node<T> first = null;
    private Node<T> last = null;

    /**
     * This method adds element to the queue (It adds this element always at the end of the queue)
     *
     * @param value is a value to be added to the queue
     * @return the actual value which was just added at the end of the queue
     */
    public boolean add(T value) {
        Node<T> newElement = new Node<>(value, null);
        if (last != null) {
            last.next = newElement;
            last = newElement;
        }
        if (first == null) {
            first = newElement;
            last = newElement;
        }
        size++;
        return true;
    }

    /**
     * This method polls (removes and returns) the element from the queue. It always pops this element from the beginning of the queue
     *
     * @return the actual value which was just removed from the beginning of the queue (first index)
     */
    public T poll() {
        Node<T> polled= first;
        if (size == 1) {
            first = null;
            last = null;
        } else if (size == 0) {
            throw new NoSuchElementException();
        } else {
            first = first.next;
        }
        size--;
        return polled.value;
    }

    /**
     * This method retrieves the element from the queue(meaning just returns it without deleting)
     *
     * @return the actual value which is at the beginning of the queue(first index)
     */
    public T peek() {
        return first.value;
    }

    /**
     * This method checks if the queue is empty or not
     *
     * @return true is the queue is empty, false - if queue is not empty
     */
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * This method gets the size of the queue (how many elements it stores right now)
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Created Node which keeps the actual value and its neighbor -  next element
     *
     * @param <T>
     */
    static class Node<T> {
        T value; //actual value to be stored
        Node<T> next; //next element Node

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
