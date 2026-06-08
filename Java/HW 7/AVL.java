import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Your implementation of an AVL.
 *
 * @author Devin Fromond
 * @version 1.0
 * @userid fromond3
 * @GTID 903761713
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot add null elements to the tree");
            }
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            root = new AVLNode<>(data);
            size++;
            return;
        }
        root = addHelper(root, data);
    }
    

    /**
     * Recurisve add helper that models the BST add
     * 
     * @param node which is the node to be added or root node
     * @param data which is the passed in data
     * @return which is the root node
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        int cmp = data.compareTo(node.getData());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else {
            node.setRight(addHelper(node.getRight(), data));
        }
    
        // update height and balance factor
        update(node);
    
        // rebalance if necessary
        return rebalance(node);
    }
    
    /**
     * Updates the height and balance factor of the nodes
     * 
     * @param node which is the node to be updated
     */
    private void update(AVLNode<T> node) {
        int leftHeight = node.getLeft() == null ? -1 : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null ? -1 : node.getRight().getHeight();
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }
    

    /**
     * Preforms necessary rebalance operations on the node cluster
     * 
     * @param node which is the node to be rebalanced
     * @return which is the new root node
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            node = leftRotate(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            node = rightRotate(node);
        }

        return node;
    }
    
    
    /**
     * Preforms a right-left or a right rotation on the AVL node cluster
     * 
     * @param node which is the node to be rotated from
     * @return the new root
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);
        update(node);
        update(leftChild);
        return leftChild;
    }
    
    /**
     * Preforms a left-right or a left rotation on the AVL node cluster
     * 
     * @param node which is the node to be rotated from
     * @return the new root
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);
        update(node);
        update(rightChild);
        return rightChild;
    }
    

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        AVLNode<T> nodeToRemove = findNode(root, data);
        if (nodeToRemove == null) {
            throw new java.util.NoSuchElementException("Data not found.");
        }
        T removedData = nodeToRemove.getData();
        root = removeNode(root, nodeToRemove);
        size--;
        return removedData;
    }
    

    /**
     * Removes a given node from the AVL tree and returns the root node of the updated tree.
     * 
     * @param node the root node of the current subtree being traversed.
     * @param nodeToRemove the node to remove from the tree.
     * @return the root node of the updated tree after the removal operation
     */

    private AVLNode<T> removeNode(AVLNode<T> node, AVLNode<T> nodeToRemove) {
        if (node == null) {
            return null;
        }
        int cmp = nodeToRemove.getData().compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(removeNode(node.getLeft(), nodeToRemove));
        } else if (cmp > 0) {
            node.setRight(removeNode(node.getRight(), nodeToRemove));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                // case 1 or 2: node has 0 or 1 child
                AVLNode<T> child = (node.getLeft() != null) ? node.getLeft() : node.getRight();
                if (child == null) {
                    // node is a leaf
                    return null;
                } else {
                    // replace node with its child
                    node.setLeft(null);
                    node.setRight(null);
                    node = child;
                }
            } else {
                // case 3: node has 2 children
                AVLNode<T> predecessor = getPredecessor(node.getLeft());
                node.setData(predecessor.getData());
                node.setLeft(removeNode(node.getLeft(), predecessor));
            }
        }
        // update height and balance factor
        update(node);

        // rebalance if necessary
        return rebalance(node);
    }
    
    /**
     * Gets the predecessor of a given node
     * Used in the remove method
     * 
     * @param node which is the node to which the predecessor belongs to
     * @return the predecessor
     */
    private AVLNode<T> getPredecessor(AVLNode<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }
    

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> node = findNode(root, data);
        if (node == null) {
            throw new java.util.NoSuchElementException("Data not found in tree");
        }
        return node.getData();
    }

    /**
     * Finds the node in the tree containing the given data.
     *
     * @param node the root node of the subtree to search in
     * @param data the data to search for
     * @return the node containing the data, or null if the data is not in the tree
     */
    private AVLNode<T> findNode(AVLNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            return findNode(node.getLeft(), data);
        } else if (cmp > 0) {
            return findNode(node.getRight(), data);
        } else {
            return node;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        
        AVLNode<T> current = root;
        while (current != null) {
            int cmp = data.compareTo(current.getData());
            if (cmp < 0) {
                current = current.getLeft();
            } else if (cmp > 0) {
                current = current.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        deepestBranchesHelper(list, root);
        return list;
    }

    /**
     * Recursive helper method for deepestBranches
     *
     * @param list which is the list to be returned
     * @param node which is the node to comapre
     */
    private void deepestBranchesHelper(List<T> list, AVLNode<T> node) {
        if (node == null) {
            return;
        } else {
            list.add(node.getData());
            if (node.getLeft() != null
                    && !(node.getHeight() - node.getLeft().getHeight() > 1)) {
                deepestBranchesHelper(list, node.getLeft());
            }
            if (node.getRight() != null
                    && !(node.getHeight() - node.getRight().getHeight() > 1)) {
                deepestBranchesHelper(list, node.getRight());
            }
        }
    }
    
    
    
    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("RAH! THE DATA YOU PASSED IN IS NULL");
        }
        List<T> list = new ArrayList<>();
        if (data1.equals(data2)) {
            return list;
        } else {
            sortedInBetweenHelper(data1, data2, root, list);
        }
        return list;
    }

    /**
     * This is a recursive helper method.
     *
     * @param data1 which is the first data
     * @param data2 which is the second data
     * @param list which is the list where the data is to be added
     * @param node which is the node to compare
     */
    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        } else {
            if (node.getData().compareTo(data1) > 0) {
                sortedInBetweenHelper(data1, data2, node.getLeft(), list);
            }
            if (node.getData().compareTo(data1) > 0
                    && node.getData().compareTo(data2) < 0) {
                list.add(node.getData());
            }
            if (node.getData().compareTo(data2) < 0) {
                sortedInBetweenHelper(data1, data2, node.getRight(), list);
            }
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
    public AVLNode<T> getRoot() {
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