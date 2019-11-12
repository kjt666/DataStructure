public class DoubleLinkedListDemo {

    public static void main(String args[]) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        DoubleHeroNode node1 = new DoubleHeroNode(1, "宋江", "及时雨");
        DoubleHeroNode node2 = new DoubleHeroNode(2, "吴用", "智多星");
        DoubleHeroNode node3 = new DoubleHeroNode(3, "卢俊义", "玉麒麟");
        DoubleHeroNode node4 = new DoubleHeroNode(4, "林冲", "豹子头");

        doubleLinkedList.insert(node1);
        doubleLinkedList.insert(node2);
        //测试是否按顺序插入
        doubleLinkedList.insert(node4);
        doubleLinkedList.insert(node3);
        doubleLinkedList.showList();
        //修改结点内容
        DoubleHeroNode modifyNode = new DoubleHeroNode(1, "宋江", "带头大哥");
        doubleLinkedList.modify(modifyNode);
        System.out.println("修改后");
        doubleLinkedList.showList();
        //删除结点
        doubleLinkedList.delete(4);
        System.out.println("删除后");
        doubleLinkedList.showList();
    }
}

/**
 * 单链表和双链表的区别
 * 单链表只能向一个方向查找，而双链表向前向后后可以
 * 双链表可以进行结点自删除，而单链表需要找到被删除结点的前一个才可以
 */
class DoubleLinkedList {

    DoubleHeroNode head = new DoubleHeroNode();

    /**
     * 遍历双链表，输出结点内容
     */
    public void showList() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        DoubleHeroNode temp = head.next;
        while (temp != null) {
            System.out.println(temp.toString());
            temp = temp.next;
        }
    }

    /**
     * 修改结点
     */
    public void modify(DoubleHeroNode heroNode) {
        if (head.next == null || heroNode == null)
            return;
        DoubleHeroNode temp = head.next;
        while (temp != null) {
            if (temp.id == heroNode.id) {
                temp.name = heroNode.name;
                temp.nickName = heroNode.nickName;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 按顺序插入结点。
     * 思路：如果链表为空的话，直接插到头结点后边。否则找到插入结点位置的上一个结点，将插入结点的next指向上一个结点的next，再将上一个结点的next指向插入结点。
     */
    public void insert(DoubleHeroNode heroNode) {

        DoubleHeroNode temp = head;
        while (true) {
            //链表为空直接插入头结点后边或者如果遍历完还没有找到位置，直接将结点插入到最后
            if (temp.next == null) {
                temp.next = heroNode;
                heroNode.pre = temp;
                break;
            }
            //将结点插入temp后面
            if (temp.next.id > heroNode.id) {
                heroNode.next = temp.next;
                temp.next.pre = heroNode;
                heroNode.pre = temp;
                temp.next = heroNode;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 删除指定id结点
     * 双链表支持结点自删除，所以直接找到删除结点即可
     *
     * @param id
     */
    public void delete(int id) {
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        DoubleHeroNode temp = head.next;
        while (true) {
            //遍历完没有找到说明无此结点，退出循环
            if (temp == null) {
                System.out.println("没有此结点~");
                break;
            }
            if (temp.id == id) {
                temp.pre.next = temp.next;
                //注意最后一个结点的next为空
                if (temp.next != null)
                    temp.next.pre = temp.pre;
                break;
            }
            temp = temp.next;
        }
    }

}

/**
 * 双链表结点，一个结点包含数据域和指针域
 */
class DoubleHeroNode {
    //数据域
    int id;
    String name;
    String nickName;
    //指针域
    DoubleHeroNode next;
    DoubleHeroNode pre;

    public DoubleHeroNode() {
    }

    public DoubleHeroNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "DoubleHeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}