public class BinaryTreeDemo {
    public static void main(String args[]) {

        BinaryHero hero1 = new BinaryHero(1, "宋江", "及时雨");
        BinaryHero hero2 = new BinaryHero(2, "吴用", "智多星");
        BinaryHero hero3 = new BinaryHero(3, "卢俊义", "玉麒麟");
        BinaryHero hero4 = new BinaryHero(4, "林冲", "豹子头");

        hero1.left = hero2;
        hero1.right = hero3;
        hero3.right = hero4;

        BinaryTree binaryTree = new BinaryTree(hero1);
        //打印前序遍历顺序 1->2->3->4
//        binaryTree.preOrder();
        //打印中序遍历 2->1->3->4
//        binaryTree.midOrder();
        //打印后序遍历 2->4->3->1
//        binaryTree.postOrder();
        //前序查找
//        System.out.println(binaryTree.preFind(4));
        //中序查找
//        System.out.println(binaryTree.midFind(3));
        //后序查找
//        System.out.println(binaryTree.postFind(2));
        //添加结点
        BinaryHero hero5 = new BinaryHero(5, "关胜", "关三哥");
        hero3.left = hero5;
        //删除前
        System.out.println("删除结点前");
        binaryTree.preOrder();
        //删除后
        binaryTree.delNode(3);
        System.out.println("删除结点后");
        binaryTree.preOrder();
    }
}

/**
 * 二叉树
 */
class BinaryTree {

    //根节点
    private BinaryHero root;

    public BinaryTree(BinaryHero root) {
        this.root = root;
    }

    public BinaryHero preFind(int id) {
        return preFind(id, root);
    }

    public BinaryHero midFind(int id) {
        return midFind(id, root);
    }

    public BinaryHero postFind(int id) {
        return postFind(id, root);
    }

    public void preOrder() {
        preOrder(root);
    }

    public void midOrder() {
        midOrder(root);
    }

    public void postOrder() {
        postOrder(root);
    }

    public void delNode(int id) {
        if (root == null)
            return;
        //如果id等于根节点id，删除整棵树
        if (root.id == id)
            root = null;
        else
            delNode(id, root);
    }

    /**
     * 删除结点（如果是叶子结点，删除结点，如果是非叶子结点，则删除该子树）
     *
     * @param id
     */
    private void delNode(int id, BinaryHero hero) {
        //如果当前结点左子节点不为空并且id相等，将左子节点树删除
        if (hero.left != null && hero.left.id == id) {
            hero.left = null;
            return;
        }
        //如果当前结点右子节点不为空并且id相等，将右子节点树删除
        if (hero.right != null && hero.right.id == id) {
            hero.right = null;
            return;
        }
        //递归向左子树查找进行删除
        if (hero.left != null)
            delNode(id, hero.left);
        //递归向右子树查找进行删除
        if (hero.right != null)
            delNode(id, hero.right);
    }

    /**
     * 前序查找 中-左-右
     *
     * @param id
     * @param binaryHero
     * @return
     */
    private BinaryHero preFind(int id, BinaryHero binaryHero) {
        if (binaryHero == null)
            return null;
        //先与当前结点比较，如果id相等，直接返回当前结点
        if (binaryHero.id == id)
            return binaryHero;
        BinaryHero hero = null;
        //如果当前结点左子树不为空，递归进行查找
        if (binaryHero.left != null)
            hero = preFind(id, binaryHero.left);
        //说明在左子树找到结点，返回
        if (hero != null)
            return hero;
        //如果当前结点右子树不为空，递归进行查找
        if (binaryHero.right != null)
            hero = preFind(id, binaryHero.right);
        //说明在右子树找到结点，返回
        if (hero != null)
            return hero;
        //说明当前树没有此id结点
        return null;
    }

    /**
     * 中序查找 左-中-右
     *
     * @param id
     * @param binaryHero
     * @return
     */
    private BinaryHero midFind(int id, BinaryHero binaryHero) {
        if (binaryHero == null)
            return null;
        BinaryHero hero = null;
        //如果当前结点左子树不为空，递归进行查找
        if (binaryHero.left != null)
            hero = midFind(id, binaryHero.left);
        //说明在左子树找到结点，返回
        if (hero != null)
            return hero;
        //与当前结点比较，如果id相等，直接返回当前结点
        if (binaryHero.id == id)
            return binaryHero;
        //如果当前结点右子树不为空，递归进行查找
        if (binaryHero.right != null)
            hero = midFind(id, binaryHero.right);
        //说明在右子树找到结点，返回
        if (hero != null)
            return hero;
        //说明当前树没有此id结点
        return null;
    }

    /**
     * 中序查找 左-右-中
     *
     * @param id
     * @param binaryHero
     * @return
     */
    private BinaryHero postFind(int id, BinaryHero binaryHero) {
        if (binaryHero == null)
            return null;
        BinaryHero hero = null;
        //如果当前结点左子树不为空，递归进行查找
        if (binaryHero.left != null)
            hero = postFind(id, binaryHero.left);
        //说明在左子树找到结点，返回
        if (hero != null)
            return hero;
        //如果当前结点右子树不为空，递归进行查找
        if (binaryHero.right != null)
            hero = postFind(id, binaryHero.right);
        //说明在右子树找到结点，返回
        if (hero != null)
            return hero;
        //与当前结点比较，如果id相等，直接返回当前结点
        if (binaryHero.id == id)
            return binaryHero;
        //说明当前树没有此id结点
        return null;
    }


    /**
     * 前序遍历二叉树，中间结点->左边结点->右边结点
     * 1、先打印当前结点
     * 2、如果结点左子节点不为空，则递归继续进行前序遍历
     * 3、如果结点右子节点不为空，则递归继续进行前序遍历
     *
     * @param hero
     */
    private void preOrder(BinaryHero hero) {
        if (hero == null)
            return;
        System.out.println(hero.toString());
        if (hero.left != null)
            preOrder(hero.left);
        if (hero.right != null)
            preOrder(hero.right);
    }

    /**
     * 中序遍历二叉树，左边结点->中间结点->右边结点
     * 1、如果结点左子节点不为空，则递归继续进行前序遍历
     * 2、打印当前结点
     * 3、如果结点右子节点不为空，则递归继续进行前序遍历
     *
     * @param hero
     */
    private void midOrder(BinaryHero hero) {
        if (hero == null)
            return;
        if (hero.left != null)
            midOrder(hero.left);
        System.out.println(hero.toString());
        if (hero.right != null)
            midOrder(hero.right);
    }

    /**
     * 后序遍历二叉树，左边结点->右边结点->中间结点
     * 1、如果结点左子节点不为空，则递归继续进行前序遍历
     * 2、如果结点右子节点不为空，则递归继续进行前序遍历
     * 3、打印当前结点
     *
     * @param hero
     */
    private void postOrder(BinaryHero hero) {
        if (hero == null)
            return;
        if (hero.left != null)
            postOrder(hero.left);
        if (hero.right != null)
            postOrder(hero.right);
        System.out.println(hero.toString());
    }
}

class BinaryHero {

    int id;
    String name;
    String nickName;
    BinaryHero left;
    BinaryHero right;

    public BinaryHero(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "BinaryHero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}