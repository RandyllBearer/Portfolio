import java.util.Iterator;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Randyll Bearer
 */
public class DriverClass {
    public static void main(String args[]){
        TernaryTree ternaryTree = new TernaryTree(5,new TernaryTree(1),new TernaryTree(2),new TernaryTree(3));
        System.out.println("This ternary tree has height: " + ternaryTree.getHeight()); //Should be 2
        System.out.println("This ternary tree has # of nodes: " + ternaryTree.getNumberOfNodes()); // Should be 4
        System.out.println("The value of root: " + ternaryTree.getRootData()); // should be 5
        if(ternaryTree.isEmpty() == true){
            System.out.println("THIS SHOULD NOT HAVE HAPPENED");
        }
        
        //ITERATORS
        System.out.print("\nPreorder:\n");
        Iterator preorderIterator = ternaryTree.getPreorderIterator();
        while(preorderIterator.hasNext()){
            Object element = preorderIterator.next();
            System.out.println(element);
        }
        
        System.out.print("\nPostorder:\n");
        Iterator postorderIterator = ternaryTree.getPostorderIterator();
        while(postorderIterator.hasNext()){
            Object element = postorderIterator.next();
            System.out.println(element);
        }
        
        System.out.print("\nLevelOrder:\n");
        Iterator levelorderIterator = ternaryTree.getLevelOrderIterator();
        while(levelorderIterator.hasNext()){
            Object element = levelorderIterator.next();
            System.out.println(element);
        }
        //
        ternaryTree.setTree(10);
        System.out.println("\nThe second ternary tree has height: " + ternaryTree.getHeight()); // should be 1
        System.out.println("the second ternary tree has # of nodes: " + ternaryTree.getNumberOfNodes()); // should be 1
        System.out.println("The value of second root: " + ternaryTree.getRootData()); // should be 10
        if(ternaryTree.isEmpty() == true){
            System.out.println("THIS SHOULD NOT HAVE HAPPENED");
        }
        
        ternaryTree.setTree(15, new TernaryTree(20),new TernaryTree(25),new TernaryTree(30));
        System.out.println("\nThe third ternary tree has height: " + ternaryTree.getHeight()); //should be 2
        System.out.println("The third ternary tree has # of nodes: " + ternaryTree.getNumberOfNodes()); //should be 4
        System.out.println("the value of root: " + ternaryTree.getRootData()); // should be 15
        if(ternaryTree.isEmpty() == true){
            System.out.println("THIS SHOULD NOT HAVE HAPPENED");
        }
        
        ternaryTree.setRootData(777);
        System.out.println("\n The new root data is: " + ternaryTree.getRootData());
        
        ternaryTree.clear();
        System.out.println("\nTernary Tree height after clear: " + ternaryTree.getHeight()); // Should be 0
        System.out.println("# of nodes in TernaryTree after clear: " + ternaryTree.getHeight()); // should be 0
        if(ternaryTree.isEmpty() == true){
            System.out.println("Yes, the tree is empty");
        }
        
        TernaryTree leftTreeTest = new TernaryTree(10, new TernaryTree(11), new TernaryTree(12), new TernaryTree(13));
        TernaryTree middleTreeTest = new TernaryTree(15, new TernaryTree(16), new TernaryTree(17), new TernaryTree(18));
        TernaryTree rightTreeTest = new TernaryTree (20, new TernaryTree(21), new TernaryTree(22), new TernaryTree(23));
        TernaryTree treeTest = new TernaryTree(1, leftTreeTest, middleTreeTest, rightTreeTest);
        
        System.out.println("\nNow commencing treeTest: ");
        System.out.println("treeTest has height: "+treeTest.getHeight());
        System.out.println("treeTest has # of Nodes: " + treeTest.getNumberOfNodes());
        System.out.println("Value of treeTest root: "+ treeTest.getRootData());
        if(treeTest.isEmpty()){
            System.out.println("treeTest SHOULD NOT BE EMPTY AT THIS TIME");
        }
        System.out.print("\nPreorder:\n");
        Iterator preorderIteratorTest = treeTest.getPreorderIterator();
        while(preorderIteratorTest.hasNext()){
            Object element = preorderIteratorTest.next();
            System.out.println(element);
        }
        
        System.out.print("\nPostorder:\n");
        Iterator postorderIteratorTest = treeTest.getPostorderIterator();
        while(postorderIteratorTest.hasNext()){
            Object element = postorderIteratorTest.next();
            System.out.println(element);
        }
        
        System.out.print("\nLevelOrder:\n");
        Iterator levelorderIteratorTest = treeTest.getLevelOrderIterator();
        while(levelorderIteratorTest.hasNext()){
            Object element = levelorderIteratorTest.next();
            System.out.println(element);
        }
        
        System.out.println("\nNOW CLEARING treeTest: ");
        treeTest.clear();
        System.out.println("Heigh of treeTest after clear: " + treeTest.getHeight());
        System.out.println("# of nodes in treeTest after clear: " + treeTest.getNumberOfNodes());
        if(treeTest.isEmpty()){
            System.out.println("treeTest is empty");
        }
        
        System.out.println("NOW PROCESSING [EVEN] 4-LAYER TREE TEST");
        
        TernaryTree tree1 = new TernaryTree(1, new TernaryTree(2), new TernaryTree(3), new TernaryTree(4));
        TernaryTree tree2 = new TernaryTree(5, new TernaryTree(6), new TernaryTree(7), new TernaryTree(8));
        TernaryTree tree3 = new TernaryTree(9, new TernaryTree(10), new TernaryTree(11), new TernaryTree(12));
        TernaryTree tree5 = new TernaryTree(13, new TernaryTree(14), new TernaryTree(15), new TernaryTree(16));
        TernaryTree tree6 = new TernaryTree(17, new TernaryTree(18), new TernaryTree(19), new TernaryTree(20));
        TernaryTree tree7 = new TernaryTree(21, new TernaryTree(22), new TernaryTree(23), new TernaryTree(24));
        TernaryTree tree9 = new TernaryTree(25, new TernaryTree(26), new TernaryTree(27), new TernaryTree(28));
        TernaryTree tree10 = new TernaryTree(29, new TernaryTree(30), new TernaryTree(31), new TernaryTree(32));
        TernaryTree tree11 = new TernaryTree(33, new TernaryTree(34), new TernaryTree(35), new TernaryTree(36));
        TernaryTree tree4 = new TernaryTree(37, tree1, tree2, tree3);
        TernaryTree tree8 = new TernaryTree(38, tree5, tree6, tree7);
        TernaryTree tree12 = new TernaryTree(39, tree9, tree10, tree11);
        TernaryTree tree13 = new TernaryTree(40, tree4, tree8, tree12);
        
        System.out.println("even4-layer has height: "+tree13.getHeight());
        System.out.println("even4-layer has # of Nodes: " + tree13.getNumberOfNodes());
        System.out.println("Value of even4-layer root: "+ tree13.getRootData());
        if(tree13.isEmpty()){
            System.out.println("treeTest SHOULD NOT BE EMPTY AT THIS TIME");
        }
        System.out.print("\nPreorder:\n");
        Iterator preorderIteratorTest2 = tree13.getPreorderIterator();
        while(preorderIteratorTest2.hasNext()){
            Object element = preorderIteratorTest2.next();
            System.out.println(element);
        }
        
        System.out.print("\nPostorder:\n");
        Iterator postorderIteratorTest2 = tree13.getPostorderIterator();
        while(postorderIteratorTest2.hasNext()){
            Object element = postorderIteratorTest2.next();
            System.out.println(element);
        }
        
        System.out.print("\nLevelOrder:\n");
        Iterator levelorderIteratorTest2 = tree13.getLevelOrderIterator();
        while(levelorderIteratorTest2.hasNext()){
            Object element = levelorderIteratorTest2.next();
            System.out.println(element);
        }
        
        System.out.println("\nNOW CLEARING even4-layer test: ");
        tree13.clear();
        System.out.println("Heigh of treeTest after clear: " + tree13.getHeight());
        System.out.println("# of nodes in treeTest after clear: " + tree13.getNumberOfNodes());
        if(tree13.isEmpty()){
            System.out.println("even4-layer is empty");
        }
    }
}
