import java.util.Stack;

public class SingleLinkedListDemo {

    public static void main(String args[]) {

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "吴用", "智多星");
        HeroNode node3 = new HeroNode(3, "卢俊义", "玉麒麟");
        HeroNode node4 = new HeroNode(4, "林冲", "豹子头");

        singleLinkedList.insert(node1);
        singleLinkedList.insert(node2);
        //测试是否按顺序插入
        singleLinkedList.insert(node4);
        singleLinkedList.insert(node3);
        singleLinkedList.showList();
        //修改结点内容
        HeroNode modifyNode = new HeroNode(1, "宋江", "带头大哥");
        singleLinkedList.modify(modifyNode);
        System.out.println("修改后");
        singleLinkedList.showList();
        //删除结点
        singleLinkedList.delete(4);
        System.out.println("删除后");
        singleLinkedList.showList();
        System.out.println("链表结点个数为：" + singleLinkedList.getLength());
        System.out.println("倒数第一个结点为：" + singleLinkedList.getLastIndexNode(1));
        //倒序单链表
//        System.out.println("倒序后的单链表");
//        singleLinkedList.reverse();
//        singleLinkedList.showList();
        //倒序打印单链表
        System.out.println("倒序打印单链表");
        singleLinkedList.reverseShowList();
    }
}

/**
 * 带头结点的顺序单链表,头结点没有数据，只有指针。
 * 实现插入、修改、删除和遍历结点的功能。
 */
class SingleLinkedList {

    private HeroNode head = new HeroNode();

    /**
     * 遍历单链表，输出结点内容
     */
    public void showList() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        while (temp != null) {
            System.out.println(temp.toString());
            temp = temp.next;
        }
    }

    /**
     * 修改结点
     */
    public void modify(HeroNode heroNode) {
        if (head.next == null || heroNode == null)
            return;
        HeroNode temp = head.next;
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
    public void insert(HeroNode heroNode) {

        HeroNode temp = head;
        while (true) {
            //链表为空直接插入头结点后边或者如果遍历完还没有找到位置，直接将结点插入到最后
            if (temp.next == null) {
                temp.next = heroNode;
                break;
            }
            if (temp.next.id > heroNode.id) {
                heroNode.next = temp.next;
                temp.next = heroNode;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 删除指定id结点
     * 思路，找到删除结点的上一个结点，将上一个结点的next指向被删除结点的next
     *
     * @param id
     */
    public void delete(int id) {
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        HeroNode temp = head;
        while (true) {
            //遍历完没有找到说明无此结点，退出循环
            if (temp.next == null) {
                System.out.println("没有此结点~");
                break;
            }
            if (temp.next.id == id) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 获取单链表结点个数（不包括头结点）
     */
    public int getLength() {
        int count = 0;
        HeroNode temp = head.next;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    /**
     * 获取倒数第index个结点
     * 思路：因为是单链表，不可能逆序查找，所以
     * 1、获取单链表长度
     * 2、遍历长度-index，获取结点
     *
     * @param index
     * @return
     */
    public HeroNode getLastIndexNode(int index) {
        int count = getLength();
        HeroNode node = null;
        if (count != 0 && index > 0 && index <= count) {
            node = head.next;
            for (int i = 0; i < count - index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    /**
     * 倒序单链表
     * 思路：1、创建一个新的头结点
     * 2、遍历单链表，每遍历一个结点，就将结点插入新头结点后边
     */
    public void reverse() {
        if (head.next == null)
            return;
        HeroNode newHead = new HeroNode();
        HeroNode temp = head.next;
        HeroNode next;
        while (temp != null) {
            //单独保存next，否者直接temp.next = newHead.next会在开始就导致temp.next = null，结果单链表只有一个元素。
            next = temp.next;
            temp.next = newHead.next;
            newHead.next = temp;
            temp = next;
        }
        head = newHead;
    }

    /**
     * 倒序打印单链表，要求不能改单链表顺序
     * 方法一：上面的倒序方法，从新生成一个单链表，再遍历打印新单链表
     * 方法二：遍历单链表，将结点入栈，因为栈是后进先出，所以便可以倒叙打印
     */
    public void reverseShowList() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //方法二
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = head.next;
        //遍历将结点入栈
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        //依次出栈打印
        while (!stack.empty())
            System.out.println(stack.pop().toString());
    }
}

/**
 * 单链表结点，一个结点包含数据域和指针域
 */
class HeroNode {
    //数据域
    int id;
    String name;
    String nickName;
    //指针域
    HeroNode next;

    public HeroNode() {
    }

    public HeroNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}