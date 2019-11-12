public class QueueDemo {

    public static void main(String args[]) {
        Queue queue = new Queue(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.show();
        /*System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());*/
    }

}

/*
 * 一次性队列
 * 数组模拟队列，这个队列可设置大小，数据的出队、入队.
 * 因为队列遵循先进先出的原则，对此，需要创建指向队列顶端和尾端的指针。
 * 当数据入队时，先将尾指针加1，然后把数据置入尾指针指向的位置，
 * 当数据出队时，将顶指针指向位置的数据返回，并将顶指针加1。
 * 顶指针、尾指针初始指向-1.
 * */
class Queue {

    private int[] myQueue;
    private int maxSize;
    //顶指针
    private int front = -1;
    //尾指针
    private int rear = -1;

    public Queue(int maxSize) {
        maxSize = maxSize;
        myQueue = new int[maxSize];
    }

    /**
     * 入队操作,将数据插入尾指针的位置
     *
     * @param value
     */
    public void add(int value) {

        if (rear >= myQueue.length - 1) {
            System.out.println("队列已满~");
            return;
        }
        rear++;
        myQueue[rear] = value;
    }

    /**
     * 出队操作，将顶指针位置的数据返回
     *
     * @return
     */
    public int get() {

        if (rear ==  front ) {
            throw new RuntimeException("队列为空~");
        }
        if (front < rear) {
            front++;
            return myQueue[front];
        }
        return -1;
    }

    public void show() {
        for (int i = 0; i < maxSize; i++) {
            System.out.println(myQueue[i]);
        }
    }
}
