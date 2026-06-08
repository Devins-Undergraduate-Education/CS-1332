import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("An data is null");
        }

        for (T element: data) {
            if (element == null) {
                throw new java.lang.IllegalArgumentException("An element in data is null");
            }

            if (size == 0) {
                root = new BSTNode<T>(element);
                size++;
            } else {
                add(element);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("An data is null");
        }

        root = addHelper(root, data);
        
    }

    /**
     * Recursively searches the BST to the necessary position and adds the data
     * 
     * @param node which is a node in the BST
     * @param data which is the data being added
     * @return BSTNode which is the node being added
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("An data is null");
        } else if (node == null) {
            size++;
            return new BSTNode<T>(data);
        }

        if (node.getData().compareTo(data) > 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else {
            return node;
        }

        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data passed in is null.");
        }

        BSTNode<T> toBeReturned = new BSTNode<T>(null);
        boolean flag = false;
        root = deleteRec(root, data, toBeReturned, flag);

        if (toBeReturned == null || toBeReturned.getData() == null) {
            throw new java.util.NoSuchElementException("The element is not in the tree.");
        }
        size--;
        return toBeReturned.getData();
    }

    /**
     * Helper function that uses three cases and decides what the
     * heck to do with the nodes depending on what case is met. Refer
     * to previous method's cases for more info.
     * 
     * @param root which represents the node on the BST
     * @param data which represents the data to be compared to
     * @param toBeReturned which represents the data in the BST to be removed
     * @param flag which gets turned on if case 3 is met. This ensures toBeReturned 
     * does not get switched when it shouldnt
     * @return BSTNode which represents nodes on a BST
     */
    private BSTNode<T> deleteRec(BSTNode<T> root, T data, BSTNode<T> toBeReturned, boolean flag) {
        if (root == null) {
            return root;
        }

        int compare = data.compareTo(root.getData());

        if (compare < 0) {
            root.setLeft(deleteRec(root.getLeft(), data, toBeReturned, flag));
        } else if (compare > 0) {
            root.setRight(deleteRec(root.getRight(), data, toBeReturned, flag));
        } else if (compare == 0) {
            if (!flag) {
                toBeReturned.setData(root.getData());
                flag = true;
            }
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }
            T min = minValue(root.getRight());
            root.setData(min);
            root.setRight(deleteRec(root.getRight(), min, toBeReturned, true));
        }
        return root;
    }

    /**
     * This recursive helper method is used to find the successor node
     * 
     * @param root which is the node on the BST
     * @return T which is the data that is the successor
     */
    private T minValue(BSTNode<T> root) {
        if (root.getLeft() != null) {
            return minValue(root.getLeft());
        }
        return root.getData();
    }



    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> node = findNode(root, data);
        if (node == null) {
            throw new java.util.NoSuchElementException("Data not found in tree");
        }
        return node.getData();
    }
    
    /**
     * Recursive method to find the node in the tree
     * 
     * @param node which is a node in the BST
     * @param data which is the data being searched for
     * @return BSTNode which is the node corresponding to the data
     */
    private BSTNode<T> findNode(BSTNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        int compare = data.compareTo(node.getData());
        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return findNode(node.getLeft(), data);
        } else {
            return findNode(node.getRight(), data);
        }
    }
    

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return containsHelper(root, data);
    }
    
    /**
     * Recursively searches the BST
     * 
     * @param node which is a node in the BST
     * @param data which is the data being searched for
     * @return boolean which true if it is found and false if not
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            return containsHelper(node.getLeft(), data);
        } else if (cmp > 0) {
            return containsHelper(node.getRight(), data);
        } else {
            return true;
        }
    }
    

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderHelper(root, list);
        return list;
    }  

    /**
     * Recursive method to traverse a BST
     * 
     * @param node which is a node on the BST
     * @param list which is the elements in the tree sorted
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }

        list.add(node.getData());
        preorderHelper(node.getLeft(), list);
        preorderHelper(node.getRight(), list);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * Recursive method to traverse a BST
     * 
     * @param node which is a node in the BST
     * @param list which are the elements in the tree sorted
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }

        inorderHelper(node.getLeft(), list);
        list.add(node.getData());
        inorderHelper(node.getRight(), list);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * Recursive method to traverse a BST
     * 
     * @param node which is a node in the BST
     * @param list which are the elements in the tree sorted
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }

        postorderHelper(node.getLeft(), list);
        postorderHelper(node.getRight(), list);
        list.add(node.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> result = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.remove();
            result.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return height(root);
        }
    }
    
    /**
     * Returns the height of the specified node in the tree.
     *
     * @param node the node to get the height of
     * @return the height of the node
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeight = height(node.getLeft());
            int rightHeight = height(node.getRight());
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k must be between 0 and size of the tree");
        }
        List<T> result = new LinkedList<>(); // Use LinkedList to efficiently add to the front
        kLargestHelper(root, k, result);
        return result;
    }
    
    /**
     * Recursive method to search and sort the elements of the BST
     * 
     * @param node which is a node of the BST
     * @param k which is the kth largest elements
     * @param result which is the result in list form
     */
    private void kLargestHelper(BSTNode<T> node, int k, List<T> result) {
        if (node == null || result.size() == k) {
            return;
        }
        kLargestHelper(node.getRight(), k, result);
        if (result.size() < k) {
            result.add(0, node.getData()); // Add to front of list
            kLargestHelper(node.getLeft(), k, result);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
