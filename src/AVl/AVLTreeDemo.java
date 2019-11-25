package AVl;

public class AVLTreeDemo {

    public static void main(String args[]) {

        int arr[] = {4, 3, 6, 5, 7, 8};
        int arr2[] = {10, 12, 8, 9, 7, 6};
        int arr3[] = {10, 11, 7, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        for (int i : arr3) {
            avlTree.add(new Node(i));
        }
        avlTree.midOrder();
        //arr
       /* System.out.println("没有平衡处理前树的高度为：" + avlTree.root.height());//4
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//1
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//3*/
       /* System.out.println("左旋转平衡处理后树的高度为：" + avlTree.root.height());//3
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//2
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//2*/
        //arr2
         /* System.out.println("没有平衡处理前树的高度为：" + avlTree.root.height());//4
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//3
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//1*/
       /* System.out.println("右旋转平衡处理后树的高度为：" + avlTree.root.height());//3
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//2
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//2*/
        //arr3
         /* System.out.println("没有平衡处理前树的高度为：" + avlTree.root.height());//4
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//1
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//3*/
        System.out.println("双旋转平衡处理后树的高度为：" + avlTree.root.height());//3
        System.out.println("树的左子树高度为：" + avlTree.root.leftHeight());//2
        System.out.println("树的右子树高度为：" + avlTree.root.rightHeight());//2
    }
}

/**
 * 平衡二叉树
 */
class AVLTree {
    Node root;

    /**
     * 左旋转操作，当树的右子树高度 - 左子树高度大于1时进行的平衡操作
     *
     * @param node
     */
    private void leftRotate(Node node) {

        //1、创建一个新的结点newNode，值等于当前根结点的值
        Node newNode = new Node(node.value);
        //2、把新结点的左子树设置成当前结点的左子树
        newNode.left = node.left;
        //3、把新结点的右子树设置成当前结点右子树的左子树
        newNode.right = node.right.left;
        //4、把当前结点的值换成当前结点右子结点的值
        node.value = node.right.value;
        //5、把当前结点的右子树设置成当前结点右子树的右子树
        node.right = node.right.right;
        //6、把当前结点的左子树设置成新结点
        node.left = newNode;
    }

    /**
     * 右旋转操作，当树的左子树高度 - 右子树高度大于1时进行的平衡操作
     *
     * @param node
     */
    private void rightRotate(Node node) {
        //1、创建一个新的结点newNode，值等于当前根结点的值
        Node newNode = new Node(node.value);
        //2、把新结点的右子树设置成当前结点的右子树
        newNode.right = node.right;
        //3、把新结点的左子树设置成当前结点左子树的右子树
        newNode.left = node.left.right;
        //4、把当前结点的值换成当前结点左子结点的值
        node.value = node.left.value;
        //5、把当前结点的左子树设置成当前结点左子树的左子树
        node.left = node.left.left;
        //6、把当前结点的右子树设置成新结点
        node.right = newNode;
    }

    public void add(Node node) {
        if (root == null) {
            root = node;
            return;
        }
        add(root, node);
    }

    /**
     * 添加结点
     * 把添加的结点与非叶子结点进行比较，小于非叶子结点时放在它的左子结点，大于时放在右子结点。
     *
     * @param node
     */
    private void add(Node preNode, Node node) {
        if (root == null) {
            root = node;
            return;
        }
        if (preNode.value > node.value) {
            if (preNode.left == null)
                preNode.left = node;
            else
                add(preNode.left, node);
        } else {
            if (preNode.right == null)
                preNode.right = node;
            else
                add(preNode.right, node);
        }
        //当添加完一个结点后，如果右子树的高度 - 左子树高度 > 1，进行左旋转
        if (root.rightHeight() - root.leftHeight() > 1) {
            //对于当前结点的右子树，如果它的左子树高度大于右子树，先针对右子树进行右旋转
            if (root.right.leftHeight() > root.right.rightHeight())
                rightRotate(root.right);
            leftRotate(root);
        }
        //当添加完一个结点后，如果左子树的高度 - 右子树高度 > 1，进行右旋转
        else if (root.leftHeight() - root.rightHeight() > 1) {
            //对于当前结点的左子树，如果它的右子树高度大于左子树，先针对左子树进行左旋转
            if (root.left.rightHeight() > root.left.leftHeight())
                leftRotate(root.left);
            rightRotate(root);
        }
    }

