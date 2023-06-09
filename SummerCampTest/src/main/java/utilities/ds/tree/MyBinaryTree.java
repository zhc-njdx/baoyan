package utilities.ds.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 自定义的二叉树
 * @param <T> 泛形参数
 */
public class MyBinaryTree<T> {
    MyNode<T> root;

    public MyBinaryTree(T value) {
        this.root = new MyNode<>(value);
    }

    /**
     * 向树中插入一个节点
     * @param parent 父节点
     * @param child 子节点
     * @param side 插入的位置
     */
    public void insert(MyNode<T> parent, MyNode<T> child, TreeConstant side) {
        if (side == TreeConstant.LEFT_CHILD) {
            if (parent.left != null) return;
            parent.left = child;
        } else if (side == TreeConstant.RIGHT_CHILD) {
            if (parent.right != null) return;
            parent.right = child;
        }
    }

    /**
     * 判断一个节点是否是叶子节点
     * @param root 待判断的节点
     * @return true则是叶子节点，false则不是
     */
    public boolean isLeaf(MyNode<T> root) {
        return root.left == null && root.right == null;
    }

    /*
    四种遍历次序
     */

    /**
     * 按层次遍历该树
     * @return 遍历结果
     */
    public List<MyNode<T>> levelTraversal() {
        Queue<MyNode<T>> queue = new LinkedList<>();
        List<MyNode<T>> result = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i ++) {
                MyNode<T> node = queue.poll();
                if (node == null) continue;
                result.add(node);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return result;
    }

    /**
     * 前序遍历
     * @return 遍历结果
     */
    public List<MyNode<T>> preorderTraversal() {
        List<MyNode<T>> result = new ArrayList<>();
        dfs(root, result, TreeConstant.PREORDER);
        return result;
    }

    /**
     * 中序遍历
     * @return 遍历结果
     */
    public List<MyNode<T>> inorderTraversal() {
        List<MyNode<T>> result = new ArrayList<>();
        dfs(root, result, TreeConstant.INORDER);
        return result;
    }

    /**
     * 后序遍历
     * @return 遍历结果
     */
    public List<MyNode<T>> postorderTraversal() {
        List<MyNode<T>> result = new ArrayList<>();
        dfs(root, result, TreeConstant.POSTORDER);
        return result;
    }

    /**
     * 按照指定顺序深度遍历该树
     * @param root 根节点
     * @param result 遍历结果
     * @param order 遍历顺序
     */
    private void dfs(MyNode<T> root, List<MyNode<T>> result, TreeConstant order) {
        if (root == null) return;
        if (order == TreeConstant.PREORDER) result.add(root);
        dfs(root.left, result, order);
        if (order == TreeConstant.INORDER) result.add(root);
        dfs(root.right, result, order);
        if (order == TreeConstant.POSTORDER) result.add(root);
    }

    /**
     * 以满二叉树的格式按层次打印二叉树
     * @return 打印字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<MyNode<T>> queue = new LinkedList<>();
        queue.add(root);
        boolean isLastLevel = false;
        while (!isLastLevel) {
            int size = queue.size();
            isLastLevel = true;
            for (int i = 0; i < size; i ++) {
                MyNode<T> node = queue.poll();

                if (node == null) {
                    sb.append("null ");
                    node = new MyNode<>(null);
                } else {
                    sb.append(node.value.toString()).append(" ");
                }

                if (node.left != null || node.right != null) isLastLevel = false;

                queue.add(node.left);
                queue.add(node.right);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
