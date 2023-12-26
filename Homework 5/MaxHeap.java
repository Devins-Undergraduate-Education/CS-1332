import java.util.ArrayList;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Devin Fromond
 * @version 1.0
 * @userid dfromond3
 * @GTID 903761712
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable<?>[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot be null");
        }

        size = data.size();
        backingArray = (T[]) new Comparable<?>[2 * size + 1];
        int i = 1;

        for (T element : data) {
            if (element == null) {
                throw new java.lang.IllegalArgumentException("Elements in data cannot be null");
            }

            backingArray[i] = element;
            i++;
        }
        heapify();
    }

    /**
     * Private helper method to turn specific clusters of elements in the array into a cluser
     */
    private void heapify() {
        for (int i = size / 2; i >= 1; i--) {
            siftDown(i);
        }
    }

    /**
     * Restores the heap property by percolating the new element up to its proper place in the heap
     * @param index which represents the current index of the array
     */
    private void siftUp(int index) {
        while (index > 1 && backingArray[index].compareTo(backingArray[index / 2]) > 0) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;
            index /= 2;
        }
    }

    /**
     * restores the heap property by percolating the new root down to its proper place in the heap
     * @param index which represents the current index of the array
     */
    private void siftDown(int index) {
        while (index * 2 <= size) {
            int j = index * 2;
            if (j < size && backingArray[j].compareTo(backingArray[j + 1]) < 0) {
                j++;
            }
            if (backingArray[index].compareTo(backingArray[j]) >= 0) {
                break;
            }
            T temp = backingArray[index];
            backingArray[index] = backingArray[j];
            backingArray[j] = temp;
            index = j;
        }
    }

    

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
    
        if (size + 1 >= backingArray.length) {
            T[] newBackingArray = (T[]) new Comparable<?>[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                newBackingArray[i] = backingArray[i];
            }
            backingArray = newBackingArray;
        }
    
        backingArray[++size] = data;
        siftUp(size);
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
        
        T removedData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size--] = null;
        
        siftDown(1);
        
        return removedData;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
            
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable<?>[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}