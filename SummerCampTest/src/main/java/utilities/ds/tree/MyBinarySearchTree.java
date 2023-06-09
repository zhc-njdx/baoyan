package utilities.ds.tree;

import java.util.List;

/**
 * 自定义的二叉搜索树
 * @param <T>  可比较的泛形参数
 */
public class MyBinarySearchTree<T extends Comparable<T>> extends MyBinaryTree<T> {
    public MyBinarySearchTree(T value) {
        super(value);
    }

    /**
     * 找到指定value的节点
     * @param value 要查找的value
     * @return value对应的节点
     */
    public MyNode<T> find(T value) {
        return find(root, value);
    }

    /**
     * 在以root为根节点的树下查找value对应的节点
     * @param root 根节点
     * @param value 要查找的value
     * @return value对应的节点
     */
    private MyNode<T> find(MyNode<T> root, T value) {
        if (root.value.compareTo(value) == 0) return root;
        else if (root.value.compareTo(value) < 0) return find(root.right, value);
        else return find(root.left, value);
    }

    /**
     * 插入一个新的节点
     * @param value 节点的value
     */
    public void insert(T value) {
        insert(new MyNode<>(value));
    }

    /**
     * 插入一个节点
     * @param node 待插入的节点
     */
    public void insert(MyNode<T> node) {
        if (node == null) return;
        root = insert(root, node);
    }

    /**
     * 将node插入到一颗树中
     * @param root 根节点
     * @param node 待插入的节点
     * @return 插入完成的根节点
     * @attention node不能为null
     */
    private MyNode<T> insert(MyNode<T> root, MyNode<T> node) {
        if (root == null) return node;
        int code = root.value.compareTo(node.value);
        if (code > 0) {
            root.left = insert(root.left, node);
        } else if (code < 0) {
            root.right = insert(root.right, node);
        }
        return root;
    }

    /**
     * 返回某树下的最大值
     * @param root 根节点
     * @return 最大值
     * @attention root不能为null
     */
    private T findMaxValue(MyNode<T> root) {
        MyNode<T> node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.value;
    }

    /**
     * 返回某树下的最小值
     * @param root 根节点
     * @return 最小值
     * @attention root不能为null
     */
    private T findMinValue(MyNode<T> root) {
        MyNode<T> node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    /**
     * 删除指定value对应的节点
     * 1、该节点可能是叶子节点，直接删除
     * 2、该节点可能是内部节点，
     * @param value value
     */
    public void delete(T value) {
        root = delete(root, value);
    }

    /**
     * 删除某个根节点下的节点
     * @param root 根节点
     * @param value 待删除节点的value
     * @return 删除后该树的根节点
     */
    private MyNode<T> delete(MyNode<T> root, T value) {
        if (root == null) return null;
        if (root.value.compareTo(value) > 0) {
            root.left = delete(root.left, value);
        } else if (root.value.compareTo(value) < 0) {
            root.right = delete(root.right, value);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            // 找到左子树的最大值或者右子树的最小值，替换root
            T maxValueInLeft = findMaxValue(root.left);
            root.value = maxValueInLeft;
            // 去左子树上删除掉该元素
            root.left = delete(root.left, maxValueInLeft);
        }
        return root;
    }
}
