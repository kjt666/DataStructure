/**
 * google上机题：有一个公司，当有新员工入职时，需要保存员工的信息（id，姓名、地址。。），并且输入员工id时，要求可以查找到其所有信息
 * 要求：不使用数据库，插入、删除、查找的速度越快越好.
 * 答案：使用哈希表（散列表）
 * 哈希表分两种结构：1、数组+链表 2、数组+二叉树
 * 本次demo使用第一种结构，链表的数量越多，查找速度越快。如有3条链表，则查询速度为1条的3倍。
 */
public class HashTableDemo {

    public static void main(String args[]) {

        HashTable hashTable = new HashTable(5);
        Emp emp1 = new Emp(1, "kjt", "beijing");
        Emp emp2 = new Emp(2, "hqm", "guangxi");
        hashTable.add(emp1);
        hashTable.add(emp2);
        hashTable.show();
        hashTable.find(1);
    }
}

class HashTable {

    private int arraySize;
    private EmpLinkedList[] empLinkedLists;

    public HashTable(int arraySize) {
        this.arraySize = arraySize;
        empLinkedLists = new EmpLinkedList[arraySize];
        //初始化数组中的链表
        for (int i = 0; i < arraySize; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }

    public void add(Emp emp) {
        //根据id求出其应该放在那条链表
        int index = hashIndex(emp.id);
        empLinkedLists[index].add(emp);
    }

    public void del(int id) {
        //根据id求出其应该在那条链表
        int index = hashIndex(id);
        empLinkedLists[index].del(id);
    }

    public void show() {
        for (int i = 0; i < arraySize; i++) {
            empLinkedLists[i].showList(i + 1);
        }
    }

    public void find(int id) {
        //根据id求出其应该在那条链表
        int index = hashIndex(id);
        empLinkedLists[index].find(id);
    }

    /**
     * 哈希算法：根据员工id算出插入那条链表
     *
     * @param id
     * @return
     */
    private int hashIndex(int id) {
        return id % arraySize;
    }
}

class EmpLinkedList {

    private Emp head = new Emp();
    private Emp next;

    /**
     * 添加雇员
     *
     * @param emp
     */
    public void add(Emp emp) {
        if (head.next == null)
            head.next = emp;
        else {
            Emp temp = head.next;
            //找到最后一个结点
            while (temp.next != null) {
                temp = temp.next;
            }
            //将emp插入到最后一个结点后边
            temp.next = emp;
        }
    }

    /**
     * 删除雇员
     *
     * @param id
     */
    public void del(int id) {
        if (head.next == null)
            return;
        Emp temp = head;
        while (true) {
            if (temp.next == null) {
                System.out.println("没有此员工");
                break;
            }
            if (temp.next.id == id) {
                temp.next = temp.next.next;
                break;
            }
        }
    }

    /**
     * 遍历打印当前链表
     */
    public void showList(int i) {
        if (head.next == null) {
            System.out.println("第" + i + "条链表为空");
            return;
        }
        System.out.println("第" + i + "条链表内容为：");
        Emp temp = head.next;
        while (temp != null) {
            System.out.println(temp.toString());
            temp = temp.next;
        }
    }

    /**
     * 查询指定id员工信息
     *
     * @param id
     */
    public void find(int id) {
        if (head.next == null) {
            System.out.println("当前链表为空");
            return;
        }
        Emp temp = head.next;
        while (temp != null) {
            if (temp.id == id) {
                System.out.println("id为" + id + "的员工信息为：" + temp.toString());
                break;
            }
            temp = temp.next;
        }
    }

}

/**
 * 雇员类
 */
class Emp {

    int id;
    String name;
    String address;
    Emp next;

    public Emp() {
    }

    public Emp(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}