    /**
     * 删除结点
     * 删除的结点有三种类型，每种有不同的删除策略：
     * 1、叶子结点(找到它和它的父结点，判断它是父结点的左还是右子结点)
     * 2、有一颗子树的非叶子结点(判断target的子节点是左是右，再判断target是parent的左还是右结点，例如target含有左子结点，其为parent的右子结点，则有parent.right = target.left)
     * 3、有两颗子树的非叶子结点(找到target右子树的最小值结点（或者找左子树的最大值结点），用temp记录最小值，然后删除此结点，再讲temp赋值给target)
     *
     * @param value
     */
    public void del(int value) {

        Node target = searchTarget(value);
        Node parent = searchParentNode(value);

        if (target == null)
            return;
        //删除叶子结点
        if (target.left == null && target.right == null) {
            if (parent.left != null && parent.left == target)
                parent.left = null;
            else if (parent.right != null && parent.right == target)
                parent.right = null;
        } //删除有两颗子树的非叶子结点
        else if (target.left != null && target.right != null) {
            //删除target右子树的最小结点并返回此结点的值
            int val = delMinNodeForTree(target.right);
            target.value = val;
        } //删除有一颗子树的非叶子结点
        else {
            //注意：删除只有一颗子树的非叶子结点时要考虑parent为空的情况，比如二叉树只有7和3这两个结点，当删除7时，也就是删除根结点，根结点没有父节点
            if (parent == null)
                if (target.left != null)
                    root = target.left;
                else
                    root = target.right;
            else {
                //判断target的子节点是左是右
                if (target.left != null) {
                    //判断target是parent的左还是右结点
                    if (parent.left == target)
                        parent.left = target.left;
                    else
                        parent.right = target.left;
                } else {
                    if (parent.left == target)
                        parent.left = target.right;
                    else
                        parent.right = target.right;
                }
            }
        }
    }

    /**
     * 删除以node为跟结点的二叉树中的最小值结点
     *
     * @param node 二叉树根结点
     * @return 最小值结点的值
     */
    private int delMinNodeForTree(Node node) {
        Node temp = node;
        while (temp.left != null) {
            temp = temp.left;
        }
        del(temp.value);
        return temp.value;
    }

    public Node searchTarget(int value) {
        if (root == null)
            return null;
        return searchTarget(root, value);
    }

    /**
     * 按value搜索结点
     *
     * @param value
     * @return
     */
    private Node searchTarget(Node node, int value) {
        if (node.value == value)
            return node;
        else if (value < node.value) {
            //递归种植条件，查询不到返回null
            if (node.left == null)
                return null;
            return searchTarget(node.left, value);
        } else {
            //递归种植条件，查询不到返回null
            if (node.right == null)
                return null;
            return searchTarget(node.right, value);
        }
    }

    public Node searchParentNode(int value) {
        if (root == null)
            return null;
        return searchParentNode(root, value);
    }

    /**
     * 搜索value对应结点的父结点
     *
     * @param node
     * @param value
     * @return
     */
    private Node searchParentNode(Node node, int value) {
        if ((node.left != null && node.left.value == value) ||
                (node.right != null && node.right.value == value))
            return node;
        else if (value < node.value) {
            if (node.left == null)
                return null;
            return searchParentNode(node.left, value);
        } else {
            if (node.right == null)
                return null;
            return searchParentNode(node.right, value);
        }
    }

    public void midOrder() {
        if (root == null)
            return;
        midOrder(root);
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    private void midOrder(Node node) {
        if (node == null)
            return;
        midOrder(node.left);
        System.out.println(node.toString());
        midOrder(node.right);
    }
}

class Node {

    Node left;
    Node right;
    int value;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 当前结点的左子树高度
     *
     * @return
     */
    public int leftHeight() {
        return left == null ? 0 : left.height();
    }

    /**
     * 当前结点的右子树高度
     *
     * @return
     */
    public int rightHeight() {
        return right == null ? 0 : right.height();
    }

    /**
     * 获取以当前结点为根结点的树的高度
     *
     * @return
     */
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}