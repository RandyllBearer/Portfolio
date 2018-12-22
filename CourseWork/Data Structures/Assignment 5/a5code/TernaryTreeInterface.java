/**
 * A ternary tree, i.e., a tree where each node has 0–3 children.
 */
public interface TernaryTreeInterface<T> extends TreeInterface<T>, TreeIteratorInterface<T> {

    /**
     * Sets the ternary tree to a new one-node ternary tree with the given data
     * @param rootData The data for the new tree's root node
     */
    public void setTree(T rootData);

    /**
     * Sets this ternary tree to a new ternary tree
     * @param rootData The data for the new tree's root node
     * @param leftTree The left subtree of the new tree
     * @param middleTree The middle subtree of the new tree
     * @param rightTree The right subtree of the new tree
     */
    public void setTree(T rootData, TernaryTreeInterface<T> leftTree,
            TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree);

}


