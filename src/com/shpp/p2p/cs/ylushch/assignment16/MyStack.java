package com.shpp.p2p.cs.ylushch.assignment16;

import java.util.NoSuchElementException;

/**
 *  * <>MyStack is the Collection where every element is bounded to its previous and next neighbor
 *  * The Queue works by the principle FIFO - Last In - First Out</>
 * @param <T> is the generic type of the Stack
 */

public class MyStack<T>{
    private int size;
    //initialization of the last node
    private Node<T> last = null;

    /**
     * This method pushes the element to the stack (It pushes this element always at the end of the stack)
     * @param value is a value to be pushed to the stack
     * @return the actual value which was just pushed at the end of stack
     */
    public T push(T value){
        Node<T> newElement = new Node<>(value,null);
        if (last != null) {
            newElement.prev = last;
            last = newElement;
        }
        if (last == null) {
            last = newElement;
        }
        size++;
        return newElement.value;
    }

    /**
     * This method pops (removes and returns) the element from the stack. It always pops this element from the end of the stack
     * @return the actual value which was just removed from the end of the stack (last index)
     */
    public T pop(){
        Node<T> popped = last;
        if (size == 1) {
            last = null;
        } else if (size == 0) {
            throw new NoSuchElementException();
        } else {
            last = last.prev;
        }
        size--;
        return popped.value;
    }

    /**
     * This method retrieves the element from the stack(meaning just returns it without deleting)
     * @return the actual value which is at the end of the stack(last index)
     */
    public T peek(){
        return last.value;
    }

    /**
     * This method checks if the stack is empty or not
     * @return true is the stack is empty, false - if stack is not empty
     */
    public boolean empty() {
        return size <= 0;
    }

    /**
     * This method gets the size of the stack (how many elements it stores right now)
     * @return the size of the stack
     */
    public int size(){
        return size;
    }
    /**
     * Created Node which keeps the actual value and its neighbor -  previous element
     *
     * @param <T>
     */
    static class Node<T> {
        T value;  //actual value to be stored
        Node<T> prev; //prev element Node

        Node(T value, Node<T> prev) {
            this.value = value;
            this.prev = prev;
        }
    }
}

