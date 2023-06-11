package utilities.ds.tree;

public class Test {
    public static void main(String[] args) {
        MyBalancedBinarySearchTree<Integer> binarySearchTree = new MyBalancedBinarySearchTree<>(100);
        binarySearchTree.insert(200);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(98);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(1);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(32);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(40);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(250);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(190);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(23);
        System.out.println(binarySearchTree);
        binarySearchTree.insert(176);
        System.out.println(binarySearchTree);
        binarySearchTree.delete(98);
        System.out.println(binarySearchTree);
    }
}
