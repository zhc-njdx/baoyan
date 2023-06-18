package utilities.ds.tree;

/**
 * 自定义的自平衡二叉搜索树
 * @param <T> 可比较的泛形参数
 */
public class MyBalancedBinarySearchTree<T extends Comparable<T>> extends MyBinarySearchTree<T>{
    public MyBalancedBinarySearchTree(T value) {
        super(value);
    }

    /**
     * 得到某节点的高度
     * @param node 节点
     * @return 高度: 叶子节点到该节点的最长路径的节点个数
     */
    private int getHeight(MyNode<T> node) {
        if (node == null) return 0;
        return node.height;
    }

    /**
     * 得到一个节点的平衡因子
     * @param node 节点
     * @return 平衡因子
     */
    private int getBalancedFactor(MyNode<T> node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 更新一个节点的高度
     * @param node 节点
     */
    private void updateHeight(MyNode<T> node) {
        if (node == null) return;
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * 左旋
     * @param node 需要左旋的树的根节点
     * @return 左旋后的树的根节点
     */
    private MyNode<T> leftRotate(MyNode<T> node) {
        MyNode<T> newNode = node.right;
        node.right = newNode.left;
        newNode.left = node;
        updateHeight(node);
        updateHeight(newNode);
        return newNode;
    }

    /**
     * 右旋
     * @param node 需要右旋的树的根节点
     * @return 右旋后的树的根节点
     */
    private MyNode<T> rightRotate(MyNode<T> node) {
        MyNode<T> newNode = node.left;
        node.left = newNode.right;
        newNode.right = node;
        updateHeight(node);
        updateHeight(newNode);
        return newNode;
    }

    /**
     * 向root为根节点的树中插入一个AVL节点
     * @param root 根节点
     * @param node 待插入的AVL节点
     * @return 插入后树的根节点
     */
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

    /**
     * 在root为根节点的树里删除一个AVL节点
     * @param root 根节点
     * @param value 待删除的AVL节点
     * @return 删除后树的根节点
     */
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

    /**
     * 当树不平衡时，通过左旋或者右旋来调整该书树
     * @param root 根节点
     * @return 调整后的根节点
     */
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

    /**
     * AVL树插入新节点
     * @param value 节点的value
     */
    @Override
    public void insert(T value) {
        insert(new MyNode<>(value));
    }

    /**
     * AVL树插入节点
     * @param node 待插入的节点
     */
    @Override
    public void insert(MyNode<T> node) {
        root = insertAVL(root, node);
    }

    /**
     * AVL树删除节点
     * @param value value
     */
    @Override
    public void delete(T value) {
        root = deleteAVL(root, value);
    }
}
