public class SingleCircleLinkedListDemo {

    public static void main(String args[]) {
        //2->4->1->5->3
        SingleCircleLinkedList linkedList = new SingleCircleLinkedList(10);
        linkedList.josephu(3, 5);
    }

}

/**
 * 利用单向循环链表解决约瑟夫环问题
 * 约瑟夫问题：设编号1，2。。。n的人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，
 * 数到m的那个人出列，他的下一位又从1开始报数，数到m的人又出列，以此类推，直至所有人出列，求出列编号的顺序。
 */
class SingleCircleLinkedList {

    //第一个结点，不再只是一个指针
    Child head;
    int size;

    /**
     * 创建一个含有size个数的环形链表
     *
     * @param size
     */
    public SingleCircleLinkedList(int size) {
        this.size = size;
        Child child;
        for (int i = 1; i <= size; i++) {
            child = new Child(i);
            insert(child);
        }
    }

    /**
     * 打印约瑟夫问题出队顺序
     *
     * @param start
     * @param num
     */
    public void josephu(int start, int num) {
        if (head == null || start < 1 || start > size)
            return;

        Child temp = head;
        int count = 1;
        //先找到编号为start的结点
        while (true) {
            if (temp.id == start) {
                break;
            }
            temp = temp.next;
        }
        //从start开始遍历输出数到num的人，直到所有人输出完成
        while (size != 0) {
            if (count % num == 0) {
                System.out.println(temp.id);
                delete(temp.id);
                size--;
            }
            count++;
            temp = temp.next;
        }
    }

    /**
     * 插入结点
     *
     * @param child
     */
    public void insert(Child child) {
        if (head == null) {
            head = child;
            head.next = head;
        } else {
            Child temp = head;
            while (true) {
                if (temp.next == head) {
                    temp.next = child;
                    child.next = head;
                    break;
                }
                temp = temp.next;
            }
        }
    }

    /**
     * 删除指定id结点
     *
     * @param id
     */
    public void delete(int id) {
        if (head == null)
            return;
        Child temp = head;
        //从head的下一个结点开始比较，因为是环形，head会在最后被对比
        while (true) {
            if (temp.next.id == id) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

}

class Child {

    int id;
    Child next;

    public Child(int id) {
        this.id = id;
    }
}