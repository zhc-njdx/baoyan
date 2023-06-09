package utilities.ds.tree;

public class MyNode<T> {
    T value;
    MyNode<T> left;
    MyNode<T> right;

    public MyNode(T value) {
        this.value = value;
        this.left = this.right = null;
    }
}
