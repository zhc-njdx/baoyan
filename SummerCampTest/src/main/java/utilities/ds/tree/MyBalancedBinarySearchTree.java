package utilities.ds.tree;

public class MyBalancedBinarySearchTree<T extends Comparable<T>> extends MyBinarySearchTree<T>{
    public MyBalancedBinarySearchTree(T value) {
        super(value);
    }

    private int getHeight(MyNode<T> node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalancedFactor(MyNode<T> node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private void updateHeight(MyNode<T> node) {
        if (node == null) return;
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private MyNode<T> leftRotate(MyNode<T> node) {
        MyNode<T> newNode = node.right;
        node.right = newNode.left;
        newNode.left = node;
        updateHeight(node);
        updateHeight(newNode);
        return newNode;
    }

    private MyNode<T> rightRotate(MyNode<T> node) {
        MyNode<T> newNode = node.left;
        node.left = newNode.right;
        newNode.right = node;
        updateHeight(node);
        updateHeight(newNode);
        return newNode;
    }

    private MyNode<T> insertAVL(MyNode<T> root, MyNode<T> node) {
        if (root == null) return node;

        int code = root.value.compareTo(node.value);
        if (code > 0) {
            root.left = insertAVL(root.left, node);
        } else if (code < 0) {
            root.right = insertAVL(root.right, node);
        } else {
            return root;
        }

        return adjustTree(root);
    }

    private MyNode<T> deleteAVL(MyNode<T> root, T value) {
        if (root == null) return null;

        int code = value.compareTo(root.value);
        if (code > 0) {
            root.right = deleteAVL(root.right, value);
        } else if (code < 0) {
            root.left = deleteAVL(root.left, value);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            T maxValueInLeft = findMaxValue(root.left);
            root.value = maxValueInLeft;
            root.left = deleteAVL(root.left, maxValueInLeft);
        }

        return adjustTree(root);
    }

    private MyNode<T> adjustTree(MyNode<T> root) {
        updateHeight(root);
        int factor = getBalancedFactor(root);

        if (factor > 1) { // 左子树过高，右旋
            if (getBalancedFactor(root.left) >= 0) {
                // 左子节点的左子树高于右子树
                // 直接右旋不会导致不平衡
            } else {
                // 左子节点的右子树高于左子树
                // 直接右旋会导致右子树更高
                // 需要先左旋再右旋
                root.left = leftRotate(root.left);
            }
            return rightRotate(root);
        } else if (factor < -1) { // 右子树过高，左旋
            if (getBalancedFactor(root.right) <= 0) {
                // 右子节点的右子树高于左子树
                // 直接左旋不会导致不平衡
            } else {
                // 右子节点的左子树高于右子树
                // 直接左旋会导致左子树更高
                // 需要先右旋再左旋
                root.right = rightRotate(root.right);
            }
            return leftRotate(root);
        }

        return root;
    }

    @Override
    public void insert(T value) {
        insert(new MyNode<>(value));
    }
    @Override
    public void insert(MyNode<T> node) {
        root = insertAVL(root, node);
    }

    @Override
    public void delete(T value) {
        root = deleteAVL(root, value);
    }
}
