import java.util.ArrayList;
import java.util.Collections;

public class HuffmanTreeDemo {

    public static void main(String args[]) {

        //测试生成霍夫曼树
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTree huffmanTree = new HuffmanTree();
        Node node = huffmanTree.createHuffmanTree(arr);
        //67-29-38-15-7-23-10-4-1-3-6-13
        huffmanTree.preOrder(node);
    }

}

/**
 * 霍夫曼树
 */
class HuffmanTree {

    /**
     * 根据数组创建一颗霍夫曼树
     * 1.将数组进行排序，数组中的每个数据都是一个结点，每个结点都是一颗二叉树（初识时为最简单的二叉树，左右子结点为空）
     * 2.取出根结点权值最小的两颗二叉树
     * 3.将这两颗二叉树组合成一颗新的二叉树，新二叉树根结点的权值即为两颗二叉树根结点权值的和
     * 4.将新二叉树根结点的权值与数组中剩余结点的权值进行排序，重复以上步骤，直至所有结点被处理，就得到一颗霍夫曼树
     *
     * @param arr
     */
    public Node createHuffmanTree(int[] arr) {

        if (arr == null || arr.length == 0)
            return null;
        //首先将数组中的数据加工成结点，放入一个新的集合
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i : arr) {
            nodes.add(new Node(i));
        }
        //当集合只剩一个结点时，即所有结点已被处理完成
        while (nodes.size() > 1) {
            //排序
            Collections.sort(nodes);
            //取出权值最小的两棵树组合成新树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node newNode = new Node(leftNode.value + rightNode.value);
            newNode.left = leftNode;
            newNode.right = rightNode;
            //移除上面两颗子树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新树加入集合
            nodes.add(newNode);
        }
        return nodes.get(0);
    }

    /**
     * 前序遍历打印二叉树结点
     *
     * @param node
     */
    public void preOrder(Node node) {
        if (node == null)
            return;
        System.out.println(node.toString());
        if (node.left != null)
            preOrder(node.left);
        if (node.right != null)
            preOrder(node.right);
    }

}

class Node implements Comparable<Node> {

    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HuffmanCode.Node{" +
                "value=" + value +
                '}';
    }

    /**
     * 根据value从小到大排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}