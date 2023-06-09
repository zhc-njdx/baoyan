package utilities.ds.tree;

public class Test {
    public static void main(String[] args) {
        MyBinarySearchTree<Integer> binarySearchTree = new MyBinarySearchTree<>(100);
        binarySearchTree.insert(200);
        binarySearchTree.insert(98);
        binarySearchTree.insert(1);
        binarySearchTree.insert(32);
        binarySearchTree.insert(40);
        binarySearchTree.insert(250);
        binarySearchTree.insert(190);
        binarySearchTree.insert(23);
        binarySearchTree.insert(176);
        System.out.println(binarySearchTree.toString());
        binarySearchTree.delete(100);
        System.out.println(binarySearchTree.toString());
    }
}
