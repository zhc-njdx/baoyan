package datastructure.tree;

public class MyNode<T> {
    T value;
    MyNode<T> left;
    MyNode<T> right;

    int height;

    public MyNode(T value) {
        this.value = value;
        this.left = this.right = null;
        this.height = 1;
    }
}
