package TreePackage;
import java.util.Iterator;
import java.util.NoSuchElementException;
import StackAndQueuePackage.*; // Needed by tree iterators
/**
   A class that implements the ADT binary tree.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class BinaryTree<T> implements BinaryTreeInterface<T> {
    private BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    } // end default constructor

    public BinaryTree(T rootData) {
        root = new BinaryNode<>(rootData);
    } // end constructor

    public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, rightTree);
    } // end constructor

    public void setTree(T rootData) {
        root = new BinaryNode<>(rootData);
    } // end setTree

    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
        privateSetTree(rootData, (BinaryTree<T>)leftTree,
                       (BinaryTree<T>)rightTree);
    } // end setTree

    private void privateSetTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        root = new BinaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        } // end if

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    } // end privateSetTree

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    } // end getRootData

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public void clear() {
        root = null;
    } // end clear

    protected void setRootData(T rootData) {
        root.setData(rootData);
    } // end setRootData

    protected void setRootNode(BinaryNode<T> rootNode) {
        root = rootNode;
    } // end setRootNode

    protected BinaryNode<T> getRootNode() {
        return root;
    } // end getRootNode

    public int getHeight() {
        return root.getHeight();
    } // end getHeight

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    } // end getNumberOfNodes

    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    } // end getPreorderIterator

    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    } // end getInorderIterator

    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    } // end getPostorderIterator

    public Iterator<T> getLevelOrderIterator() {
        return new LevelOrderIterator();
    } // end getLevelOrderIterator

    private class PreorderIterator implements Iterator<T> {
        private StackInterface<BinaryNode<T>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext

        public T next() {
            BinaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                BinaryNode<T> leftChild = nextNode.getLeftChild();
                BinaryNode<T> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PreorderIterator

    public void iterativePreorderTraverse() {
        StackInterface<BinaryNode<T>> nodeStack = new LinkedStack<>();
        if (root != null) {
            nodeStack.push(root);
        }
        BinaryNode<T> nextNode;
        while (!nodeStack.isEmpty()) {
            nextNode = nodeStack.pop();
            BinaryNode<T> leftChild = nextNode.getLeftChild();
            BinaryNode<T> rightChild = nextNode.getRightChild();

            // Push into stack in reverse order of recursive calls
            if (rightChild != null) {
                nodeStack.push(rightChild);
            }

            if (leftChild != null) {
                nodeStack.push(leftChild);
            }

            System.out.print(nextNode.getData() + " ");
        } // end while
    } // end iterativePreorderTraverse

    private class InorderIterator implements Iterator<T> {
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public InorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext

        public T next() {
            BinaryNode<T> nextNode = null;

            // Find leftmost node with no left child
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            } // end while

            // Get leftmost node, then move to its right subtree
            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                assert nextNode != null; // Since nodeStack was not empty
                // before the pop
                currentNode = nextNode.getRightChild();
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end InorderIterator

    public void iterativeInorderTraverse() {
        StackInterface<BinaryNode<T>> nodeStack = new LinkedStack<>();
        BinaryNode<T> currentNode = root;

        while (!nodeStack.isEmpty() || (currentNode != null)) {
            // Find leftmost node with no left child
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            } // end while

            // Visit leftmost node, then traverse its right subtree
            if (!nodeStack.isEmpty()) {
                BinaryNode<T> nextNode = nodeStack.pop();
                assert nextNode != null; // Since nodeStack was not empty
                // before the pop
                System.out.print(nextNode.getData() + " ");
                currentNode = nextNode.getRightChild();
            } // end if
        } // end while
    } // end iterativeInorderTraverse

    private class PostorderIterator implements Iterator<T> {
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext
        public T next() {
            boolean foundNext = false;
            BinaryNode<T> leftChild, rightChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                if (leftChild == null) {
                    currentNode = currentNode.getRightChild();
                } else {
                    currentNode = leftChild;
                }
            } // end while

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                BinaryNode<T> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        currentNode = parent.getRightChild();
                    } else {
                        currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            } // end if

            return nextNode.getData();
        } // end next


        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PostorderIterator

    private class LevelOrderIterator implements Iterator<T> {
        private QueueInterface<BinaryNode<T>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        } // end hasNext

        public T next() {
            BinaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                BinaryNode<T> leftChild = nextNode.getLeftChild();
                BinaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end LevelOrderIterator
} // end BinaryTree
