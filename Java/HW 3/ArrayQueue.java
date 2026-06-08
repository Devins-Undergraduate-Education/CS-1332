/**
 * Your implementation of an ArrayQueue.
 *
 * @author Devin Fromond
 * @version 1.0
 * @userid dfromond3
 * @GTID 903761713
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Resizes the ArrayQueue to 2 times its current capacity.
     *
     * @param array which represents the passed in ArrayQueue
     * @return tempArray which is used in the non-helper methods as the new, larger version of the working array
     */
    private T[] resizeArray(T[] array) {
        T[] tempArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            if (front + i >= array.length) {
                front = i * -1;
            }
            tempArray[i] = array[front + i];
        }
        front = 0;
        return tempArray; 
    }

    /**
     * Adds the data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        int index = size + front - backingArray.length;
        if (data == null) {
            throw new IllegalArgumentException("data is null; please enter a valid data element.");
        } else if (size == backingArray.length) {
            backingArray = resizeArray(backingArray);
            backingArray[size] = data;
            size++;
        } else if (size + front < backingArray.length) {
            backingArray[size + front] = data;
            size++;
        } else {
            backingArray[index] = data; 
            size++;
        } 
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you dequeue from with null.
     *
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The ArrayQueue is empty.");
        } else {
            T toBeReturned = backingArray[front]; 
            backingArray[front] = null;
            if (front + 1 == backingArray.length) {
                front = 0;
            } else {
                front++;
            }
            size--;
            return toBeReturned;
        }
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The ArrayQueue is empty! No element to be found!");
        }
        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the front index of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the front index of the queue
     */
    public int getFront() {
        // DO NOT MODIFY THIS METHOD!
        return front;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
