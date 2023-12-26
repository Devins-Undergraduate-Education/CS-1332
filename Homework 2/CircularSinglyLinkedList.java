/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Either the index you provided is negative or the index you "
             + "entered is greater than the size of the list.");
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("The data passed in is null.");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data passed in is null.");
        }

        if (head == null) {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            head = newNode;
            head.setNext(head);
            size++;
        } else {
            T temp;
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            temp = head.getData();
            head.setData(newNode.getData());
            newNode.setData(temp);
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data passed in is null.");
        }

        if (head == null) {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            head = newNode;
            head.setNext(head);
            size++;
        } else {
            T temp;
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            temp = head.getData();
            head.setData(newNode.getData());
            newNode.setData(temp);
            head = head.getNext();
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("The index you entered is either "
            + "negative or greater than the size of the list.");
        }

        T toBeReturned;
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            toBeReturned = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return toBeReturned;
        }
        
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new java.util.NoSuchElementException("The list is empty.");
        }
        
        T toBeReturned = head.getData();
        if (head.getNext() == head) {
            head.setNext(null);
            head = null;
            size--;
            return toBeReturned;
        } else {
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return toBeReturned;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new java.util.NoSuchElementException("The list is empty.");
        }

        T toBeReturned;
        if (head.getNext() == head) {
            return removeFromFront();
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.getNext();
            }
            toBeReturned = current.getNext().getData();
            current.setNext(head);
            size--;
            return toBeReturned;
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("You either entered a negative index "
            + "or an index greater than the size of the list.");
        }

        if (index == 0) {
            return head.getData();
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        //head.setNext(null);
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data provided is null.");
        }
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty. Therefore, the data "
            + "being searched for is not found.");
        }

        T tempData = null;
        CircularSinglyLinkedListNode<T> current = head;
        CircularSinglyLinkedListNode<T> tempNodePrev = null;
        CircularSinglyLinkedListNode<T> tempNodeNext = null;
        int indexFound = -1;
        if (head.getData().equals(data)) {
            indexFound = 0;
        }
        for (int i = 0; i < size - 1; i++) {
            if (current.getNext().getData().equals(data)) {
                indexFound = i + 1;
                tempNodePrev = current;
                tempNodeNext = current.getNext().getNext();
                tempData = current.getNext().getData();
            }
            current = current.getNext();
        }
        if (indexFound == -1) {
            throw new java.util.NoSuchElementException("The data was not found in the list.");
        } else if (indexFound == 0) {
            return removeFromFront();
        } else {
            tempNodePrev.setNext(tempNodeNext);
            size--;
            return tempData;
        }

    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arrayForm = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            arrayForm[i] = current.getData();
            current = current.getNext();
        }
        return arrayForm;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
