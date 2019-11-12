public class OrderBinaryTreeDemo {
    public static void main(String args[]) {
        int[] orderArr = new int[10];
        for (int i = 0; i < orderArr.length; i++) {
            orderArr[i] = i;
        }
        OrderBinaryTree orderBinaryTree = new OrderBinaryTree(orderArr);
        //前序遍历
        System.out.println("顺序存储二叉树前序遍历");
        orderBinaryTree.preOrder();
        //中序遍历
        System.out.println("\n顺序存储二叉树中序遍历");
        orderBinaryTree.midOrder();
        //后序遍历
        System.out.println("\n顺序存储二叉树后序遍历");
        orderBinaryTree.postOrder();
    }
}

/**
 * 顺序存储二叉树(用于堆排序)
 * 顺序存储二叉树只可用于完全二叉树，其是用一个数组表示顺序二叉树。
 * 对于某个结点，假如它对应数组的位置的下标为n，那么它的左子节点的下标即2n+1，右子结点的下标为2n+2，它的父结点下标为（n-1)/2。
 * 如，根结点在数组中的第0位，那么根节点的左子结点的位置为数组中的第1位，右子节点为数组中的第2位
 */
class OrderBinaryTree {

    private int[] orderArr;

    public OrderBinaryTree(int[] orderArr) {
        this.orderArr = orderArr;
    }

    public void preOrder() {
        preOrder(0);
    }

    public void midOrder() {
        midOrder(0);
    }

    public void postOrder() {
        postOrder(0);
    }

    /**
     * 顺序存储二叉树的前序遍历
     *
     * @param no 数组下标
     */
    private void preOrder(int no) {
        if (orderArr == null || orderArr.length == 0)
            return;
        System.out.print(orderArr[no] + " ");
        if (no * 2 + 1 < orderArr.length)
            preOrder(no * 2 + 1);
        if (no * 2 + 2 < orderArr.length)
            preOrder(no * 2 + 2);
    }

    /**
     * 顺序存储二叉树的中序遍历
     *
     * @param no 数组下标
     */
    private void midOrder(int no) {
        if (orderArr == null || orderArr.length == 0)
            return;
        if (no * 2 + 1 < orderArr.length)
            midOrder(no * 2 + 1);
        System.out.print(orderArr[no] + " ");
        if (no * 2 + 2 < orderArr.length)
            midOrder(no * 2 + 2);
    }

    /**
     * 顺序存储二叉树的后序遍历
     *
     * @param no 数组下标
     */
    private void postOrder(int no) {
        if (orderArr == null || orderArr.length == 0)
            return;
        if (no * 2 + 1 < orderArr.length)
            postOrder(no * 2 + 1);
        if (no * 2 + 2 < orderArr.length)
            postOrder(no * 2 + 2);
        System.out.print(orderArr[no] + " ");
    }

}