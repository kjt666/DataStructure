public class ClueBinaryTreeDemo {

    public static void main(String args[]) {


        ClueHeroNode root = new ClueHeroNode(1, "宋江");
        ClueHeroNode hero2 = new ClueHeroNode(2, "卢俊义");
        ClueHeroNode hero3 = new ClueHeroNode(3, "吴用");
        ClueHeroNode hero4 = new ClueHeroNode(4, "公孙胜");
        ClueHeroNode hero5 = new ClueHeroNode(5, "关胜");
        ClueHeroNode hero6 = new ClueHeroNode(6, "林冲");

        root.left = hero2;
        root.right = hero3;
        hero2.left = hero4;
        hero2.right = hero5;
        hero3.left = hero6;

        ClueBinaryTree clueBinaryTree = new ClueBinaryTree(root);
        clueBinaryTree.midClue();
        //中序遍历顺序为4-2-5-1-6-3
        //中序线索化后的,5的前驱应为2，后继应为1
        System.out.println("5的前驱结点为：" + hero5.left.toString());
        System.out.println("5的后继结点为：" + hero5.right.toString());
        //遍历线索化二叉树
        System.out.println("遍历线索化二叉树");
        clueBinaryTree.midOrder();
    }
}

/**
 * 线索化二叉树(中序)
 * 对于二叉树，它的叶子结点左右结点指针通常为空，一颗有n个结点的二叉树，就有n+1个空指针
 */
class ClueBinaryTree {

    private ClueHeroNode head;

    //线索化二叉树需要使用当前结点的前驱结点
    private ClueHeroNode pre = null;

    public ClueBinaryTree(ClueHeroNode head) {
        this.head = head;
    }

    public void midClue() {

        midClue(head);
    }

    /**
     * 中序遍历线索化二叉树
     * 因为二叉树进行了线索化，因此递归遍历不再适用
     */
    public void midOrder() {
        ClueHeroNode temp = head;
        while (temp != null) {

            //循环找到leftypeT= 1的结点,第一个找到的就是开始结点
            //后面随着遍历而变化，当leftType=1时说明该结点是按线索化处理后的有效结点
            while (temp.leftType != 1)
                temp = temp.left;

            //输出当前结点
            System.out.println(temp.toString());

            //如果当前结点的右指针类型是后继结点，就一直输出
            while (temp.rightType == 1) {
                temp = temp.right;
                System.out.println(temp.toString());
            }
            //替换遍历结点
            temp = temp.right;
        }
    }

    /**
     * 中序线索化
     *
     * @param node
     */
    private void midClue(ClueHeroNode node) {
        if (node == null)
            return;
        //左
        midClue(node.left);
        //中
        //处理当前结点的前驱结点
        if (node.left == null) {
            //当前结点的左指针指向前驱结点
            node.left = pre;
            //标记当前结点的左作针类型
            node.leftType = 1;
        }
        //当前结点的后继结点,需要在它下一个结点的线索化中赋值
        if (pre != null && pre.right == null) {
            pre.right = node;
            pre.rightType = 1;
        }
        //将pre设为下一个结点的前驱结点
        pre = node;
        //右
        midClue(node.right);
    }
}

class ClueHeroNode {

    int id;
    String name;
    ClueHeroNode left;
    ClueHeroNode right;
    //左指针类型 0表示指向左子树，1表示指向前驱结点
    int leftType;
    //右指针类型 0表示指向右子树，1表示指向后继结点
    int rightType;

    public ClueHeroNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BinaryHero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}