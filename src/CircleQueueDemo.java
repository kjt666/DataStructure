import javafx.beans.binding.When;

import java.util.Scanner;

public class CircleQueueDemo {

    public static void main(String args[]) {
        //最大有效数为3，因为空出一个给rear做约定使用
        CircleQueue queue = new CircleQueue(4);
        char key;
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(显示队列)");
            System.out.println("a(添加数据)");
            System.out.println("g(取出数据)");
            System.out.println("e(退出程序)");
            System.out.println("h(查看队列头)");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.show();
                    break;
                case 'a':
                    System.out.println("请输入数据");
                    int value = scanner.nextInt();
                    queue.add(value);
                    break;
                case 'g':
                    int i = queue.get();
                    if (i == -1)
                        System.out.println("队列为空~");
                    else
                        System.out.println(i);
                    break;
                case 'e':
                    loop = false;
                    break;
                case 'h':
                    queue.showHead();
                    break;
            }
        }

    }

}

/*
 * 因为Queue是一次性的，所以这次写一个可以重复使用的环形队列。
 * 因为采用取模的方式确定指针的位置，所以顶尾指针的初始位置从0开始。front指向队列的第一个元素，rear指向最后一个元素的后一个位置，空出一个位置做约定。
 * 入队：队列不为满时就插入尾指针取模后的位置，再将尾指针后移加1。
 * 出队：队列不为空时就取出头指针取模后位置的数据，再将头指针后移加1。
 * 判断满的条件：(rear + 1) % maxSize = front
 * 判断空的条件：rear == front
 * 判断队列有效数据的条件：（rear + maxSize - front）% maxSize
 * */
class CircleQueue {

    private int[] myQueue;
    //顶指针
    private int front;
    //尾指针
    private int rear;
    //数组的最大容量，对它取模
    private int maxSize;

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        myQueue = new int[maxSize];
    }

    public void add(int value) {
        if (mor(rear + 1) == front) {
            System.out.println("队列已满~");
            return;
        }
        //直接将数据加入
        myQueue[rear] = value;
        //考虑到数组越界，必须对rear做取模处理
        rear = mor(rear + 1);
    }

    public int get() {
        if (front == rear) {
            return -1;
        }
        int value = myQueue[mor(front)];
        front = mor(front + 1);
        return value;
    }

    private int mor(int index) {
        return index % maxSize;
    }

    public void show() {
        if (rear == front) {
            System.out.println("队列为空~");
        }
        //当前队列的有效个数
        int count = (rear + maxSize - front) % maxSize;
        for (int i = front; i < front + count; i++) {
            System.out.printf("arr[%d]=%d", mor(i), myQueue[mor(i)]);
            System.out.println();
        }
    }

    public void showHead() {
        if (rear == front) {
            System.out.println("队列为空~");
        }
        System.out.println(myQueue[front]);
    }
}