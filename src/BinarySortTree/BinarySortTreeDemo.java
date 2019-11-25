package BinarySortTree;

public class BinarySortTreeDemo {
    public static void main(String args[]) {
        BinarySortTree binarySortTree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int i : arr) {
            binarySortTree.add(new Node(i));
        }
        binarySortTree.midOrder();
       /* //测试删除结点所需方法是否可用
        Node target = binarySortTree.searchTarget(1);
        System.out.println(target.toString());
        Node parentNode = binarySortTree.searchParentNode(3);
        System.out.println(parentNode.toString());//7*/
        binarySortTree.del(7);
        System.out.println("删除结点后");
        binarySortTree.midOrder();
    }
}

/**
 * 二叉排序树
 */
class BinarySortTree {

    Node root;

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

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}