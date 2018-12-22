import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.LinkedList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Randyll Bearer
 */
public class TernaryTree<T> implements TernaryTreeInterface<T> {
    private TernaryNode<T> root;
    
    public TernaryTree(){
        root = null;
    }
    public TernaryTree(T rootData){
        root = new TernaryNode<>(rootData);
    }
    public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree){
        privateSetTree(rootData, (TernaryTree<T>)leftTree, (TernaryTree<T>)middleTree, (TernaryTree<T>)rightTree);
    }
    
    public void setTree(T rootData){
        root = new TernaryNode<T>(rootData);
    }
    
    public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree){
        privateSetTree(rootData, (TernaryTree<T>)leftTree, (TernaryTree<T>)middleTree, (TernaryTree<T>)rightTree);
    }
    
    public void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree){
        root = new TernaryNode<>(rootData);
        
        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }
        
        if((middleTree != null) && !middleTree.isEmpty()){
            if(middleTree != leftTree){
                root.setMiddleChild(middleTree.root);
            }else{
                root.setMiddleChild(middleTree.root.copy());
            }
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree && rightTree != middleTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        } // end if

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }
        
        if((middleTree != null) && (middleTree != this)){
            middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }
    
    public T getRootData(){
        if (this.isEmpty()){
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    }
    
    public boolean isEmpty(){
        if(root == null){
            return true;
        }else{
            return false;
        }
    }
    
    public void clear(){
        root = null;
    }
    
    protected void setRootData(T newRootData){
        root.setData(newRootData);
    }
    
    protected void setRootNode(TernaryNode<T> newRootNode){
        root = newRootNode;
    }
    
    protected TernaryNode<T> getRootNode(){
        return root;
    }
    
    public int getHeight(){
        if(root == null){
            return 0;
        }
        return root.getHeight();
    }
    
    public int getNumberOfNodes(){
        if(root == null){
            return 0;
        }
        return root.getNumberOfNodes();
    }
    
    public Iterator<T> getPreorderIterator(){
        return new PreorderIterator();
    }
    public Iterator<T> getInorderIterator(){
        throw new UnsupportedOperationException("This operation is not supported by this implementation of TernaryTree");
    }
    public Iterator<T> getPostorderIterator(){
        return new PostorderIterator();
    }
    public Iterator<T> getLevelOrderIterator(){
        return new LevelOrderIterator();
    }
    
    private class PreorderIterator implements Iterator<T> {
        private Stack<TernaryNode<T>> nodeStack;

        public PreorderIterator() {
            nodeStack = new Stack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                
                if(middleChild != null){
                    nodeStack.push(middleChild);
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
        Stack<TernaryNode<T>> nodeStack = new Stack<>();
        if (root != null) {
            nodeStack.push(root);
        }
        TernaryNode<T> nextNode;
        while (!nodeStack.isEmpty()) {
            nextNode = nodeStack.pop();
            TernaryNode<T> leftChild = nextNode.getLeftChild();
            TernaryNode<T> middleChild = nextNode.getMiddleChild();
            TernaryNode<T> rightChild = nextNode.getRightChild();

            // Push into stack in reverse order of recursive calls
            if (rightChild != null) {
                nodeStack.push(rightChild);
            }

            if(middleChild != null){
                nodeStack.push(middleChild);
            }
            
            if (leftChild != null) {
                nodeStack.push(leftChild);
            }

            System.out.print(nextNode.getData() + " ");
        } // end while
    } // end iterativePreorderTraverse

    private class PostorderIterator implements Iterator<T> {
        private LinkedList<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;
        private int flag;

        public PostorderIterator() {
            nodeStack = new LinkedList<>();
            currentNode = root;
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext
        public T next() {
            TernaryNode<T> leftChild, middleChild, rightChild, nextNode = null;
            
            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                middleChild = currentNode.getMiddleChild();
                rightChild = currentNode.getRightChild();
                if (leftChild == null && middleChild == null) {
                    currentNode = rightChild;
                }else if(leftChild == null){
                    currentNode = middleChild;
                }
                else {
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

                TernaryNode<T> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        if(parent.getMiddleChild() != null){
                            currentNode = parent.getMiddleChild();
                        }else{
                            currentNode = parent.getRightChild();
                        }
                    }else if(nextNode == parent.getMiddleChild()){
                        currentNode = parent.getRightChild();
                    } 
                    else {
                        currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            } // end if
           // System.out.println("Currentnode = " + currentNode.getData());
            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PostorderIterator
    
    private class LevelOrderIterator implements Iterator<T> {
        private LinkedList<TernaryNode<T>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedList<>();
            if (root != null) {
                nodeQueue.add(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.remove();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.add(leftChild);
                }
                
                if(middleChild != null){
                    nodeQueue.add(middleChild);
                }
                
                if (rightChild != null) {
                    nodeQueue.add(rightChild);
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
    
    // TERNARYNODE INNER CLASS
    private class TernaryNode<T> { 
        private T data = null;
        private TernaryNode<T> leftChild;
        private TernaryNode<T> middleChild;
        private TernaryNode<T> rightChild;
        
        public TernaryNode(){
            this(null);
        }
        public TernaryNode(T dataPortion){
            this(dataPortion, null, null, null);
        }
        public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newMiddleChild, TernaryNode<T> newRightChild){
            data = dataPortion;
            leftChild = newLeftChild;
            middleChild = newMiddleChild;
            rightChild = newRightChild;
        }
        
        public T getData(){
            return data;
        }
        public void setData(T newData){
            data = newData;
        }
        
        public TernaryNode<T> getLeftChild(){
            return leftChild;
        }
        public void setLeftChild(TernaryNode<T> newLeftChild){
            leftChild = newLeftChild;
        }
        public boolean hasLeftChild(){
            if(leftChild != null){
                return true;
            }
            return false;
        }
        
        public TernaryNode<T> getMiddleChild(){
            return middleChild;
        }
        public void setMiddleChild(TernaryNode<T> newMiddleChild){
            middleChild = newMiddleChild;
        }
        public boolean hasMiddleChild(){
            if(middleChild != null){
                return true;
            }
            return false;
        }
        
        public TernaryNode<T> getRightChild(){
            return rightChild;
        }
        public void setRightChild(TernaryNode<T> newRightChild){
            rightChild = newRightChild;
        }
        public boolean hasRightChild(){
            if(rightChild != null){
                return true;
            }
            return false;
        }
        
        public boolean isLeaf(){
            if(leftChild == null && middleChild == null && rightChild == null){
                return true;
            }
            return false;
        }
        
        public int getNumberOfNodes(){
            int leftNumber = 0;
            int middleNumber = 0;
            int rightNumber = 0;
            
            if(leftChild != null){
                leftNumber = leftChild.getNumberOfNodes();
            }
            if(middleChild != null){
                middleNumber = middleChild.getNumberOfNodes();
            }
            if(rightChild != null){
                rightNumber = rightChild.getNumberOfNodes();
            }
            
            return 1 + leftNumber + middleNumber + rightNumber;
        }
        
        public int getHeight(){
            if(this == null){
                return 0;
            }
            return getHeight(this);
        }
        private int getHeight(TernaryNode<T> root){
            int leftHeight;
            int middleHeight;
            int rightHeight;
            
            if(root == null){
                return 0;
            }else if(root.getLeftChild() == null && root.getMiddleChild() == null && root.getRightChild() == null){
                return 1;
            }
            
            if(root.getLeftChild() == null){
                leftHeight = 0;
            }else{
                leftHeight = getHeight(root.getLeftChild());
            }
            
            if(root.getMiddleChild() == null){
                middleHeight = 0;
            }else{
                middleHeight = getHeight(root.getMiddleChild());
            }
            
            if(root.getRightChild() == null){
                rightHeight = 0;
            }else{
                rightHeight = getHeight(root.getRightChild());
            }
            
            int height = 0;
            
            if(leftHeight > middleHeight && leftHeight > rightHeight){
                height = leftHeight;
            }
            else if(middleHeight > leftHeight && middleHeight > rightHeight){
                height = middleHeight;
            }
            else if(rightHeight > leftHeight && rightHeight > middleHeight){
                height = rightHeight;
            }
            else if(leftHeight > rightHeight && leftHeight == middleHeight){
                height = leftHeight;
            }
            else if(leftHeight > middleHeight && leftHeight == rightHeight){
                height = leftHeight;
            }
            else if(middleHeight > leftHeight && middleHeight == rightHeight){
                height = middleHeight;
            }
            else if(middleHeight > rightHeight && middleHeight == leftHeight){
                height = middleHeight;
            }
            else if(rightHeight > leftHeight && rightHeight == middleHeight){
                height = rightHeight;
            }
            else if(rightHeight > middleHeight && rightHeight == leftHeight){
                height = rightHeight;
            }else if(leftHeight == middleHeight && leftHeight == rightHeight){
                height = leftHeight;
            }
            
            height = height + 1;
            return height;
        }
        
        /** Copies the subtree rooted at this node.
        @return  The root of a copy of the subtree rooted at this node. */
        public TernaryNode<T> copy() {
            TernaryNode<T> newRoot = new TernaryNode<>(data);

            if (leftChild != null) {
                newRoot.setLeftChild(leftChild.copy());
            }
            
            if (middleChild != null){
                newRoot.setMiddleChild(middleChild.copy());
            }
            
            if (rightChild != null) {
                newRoot.setRightChild(rightChild.copy());
            }

            return newRoot;
        } // end copy
    }
}
